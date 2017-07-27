package StatusPortal

import SecureApp.User

class ProjectInfo {

	
		
	String project_id
	String projectName
	String projectStartDate
	
	static belongsTo = [user:User,methodology:DevelopementMethodology]
	
	static mapping = {
		
		id generator: 'assigned', name: 'project_id'
		user column:'Manager_Id'
		methodology column:'developement_methodology'
		version false
	}
		
}
