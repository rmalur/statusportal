package StatusPortal


import grails.converters.JSON
import grails.plugin.mail.MailService
import grails.plugin.springsecurity.annotation.Secured

import java.text.SimpleDateFormat
import java.util.regex.Pattern
import org.apache.commons.logging.LogFactory


import ListProcessing.ReturnProcessedList
import SecureApp.Role
import SecureApp.User
import SecureApp.UserRole



class TicketDataController {
	private static final log  =  LogFactory.getLog(this)
	MailService mailService
	def springSecurityService
	

	//developement methodology list
	@Secured('IS_AUTHENTICATED_FULLY')
	def getMethodologyList(){
		
		def methodologyList  =  DevelopementMethodology.findAll()
		render methodologyList as JSON
		
	}
	
		//fetch methodology
	@Secured('IS_AUTHENTICATED_FULLY')
	def fetchMethodology(){
		def data  =  JSON.parse(request)
		def project
		def methodologyList = []
		try{
		if(data.projectId){
			project  =  ProjectInfo.findWhere(project_id:data.projectId)
		}else{
			def user = User.findWhere(id:springSecurityService.principal.id )
			def userProject = UserProjectMapping.findWhere(employee_id:user.employeeId)
			project = ProjectInfo.findWhere(project_id:userProject.project_id)
		}
		methodologyList.add(project.methodology.methodology)
		}catch(Exception e){
			log.error(e.message)
		}
		
	
		render methodologyList as JSON
	}
	
	
	
	@Secured('IS_AUTHENTICATED_FULLY')
	def createProject(){
	}

	//manager List
	//@Secured('IS_AUTHENTICATED_FULLY')
	@Secured('permitAll')
	def getManagerList(){
		def roleId = Role.findWhere(authority:"ROLE_MANAGER")
		def managerUser = UserRole.findAllWhere(role:roleId)
		def managerNameList = []
		for (var in managerUser) {
			def user = User.get(var.user.id)
			managerNameList.add(user.username)
		}
		render managerNameList as JSON
	}

  //adding new project
	@Secured('IS_AUTHENTICATED_FULLY')
	def addProjectInfo(){
		def projectSavedMessage = []
		def projectSavedMessageNo
		def data = JSON.parse(request)
		try{
			def manager = User.findWhere(username:data.managerName)
			def project = new ProjectInfo()
			project.user = manager
			project.project_id = data.project_id
			project.projectName = data.projectName
			
			
			project.projectStartDate = data.projectStartDate.toString()
			def methodology = DevelopementMethodology.findWhere(methodology:data.methodology.methodology)
			project.methodology = methodology
			project.save(flush:true,failOnError:true)
			 projectSavedMessageNo = 1
			projectSavedMessage.add(projectSavedMessageNo)

		}catch(Exception e){
			//e.printStackTrace()
			log.error(e.message)
			projectSavedMessageNo = 0
			projectSavedMessage.add(projectSavedMessageNo)
		}
		render projectSavedMessage as JSON
	}

  //for creating new user
	@Secured('permitAll')
	def createUser(){

	}

