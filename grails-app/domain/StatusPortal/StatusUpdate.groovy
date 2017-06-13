package StatusPortal

import SecureApp.User

class StatusUpdate {

	String impediments
	//float prvsWorkHrs
	float todaysWorkHrs
	String workDoneForToday
	String workdoneBy
	String updateDate
	String updatedStatus
	
	
	
	static belongsTo =[ticket:TicketSummary,user:User]
	
    static constraints = {
    }
	
	static mapping = {
		
		version false
		
	}
}
