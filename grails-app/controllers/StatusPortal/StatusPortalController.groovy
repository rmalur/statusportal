package StatusPortal

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.regex.Pattern

import SecureApp.User

class StatusPortalController {

	def springSecurityService
	def exportService
	def grailsApplication  //inject GrailsApplication
	
	@Secured('IS_AUTHENTICATED_FULLY')
	def index() {
 // render( view: 'todaysTickets')
	}
	@Secured('IS_AUTHENTICATED_FULLY')
	def changeView(){
		render( view: 'todaysTickets')
	}
	//loading all user list as assignee
	@Secured('IS_AUTHENTICATED_FULLY')
	def assigneeList(){
		def data = JSON.parse(request)
		def assigneeList = []
		def q
		if(data.projectId){
		 q = UserProjectMapping.findAllWhere(project_id:data.projectId)
		}else{
		
			def user = User.get(springSecurityService.principal.id )
			def project = UserProjectMapping.findWhere(user_id:user.employeeId)
			q = UserProjectMapping.findAllWhere(project_id:project.project_id )
			
		}
		for(employee in q){
			def user = User.findWhere(employeeId:employee.user_id )
				assigneeList.add(user.username)
			
			}	
		render assigneeList as JSON
	}
	
		//for getting the all the ticketIds respective to user id to autopopulate on page
	@Secured('IS_AUTHENTICATED_FULLY')
	def ticketIds(){
		def project
		def ticket
		def data = JSON.parse(request)
		def user = User.get(springSecurityService.principal.id)
		def ticketIds = []
		if(data.projectId){
			project = ProjectInfo.findWhere(project_id:data.projectId)
		}

		def results  =  StatusUpdate.withCriteria {
		
			ne("updatedStatus","Closed")
			projections { distinct("ticket") }
		}
		
		for (var in results) {

			if(project){
				ticket = TicketSummary.findWhere(ticket_id:var.ticket_id,project:project)
			}else {
				ticket = TicketSummary.findWhere(ticket_id:var.ticket_id)
			}

			if(ticket){
				def ticketInfo = StatusUpdate.findWhere(ticket:ticket,updatedStatus:"Closed")
				if(ticketInfo){
						continue
				}else{

					ticketIds.add(var.ticket_id)
				}
			}else{
				continue
			}
		}
		render ticketIds as JSON
	}

	//for getting ticket info respective with ticketId
	@Secured('IS_AUTHENTICATED_FULLY')
	def getTicketInfo(){
		
		def user = User.get(springSecurityService.principal.id)
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
		
		def totalHrs = totalWorkHrs.toString()+"."+totalWorkMinutes.toString()
		result.add(ticketInfo)
		result.add(results[0].updatedStatus)
		result.add(totalHrs)
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
			def results = TicketSummary.findAllWhere(creationDate:todaysdate,user:userLogedIn)
			if(results){
				[results:results]
			}else{
				 def message = "No record found for todays date"
				redirect controller:'StatusPortal',action:'index'
			}
		}catch(Exception e){

			flash.message = "Some Error is occured"
			redirect controller:'StatusPortal',action:'index'
		}
	}

	//for update the ticket which is updated by assignee
