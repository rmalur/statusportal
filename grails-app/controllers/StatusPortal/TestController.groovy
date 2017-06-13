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


	@Secured('IS_AUTHENTICATED_FULLY')
	def addProjectInfo(){

		println "addProjectInfo method"

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
			flash.message="project successfully saved!"
			render controller:'test', action:'createProject'
		}catch(Exception e){

			e.printStackTrace()
			flash.message="project not saved!"
			render controller:'test', action:'createProject'

		}
	}

	
	@Secured('permitAll')
	def createUser(){

	}

	
@Secured('permitAll')
	def saveUser(){
		def normalUser = new Role(authority: 'ROLE_NORMAL').save(flush: true)
		def data=JSON.parse(request)
	//	println data
		def multipleroject= data.project
		def flag=false
		def flagList=[]
		try{
			def newUser=new User()
			newUser.username=data.employeeName
			newUser.password=data.password
			newUser.employeeId=data.employeeId
			newUser.employeeEmailId=data.employeeEmailId
			if(newUser.save(flush:true,failOnError:true))
				{
					
					for (project in multipleroject) {
						println "project="+project.project_id
			
						def userProjectMap=new UserProjectMapping()
						userProjectMap.user_id=data.employeeId
						userProjectMap.project_id=project.project_id
						userProjectMap.save(flush:true,failOnError:true)
				}
			}
				
			flag=true
			flagList.add(flag)
			flagList as JSON
			return flagList as JSON
		}catch(Exception e){

			flag=false
			flagList.add(flag)
			render flagList as JSON
		}
	}


	@Secured('permitAll')
	def getProjectList(){
		println params
		def manager=User.findWhere(username:params.id)
		def projectList=ProjectInfo.findAllWhere(user:manager)

		render  projectList as JSON
	}

	@Secured('IS_AUTHENTICATED_FULLY')
	def getProjectListOfUser(){
		
		println "getProjectListOfUser"
		try{
		def user=User.get(springSecurityService.principal.id)
		def projects=UserProjectMapping.findAllByUser_id(user.employeeId)
		def projectListOfUser=[];
		for (project in projects) {
			def projectInfo=ProjectInfo.findWhere(project_id:project.project_id)
			projectListOfUser.add(projectInfo)
		}
		println "project List="+projectListOfUser
		render projectListOfUser as JSON
		}catch(Exception e){
			e.printStackTrace()
		}
	}
}
