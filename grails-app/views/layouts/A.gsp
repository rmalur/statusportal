<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Status Portal</title>

 <link	href="${request.contextPath}/startbootstrap-bare-gh-pages/css/bootstrap.min.css"	rel="stylesheet">
<!-- Bootstrap Core CSS -->
<link	href="${request.contextPath}/startbootstrap-bare-gh-pages/css/datepicker.css" rel="stylesheet"><%--

for loading border in DSR--%>



<!-- Script loaded form controller.js file -->

<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.6.1/angular.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.6.1/angular-animate.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.6.1/angular-sanitize.js"></script>
    <script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-2.5.0.js"></script>
  

<script	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<%--<script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-1.3.3.js"></script> **************duplicate******************
--%><script type="text/javascript"	src="${request.contextPath}/startbootstrap-bare-gh-pages/js/controller/myController.js"></script>
<script type="text/javascript"	src="${request.contextPath}/startbootstrap-bare-gh-pages/js/controller/newEntityCreationController.js"></script>
<script type="text/javascript"	src="${request.contextPath}/startbootstrap-bare-gh-pages/js/controller/changePasswordController.js"></script>
<script type="text/javascript"	src="${request.contextPath}/startbootstrap-bare-gh-pages/js/controller/changePasswordResultController.js"></script>
<script type="text/javascript"	src="${request.contextPath}/startbootstrap-bare-gh-pages/js/controller/ticketController.js"></script>

<script	src="${request.contextPath}/startbootstrap-bare-gh-pages/js/jquery-1.11.2.min.js"></script>
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<script	src="${request.contextPath}/startbootstrap-bare-gh-pages/js/bootstrap-datepicker.js"></script>
<script	src="${request.contextPath}/startbootstrap-bare-gh-pages/js/moment.min.js"></script>
<link rel="stylesheet"	href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css" />
<%--<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script> **************duplicate******************
--%>
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
 
<link rel="stylesheet"	href="https://code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css" />
<style>
body {
	padding-top: 70px;
	/* Required padding for .navbar-fixed-top. Remove if using .navbar-static-top. Change if height of navigation changes. */
}
</style>
<r:require module="export"/>
<export:resource/>
</head>

<body ng-app="myApp" >


<div class="navbar navbar-fixed-top navbar-inverse" role="navigation">
  <div class="container">
   <div class="navbar-header">
    	<a class="navbar-brand" href="${createLink(controller:'logout')}">Status Update</a>
   </div>
   <div class="collapse navbar-collapse" id="b-menu-1">
    <ul class="nav navbar-nav navbar-left">
     <li><g:link controller="StatusPortal" action="todaysTickets">Status Update</g:link></li>
     <li><g:link controller="StatusPortal" action="getAllTicketsOfUser">Status History</g:link></li>
     
     
      <li class="dropdown"><a data-toggle="dropdown" href="#">
        <sec:ifLoggedIn>
         <sec:username />
        </sec:ifLoggedIn> <span class="caret"></span>
      </a>
       <ul class="dropdown-menu" ng-controller="myController" >
        <sec:ifAnyGranted roles="ROLE_MANAGER">
        	<li><g:link controller="ticketData" action="createProject">
           <i class="fa fa-picture-o"></i>Create Project</g:link></li>
        </sec:ifAnyGranted>
        <sec:ifAnyGranted roles="ROLE_LEAD,ROLE_MANAGER">
         <li><g:link  controller="ticketData"	action="createUser">
           <i class="fa fa-picture-o"></i>Add Resource</g:link></li>
        </sec:ifAnyGranted>
        <li><a href="#" ng-click="changePassword()">Change
          password</a></li>
        <li><g:link controller="logout">Logout</g:link></li>
       </ul></li>
     
    </ul>
   </div>
   <!-- /.nav-collapse -->
  </div>
  <!-- /.container -->
 </div>
 <!-- /.navbar -->
	<div>
		<br>
	</div>
	
	<div class="form-group" >
		<g:layoutBody />
	</div>


	<script type="text/ng-template" id="changePassword.html">


      <div class="modal-header">
			<div ng-show="passwordFlag"	>
					<div class="alert alert-danger">
		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>				
	<label>Info:</label>
        			New password value and Confirm password value does not match
				
          </div>
			</div>
        <h3 class="modal-title">Change your password</h3>
      </div>
      <div class="modal-body">
        <form ng-submit="ok()">
          <div class="input-group animated fadeOut">
			<label class="control-label">Current Password:</label>
           	 <input type="password" class="form-control finderBar" ng-model="currentPassword" placeholder="Current Password..." autofocus>
			</br>
			<label class="control-label">New Password:</label>
        	<input type="password" class="form-control finderBar" ng-model="newPassword" placeholder="New Password..." autofocus>    
			</br>
			<label class="control-label">Confirm Password:</label>
        	<input type="password" class="form-control finderBar" ng-model="confirmPassword" placeholder="Confirm Password..." autofocus>
		
          </div>
        </form>

      </div>
      <div class="modal-footer">
        <button class="btn btn-primary" type="button" ng-click="ok()">OK</button>
        <button class="btn btn-warning" type="button" ng-click="cancel()">Cancel</button>
      </div>
    </script>
    
    <script type="text/ng-template" id="result.html">


      <div class="modal-header" ng-init="init()">
        <h3 class="modal-title">Info</h3>
      </div>
      <div class="modal-body">
        <form ng-submit="ok()">
          <div class="input-group animated fadeOut">
			<label class="control-label">Info:</label>
        		{{message}}
          </div>
        </form>

      </div>
      <div class="modal-footer">
        <button class="btn btn-primary" type="button" ng-click="ok()">OK</button>
  
      </div>
    </script>
    
  
<div class="wrapper" >
		<footer class="footer">
		
			<div id="content">
				 <img align="middle"  src="/StatusPortal/images/cybage-logo.png">
		</div>

		</footer>

	</div>

</body>



</html>
