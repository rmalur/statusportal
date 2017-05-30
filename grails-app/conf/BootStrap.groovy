import SecureApp.Role
import SecureApp.User
import SecureApp.UserRole

class BootStrap {

    def init = { servletContext ->
		def leadRole = new Role(authority: 'ROLE_LEAD').save(flush: true)
		def managerRole = new Role(authority: 'ROLE_MANAGER').save(flush: true)
		def normalUser = new Role(authority: 'ROLE_NORMAL').save(flush: true)
		
		//project users
		def testLeadUser = new User(username: 'lead', password: 'lead')
		
		def managerUser1=new User(username:'ravindran',password:'ravindran')
		
		testLeadUser.save(flush: true)
		
		//project users
	
		managerUser1.save(flush:true)
		
		
		
		
		UserRole.create testLeadUser,leadRole,true
		
		//project users
		
		UserRole.create managerUser1,managerRole,true
			
		
    }
    def destroy = {
    }
}
