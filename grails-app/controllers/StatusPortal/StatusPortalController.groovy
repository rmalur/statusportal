package StatusPortal

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

//import java.text.DateFormat

import java.text.SimpleDateFormat
import java.util.regex.Pattern

import org.springframework.web.bind.ServletRequestUtils.FloatParser;

import SecureApp.User

class StatusPortalController {

	def springSecurityService
	def exportService
	def grailsApplication  //inject GrailsApplication
	
	@Secured('IS_AUTHENTICATED_FULLY')
	def index() {
 // render( view: 'todaysTickets')
	}
	
	//loading all user list as assignee
		//loading all user list as assignee
	@Secured('IS_AUTHENTICATED_FULLY')
	def assigneeList(){
		def data = JSON.parse(request)
		def assigneeList = []
		def q
		try{
		if(data.projectId){
			q = UserProjectMapping.findAllWhere(project_id:data.projectId)
		}else{

			def user = User.get(springSecurityService.principal.id )
			def project = UserProjectMapping.findWhere(employee_id:user.employeeId)
			q = UserProjectMapping.findAllWhere(project_id:project.project_id )

		}
		}catch(Exception e){
			log.error(e.message)
		}
		
		for(employee in q){
			def user = User.findWhere(employeeId:employee.employee_id )
			assigneeList.add(user.username)

		}
		render assigneeList as JSON
	}
	
	@Secured('IS_AUTHENTICATED_FULLY')
	def fetchReasonList(){
			def reasonList=Reason.all
			
			render reasonList as JSON
		}
	
		//for getting the all the ticketIds respective to user id to autopopulate on page
	@Secured('IS_AUTHENTICATED_FULLY')
	def ticketIds(){
		def project
		def ticket
		def data = JSON.parse(request)
		//def user = User.get(springSecurityService.principal.id)
		def ticketIds = []
		if(data.projectId){
			project = ProjectInfo.findWhere(project_id:data.projectId)
		}

		def results  =  StatusUpdate.withCriteria {
		
			//ne("updatedStatus","Closed")
			projections { distinct("ticket") }
		}
		
		for (var in results) {

			if(project){
				ticket = TicketSummary.findWhere(ticket_id:var.ticket_id,project:project)
			}else {
				ticket = TicketSummary.findWhere(ticket_id:var.ticket_id)
			}
			ticketIds.add(var.ticket_id)
			/*if(ticket){
				def ticketInfo = StatusUpdate.findWhere(ticket:ticket)
				if(ticketInfo){
						continue
				}else{

					ticketIds.add(var.ticket_id)
				}
			}else{
				continue
			}*/
		}
		render ticketIds as JSON
	}

	//for getting ticket info respective with ticketId
	@Secured('IS_AUTHENTICATED_FULLY')
	def getTicketInfo(){
		
		//def user = User.get(springSecurityService.principal.id)
		def result = []
		def ticketInfo = TicketSummary.findWhere(ticket_id:params.id) //fetching the data on basis of ticket id  from ticketSummary table
		def ticketAllInfo = StatusUpdate.findAllWhere(ticket:ticketInfo)  //fetching the data on basis of ticket id from StatusUpdate table
		def c  =  StatusUpdate.createCriteria()
		def results  =  c.list {
			
				eq("ticket", ticketInfo)
				/*and {
					eq("user",user)
				}*/
			
			order("updateDate", "desc")
		}	
		
		def totalWorkHrs = 0
		def totalWorkMinutes = 0;
		//for calculating the totalWorkHrs
		for (var in ticketAllInfo) {
			
			String todaysWorkHrs = var.todaysWorkHrs.toString()
			def workingHrs = todaysWorkHrs.split(Pattern.quote("."))
			def workhrs = workingHrs[0]
			def workingMinutes = workingHrs[1]
			
			totalWorkHrs = totalWorkHrs+Integer.parseInt(workhrs)
			totalWorkMinutes = totalWorkMinutes+Integer.parseInt(workingMinutes)
			def minutesConversionToHrs
			def remainingMinutes
			if(totalWorkMinutes>= 60){
				
				minutesConversionToHrs = (int)totalWorkMinutes/60
				remainingMinutes = totalWorkMinutes%60
				totalWorkHrs = totalWorkHrs+minutesConversionToHrs
				totalWorkMinutes = remainingMinutes
			}
			
		}
		def eta=EtaTicketMapping.findWhere(ticket:ticketInfo)
		def totalHrs = totalWorkHrs.toString()+"."+totalWorkMinutes.toString()
		result.add(ticketInfo)
		result.add(results[0].updatedStatus)
		result.add(totalHrs)
		result.add(eta.eta)
		render result as JSON
	}