  //for saving new user
@Secured('permitAll')
	def saveUser(){
		def userSavedNotification = []
		def userSavedflag = 1
		try{
			def data = JSON.parse(request)
			def userRole

			if(data.role.contains("Manager")){

				userRole = Role.findByAuthority("ROLE_MANAGER")
			}else if(data.role.contains("Lead")){
				userRole = Role.findByAuthority("ROLE_LEAD")
			}else{
				userRole = Role.findByAuthority("ROLE_NORMAL")
			}


			def multipleProject =  data.project


			def newUser = new User()
			newUser.username = data.employeeName
			newUser.password = data.password
			newUser.employeeId = data.employeeId
			newUser.employeeEmailId = data.employeeEmailId
			newUser.fullNameOfEmployee = data.employeeFirstName+" "+data.employeeLastName


			if(newUser.save(flush:true,failOnError:true))
			{
				UserRole.create newUser,userRole,true
				if(multipleProject && data.managerName ){
					for (project in multipleProject) {
						def userProjectMap = new UserProjectMapping()
						userProjectMap.user_id = data.employeeId
						userProjectMap.project_id = project.project_id
						userProjectMap.save(flush:true,failOnError:true)
					}

					def userManagerMapping = new UserManager()
					def manager = User.findWhere(username:data.managerName)
					userManagerMapping.manager_id = manager.employeeId
					userManagerMapping.employee_id = data.employeeId

					if(data.lead != null){
						def lead_id = User.findWhere(username:data.lead)
						userManagerMapping.lead_id = lead_id.employeeId
					}else{
						userManagerMapping.lead_id = "NA"
					}

					userManagerMapping.save(flush:true,failOnError:true)
					userSavedflag = 1;
				}else{

					userSavedflag = 0;
				}
			}
			userSavedNotification.add(userSavedflag)
			render userSavedNotification as JSON
		}catch(Exception e){
			log.error(e. message)
			userSavedflag = 0
			userSavedNotification.add(userSavedflag)
			render userSavedNotification as JSON
		}
	}

	
	//for loading tickets of user initiallly
	@Secured('permitAll')
	def loadAllTicketsOfuser(){
		def currentUser = User.get(springSecurityService.principal.id)
		String role = springSecurityService.principal.authorities
		List<StatusUpdate>allTicketsOfUser = new ArrayList<StatusUpdate>()
		if(role.contains('ROLE_MANAGER')){
			 def projects = ProjectInfo.findAllWhere(user:currentUser)
				for (var in projects) {
					def tickets = TicketSummary.findAllWhere(project:var)
					for (ticket in tickets) {
						def ticketStatusUpdate = StatusUpdate.findAllWhere(ticket:ticket)
						for(statusUpdateObject in ticketStatusUpdate){
							allTicketsOfUser.add(statusUpdateObject	)
						}
					}
				
				}
		}else{
		allTicketsOfUser = StatusUpdate.findAllWhere(workdoneBy:currentUser.username)
		}
		def ticketList = []
		def listOfTicketIds = []
		
		ticketList= ReturnProcessedList.serviceForListProcessing(allTicketsOfUser)
		listOfTicketIds=ReturnProcessedList.returnUniqueListOfticketIds(allTicketsOfUser)
		
		def result = []
		result.add(ticketList)
		result.add(listOfTicketIds)
		render result as JSON
	}
	
