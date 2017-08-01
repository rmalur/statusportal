

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
	
	<div ng-controller="myController">
		<div class="alert alert-info alert-dismissable" ng-show="success">
			<a href="" class="close" data-dismiss="alert" ng-click="reload()" aria-label="close">X</a>
			<strong>Info!</strong>Data is saved successFully!
		</div>
		<div class="alert alert-error alert-dismissable" ng-show="failure">
			<a href="" class="close" data-dismiss="alert" ng-click="reload()" aria-label="close">X</a>
			<strong>Info!</strong>Data is not saved successFully!
		</div>
		
		<div class="alert alert-error alert-dismissable" ng-show="showError">
			<a href="" class="close" data-dismiss="alert" ng-click="reload()" aria-label="close">X</a>
			<strong>Info!</strong>please fill the mandatory fields
		</div>
	
		<div class="container">
			<div class="row">
				<div class="col-md-10">

					<form>
							<div class="col-lg-6">
								<label class="control-label" >  Select Project:</label>
								<select
										ng-model='project' class="form-control"
										style="width: 195px; height: 35px;"
										ng-disabled="projectListHidden"
										ng-change="loadAllData()"
										ng-options='project.projectName for project in projectListOfUser'
										title="projectName"></select>
										
									
							</div><%--

						<div class="col-xs-12" id="cash"
							ng-if="project.methodology == 'SCRUM'">
							<div class="col-lg-12">
								<label for="todayswork">Tomorrow's Plan <span
									style="color: red">*</span>:
								</label></br>
								<textarea class="form-control textarea" rows="3" cols="45"
									id="todayswork" name="todayswork"
									ng-model="ticketData.todayswork"></textarea>
							</div>
						</div>
						--%><div class="col-lg-5">

								<label for="ticket_ID" class="control-label">TicketID<span style="color:red">*</span>:</label></br>
								<input type="text" class="form-control" id="tickets" name="ticket_id" ng-model="ticketData.ticket_id" required=""
									placeholder="TicketId">
							</div>
							<div class="col-lg-12">	
								<label for="summary">Summary<span style="color:red">*</span>:</label></br>
								<input type="text" class="form-control" id="summary" 
									name="summary" ng-model="ticketData.summary" ng-disabled="SummaryDisabled"
									placeholder="Summary"  >
							</div>

							<div class="col-sm-12">
								<div class="col-lg-6">
									<label for="assignee">Assignee<span style="color:red">*</span>:</label> <select
										ng-model='assignee' class="form-control"
										style="width: 195px; height: 35px;"
										ng-options='assignee for assignee in assigneeList'
										title="Assignee"></select>
								</div>
								<div style="float: left;">
									<label for="workdoneBy" class="controle-label">Work done
										by:</label> <select
										ng-model='workDoneBy' class="form-control"
										style="width: 195px; height: 35px;"
									    ng-options='assignee for assignee in assigneeList'
										title="workdoneBy"></select>
								</div>
							</div>
			
							<div class="col-lg-12">
								<label for="todayswork">Today's Work <span style="color:red">*</span>:</label></br>
								<textarea class="form-control textarea" rows="3" cols="45" id="todayswork"
									name="todayswork" ng-model="ticketData.todayswork"></textarea>
							</div>

							<div class="col-lg-12"  ng-hide="methodology=='AGILE'">
								<label for="todayswork">Tomorrow's Plan <span style="color:red">*</span>:</label></br>
								<textarea class="form-control textarea" rows="3" cols="45" id="todayswork"
									name="todayswork" ng-model="ticketData.todayswork"></textarea>
							</div>
							
							<div class="col-lg-12">
								<label for="impediments">Impediments :</label></br>
								<textarea class=" form-control" rows="3" cols="45" id="impediments"
									name="impediments" ng-model="ticketData.impediments"></textarea>
							</div>
							
							<div class="col-sm-12">
								<div class="col-lg-6">

									<label for="todaysWorkHrs">Todays Work Hrs<span style="color:red">*</span>:</label></br> 
										
										<%--<input
										type="time" style="width: 87%" class="form-control "
										id="todaysWorkHrs" name="todaysWorkHrs"
										ng-model="ticketData.todaysWorkHrs">--%>
										<div class="col-lg-6 ">
											<select  class="form-control" ng-model="ticketData.workingHrs">
												<option value="" disabled selected style="display: none;">Hrs</option>
													<option>0</option>
													<option>1</option>
													<option>2</option>
													<option>3</option>
													<option>4</option>
													<option>5</option>
													<option>6</option>
													<option>7</option>
													<option>8</option>
													<option>9</option>
													<option>10</option>
													<option>11</option>
													<option>12</option>
											</select>
											</div>
											<div class="col-lg-6 ">
											<select class="col-lg-3 form-control" ng-model="ticketData.workingMinutes">
													<option value="" disabled selected style="display: none;">Mnts</option>
													<option>00</option>
													<option>10</option>
													<option>20</option>
													<option>30</option>
													<option>40</option>
													<option>50</option>
													<option>60</option>
											</select>
											</div>
									
								</div>
								<div style="float: left">
									<label for="totalWorkHrs">Total Work Hrs<span style="color:red">*</span>:</label></br>
									
									
									 <input
										type="text"  class="form-control" id="totalWorkHrs"
										name="totalWorkHrs" ng-model="totalWorkHrs" readonly="">
								</div>
							</div>

							

							<div class="col-sm-12">
								<div class="col-lg-6">
									 <label for="updatedDate">Date Of Work Done</label></br>
									 <input	type="text" style="width: 87%" class="form-control" ng-model="creationDate" jqdatepicker />
								</div>
								<div style="float: left">
									<label for="status">Status:</label></br> <select id="status"
										class="form-control" style="width: 195px; height: 34px;"
										name="status" ng-model="ticketData.status">
										<option>Open</option>
										<option>In progress</option>
										<option>Closed</option>
									</select>
								</div>
							</div>
							
							<div class="col-lg-5">
								<button name="updateTicket" ng-click="updateTicketInfo()" style="margin-top:10px"
									class="btn btn-primary">Update</button>
									
							</div>
							
					</form>

				</div>

			</div>
		</div>
	</div>

</body>

</html>