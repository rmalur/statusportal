

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
	
	<div ng-controller="myController" >
		<div class="alert alert-info alert-dismissable" ng-show="success">
			<a href="" class="close" data-dismiss="alert" aria-label="close">X</a>
			<strong>Info!</strong>Data is saved successFully!
		</div>
		<div class="alert alert-error alert-dismissable" ng-show="failure">
			<a href="" class="close" data-dismiss="alert" aria-label="close">X</a>
			<strong>Info!</strong>Data is not saved successFully!
		</div>
		
		<div class="alert alert-error alert-dismissable" ng-show="showError">
			<a href="" class="close" data-dismiss="alert" aria-label="close">X</a>
			<strong>Info!</strong>please fill the mandatory fileds
		</div>
	
		<div class="container">
			<div class="row">
				<div class="col-md-10">

					<form>

					
							<div class="col-lg-5">

								<label for="ticket_ID" class="control-label">TicketID:</label></br>
								<input type="text" class="form-control" id="tickets" name="ticket_id" ng-model="ticketData.ticket_id" required=""
									placeholder="TickketId">
							</div>
							<div class="col-lg-12">	
								<label for="summary">Summary:</label></br>
								<input type="text" class="form-control" id="summary" 
									name="summary" ng-model="ticketData.summary"
									placeholder="Summary">
							</div>

							<div class="col-sm-12">
								<div class="col-lg-6">
									<label for="assignee">Assignee:</label> <select
										ng-model='assignee' class="form-control"
										style="width: 195px; height: 35px;"
										ng-options='assignee for assignee in assigneeList'
										title="Assignee"></select>
								</div>
								<div style="float: left;">
									<label for="workdoneBy" class="controle-label">Work done
										by:</label> <select
										ng-model='WorkdoneBy' class="form-control"
										style="width: 195px; height: 35px;"
										ng-options='workdoneBy for workdoneBy in workdoneByList'
										title="workdoneBy"></select>
								</div>
							</div>
			
							<div class="col-lg-12">
								<label for="todayswork">Today's Work :</label></br>
								<textarea class="form-control textarea" rows="3" cols="45" id="todayswork"
									name="todayswork" ng-model="ticketData.todayswork"></textarea>
							</div>

							<div class="col-sm-12">
								<div class="col-lg-6">

									<label for="todaysWorkHrs">Todays Work Hrs:</label></br> <input
										type="text" style="width: 87%" class="form-control "
										id="todaysWorkHrs" name="todaysWorkHrs"
										ng-model="ticketData.todaysWorkHrs">
								</div>
								<div style="float: left">
									<label for="totalWorkHrs">Total Work Hrs:</label></br> <input
										type="text"  class="form-control" id="totalWorkHrs"
										name="totalWorkHrs" ng-model="totalWorkHrs">
								</div>
							</div>

							<div class="col-lg-12">
								<label for="impediments">Impediments :</label></br>
								<textarea class=" form-control" rows="3" cols="45" id="impediments"
									name="impediments" ng-model="ticketData.impediments"></textarea>
							</div>

							<div class="col-sm-12">
								<div class="col-lg-6">
									 <label for="updatedDate">Date Of Work Done</label></br>
									 <input	type="text" style="width: 87%" class="form-control" ng-model="creationDate" jqdatepicker />
								</div>
								<div style="float: left">
									<label for="status">Status</label></br> <select id="status"
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