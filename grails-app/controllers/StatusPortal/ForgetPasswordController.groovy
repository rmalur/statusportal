package StatusPortal
import grails.converters.JSON
import grails.plugin.mail.MailService
import grails.plugin.springsecurity.annotation.Secured

import org.apache.commons.lang.RandomStringUtils
import org.apache.commons.lang.math.RandomUtils
import org.apache.commons.logging.LogFactory 

import SecureApp.User


class ForgetPasswordController {
	private static final log  =  LogFactory.getLog(this)
	MailService mailService
	def springSecurityService
	
	@Secured('permitAll')
	def forgetPassword(){
			def data  =  JSON.parse(request)
			
			def forgetPasswordFlag  =  []
			def sentMailFlag  =  false
		try{
			def user  =  User.findWhere(employeeEmailId:data.emailId)
			String charset  =  (('A'..'Z') + ('0'..'9')).join()
			Integer length  =  9
			String randomString  =  RandomStringUtils.random(length, charset.toCharArray())
			if(user!= null) {
				user.password  =  randomString
				user.save(flush:true,failOnError:true)
				mailService.sendMail {
					from "statusportal@evolvingsols.com"
					to  user.employeeEmailId
					subject "Password Change"
					body 'Your New password is '+randomString
				}
				sentMailFlag  =  true
				forgetPasswordFlag.add(sentMailFlag)
				
			}else{
			sentMailFlag  =  false
			forgetPasswordFlag.add(sentMailFlag)
			println data.emailId
			log.info("user not found with enail id = "+data.emailId);
			}
		}catch(Exception e){
			sentMailFlag  =  false
			forgetPasswordFlag.add(sentMailFlag)
			log.error(e.message) 
			
		}
		render forgetPasswordFlag as JSON
	}
	
	@Secured('permitAll')
	def changePassword(){
		def passwordChangedFlag  =  []
		def saveFlag  =  false;
		def data = JSON.parse(request)
		try{
		def user = User.get(springSecurityService.principal.id)
		user.password = data.password.newPassword
		if(user.save(flush:true,failOnError:true)){
			mailService.sendMail {
				from "statusportal@evolvingsols.com"
				to  user.employeeEmailId
				subject "Password  Change"
				body 'You have changed your password,please use new password for login.'
			}

			saveFlag = 1;
			passwordChangedFlag.add(saveFlag)
		}else{
			saveFlag = 0;
			passwordChangedFlag.add(saveFlag)
		}
		
		}catch(Exception e){
		saveFlag = 0;
		passwordChangedFlag.add(saveFlag)
		log.error(e.message)
		}
		render passwordChangedFlag as JSON
	}
	
	
}
