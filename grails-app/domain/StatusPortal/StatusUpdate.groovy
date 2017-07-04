package StatusPortal

import SecureApp.User

class StatusUpdate {

	String impediments
	
	float todaysWorkHrs
	String workDoneForToday
	String workdoneBy
	String updateDate
	String updatedStatus
	String workDoneBy
	
	
	static belongsTo =[ticket:TicketSummary,user:User]
	
    static constraints = {
    }
	
	static mapping = {
		
		version false
		
	}
}
