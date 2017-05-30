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
		def testManagerUser = new User(username: 'manager', password: 'manager')
		def testNormalUser=new User(username:'normaluser',password:'normaluser')
		def leadUser1=new User(username:'abc',password:'abc')
		def managerUser1=new User(username:'ravindran',password:'ravindran')
		def normalUser1=new User(username:'veerendra',password:'veerendra')
		def normalUser2=new User(username:'nuthan',password:'nuthan')
		def normalUser3=new User(username:'shilpa',password:'shilpa')
		def normalUser4=new User(username:'ankita',password:'ankita')
		def normalUser5=new User(username:'nikhil',password:'nikhil')
		def normalUser6=new User(username:'rishabh',password:'rishabh')
		
		testLeadUser.save(flush: true)
		testManagerUser.save(flush: true)
		testNormalUser.save(flush: true)
		//project users
		leadUser1.save(flush:true)
		managerUser1.save(flush:true)
		normalUser1.save(flush:true)
		normalUser2.save(flush:true)
		normalUser3.save(flush:true)
		normalUser4.save(flush:true)
		normalUser5.save(flush:true)
		normalUser6.save(flush:true)
		
		
		
		UserRole.create testLeadUser,leadRole,true
		UserRole.create testManagerUser,managerRole,true
		UserRole.create testNormalUser,normalUser,true
		//project users
		UserRole.create leadUser1,leadRole,true
		UserRole.create managerUser1,managerRole,true
		UserRole.create normalUser1,normalUser,true
		UserRole.create normalUser2,normalUser,true
		UserRole.create normalUser3,normalUser,true
		UserRole.create normalUser4,normalUser,true
		UserRole.create normalUser5,normalUser,true
		UserRole.create normalUser6,normalUser,true
		
		
		
    }
    def destroy = {
    }
}
