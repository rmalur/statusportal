<html lang="en">

<head>

<meta name="layout" content="A" />

<title>Status Update Portal</title>
<style>
body {
	padding-top: 70px;
	/* Required padding for .navbar-fixed-top. Remove if using .navbar-static-top. Change if height of navigation changes. */
}
</style>

</head>
<body >
	
		<g:if test="${flash.message}">
			<div class="alert alert-info alert-dismissable">
				<a href="" class="close" data-dismiss="alert" aria-label="close">×</a>
				<strong>Info!</strong>
				${flash.message}

			</div>
			</g:if>
	
	<div ng-controller="testController" ng-init="init()">
	<div class="container">
			<div class="row">
				<div class="col-md-10">

					<form>

					
							<div class="col-lg-5">
								<label for="employeeId">Employee ID</label>
								<input type="text" class= "form-control" id="employeeId" ng-model="employee.employeeId" name="project_id" required="">
							
								<label for="employeeName">Name</label>
								<input type="text" class= "form-control" id="employeeId" ng-model="employee.employeeename" name="project_id" required="">
							
							
								<label for="employeeManager">Manager Name</label>		
								<select ng-model='managerName' class= "form-control" ng-change="loadProjectList()"  ng-options='manager for manager in managerList'></select>
							
								<label for="projectName">Project Name </label>
								<select ng-model='projectName' class= "form-control" ng-options='project.projectName for project in projectList' multiple="multiple"></select>
							
									<label for="employeeEmailId"> email Id </label>
									<input type="email" id="employeeEmailId" class= "form-control" ng-model="employee.employeeEmailId" name="employeeEmailId" required="">
	
																<label for="employeePassword">Password</label>
									<input type="password" class= "form-control"id="password" ng-model="employee.password" name="password" required="">
							
								<button class="btn btn-primary" style="margin-top:10px" ng-click="saveUser()">Save</button> 
							</div>
					</form>

				</div>

			</div>
	</div>
	
</body>

</html>