@Secured('IS_AUTHENTICATED_FULLY')
	def updateTicketStatus(){
		try{

			def userLogedIn = User.get(springSecurityService.principal.id)
			println("userName = "+userLogedIn.username)
			def ticketInfo = TicketSummary.findWhere(ticket_id:params.id)
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


	//for updating the ticket related to user
	@Secured('IS_AUTHENTICATED_FULLY')
	def updateTodaysTicket(){

		def flag = false
		def ticketSummaryObject = null
		def presentTicket
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

			if(data.ticketData.creationDate){
				Date creationDate = df.parse(data.ticketData.creationDate)
				def formatedDate = df.format(creationDate)
				Date creationDate1 = df.parse(formatedDate)
				presentTicket = StatusUpdate.findWhere(user:currentUser,updateDate:creationDate1,ticket:ticketSummaryObject)
			}

			if(presentTicket){
				String todaysWorkHrs = presentTicket.todaysWorkHrs.toString()
				def workingHrs = todaysWorkHrs.split(Pattern.quote("."))
				
				
				
				presentTicket.workDoneForToday = data.ticketData.todayswork
				presentTicket.workdoneBy = data.ticketData.workDoneBy
				if(data.ticketData.workingHrs==null){
					hrs = workingHrs[0]
				}else{
					hrs = data.ticketData.workingHrs
				}
				if(data.ticketData.workingHrs==null){
					minutes = workingHrs[1]
				}else{
					minutes = data.ticketData.workingMinutes
				}

				todaysWorkingHrs = hrs+"."+minutes
				presentTicket.todaysWorkHrs = todaysWorkingHrs
				presentTicket.impediments = data.ticketData.impediments
				presentTicket.save(flush:true,failOnError:true)

			}else{
				StatusUpdate newTicketStatus = new StatusUpdate()

				if(ticketSummaryObject){
					TicketSummary tSummary = TicketSummary.findByTicket_id(data.ticketData.ticket_id)

					newTicketStatus.ticket = tSummary
					flag = true
				}else{

					TicketSummary newTicketSummary = new TicketSummary()
					newTicketSummary.user = currentUser
					newTicketSummary.ticket_id = data.ticketData.ticket_id
					newTicketSummary.summary = data.ticketData.summary
					newTicketSummary.assignee = data.ticketData.assignee
					newTicketSummary.status = data.ticketData.status
					//changing the String into date format

					Date creationDate = df.parse(data.ticketData.creationDate)
					def formatedDate = df.format(creationDate)
					Date creationDate1 = df.parse(formatedDate)
					newTicketSummary.creationDate = creationDate1

					def userProjectMap
					if(data.ticketData.project==null){
						
						userProjectMap = UserProjectMapping.findWhere(user_id:currentUser.employeeId)
						newTicketSummary.project = ProjectInfo.findWhere(project_id:userProjectMap.project_id)
						
					}else{
						
						newTicketSummary.project = ProjectInfo.findWhere(project_id:data.ticketData.project.project_id)
					}
					newTicketSummary.save(flush:true,failOnError:true)
					newTicketStatus.ticket = TicketSummary.findByTicket_id(newTicketSummary.ticket_id)
				}

				newTicketStatus.user = currentUser
				newTicketStatus.workdoneBy  = data.ticketData.workDoneBy

				//for calculating the todays work hrs
				 hrs = data.ticketData.workingHrs
				 minutes = data.ticketData.workingMinutes
				 todaysWorkingHrs = hrs+"."+minutes
				newTicketStatus.todaysWorkHrs = todaysWorkingHrs

				newTicketStatus.updatedStatus = data.ticketData.status

				//Converting String date into date format
				Date updateStatusDate = df.parse(data.ticketData.creationDate)
				newTicketStatus.updateDate = updateStatusDate

				newTicketStatus.workDoneForToday = data.ticketData.todayswork
				newTicketStatus.impediments = data.ticketData.impediments

				newTicketStatus.save(flush:true,failOnerror:true)
			}
			flag = true
		
		}catch(Exception e){
			log.error(e.message)
			flag = false
			flash.message = "Something went wrong"
		
		}
		render flag
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

			println ( params .get ( 'zest' ))

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
					println "params list"
					def ticketInfo=TicketSummary.findByTicket_id(params.id)
					result=StatusUpdate.findAllWhere(ticket:ticketInfo)
				}else{
				println "statusUpdate list"
					result=StatusUpdate.list()
				}	

				Map parameters =  ["column.widths":[0.05,0.15,0.2,0.13,0.13,0.3,0.12,0.12,0.07,0.3]]
				try{
					exportService . export (format , response . outputStream , result ,fields , labels , formatters , parameters )
				}catch(Exception e){
					println "exception="+e.message
				}
			}

		}
	}

}
