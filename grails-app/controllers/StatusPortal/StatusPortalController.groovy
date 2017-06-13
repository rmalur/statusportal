package StatusPortal

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import groovy.json.JsonBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat
import java.util.Calendar;

import SecureApp.User;

class StatusPortalController {

	def springSecurityService
	@Secured('IS_AUTHENTICATED_FULLY')
	def index() {

	}
	
	@Secured('IS_AUTHENTICATED_FULLY')
	def assigneeList(){
		
		
				def c = User.createCriteria()
				def assigneeList = c.list  {
					projections {                    //projection does the trick
						property('username') }
				}
				
			
			render	assigneeList as JSON
				
		
	}
	
	@Secured('IS_AUTHENTICATED_FULLY')
	def workdoneByList(){
		
		
				def w = User.createCriteria()
				def workdoneByList = w.list  {
					projections {                  
						property('username') }
				}
				
				println "workdonelist= "+workdoneByList
			render	workdoneByList as JSON
				
		
	}


	//for getting the all the ticketIds irrespective to user id
	@Secured('IS_AUTHENTICATED_FULLY')
	def ticketIds(){
		def user=User.get(springSecurityService.principal.id)
		def ticketIds = TicketSummary.createCriteria ()
		def ticketList = ticketIds.list  {
			eq("user",user)
			projections {                     property('ticket_id') }
		}

		println ("ticketIds"+ticketList)
		render ticketList as JSON
	}

	//for getting tickets info
	@Secured('IS_AUTHENTICATED_FULLY')
	def getTicketInfo(){
		println "getTicketInfo"
		def user=User.get(springSecurityService.principal.id)
		def ticketInfo=TicketSummary.findWhere(ticket_id:params.id,user:user)
		render ticketInfo as JSON

	}

	//for updating the status of ticket on the basis of ticket id if ticket is new then save it as it is
	//if ticket is not new then update the status it

	//for todays tickets only
	@Secured('IS_AUTHENTICATED_FULLY')
	def todaysTickets(){

		try{
println "todaysTickets"
			def userLogedIn=User.get(springSecurityService.principal.id)
			Date myDate = new Date();
			def res=new SimpleDateFormat("dd/MM/YYYY").format(myDate);
			
			def results=TicketSummary.findAllWhere(creationDate:res,user:userLogedIn)
			
			if(results){
				[results:results]
			}else{
				flash.message="No record found for todays date"
				redirect controller:'StatusPortal',action:'index'
			}
		}catch(Exception e){

			flash.message="Some Error is occured"
			redirect controller:'StatusPortal',action:'index'
		}
	}

	//for update the ticket which is updated by assignee
@Secured('IS_AUTHENTICATED_FULLY')
	def updateTicketStatus(){


		//println "updateTicketStatus for="+springSecurityService.principal.id
		try{

			def userLogedIn=User.get(springSecurityService.principal.id)
			println("userName="+userLogedIn.username)
			def ticketInfo=TicketSummary.findWhere(ticket_id:params.id)
			if(ticketInfo){
				[ticketInfo:ticketInfo]
			}else{
				flash.message="Some error is occured while fetching the data"

			}

		}catch(Exception e){
			flash.message="User is not autherised to update this ticket"
			log.error(e.message)
			redirect controller:'StatusPortal',action:'todaysTickets'
		}
	}


	//for updating the ticket related to user
	@Secured('IS_AUTHENTICATED_FULLY')
	def updateTodaysTicket(){

		def flag=false
		try{

			println "updateStatusTicket method"
			def data=JSON.parse(request)
			println data
			

			Date myDate = new Date();
			def todaysDate=new SimpleDateFormat("dd/MM/YYYY").format(myDate);
			def currentUser=User.get(springSecurityService.principal.id)
			println "currentUser="+currentUser

			//def presentTicket=StatusUpdate.findAllWhere(user:currentUser,updateDate:todaysDate,ticket_id:data.ticketData.ticket_id)
			def ticketSummaryObject=TicketSummary.findByTicket_id(data.ticketData.ticket_id)
			def presentTicket=StatusUpdate.findWhere(user:currentUser,updateDate:todaysDate,ticket:ticketSummaryObject)

			if(presentTicket){

				if(Float.valueOf(data.ticketData.todaysWorkHrs)==0){
					flash.message="todays work hrs should not be enmpty"
					presentTicket.todaysWorkHrs=presentTicket.todaysWorkHrs
					flag=true

				}
				presentTicket.todaysWorkHrs=Float.valueOf(data.ticketData.todaysWorkHrs)
				//presentTicket.workDoneForToday=data.ticketData.workDone

				presentTicket.save(flush:true,failOnError:true)
			}else{



				StatusUpdate newTicketStatus = new StatusUpdate()

				if(ticketSummaryObject){
					TicketSummary tSummary=TicketSummary.findByTicket_id(data.ticketData.ticket_id)
					
					newTicketStatus.ticket = tSummary
					flag=true
				}else{

					TicketSummary newTicketSummary=new TicketSummary()
					newTicketSummary.user=currentUser
					newTicketSummary.ticket_id=data.ticketData.ticket_id
					newTicketSummary.summary=data.ticketData.summary
					newTicketSummary.assignee=data.ticketData.assignee
					
					newTicketSummary.status=data.ticketData.status
					newTicketSummary.creationDate=data.ticketData.creationDate
					newTicketSummary.project=ProjectInfo.findWhere(project_id:"TST")
					
					newTicketSummary.save(flush:true,failOnError:true)
					newTicketStatus.ticket=TicketSummary.findByTicket_id(newTicketSummary.ticket_id)
				}
						
					newTicketStatus.user=currentUser
					newTicketStatus.WorkdoneBy=data.ticketData.WorkdoneBy
					newTicketStatus.todaysWorkHrs=Float.valueOf(data.ticketData.todaysWorkHrs)
					newTicketStatus.updatedStatus=data.ticketData.status
					newTicketStatus.updateDate=data.ticketData.creationDate
					newTicketStatus.workDoneForToday=data.ticketData.todayswork
					newTicketStatus.impediments=data.ticketData.impediments

					newTicketStatus.save(flush:true,failOnerror:true)
					

					flag=true
					render flag
			}

		}catch(Exception e){
			log.error(e.message)
			flag=false
			 flash.message="Something went wrong"
			render flag

		}



	}
	
	//for all tickets history irespective of the user 
	@Secured('IS_AUTHENTICATED_FULLY')
	def getAllTicketsOfUser(){
		
		def currentUser=User.get(springSecurityService.principal.id)
		def allTicketsOfUser=StatusUpdate.findAllWhere(user:currentUser)
		if(allTicketsOfUser){
		 [hist:allTicketsOfUser]
		 }else{
		  flash.message="No records found"
		 
		 }
	}

	
	@Secured('IS_AUTHENTICATED_FULLY')
	def getAllTicketHistory(){
		
/*		def data=JSON.parse(request)
		
		def startDate=data.startDate
		def endDate=data.endDate
		
		def allTicketList=StatusUpdate.createCriteria()
		def results=allTicketList.list {
			between('updateDate',startDate,endDate)
			
		}
		results.each {
			println results.ticket.ticket_id
			
		}	
	 
*/
		
			def allTickets=StatusUpdate.findAll();
			
			println allTickets
			[allTickets:allTickets]
		
		
		
	}


	
}
