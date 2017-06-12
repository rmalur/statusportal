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
				<div class="col-md-12">

					<form>
						<div class="col-lg-5">
							<label for="project_id">Project ID</label> <input type="text"
								class="form-control" id="project_id"
								ng-model="project.project_id" name="project_id" required="">
						
							<label for="projectName">Project Name</label> <input type="text"
								class="form-control" id="projectName"
								ng-model="project.projectName" name="projectName" required="">
					
							<label for="projectManager">Project Manager Name</label> <select
								class="form-control" ng-model='project.managerName'
								ng-options='manager for manager in managerList'></select>
					
							<label for="projectStartDate">Project Start Date</label> <input
								type="text" class="form-control" ng-model="creationDate"
								jqdatepicker />
					
							<button class="btn btn-primary" style="margin-top: 10px" ng-click="saveProject(startDate)">Save</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>

</html>
