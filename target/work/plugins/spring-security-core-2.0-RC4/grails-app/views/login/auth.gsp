<%@ page import="grails.util.Holders; grails.plugin.springsecurity.SpringSecurityUtils; grails.plugin.springsecurity.web.SecurityRequestHolder" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name='layout' content='main'/>
    <meta name='session-keepalive' content='false'/>
    <title>Contributor Desk - Login</title>
</head>
<body>
<br/>
<div class="container" ng-app="login" ng-cloak >
    <div class="row">
        <div ng-controller="LoginController" ng-init="init()" class="panel panel-default">
            <div class="panel-heading">
               <b> <h3 class="panel-title">Status Portal Login</h3></b>
            </div>
            <div class="panel-body">
                    <g:if test='${flash.message}'>
                        <div class="alert alert-danger">${flash.message}</div>
                    </g:if>
                    <g:elseif test="${params.login_error == '1'}">
                        <div class="alert alert-danger">Sorry, we were not able to find a user with that username and password.</div>
                    </g:elseif>
                    <form action="${postUrl}" method="POST" class="form-horizontal" id="loginForm" role="form" autocomplete="on">
                        <div id="loginFields" ng-hide="showPasswordView">
                            <div class="form-group">
                                <label for="email" class="control-label col-sm-2">Username:</label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" name="j_username" id="email" value="${session.getAttribute(SpringSecurityUtils.SPRING_SECURITY_LAST_USERNAME_KEY)}" autocapitalize="off"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="password" class="control-label col-sm-2">Password:</label>
                                <div class="col-sm-8">
                                    <input type="password" class='form-control' name="j_password" id="password"/>
                                    	<label for="remember_me">
                                        	<input type="checkbox" class="chk" name="${rememberMeParameter}" id="remember_me" <g:if test='${hasCookie}'>checked="checked"</g:if>/>
                                        	<g:message code="springSecurity.login.remember.me.label"/>
                                    	</label>
                                    <div class="help-text">
                                    	<a href="" ng-click="showPasswordView = !showPasswordView">Forgot password?</a>
                                    </div>
                                </div>
                            </div>

                            <div  class="form-group" id="remember_me_holder">
                                <div class="col-sm-offset-2">
                                    
                                </div>
                            </div>

                            <div class="col-sm-offset-2">
                                <button type="submit" class="btn btn-primary">Login</button>
                                <g:link  controller="test"	action="createUser">
										<input type="button" class="btn btn-primary" value="New User" >
								</g:link>
                            </div>
                             
                        </div>
                        <div id="forgotPassword" ng-show="showPasswordView">
                            <div ng-hide="requestSucceeded">
                            <div class="form-group">
                                
                                <label class="control-label col-sm-2">Email:</label>
                                <div class="col-sm-8">
                                    <g:textField ng-model="email" name="email" placeholder="Enter the e-mail address for your account" class='form-control'/>
                                   
                                    
                                   <div class="col-sm-offset-2" >
                                			<a href=""  ng-click="processIssueRequest()" class="btn btn-primary btn-sm">Continue</a>
                                			<a href="" class="btn btn-sm" ng-click="showPasswordView = !showPasswordView">Cancel</a>
                            		</div>
                                </div>
                            </div>
                            </div>
                            
                            <div ng-show="requestSucceeded">
                                <p>
                                    <span >
                                      {{message}} <a href="/StatusPortal/login/auth">Sign in</a>
                                    </span>
                                    
                                </p>
                            </div>
                            
                        </div>

                    </form>
            </div>
           

        </div>
    </div>
</div>

<script type="text/javascript" language="JavaScript">
	var login = angular.module("login",[]);
	login.controller('LoginController', function($scope, $http) {

        $scope.showPasswordView = false;
        $scope.email = "";
        $scope.message = ""
        
        $scope.requestSucceeded = false;
        

        $scope.init =function(){
            $('#email').each(function(i, o){
                o.focus();
            })
        }

        $scope.processIssueRequest=function(){
            
				console.log($scope.email);
				
				var emailId=$scope.email;
				
				$http({
					method: "POST",
				    url: "/StatusPortal/ForgetPassword/forgetPassword",
				    data: {emailId}
				}).then(function (response) {
					   $scope.requestSucceeded = true
				       if(response.data[0]==true){
			     
			        $scope.message=" We have sent you a new password.<br/> Please check your email account and login again with new password "
				       }else{
				    	   $scope.message=" Sorry we are unable to find user with this mail id Please check your mail "
					       }

				    },function(response){
			    	
				    	$scope.requestSucceeded = false
			    	
			    });
					
            }

    });
</script>

</body>

</html>