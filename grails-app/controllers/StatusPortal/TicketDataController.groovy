package StatusPortal

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import java.text.SimpleDateFormat

import SecureApp.Role
import SecureApp.User
import SecureApp.UserRole

class TicketDataController {
	def springSecurityService
	@Secured('IS_AUTHENTICATED_FULLY')
	def testIndex(){
	}

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
		if(data.projectId){
			project  =  ProjectInfo.findWhere(project_id:data.projectId)
		}else{
			def user = User.findWhere(id:springSecurityService.principal.id )
			def userProject = UserProjectMapping.findWhere(user_id:user.employeeId)
			project = ProjectInfo.findWhere(project_id:userProject.project_id)
		}
		def methodologyList = []
		methodologyList.add(project.methodology.methodology)
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
			project.projectStartDate = data.projectStartDate
			def methodology = DevelopementMethodology.findWhere(methodology:data.methodology.methodology)
			project.methodology = methodology
			project.save(flush:true,failOnError:true)
			 projectSavedMessageNo = 1
			projectSavedMessage.add(projectSavedMessageNo)

		}catch(Exception e){
			e.printStackTrace()
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
		for(ticketObject in allTicketsOfUser){
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
				if(!listOfTicketIds.contains(ticketObject.ticket.ticket_id)){
				listOfTicketIds.add(ticketObject.ticket.ticket_id)
				}
		}
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

				projects = UserProjectMapping.findAllByUser_id(user.employeeId)
			}
			for (project in projects) {
				def projectInfo = ProjectInfo.findWhere(project_id:project.project_id)
				projectListOfUser.add(projectInfo)
			}
			render projectListOfUser as JSON

		}catch(Exception e){
			e.printStackTrace()
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
	
	//for loading the list of all resources on the basis of manager
	@Secured('IS_AUTHENTICATED_FULLY')
	def getResourceListforManager(){
		def currentUser=User.get(springSecurityService.principal.id)
		String role=springSecurityService.principal.authorities
		def resourceList=[]
		if(role.contains('ROLE_MANAGER')){
			def resourceUnderProject=UserManager.findAllWhere(manager_id:currentUser.employeeId)
			//def resourceUnderProject=UserProjectMapping.findAllWhere(project_id:project.project_id)
			for (var in resourceUnderProject) {
				
				def user=User.findWhere(employeeId:var.employee_id)
				
				resourceList.add(user.username)
				
			}
		}else{}
		render resourceList as JSON
		
	}
	
	//for loading the resources regarding the project
	@Secured('IS_AUTHENTICATED_FULLY')
	def getResourcesList(){
		def project = ProjectInfo.findWhere(projectName:params.id)
		def currentUser = User.get(springSecurityService.principal.id)
		String role = springSecurityService.principal.authorities
		def resourceList = []
		if(role.contains('ROLE_LEAD')){
			def resourceUnderLead = UserManager.findAllWhere(lead_id:currentUser.employeeId)
			
			for (var in resourceUnderLead) {
				println var.employee_id
				def user = User.findWhere(employeeId:var.employee_id)
				println user.username
				resourceList.add(user.username)
			}
			
		}
		
		if(role.contains('ROLE_MANAGER')){
			try{
			def resourceUnderProject = UserProjectMapping.findAllWhere(project_id:project.project_id)
			
			
			for (var in resourceUnderProject) {
				def user = User.findWhere(employeeId:var.user_id)
				resourceList.add(user.username)
			}
			}catch(Exception e){
			log.error("message = "+e.message);
			}
		
		}else{}
		render resourceList as JSON
	}
	
	//for loading the tickets on the basis of resources
	@Secured('IS_AUTHENTICATED_FULLY')
	def getTicktetsOnBasisOfResources(){
		
		def data = JSON.parse(request)
		def ticketList = []
			def StatusUpdateTicket = StatusUpdate.findAllWhere(workdoneBy:data.resourceName)
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
			render ticketList as JSON
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


				if(role.toString().contains("ROLE_LEAD")){

					def c =  StatusUpdate.createCriteria()
					def allTicketsOfUser = c.list{
						between("updateDate",today,end)
						'in'('user', user)
					}
					for (var in allTicketsOfUser) {
						if(allTicketsOfUser){
							println"Workdone = " +var.workdoneBy
							def ticket = [:]
							ticket.put("ticket_id",var.ticket.ticket_id)
							ticket.put("summary", var.ticket.summary)
							ticket.put("assignee", var.ticket.assignee)
							ticket.put("workDoneBy",var.workdoneBy)
							ticket.put("impediments", var.impediments)
							ticket.put("todaysWorkHrs", var.todaysWorkHrs)
							ticket.put("updateDate", var.updateDate)
							ticket.put("updatedStatus", var.updatedStatus)

							ticketList.add(ticket)
						}
					}
				}

				if(ticketList){

					render ticketList as JSON
				}else{
					// flash.message = "No records found"
					render ticketList as JSON
				}
			
	}
	
	
	//for getting ticket info respective with ticketId
	@Secured('IS_AUTHENTICATED_FULLY')
	def showTicketData(){
		
		def user = User.get(springSecurityService.principal.id)
		def ticketList = []
		def ticketInfo = TicketSummary.findWhere(ticket_id:params.id) //(,user:user)fetching the data on basis of ticket id and user from ticketSummary table
		def allTicketsOfUser = StatusUpdate.findAllWhere(ticket:ticketInfo)  //(,,user:user)fetching the data on basis of ticket id and user from StatusUpdate table
		def c  =  StatusUpdate.createCriteria()
		
			for (var in allTicketsOfUser) {
			if(allTicketsOfUser){
				def ticket = [:]
				ticket.put("ticket_id",var.ticket.ticket_id)
				ticket.put("summary", var.ticket.summary)
				ticket.put("assignee", var.ticket.assignee)
				ticket.put("workDoneBy",var.workdoneBy)
				ticket.put("impediments", var.impediments)
				ticket.put("todaysWorkHrs", var.todaysWorkHrs)
				ticket.put("updateDate", var.updateDate)
				ticket.put("updatedStatus", var.updatedStatus)
			  
			ticketList.add(ticket)
			
			}
		}
			render ticketList as JSON
	}
	

}
