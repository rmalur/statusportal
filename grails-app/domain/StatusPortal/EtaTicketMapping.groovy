package StatusPortal

class EtaTicketMapping {

	String eta
	
	static belongsTo =[ticket:TicketSummary]
	
    static constraints = {
		eta blank:false,nullable:false
    }
	
	static mapping = {
		
		version false
		
	}
}