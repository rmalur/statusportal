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
		 
		
		 	
		 	<select ng-model='projectName'  ng-options='project.project_id for project in projectListOfUser'></select>
		
		
		 
		<table>
				<tr>
					<td><label for="ticket_ID">TicketID</label></td>
					<td><input  id="tickets" name="ticket_id" ng-model="ticketData.ticket_id" required=""></td>
						<td>	
							<div class="alert alert-denger alert-dismissable" ng-show="showTicket_id">
								<a href="" class="close" data-dismiss="alert" aria-label="close">Ticket id is empty It should be Empty!X</a>
							</div>
						</td>
					
				</tr>

				<tr>
					<td><label for="summary">Summary</label></td>
					<td><input type="text"  id="summary" name="summary" ng-model="ticketData.summary"  ></td>
					<td>
							<div class="alert alert-denger alert-dismissable" ng-show="showSummary">
								<a href="" class="close" data-dismiss="alert" aria-label="close">Summary field is empty It should not  be empty! X</a>
							</div>
					</td>
				</tr>

				<tr>
					<td><label for="assignee">Assignee</label></td>
					<td>
						<select ng-model='assignee'  ng-options='assignee for assignee in assigneeList'></select>
					</td>
						<td>
							<div class="alert alert-denger alert-dismissable" ng-show="showAssignee">
								<a href="" class="close" data-dismiss="alert" aria-label="close">Assignee field is empty It should not  be empty!X</a>
							</div>
					</td>
						
				</tr>

				<tr>
					<td><label for="workdone">Work done</label></td>
					<td><input type="text" id="workdone" name="workdone" ng-model="ticketData.workDone"></td>
						<td>
							<div class="alert alert-denger alert-dismissable" ng-show="showWorkDone">
								<a href="" class="close" data-dismiss="alert" aria-label="close">Workdone field is empty It should not  be empty!X</a>
							</div>
					</td>
				</tr>

				<tr>
					<td><label for="ETA">Todays Work Hrs</label></td>
					<td><input type="text" id="todaysWorkHrs" name="todaysWorkHrs"	ng-model="ticketData.todaysWorkHrs"></td>
						<td>
							<div class="alert alert-denger alert-dismissable" ng-show="showTodaysWrkHrs">
								<a href="" class="close" data-dismiss="alert" aria-label="close">Todays Work Hours field is empty It should not  be empty!X</a>
							</div>
					</td>
				</tr>

				<tr>
					<td><label for="issue">Issue</label></td>
					<td><input type="text" id="issue" name="issue"		ng-model="ticketData.impediments"></td>
						<td>
							<div class="alert alert-denger alert-dismissable" ng-show="showImpediments">
								<a href="" class="close" data-dismiss="alert" aria-label="close">Issue field is empty It should not  be empty!X</a>
							</div>
					</td>
				</tr>

				<tr>
					<td><label for="updatedDate">Creation Date</label></td>
					<td><input type="text" ng-model="creationDate" jqdatepicker />	</td>
						<td>
							<div class="alert alert-denger alert-dismissable" ng-show="showCreationDate">
								<a href="" class="close" data-dismiss="alert" aria-label="close">CreationDate field is empty It should not  be empty!X</a>
							</div>
					</td>
				</tr>

				<tr>
					<td><label for="status">Status</label></td>
					<td><select id="status" name="status"ng-model="ticketData.status">
							<option>Open</option>
							<option>In progress</option>
							<option>Closed</option>
					</select></td>
						<td>
							<div class="alert alert-denger alert-dismissable" ng-show="showStatus">
								<a href="" class="close" data-dismiss="alert" aria-label="close">Status field is empty It should not  be empty!X</a>
							</div>
					</td>
					
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
			Contact information: <a href="mailto:rishabht@cybage.com">rishabht@cybage.com</a>
		</p>
		<p align="center">
			<a href="#">Status Update Portal</a>
		</p>
	</footer>

</body>

</html>
