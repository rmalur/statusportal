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

			
	<div ng-controller="newEntityCreationController">
	
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
				<div class="col-lg-10">

					<form>
						<div class="col-lg-7">
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

							<p class="input-group"  style="padding-top:15px;display: inline-flex;">
									<label style="padding-top: 7px;">From:</label> <input
										type="text" class="form-control" style="width: fit-content;"
										uib-datepicker-popup="{{format}}" ng-model="dt"
										is-open="popup1.opened" datepicker-options="options"
										ng-required="true" close-text="Close"
										alt-input-formats="altInputFormats"
										ng-change="select()" />
									<button type="button" class="btn btn-default"
										ng-click="open1()">
										<i class="glyphicon glyphicon-calendar"></i>
									</button>
									</span></p>
							<button class="btn btn-primary" style="margin-top: 10px" ng-click="saveProject(startDate)">Save</button>
							
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>

</html>
