<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="A" />
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

<title>Status Update Portal</title>
</head>
<body>


	<div class="container" ng-controller="editTicketController"
		ng-init="init( 
			'${ticketInfo.ticket.ticket_id }','${ticketInfo.ticket.summary}','${ticketInfo.ticket.assignee}','${ticketInfo.workDoneForToday }','${ ticketInfo.todaysWorkHrs}','${ticketInfo.impediments}','${ticketInfo.updateDate }' )">
		<div class="alert alert-info alert-dismissable" ng-show="success">
			<a href="" class="close" data-dismiss="alert" ng-click="pageRedirect()"
				aria-label="close">X</a> <strong>Info!</strong>Data is saved
			successFully!
		</div>
		<div class="alert alert-danger alert-dismissable" ng-show="failure">
			<a href="" class="close" data-dismiss="alert" ng-click="reload()"
				aria-label="close">X</a> <strong>Info!</strong>Data is not saved
			successFully!
		</div>

		<div class="row">
			<div class="col-md-10">

				<div class="col-sm-12">
					<form>
						<label for="ticket_ID">TicketID</label> <input type="text"
							class="form-control" id="ticket_id"
							ng-model="ticketData.ticket_id" name="ticket_id" readonly="">


						<label for="summary">Summary</label> <input type="text"
							class="form-control" id="summary" name="summary"
							ng-model="ticketData.summary" readonly=""> <label
							for="assignee">Assignee</label> <input type="text"
							class="form-control" name="assignee"
							ng-model="ticketData.assignee" readonly=""> <label
							for="workdone">Work done</label> <input type="text"
							class="form-control" ng-model="ticketData.workDone" id="workdone"
							name="workdone"> <label for="todaysWorkHrs">Todays
							Work Hrs:</label></br>

						<div class="col-lg-6 ">
							<select class="form-control" ng-model="ticketData.workingHrs">
								<option value="" disabled selected style="display: none;">Hrs</option>
								<option id="hrs">0</option>
								<option id="hrs">1</option>
								<option id="hrs">2</option>
								<option id="hrs">3</option>
								<option id="hrs">4</option>
								<option id="hrs">5</option>
								<option id="hrs">6</option>
								<option id="hrs">7</option>
								<option id="hrs">8</option>
								<option id="hrs">9</option>
								<option id="hrs">10</option>
								<option id="hrs">11</option>
								<option id="hrs">12</option>
							</select>
						</div>
						<div class="col-lg-6 ">
							<select class="col-lg-3 form-control"
								ng-model="ticketData.workingMinutes">
								<option id="minutes" name="minutes" value="" disabled selected
									style="display: none;">Mnts</option>
								<option id=minutes>00</option>
								<option id=minutes>10</option>
								<option id=minutes>20</option>
								<option id=minutes>30</option>
								<option id=minutes>40</option>
								<option id=minutes>50</option>
								<option id=minutes>60</option>
							</select>
						</div>





						<label for="impediments">Impediments</label> <input type="text"
							class="form-control" id="impediments"
							ng-model="ticketData.impediments" name="impediments"> <label
							for="updateDate">Updated Date</label>
						<p class="input-group" style="display: inline-flex;">
							<input type="text" id="updateDate" class="form-control"
								uib-datepicker-popup="{{format}}" ng-model="updateDate"
								is-open="popup1.opened" datepicker-options="options"
								ng-required="true" close-text="Close"
								alt-input-formats="altInputFormats" ng-change="select()" />
							<button type="button" class="btn btn-default" ng-click="open1()">
								<i class="glyphicon glyphicon-calendar"></i>
							</button>

						</p>

						<button name="updateTicket"
							ng-click="updateTicketInfo('${ticketInfo.id }')"
							style="margin-top: 10px" class="btn btn-primary">Update</button>


						<form>
				</div>

			</div>
		</div>
</body>
</html>