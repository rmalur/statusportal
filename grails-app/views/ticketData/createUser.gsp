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
<body>

	<div ng-controller="newEntityCreationController">
		<div ng-show="success">
			<div class="alert alert-info">
				<a href="#" class="close" ng-click="reload()" data-dismiss="alert"
					aria-label="close">&times;</a> <label>Info:</label>New user Is
				created successfully!

			</div>
		</div>
		<div ng-show="failure">
			<div class="alert alert-danger">
				<a href="#" class="close" ng-click="reload()" data-dismiss="alert"
					aria-label="close">&times;</a> <label>Info:</label>Unable To create
				new user
			</div>
		</div>


		<div class="container" ng-init="init()">
			<div class="row">
				<div class="col-lg-12">

					<form class="form-group">
						<div class="col-lg-5">
							
								<label for="employeeId">Employee First Name<span
									style="color: red">*</span>:
								</label> <input type="text" class="form-control" id="employeeId"
									ng-model="employee.employeeFirstName" name="employeeId"
									required="">
								<label for="employeeId">Employee Last Name<span
									style="color: red">*</span>:
								</label> <input type="text" class="form-control" id="employeeId"
									ng-model="employee.employeeLastName" name="employeeId"
									required="">
								<label for="employeeId">Employee ID<span
									style="color: red">*</span>:
								</label> <input type="text" class="form-control" id="employeeId"
									ng-model="employee.employeeId" name="employeeId" required="">
								<label for="employeeName">Login Id<span
									style="color: red">*</span>:
								</label> <input type="text" class="form-control" id="employeeId"
									ng-model="employee.employeeName" name="employeeName"
									required="" placeholder="name to be used as username while login">
								<label for="employeeManager">Manager Name<span
									style="color: red">*</span>:
								</label> <select ng-model='managerName' class="form-control"
									ng-change="loadProjectList()"
									ng-options='manager for manager in managerList'></select>
								<label for="employeelead">Lead Name</label> <select
									ng-model='leadName' class="form-control"
									ng-options='lead for lead in leadList'></select>
								<label for="projectName">Project Name<span
									style="color: red">*</span>:
								</label> <select ng-model='projectName' class="form-control"
									ng-options='project.projectName for project in projectList'
									multiple="multiple"></select>
								<label for="employeeEmailId"> email Id <span
									style="color: red">*</span>:
								</label> <input type="email" id="employeeEmailId" class="form-control"
									ng-model="employee.employeeEmailId" name="employeeEmailId"
									required="">
								<label for="employeePassword">Password<span
									style="color: red">*</span>:
								</label> <input type="password" class="form-control" id="password"
									ng-model="employee.password" name="password" required="">
								</br>
								<select class="form-control" ng-model="employee.role">
									<option value="" disabled selected style="display: none;">Select
										Role</option>
									<sec:ifAnyGranted roles="ROLE_ADMIN">
										<option>Admin</option>
										<option>Manager</option>
									</sec:ifAnyGranted>
									<sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_MANAGER">
										<option>Lead</option>
									</sec:ifAnyGranted>
									<sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_MANAGER,ROLE_LEAD">
										<option>Normal User</option>
									</sec:ifAnyGranted>

								</select>
							<button class="btn btn-primary" style="margin-top: 10px"
								ng-click="saveUser()">Save</button>
								
						</div>
					</form>

				</div>

			</div>
		</div>
	</div>	
</body>

</html>
