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

	<form name="logout" method="POST"
		action="${createLink(controller:'logout') }">
		<div class row>
			<div class="small-6 text-right columns">
				<input type="submit" value="logout" class="btn btn-info btn-lg">
			</div>
		</div>
	</form>
	
	<div >


		<div class="alert alert-info alert-dismissable" ng-show="success">
			<a href="" class="close" data-dismiss="alert" aria-label="close">X</a>
			<strong>Info!</strong>Data is saved successFully!
		</div>
		<div class="alert alert-error alert-dismissable" ng-show="failure">
			<a href="" class="close" data-dismiss="alert" aria-label="close">X</a>
			<strong>Info!</strong>Data is not saved successFully!
		</div>
		
	

		

			<table>
				<tr>
					<td><label for="ticket_ID">TicketID</label></td>
					<td><input id="tickets" name="ticket_id" ng-model="ticketData.ticket_id">
						<span>
							<button name="Search" ng-click="showTicketInfo(ticketData.ticket_id)">Search</button>
					</span></td>
				</tr>

				<tr>
					<td><label for="summary">Summary</label></td>
					<td><input type="text" class="Text1" id="summary"
						name="summary" ng-model="ticketData.summary" ></td>
				</tr>

				<tr>
					<td><label for="assignee">Assignee</label></td>
					<td><input type="text" class="Text1" id="assignee"
						name="assignee" ng-model="ticketData.assignee" ></td>
				</tr>

				<tr>
					<td><label for="workdone">Work done</label></td>
					<td><input type="text" id="workdone" name="workdone"
						ng-model="ticketData.workDone"></td>
				</tr>

				<tr>
					<td><label for="ETA">Todays Work Hrs</label></td>
					<td><input type="text" id="todaysWorkHrs" name="todaysWorkHrs"
						ng-model="ticketData.todaysWorkHrs"></td>
				</tr>

				<tr>
					<td><label for="issue">Issue</label></td>
					<td><input type="text" id="issue" name="issue"
						ng-model="ticketData.impediments"></td>
				</tr>

				<tr>
					<td><label for="updatedDate">Creation Date</label></td>
					<td><input type="text" id="updated_Date" name="updatedDate"
						ng-model="ticketData.creationDate" placeholder="dd/MM/YYYY"></td>
				</tr>

				<tr>
					<td><label for="status">Status</label></td>
					<td><select id="status" name="status"
						ng-model="ticketData.status">
							<option>Open</option>
							<option>In progress</option>
							<option>Closed</option>
					</select></td>
					<%--<td><input type="text" id="updated_Date" name="updatedDate" ng-model="ticketData.creationDate" placeholder="dd/MM/YYYY"></td>
					--%>
				</tr>

				<tr>
					<td></td>
					<td><button name="updateTicket" ng-click="updateTicketInfo()">Update</button>
					</td>
				</tr>
			</table>
	</div>





	<footer>

		<p>
			Contact information: <a href="mailto:rishabht@cybage.com">rishabht@cybage.com</a>.
		</p>
		<p align="center">
			<a href="#">Status Update Portal</a>
		</p>
	</footer>

</body>

</html>
