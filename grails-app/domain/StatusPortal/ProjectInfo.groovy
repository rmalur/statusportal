package StatusPortal

import SecureApp.User

class ProjectInfo {

	
	String project_id
	String projectName
	String projectStartDate
	
	static belongsTo = [user:User]
	
	static mapping = {
		
		id generator: 'assigned', name: 'project_id'
		user column:'Manager_Id'
		version false
		
	}
	
}
