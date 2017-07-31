package StatusPortal

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import java.text.SimpleDateFormat

import SecureApp.Role
import SecureApp.User
import SecureApp.UserRole

class TestController {
	def springSecurityService
	@Secured('IS_AUTHENTICATED_FULLY')
	def testIndex(){
	}

	//developement methodology list
	@Secured('IS_AUTHENTICATED_FULLY')
	def getMethodologyList(){
		
		def methodologyList=DevelopementMethodology.findAll()
		render methodologyList as JSON
		
	}
	@Secured('IS_AUTHENTICATED_FULLY')
	def createProject(){
	}

	//manager List
	//@Secured('IS_AUTHENTICATED_FULLY')
	@Secured('permitAll')
	def getManagerList(){
		def roleId=Role.findWhere(authority:"ROLE_MANAGER")
		def managerUser=UserRole.findAllWhere(role:roleId)
		def managerNameList=[]
		for (var in managerUser) {
			def user=User.get(var.user.id)
			managerNameList.add(user.username)
		}
		render managerNameList as JSON
	}

  //adding new project
	@Secured('IS_AUTHENTICATED_FULLY')
	def addProjectInfo(){
		def flag=[]
		def data=JSON.parse(request)
		println data
		try{
			def manager=User.findWhere(username:data.managerName)
			def project=new ProjectInfo()
			project.user=manager
			project.project_id=data.project_id
			project.projectName=data.projectName
			project.projectStartDate=data.projectStartDate
			def methodology=DevelopementMethodology.findWhere(methodology:data.methodology.methodology)
			project.methodology=methodology
			project.save(flush:true,failOnError:true)
			def message=1
			flag.add(message)

		}catch(Exception e){

			e.printStackTrace()
			def message=0
			flag.add(message)

		}
		render flag as JSON
	}

  //for creating new user
	@Secured('permitAll')
	def createUser(){

	}

