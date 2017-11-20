package StatusPortal

import SecureApp.User

class StatusUpdate {

	String impediments
	//float prvsWorkHrs
	String todaysWorkHrs
	String workDoneForToday
	String workdoneBy
	Date updateDate
	String updatedStatus
	
	
	
	static belongsTo =[ticket:TicketSummary,user:User]
	
	static constraints = {
		
	}
	
	static mapping = {
		
		version false
		
	}
}
