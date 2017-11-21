package StatusPortal

class Reason {

	int reason_id
	String reasonName
	
    static constraints = {
		
    }
	
	static mapping = {
		
		version false
		id generator: 'assigned', name: 'reason_id'
		
	}
}