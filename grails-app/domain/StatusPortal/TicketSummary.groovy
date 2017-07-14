package StatusPortal

import SecureApp.User

class TicketSummary {

	String summary
	String assignee
	String status
	Date creationDate
	String ticket_id
	
    static belongsTo =[user:User,project:ProjectInfo]
	//static hasMany = [ticketStatus:StatusUpdate] 
	
	static constraints = {
		summary blank:false,nullable:false
		assignee blank:false,nullable:false
		status inList:['Closed','In progress','Open']
		creationDate  blank:false,nullable:false
		ticket_id unique: true,nullable:false,blank:false
	}
	
	static mapping = {
		
		id generator: 'assigned', name: 'ticket_id'
		version false
		
	}
}
