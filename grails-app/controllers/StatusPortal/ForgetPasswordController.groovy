package StatusPortal
import grails.converters.JSON
import grails.plugin.mail.MailService
import grails.plugin.springsecurity.annotation.Secured

import org.apache.commons.lang.RandomStringUtils
import org.apache.commons.lang.math.RandomUtils

import SecureApp.User


class ForgetPasswordController {
	MailService mailService
	def springSecurityService
	@Secured('permitAll')
	def forgetPassword(){
		def data=JSON.parse(request)
		println data.emailId
		def user=User.findWhere(employeeEmailId:data.emailId)



		def flag=[]
		def flag1=false
		try{

			String charset = (('A'..'Z') + ('0'..'9')).join()
			Integer length = 9
			String randomString = RandomStringUtils.random(length, charset.toCharArray())
			println randomString

			if(user!=null) {
				user.password=randomString
				user.save(flush:true,failOnError:true)
				
				mailService.sendMail {
					from "statusportal@evolvingsols.com"
					to  user.employeeEmailId
					subject "Password  Change"
					body 'Your New password is '+randomString
				}
				flag1=true
				flag.add(flag1)
				render flag as JSON
			}
		}catch(Exception e){
			flag1=false
			flag.add(flag1)
			e.printStackTrace()
			render flag as JSON
		}
	}
	
	@Secured('permitAll')
	def changePassword(){
		def flag=[]
		def saveFlag=false;
		def data=JSON.parse(request)
		println data.password
			def user=User.get(springSecurityService.principal.id)
					user.password=data.password.newPassword
					if(user.save(flush:true,failOnError:true)){
						mailService.sendMail {
							from "statusportal@evolvingsols.com"
							to  user.employeeEmailId
							subject "Password  Change"
							body 'Your New password is '+data.password.newPassword
						}
					
					saveFlag=1;
					flag.add(saveFlag)
					
				}else{
					saveFlag=2;
					flag.add(saveFlag)
					
				}	
				render flag as JSON
		
	}
	
	
}
