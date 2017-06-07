<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Status Update Portal</title>

<!-- Bootstrap Core CSS -->
<link	href="${request.contextPath}/startbootstrap-bare-gh-pages/css/bootstrap.min.css"	rel="stylesheet">
<link	href="${request.contextPath}/startbootstrap-bare-gh-pages/css/datepicker.css"
	rel="stylesheet">

<!-- Script loaded form controller.js file -->


<script	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-1.3.3.js"></script>
<script type="text/javascript"	src="${request.contextPath}/startbootstrap-bare-gh-pages/js/controller/myController.js"></script>
<script type="text/javascript"	src="${request.contextPath}/startbootstrap-bare-gh-pages/js/controller/testController.js"></script>
<script type="text/javascript"	src="${request.contextPath}/startbootstrap-bare-gh-pages/js/controller/changePasswordController.js"></script>
<script type="text/javascript"	src="${request.contextPath}/startbootstrap-bare-gh-pages/js/controller/changePasswordResultController.js"></script>
<script	src="${request.contextPath}/startbootstrap-bare-gh-pages/js/jquery-1.11.2.min.js"></script>
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<script	src="${request.contextPath}/startbootstrap-bare-gh-pages/js/bootstrap-datepicker.js"></script>
<script	src="${request.contextPath}/startbootstrap-bare-gh-pages/js/moment.min.js"></script>
<link rel="stylesheet"	href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css" />
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
 
<link rel="stylesheet"	href="https://code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css" />
<style>
body {
	padding-top: 70px;
	/* Required padding for .navbar-fixed-top. Remove if using .navbar-static-top. Change if height of navigation changes. */
}
</style>

</head>

<body ng-app="myApp" ng-controller="myController">
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
	</nav>
	<!-- Navigation -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<g:link class="navbar-brand" controller="StatusPortal" action="index">
				<h2>Status Update Portal</h2>
			</g:link>
		</div>
		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1" style="padding-top: 15px">
			<ul class="nav navbar-nav">
				<li><g:link controller="StatusPortal" action="todaysTickets">Today</g:link></li>
				<li><g:link controller="StatusPortal" action="getAllTicketsOfUser">Previous</g:link></li>
				<li><g:link controller="StatusPortal" action="getAllTicketHistory">Download</g:link></li>
			</ul>
			
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a data-toggle="dropdown" href="#">
						<sec:ifLoggedIn><sec:username /></sec:ifLoggedIn>
						 <span class="caret"></span>
					</a>
        			<ul class="dropdown-menu" ng-controller="myController">
        				 <sec:ifAnyGranted roles="ROLE_LEAD">
                              <li><g:link controller="Test" action="createProject"><i class="fa fa-picture-o"></i>Create Project</g:link></li>
                          </sec:ifAnyGranted>
        				<sec:ifAnyGranted roles="ROLE_LEAD,ROLE_MANAGER">
                              <li><g:link controller="Test" action="addEmployee"><i class="fa fa-picture-o"></i>Add Resource</g:link></li>
                          </sec:ifAnyGranted>	
                          	<li><a href="#"  ng-click="changePassword()" >Change password</a> </li>
          					<li><g:link controller="logout">Logout</g:link></li>	
        			</ul>
     			 </li>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
		</div>
		<!-- /.container -->
	</nav>
	<div>
		<br>
	</div>
	<div>
		<br />
	</div>
	<div class="form-group" >

		

		<g:layoutBody />
	</div>

	<div class="wrapper" >
		<footer class="footer">
		
			<div id="content">
				 <img align="middle"  src="/StatusPortal/images/cybage-logo.png">
			</div>

		</footer>

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


</body>



</html>