	//for todays tickets only
	@Secured('IS_AUTHENTICATED_FULLY')
	def todaysTickets(){

		try{
			def userLogedIn = User.get(springSecurityService.principal.id)
			def df = new SimpleDateFormat("yyyy-MM-dd");
			Date myDate = new Date();
			def todaysDate = df.format(myDate);
			Date todaysdate = df.parse(todaysDate)
			def results = StatusUpdate.findAllWhere(updateDate:todaysdate,user:userLogedIn)
			[results:results]
			/*if(results){
				
			}
				 def message = "No record found for todays date"
				redirect controller:'StatusPortal',action:'index'*/
			
		}catch(Exception e){

			flash.message = "Some Error is occured"
			redirect controller:'StatusPortal',action:'index'
		}
	}

	//for update the ticket which is updated by assignee
@Secured('IS_AUTHENTICATED_FULLY')
	def updateTicketStatus(){
		try{

			//def userLogedIn = User.get(springSecurityService.principal.id)
			//println("userName = "+userLogedIn.username)
			
			def ticketInfo=StatusUpdate.get(params.id)
			if(ticketInfo){
				[ticketInfo:ticketInfo]
			}else{
				flash.message = "Some error is occured while fetching the data"

			}

		}catch(Exception e){
			flash.message = "User is not autherised to update this ticket"
			log.error(e.message)
			redirect controller:'StatusPortal',action:'todaysTickets'
		}
	}

