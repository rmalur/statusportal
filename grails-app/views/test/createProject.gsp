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

			
	<div ng-controller="testController">
	
		<div ng-show="success">
			<div class="alert alert-info">
				<a href="#" class="close" ng-click="reload()" data-dismiss="alert" aria-label="close">&times;</a>
				<label>Info:</label>New project Is created successfully!

			</div>
		</div>
		<div ng-show="failure">
			<div class="alert alert-danger">
				<a href="#" class="close" ng-click="reload()" data-dismiss="alert" aria-label="close">&times;</a>
				<label>Info:</label>Unable To create new project
			</div>
		</div>
				
	
		<div class="container" ng-init="init()">
			<div class="row">
				<div class="col-md-6">

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
					
							<label for="methodology">Project Developement Methodology</label> <select
								class="form-control" ng-model='project.methodology'
								ng-options='methodology.methodology for methodology in methodologyList'></select>
							
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