  //for loading the projectList of manager
	@Secured('permitAll')
	def getProjectList(){
		def manager = User.findWhere(username:params.id)
		def projectList = ProjectInfo.findAllWhere(user:manager)
		render  projectList as JSON
	}
   //for loading the project related to user
	@Secured('IS_AUTHENTICATED_FULLY')
	def getProjectListOfUser(){
		
		def projectListOfUser = []
		try{
			def user = User.get(springSecurityService.principal.id)
			def role = springSecurityService.principal.authorities
			def projects
			if(role.toString().contains("ROLE_MANAGER")){
				projects = ProjectInfo.findAllWhere(user:user)
			}else{

				projects = UserProjectMapping.findAllWhere(employee_id:user.employeeId)
			}
			for (project in projects) {
				def projectInfo = ProjectInfo.findWhere(project_id:project.project_id)
				projectListOfUser.add(projectInfo)
			}
			render projectListOfUser as JSON

		}catch(Exception e){
			log.error(e.message)
		//	e.printStackTrace()
		}
	}
	
	
	//for loading the lead List
	@Secured('permitAll')
	def getLeadList(){
		def roleId = Role.findWhere(authority:"ROLE_LEAD")
		def leadUser = UserRole.findAllWhere(role:roleId)
		def leadList = []

		for (var in leadUser) {

			def user = User.get(var.user.id)
			leadList.add(user.username)
		}

		render leadList as JSON
		
	}
	//loading the list of tickets regarding to project
	@Secured('IS_AUTHENTICATED_FULLY')
	def	getTicketListOfProject(){
		
		def project = ProjectInfo.findWhere(projectName:params.id)
		def currentUser = User.get(springSecurityService.principal.id)
		String role = springSecurityService.principal.authorities
		def ticketSummaryList = TicketSummary.findAllWhere(project:project)
		
		def ticketList = []
		for (var in ticketSummaryList) {		
		
			def ticketSummary = TicketSummary.findWhere(ticket_id:var.ticket_id)
			def StatusUpdateTicket
			if(role.contains('ROLE_MANAGER')){
				StatusUpdateTicket = StatusUpdate.findAllWhere(ticket:ticketSummary)
			}else{
				StatusUpdateTicket = StatusUpdate.findAllWhere(ticket:ticketSummary,workdoneBy:currentUser.username)
			}
		
			
				for (ticketObject in StatusUpdateTicket) {
				def ticket = [:]
				ticket.put("ticket_id", ticketObject.ticket.ticket_id)
				ticket.put("summary",ticketObject.ticket.summary)
				ticket.put("assignee",ticketObject.ticket.assignee)
				ticket.put("workDoneBy",ticketObject.workdoneBy )
				ticket.put("impediments",ticketObject.impediments)
				ticket.put("todaysWorkHrs",ticketObject.todaysWorkHrs )
				ticket.put("updateDate",ticketObject.updateDate )
				ticket.put("updatedStatus",ticketObject.updatedStatus)
				ticketList.add(ticket)
			}
		}
			render ticketList as JSON
	}
	

	
		
	
	//for laoding resources reagrding user role and project
	@Secured('IS_AUTHENTICATED_FULLY')
	def getResourcesList(){

		def resourceList=[]
		def project
		if(params.id){

			project=ProjectInfo.findWhere(projectName:params.id)
		}

		def currentUser=User.get(springSecurityService.principal.id)
		String role = springSecurityService.principal.authorities
		if(role.contains('ROLE_LEAD')){
			def resourceUnderLead = UserManager.findAllWhere(lead_id:currentUser.employeeId)

			for (var in resourceUnderLead) {
				def user = User.findWhere(employeeId:var.employee_id)
				resourceList.add(user.username)
			}

		}

		if(role.contains('ROLE_MANAGER')){
			try{
				def resourceUnderProject
				if(project){
					resourceUnderProject = UserProjectMapping.findAllWhere(project_id:project.project_id)
				}else{
					resourceUnderProject=UserManager.findAllWhere(manager_id:currentUser.employeeId)
				}

				for (var in resourceUnderProject) {
					def user = User.findWhere(employeeId:var.employee_id)
					resourceList.add(user.username)
				}
			}catch(Exception e){
				log.error("message = "+e.message);
			}

		}
			if(!(resourceList.empty) ){
				resourceList.add(0, "All")
				
			}
			render resourceList as JSON
	}
	
	//for loading the tickets on the basis of resources
	@Secured('IS_AUTHENTICATED_FULLY')
	def getTicktetsOnBasisOfResources(){
		
		def data = JSON.parse(request)
		def ticketList = []
		
		if(data.resourceName.equals("All")){
			
			loadAllTicketsOfuser();
			
			}
		
		else{
			def StatusUpdateTicket = StatusUpdate.findAllWhere(workdoneBy:data.resourceName)
			
			ticketList=ReturnProcessedList.serviceForListProcessing(StatusUpdateTicket)
			render ticketList as JSON
		}
	}
	
	//for all tickets history irrespective of date and user
	@Secured('IS_AUTHENTICATED_FULLY')
	def getAllTicketsOfDate(){
		
		def data = JSON.parse(request)
		def df  =  new SimpleDateFormat("dd/MM/yyyy");
		def user = User.get(springSecurityService.principal.id)
		def role = springSecurityService.principal.authorities
		def ticketList = []
		
			Date creationDate = df.parse(data.todaysDate)
			def formatedDate = df.format(creationDate)
			Date today = df.parse(formatedDate)

		
				Date endDate = df.parse(data.endDate)
				def formatDate = df.format(endDate)
				Date end = df.parse(formatDate)


				if(role.toString().contains("ROLE_LEAD") || role.toString().contains("ROLE_NORMAL")){

					def c =  StatusUpdate.createCriteria()
					def allTicketsOfUser = c.list{
						between("updateDate",today,end)
						'in'('user', user)
					}
					
					ticketList=ReturnProcessedList.serviceForListProcessing(allTicketsOfUser)
					
				}else{
				
				def c =  StatusUpdate.createCriteria()
				def allTicketsOfUser = c.list{
					between("updateDate",today,end)
				}
				
				ticketList=ReturnProcessedList.serviceForListProcessing(allTicketsOfUser)
				}
				

				if(ticketList){

					render ticketList as JSON
				}else{
					// flash.message = "No records found"
					render ticketList as JSON
				}
			
	}
	