	@Secured('IS_AUTHENTICATED_FULLY')
	def saveUpdate(){

		
		def data = JSON.parse(request)
		def updateEntryFlag=true;
		try{
			def statusUpdateObject=StatusUpdate.get(data.ticketData.idInStatusUpdateDb)
			

			statusUpdateObject.workDoneForToday=data.ticketData.workDone
			if(data.ticketData.impediments){
			statusUpdateObject.impediments=data.ticketData.impediments
			}else{
			statusUpdateObject.impediments="NA"
			}
			def hrs=data.ticketData.workingHrs
			def mints=data.ticketData.workingMinutes
			if(!(hrs.equals(null) && mints.equals(null))){
				statusUpdateObject.todaysWorkHrs=hrs+"."+mints
			}
			def df  =  new SimpleDateFormat("dd/MM/yyyy");
			Date updatedDate = df.parse(data.ticketData.creationDate)
			def formatedDate = df.format(updatedDate)
			Date updatedDate1 = df.parse(formatedDate)
			
			statusUpdateObject.updateDate=updatedDate1


			if(statusUpdateObject.save(flush:true,failOnError:true)){

				updateEntryFlag=true;
			}else{
				updateEntryFlag=false;
			}

			render updateEntryFlag
		}catch(Exception e){
			log.error(e.message)
			updateEntryFlag=false;
			render updateEntryFlag
		}

	}
	
	
	//for updating the ticket related to user
	@Secured('IS_AUTHENTICATED_FULLY')
	def saveTicketEntry(){

		def flag = false
		def ticketSummaryObject = null
		def hrs
		def minutes
		def todaysWorkingHrs
		try{
			def data = JSON.parse(request)

			def df  =  new SimpleDateFormat("dd/MM/yyyy");
			def currentUser = User.get(springSecurityService.principal.id)
			if(params.ticket_id){
				ticketSummaryObject = TicketSummary.findByTicket_id(params.ticket_id)
			}else{
				ticketSummaryObject = TicketSummary.findByTicket_id(data.ticketData.ticket_id)
			}


			//for date convirsion
			Date creationDate = df.parse(data.ticketData.creationDate)
			def formatedDate = df.format(creationDate)
			Date creationDate1 = df.parse(formatedDate)

			//for calculation of total working hrs
			hrs = data.ticketData.workingHrs
			minutes = data.ticketData.workingMinutes
			todaysWorkingHrs = hrs+"."+minutes


			StatusUpdate newTicketStatus = new StatusUpdate()



			//Checking if ticketid is present in ticketSummary then update the eta if eta is changed
			//IF ticket is not present then create new ticketSummry object update the status
			if(ticketSummaryObject){
				TicketSummary tSummary = TicketSummary.findByTicket_id(data.ticketData.ticket_id)
				def etaTicketMapping=EtaTicketMapping.findWhere(ticket:tSummary)
				newTicketStatus.ticket = tSummary
				def etaFromFrontEnd=Float.parseFloat(data.ticketData.eta)
				def etaInDb=Float.parseFloat(etaTicketMapping.eta)


				if(etaFromFrontEnd.equals(etaInDb) ){

					println "both values are same"
				}else{

					etaTicketMapping.eta=data.ticketData.eta
					etaTicketMapping.save(flush:true,failOnError:true)
					ReasonTicketMapping reasonToTicket=new ReasonTicketMapping()
					def reason=Reason.findWhere(reason_id:data.ticketData.reason.reason_id)
					reasonToTicket.ticket_id=tSummary.ticket_id
					reasonToTicket.reason_id=reason.reason_id

					Date todaysDate=new Date();
					def formatedTodaysDate=df.format(todaysDate)
					Date dateForReasonUpdate=df.parse(formatedTodaysDate)

					reasonToTicket.creationDate=dateForReasonUpdate
					reasonToTicket.save(flush:true,failOnError:true)

				}


				flag = true
			}else{

				TicketSummary newTicketSummary = new TicketSummary()
				newTicketSummary.user = currentUser
				newTicketSummary.ticket_id = data.ticketData.ticket_id
				newTicketSummary.summary = data.ticketData.summary
				newTicketSummary.assignee = data.ticketData.assignee
				newTicketSummary.status = data.ticketData.status

				newTicketSummary.creationDate = creationDate1

				def userProjectMap
				if(data.ticketData.project==null){

					userProjectMap = UserProjectMapping.findWhere(employee_id:currentUser.employeeId)
					newTicketSummary.project = ProjectInfo.findWhere(project_id:userProjectMap.project_id)

				}else{

					newTicketSummary.project = ProjectInfo.findWhere(project_id:data.ticketData.project.project_id)
				}
				newTicketSummary.save(flush:true,failOnError:true)

				TicketSummary ticket=TicketSummary.findByTicket_id(newTicketSummary.ticket_id)
				newTicketStatus.ticket =ticket  //TicketSummary.findByTicket_id(newTicketSummary.ticket_id)
				EtaTicketMapping etaTicketMapping=new EtaTicketMapping()

				etaTicketMapping.ticket=ticket;
				if(data.ticketData.eta){
				etaTicketMapping.eta=data.ticketData.eta
				}else{
				etaTicketMapping.eta=0.00
				}
				etaTicketMapping.save(flush:true,failOnError:true)

			}

			newTicketStatus.user = currentUser
			newTicketStatus.workdoneBy  = data.ticketData.workDoneBy



			newTicketStatus.todaysWorkHrs = todaysWorkingHrs

			newTicketStatus.updatedStatus = data.ticketData.status

			//Converting String date into date format
			Date updateStatusDate = df.parse(data.ticketData.creationDate)
			newTicketStatus.updateDate = updateStatusDate

			newTicketStatus.workDoneForToday = data.ticketData.todayswork
			if(data.ticketData.ipediments){
				newTicketStatus.impediments = data.ticketData.impediments
			}else{
				newTicketStatus.impediments = "NA"
			}


			newTicketStatus.save(flush:true,failOnerror:true)

			flag = true
			render flag
		}catch(Exception e){
			log.error(e.message)
			flag = false
			flash.message = "Something went wrong"
			render flag
		}

	}
	
	//for all tickets history irespective of the user 
	@Secured('IS_AUTHENTICATED_FULLY')
	def getAllTicketsOfUser(){
	}
	
