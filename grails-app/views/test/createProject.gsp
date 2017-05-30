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
					
					<td><label for="project_id">Project ID</label></td>
					<td><input  id="project_id" ng-model="project.project_id" name="project_id" required=""></td>
					
				</tr>
				<tr>
					
					<td><label for="projectName">Project Name</label></td>
					<td><input  id="projectName" ng-model="project.projectName" name="projectName" required=""></td>
					
				</tr>
				<tr>
					
					<td><label for="projectManager">Project Manager Name</label></td>		
					<td><select ng-model='project.managerName'  ng-options='manager for manager in managerList'></select></td>
					
				</tr>
				<tr>
					
					<td><label for="projectStartDate">Project Start Date</label></td>
					<td><input type="text" ng-model="creationDate" jqdatepicker />	</td>
					
				</tr>
				<tr>
					<td></td>
					<td><button ng-click="saveProject(startDate)">Save</button> </td>
				</tr>
		</table>
	</div>

	
	<footer>

		
	</footer>

</body>

</html>
