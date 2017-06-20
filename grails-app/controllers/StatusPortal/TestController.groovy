package StatusPortal

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import SecureApp.Role
import SecureApp.User
import SecureApp.UserRole

class TestController {
	def springSecurityService
	@Secured('IS_AUTHENTICATED_FULLY')
	def testIndex(){
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

		println "addProjectInfo method"
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
		println data
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
						println "project="+project.project_id
			
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
							
							//userManagerMapping.save(flush:true,failOnError:true)
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

//for loading the projectList of manager
	@Secured('permitAll')
	def getProjectList(){
		println params
		def manager=User.findWhere(username:params.id)
		def projectList=ProjectInfo.findAllWhere(user:manager)
		
		render  projectList as JSON
	}
//for loading the project related to user
	@Secured('IS_AUTHENTICATED_FULLY')
	def getProjectListOfUser(){
		
		println "getProjectListOfUser"
		def projectListOfUser=[]
		try{
			def user=User.get(springSecurityService.principal.id)
					def projects=UserProjectMapping.findAllByUser_id(user.employeeId)
		
					for (project in projects) {
						def projectInfo=ProjectInfo.findWhere(project_id:project.project_id)
								projectListOfUser.add(projectInfo)
					}
		render projectListOfUser as JSON
		
		}catch(Exception e){
			e.printStackTrace()
		}
	}
	
	//for loading the project related to user
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
}
