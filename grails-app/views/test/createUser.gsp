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
		<table>
				<tr>
					
					<td><label for="employeeId">Employee ID</label></td>
					<td><input  id="employeeId" ng-model="employee.employeeId" name="project_id" required=""></td>
					
				</tr>
				<tr>
					
					<td><label for="employeeName">Name</label></td>
					<td><input  id="employeeName" ng-model="employee.employeeName" name="employeeName" required=""></td>
					
				</tr>
				<tr>
					
					<td><label for="employeeManager">Manager Name</label></td>		
					<td><select ng-model='managerName' ng-change="loadProjectList()"  ng-options='manager for manager in managerList'></select></td>
					
				</tr>
				<tr>
					<td><label for="projectName">Project Name </label></td></td>
					<td><select ng-model='projectName'  ng-options='project.projectName for project in projectList'></select></td>	
				</tr>	
				<tr>
					<td><label for="employeeEmailId"> email Id </label></td></td>
					<td><input  id="employeeEmailId" ng-model="employee.employeeEmailId" name="employeeEmailId" required=""></td>
					
				</tr>
				<tr>
					<td><label for="employeePassword">Password</label></td></td>
					<td><input type="password" id="password" ng-model="employee.password" name="password" required=""></td>
				</tr>
				<tr>
					<td></td>
					<td><button ng-click="saveUser()">Save</button> </td>
				</tr>
		</table>
	</div>

	
	<footer>

		
	</footer>

</body>

</html>