  //for saving new user
@Secured('permitAll')
	def saveUser(){
		def normalUser = Role.findWhere(authority:'ROLE_NORMAL')
		def data=JSON.parse(request)
		def multipleroject= data.project
		def flagList=[]
		try{
			def newUser=new User()
			newUser.username=data.employeeName
			newUser.password=data.password
			newUser.employeeId=data.employeeId
			newUser.employeeEmailId=data.employeeEmailId
			if(newUser.save(flush:true,failOnError:true))
				{
					UserRole.create newUser,normalUser,true
					for (project in multipleroject) {
						def userProjectMap=new UserProjectMapping()
						userProjectMap.user_id=data.employeeId
						userProjectMap.project_id=project.project_id
						userProjectMap.save(flush:true,failOnError:true)
					}
						def userManagerMapping=new UserManager()
						def manager=User.findWhere(username:data.managerName)
						userManagerMapping.manager_id=manager.employeeId
						userManagerMapping.employee_id=data.employeeId
					
						if(data.lead!=null){
							def lead_id=User.findWhere(username:data.lead)
								userManagerMapping.lead_id=lead_id.employeeId
							}else{
							userManagerMapping.lead_id="NA"
							}
							
						userManagerMapping.save(flush:true,failOnError:true)
				}
			
				
			def flag=1
			flagList.add(flag)
			render flagList as JSON
		}catch(Exception e){
			e.printStackTrace()
			def flag=0
			flagList.add(flag)
			render flagList as JSON
		}
	}

	
	//for loading tickets of user initiallly
	@Secured('permitAll')
	def loadAllTicketsOfuser(){
		def currentUser=User.get(springSecurityService.principal.id)
		String role=springSecurityService.principal.authorities
		List<StatusUpdate>allTicketsOfUser=new ArrayList<StatusUpdate>()
		if(role.contains('ROLE_MANAGER')){
			 def projects=ProjectInfo.findAllWhere(user:currentUser)
				for (var in projects) {
					def tickets=TicketSummary.findAllWhere(project:var)
					for (ticket in tickets) {
						def ticketStatusUpdate=StatusUpdate.findAllWhere(ticket:ticket)
						for(statusUpdateObject in ticketStatusUpdate){
							allTicketsOfUser.add(statusUpdateObject	)
						}
					}
				
				}
		}else{
		allTicketsOfUser=StatusUpdate.findAllWhere(workdoneBy:currentUser.username)
		}
		def ticketList=[]
		for(ticketObject in allTicketsOfUser){
			def ticket=[:]
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
	
  //for loading the projectList of manager
	@Secured('permitAll')
	def getProjectList(){
		def manager=User.findWhere(username:params.id)
		def projectList=ProjectInfo.findAllWhere(user:manager)
		render  projectList as JSON
	}
   //for loading the project related to user
	@Secured('IS_AUTHENTICATED_FULLY')
	def getProjectListOfUser(){
		
		def projectListOfUser=[]
		try{
			def user=User.get(springSecurityService.principal.id)
			def role=springSecurityService.principal.authorities
			def projects	
			if(role.toString().contains("ROLE_MANAGER")){
				projects=ProjectInfo.findAllWhere(user:user)
			}else{

				projects=UserProjectMapping.findAllByUser_id(user.employeeId)
					}
					for (project in projects) {
						def projectInfo=ProjectInfo.findWhere(project_id:project.project_id)
								projectListOfUser.add(projectInfo)
					}
		render projectListOfUser as JSON
		
		}catch(Exception e){
			e.printStackTrace()
		}
	}
	
	//for getting the all the ticketIds respective to user id
	@Secured('IS_AUTHENTICATED_FULLY')
	def ticketIds(){
		def project
		def ticket
		def data=JSON.parse(request)
		def user=User.get(springSecurityService.principal.id)
		def ticketIds=[]
		if(data.projectId){
			project=ProjectInfo.findWhere(project_id:data.projectId)
		}

		
		/*def results = StatusUpdate.withCriteria {
			eq("user",user)
			projections { distinct("ticket") }
		}
		*/
		def results = StatusUpdate.withCriteria {
		
			ne("updatedStatus","Closed")
			projections { distinct("ticket") }
		}
		
		
		
		
		for (var in results) {

			if(project){
				ticket=TicketSummary.findWhere(ticket_id:var.ticket_id,project:project)
			}else {
				ticket=TicketSummary.findWhere(ticket_id:var.ticket_id)
			}

			if(ticket){
				def ticketInfo=StatusUpdate.findWhere(ticket:ticket,updatedStatus:"Closed")
				if(ticketInfo){

					println ticketInfo.ticket.ticket_id
					continue
				}else{

					ticketIds.add(var.ticket_id)
				}
			}else{
				continue
			}
		}
		println "ticketIds="+ticketIds
		render ticketIds as JSON
	}

	
	//for loading the lead List
	@Secured('permitAll')
	def getLeadList(){
		def roleId=Role.findWhere(authority:"ROLE_LEAD")
		def leadUser=UserRole.findAllWhere(role:roleId)
		def leadList=[]

		for (var in leadUser) {

			def user=User.get(var.user.id)
			leadList.add(user.username)
		}

		render leadList as JSON
		
	}
	//loading the list of tickets regarding to project
	@Secured('IS_AUTHENTICATED_FULLY')
	def	getTicketListOfProject(){
		
		def project=ProjectInfo.findWhere(projectName:params.id)
		def currentUser=User.get(springSecurityService.principal.id)
		String role=springSecurityService.principal.authorities
		def ticketSummaryList=TicketSummary.findAllWhere(project:project)
		
		def ticketList=[]
		for (var in ticketSummaryList) {		
		
			def ticketSummary=TicketSummary.findWhere(ticket_id:var.ticket_id)
			def StatusUpdateTicket
			if(role.contains('ROLE_MANAGER')){
				StatusUpdateTicket=StatusUpdate.findAllWhere(ticket:ticketSummary)
			}else{
				StatusUpdateTicket=StatusUpdate.findAllWhere(ticket:ticketSummary,workdoneBy:currentUser.username)
			}
		
				for (ticketObject in StatusUpdateTicket) {
				def ticket=[:]
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
	
	//for loading the resources regarding the project
	@Secured('IS_AUTHENTICATED_FULLY')
	def getResourcesList(){
		println "getResourcesList project id="+ params.id
		def project=ProjectInfo.findWhere(projectName:params.id)
		def currentUser=User.get(springSecurityService.principal.id)
		String role=springSecurityService.principal.authorities
		def resourceList=[]
		if(role.contains('ROLE_LEAD')){
			def resourceUnderLead=UserManager.findAllWhere(lead_id:currentUser.employeeId)
			
			for (var in resourceUnderLead) {
				println var.employee_id
				def user=User.findWhere(employeeId:var.employee_id)
				println user.username
				resourceList.add(user.username)
			}
			
		}
		
		if(role.contains('ROLE_MANAGER')){
				
			//def resourceUnderManager=UserManager.findAllWhere(manager_id:currentUser.employeeId)
			def resourceUnderProject=UserProjectMapping.findAllWhere(project_id:project.project_id)
			for (var in resourceUnderProject) {
				println var.user_id
				def user=User.findWhere(employeeId:var.user_id)
				println user.username
				resourceList.add(user.username)
			}
		
		}else{}
		render resourceList as JSON
	}
	
	//for loading the tickets on the basis of resources
	@Secured('IS_AUTHENTICATED_FULLY')
	def getTicktetsOnBasisOfResources(){
		
		def data=JSON.parse(request)
		//def project=ProjectInfo.findWhere(projectName:data.projectName)
		//def ticketSummaryList=TicketSummary.findAllWhere(project:project)
		def ticketList=[]
		//for (var in ticketSummaryList) {
		
			//def ticketSummary=TicketSummary.findWhere(ticket_id:var.ticket_id)
			def StatusUpdateTicket=StatusUpdate.findAllWhere(workdoneBy:data.resourceName)
			for (ticketObject in StatusUpdateTicket) {
				def ticket=[:]
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
		//}
			render ticketList as JSON
	}
	
	//for all tickets history irrespective of date and user
	@Secured('IS_AUTHENTICATED_FULLY')
	def getAllTicketsOfDate(){
		
		def data=JSON.parse(request)
		def df = new SimpleDateFormat("dd/MM/yyyy");
		
		
		def user=User.get(springSecurityService.principal.id)
		def role=springSecurityService.principal.authorities
		println "role="+role
		def ticketList=[]
		
		
		
		
		if(data.todaysDate){
			Date creationDate=df.parse(data.todaysDate)
			def formatedDate=df.format(creationDate)
			Date today=df.parse(formatedDate)
					
			if(data.endDate){
				Date endDate=df.parse(data.endDate)
				def formatDate=df.format(endDate)
				Date end=df.parse(formatDate)
		
		
		if(role.toString().contains("ROLE_LEAD")){
			
			def c= StatusUpdate.createCriteria()
				def allTicketsOfUser=c.list{
					between("updateDate",today,end)
					'in'('user', user)
				}
				for (var in allTicketsOfUser) {
				if(allTicketsOfUser){
					println"Workdone=" +var.workdoneBy
					def ticket=[:]
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
	 // flash.message="No records found"
	render ticketList as JSON
	 }
	}
}
	}
	
	
	//for getting ticket info respective with ticketId
	@Secured('IS_AUTHENTICATED_FULLY')
	def getTicketSelector(){
		
		def user=User.get(springSecurityService.principal.id)
		def ticketList=[]
		def ticketInfo=TicketSummary.findWhere(ticket_id:params.id) //(,user:user)fetching the data on basis of ticket id and user from ticketSummary table
		def allTicketsOfUser=StatusUpdate.findAllWhere(ticket:ticketInfo)  //(,,user:user)fetching the data on basis of ticket id and user from StatusUpdate table
		def c = StatusUpdate.createCriteria()
		def results = c.list {
			
				eq("ticket", ticketInfo)
				and {
					eq("user",user)
				}
			
			//order("updateDate", "desc")
		}
			for (var in allTicketsOfUser) {
			if(allTicketsOfUser){
				println"Workdone=" +var.workdoneBy
				def ticket=[:]
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