	// for sending mail to user, lead and manager
	@Secured('permitAll')
	def sendDSR(){

		def resourceListforMail = []
		
				def currentUser = User.get(springSecurityService.principal.id)
				//String role = springSecurityService.principal.authorities
		
		
				def emailNotification = UserManager.findAllWhere(employee_id:currentUser.employeeId)
				def employeeEmailId = User.findWhere(employeeId:currentUser.employeeId)
		
				for (var in emailNotification) {
		
					if(var.lead_id.equals("NA")){
						
						resourceListforMail.add(employeeEmailId.employeeEmailId)
						def manager=User.findWhere(employeeId:var.manager_id)
						resourceListforMail.add(manager.employeeEmailId)
					}
					else{
						def lead = User.findWhere(employeeId:var.lead_id)
						def manager=User.findWhere(employeeId:var.manager_id)
		
						resourceListforMail.add(employeeEmailId.employeeEmailId)
						resourceListforMail.add(lead.employeeEmailId)
						resourceListforMail.add(manager.employeeEmailId)
		
		
					}
		
				}
					//def data  =  JSON.parse(request)
		
				def sendMailFlag  =  []
				def sentMailFlag  =  false
				try{
					//def user  =  User.findWhere(employeeEmailId:data.emailId)
					def result=[]
		
					def userLogedIn = User.get(springSecurityService.principal.id)
					def df = new SimpleDateFormat("yyyy-MM-dd");
					Date myDate = new Date();
					def todaysDate = df.format(myDate);
					Date todaysdate = df.parse(todaysDate)
					def results = StatusUpdate.findAllWhere(updateDate:todaysdate,user:userLogedIn)
					
					for(var1 in results){
							def ticketInfo= StatusUpdate.findAllWhere(ticket:var1.ticket)
							
							def totalWorkHrs = 0
							def totalWorkMinutes = 0;
							//for calculating the totalWorkHrs
							for (var in ticketInfo) {
								
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
							//println("totalWorkHrs="+totalHrs)
							def ticket = [:]
							ticket.put("ticket_id",var1.ticket.ticket_id)
							ticket.put("summary", var1.ticket.summary)
							ticket.put("assignee", var1.ticket.assignee)
							ticket.put("workDoneForToday",var1.workDoneForToday)
							ticket.put("impediments", var1.impediments)
							ticket.put("todaysWorkHrs", var1.todaysWorkHrs)
							ticket.put("updateDate", var1.updateDate)
							ticket.put("status", var1.updatedStatus)
							ticket.put("totalHrs", totalHrs)
		
							result.add(ticket)
							
							
					}
						
					mailService.sendMail {
						async true
						from "statusportal@evolvingsols.com"
						to  resourceListforMail
						subject "Daily Status Report"
						html g.render(template:'/statusPortal/DSR', model:[result:result])
					}
		
					sentMailFlag  =  true
					sendMailFlag.add(sentMailFlag)
		
				}catch(Exception e){
					sentMailFlag  =  false
					sendMailFlag.add(sentMailFlag)
					log.error(e.message)
		
				}
				render sendMailFlag as JSON
		
		
			}



	//for getting ticket info respective with ticketId
	//for getting ticket info respective with ticketId
	@Secured('IS_AUTHENTICATED_FULLY')
	def showTicketData(){
		
		//def user = User.get(springSecurityService.principal.id)
		def ticketList = []
		def ticketInfo = TicketSummary.findWhere(ticket_id:params.id) //(,user:user)fetching the data on basis of ticket id and user from ticketSummary table
		def allTicketsOfUser = StatusUpdate.findAllWhere(ticket:ticketInfo)  //(,,user:user)fetching the data on basis of ticket id and user from StatusUpdate table
		//def c  =  StatusUpdate.createCriteria()
		
		ticketList=ReturnProcessedList.serviceForListProcessing(allTicketsOfUser)
		render ticketList as JSON
	}
	

}