	//for all tickets history irespective of the user
	@Secured('IS_AUTHENTICATED_FULLY')
	def getAllTicketsOfUser1(){
		
	}
	
//all tickets present in db
	@Secured('IS_AUTHENTICATED_FULLY')
	def getAllTicketHistory(){
		def allTickets = StatusUpdate.findAll();
			[allTickets:allTickets]
	}

	
	//for exporting the data in pdf csv and excel format
	@Secured('IS_AUTHENTICATED_FULLY')
	def exportData(){



		if (! params . Max )  {

			params . Max  =  10
		}

		if  ( params . extension!=null)  {

			def format = params . extension

			if  ( "xls" . equals ( params . extension ))  {

				format = "excel"

			}

			if ( format && format !=  "Html" ) {

				response.contentType = grailsApplication.config.grails.mime.types[params.format]
				response.setHeader("Content-disposition", "attachment; filename=statusportal.${params.extension}")

				List fields =  [
					"id" ,
					"ticket.ticket_id" ,
					"ticket.summary",
					"ticket.assignee",
					"workdoneBy",
					"workDoneForToday",
					"updateDate",
					"updatedStatus",
					"todaysWorkHrs",
					"impediments"
				]

				Map labels =  [ "id" :  " " ,  "ticket.ticket_id" :  "Ticket ID" ,  "ticket.summary":" Summary","ticket.assignee": "Assignee", "workdoneBy" :"Work Done By",
					"workDoneForToday":"Work Done Description","updateDate":"Work Done on","updatedStatus":"Ticket Status","todaysWorkHrs":"Work Hrs" ,"impediments" : "Impediments"]


				def dateFormat = { domain, value ->
					def df = new SimpleDateFormat("dd/MM/yyyy");
					def updateDate=df.format(value);
					return updateDate
				}

				Map formatters =  [updateDate:dateFormat]

				def result=[]
				if(params.id!=null){
				//	println "params list"
					def ticketInfo=TicketSummary.findByTicket_id(params.id)
					result=StatusUpdate.findAllWhere(ticket:ticketInfo)
				}else{
				//println "statusUpdate list"
					result=StatusUpdate.list()
				}	

				Map parameters =  ["column.widths":[0.05,0.15,0.2,0.13,0.13,0.3,0.12,0.12,0.07,0.3]]
				try{
					exportService . export (format , response . outputStream , result ,fields , labels , formatters , parameters )
				}catch(Exception e){
					log.error(e.message)
					//println "exception="+e.message
				}
			}

		}
	}
	
	//for deleting the ticket
	@Secured('IS_AUTHENTICATED_FULLY')
	
	def deleteTicket(){
		String role = springSecurityService.principal.authorities
		def userLogedIn = User.get(springSecurityService.principal.id)
		def data  =  JSON.parse(request)

		def deleteFlag  =  []
		def deleteDataFlag  =  false
		try {


			def ticketAllInfo = StatusUpdate.get(data.row)
		
			def ticketInfo=TicketSummary.findWhere(ticket_id:ticketAllInfo.ticket.ticket_id)
			def statusUpdateEntries=StatusUpdate.findAllWhere(ticket:ticketInfo)

			def count = statusUpdateEntries.size()
			if(count == 1){
				//ReasonTicketMapping.findAllWhere(ticket_id:ticketInfo.ticket_id)
				
				ReasonTicketMapping.executeUpdate( "delete ReasonTicketMapping rtm where rtm.ticket_id= ?",[ticketInfo.ticket_id])
				//EtaTicketMapping.executeUpdate( "delete EtaTicketMApping etm where etm.ticket =? ",[ticketInfo])
				def etaTicketEntry=EtaTicketMapping.findWhere(ticket:ticketInfo)
				etaTicketEntry.delete(flush:true)
				
				ticketAllInfo.delete(flush:true)
				ticketInfo.delete(flush:true)
			}else{
			
			
			ticketAllInfo.delete(flush:true)
			
			}
			//flash.message = "Data deleted successfully!!"


			deleteDataFlag  =  true
			deleteFlag.add(deleteDataFlag)

		}catch(Exception e){
			deleteDataFlag  =  false
			deleteFlag.add(deleteDataFlag)
			log.error(e.message)

		}
		render deleteFlag as JSON
	}
}
