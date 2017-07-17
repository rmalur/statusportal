<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="A" />
<title>History</title>

</head>
<body>
	

<%--<button class="btn btn-info"  ng-click="exportToExcel('#tblMain')">Export Record In Excel Format</button>




			--%>
	<div ng-controller="ticketController" ng-init="allTicketsOfUser()">
		<div class="col-lg-11">
			<div class="col-lg-11">
				<div class="col-sm-3" style="padding-left: 20px">
					<select ng-model='projectName' placeholder="select your beverage" ng-disabled="projectListHidden"
						ng-change="loadTicketsOfProject()" class="form-control"
						ng-options='project.projectName for project in projectList'>
						<option value="" label="-- Select Project --" disabled selected="selected">  </option>
						</select>
				</div>

			<div class="col-md-3" style="padding-right: 25px">
				<p class="input-group" style="display: inline-flex;">
					<label style="padding-top: 7px;">Date:</label> <input type="text" ng-change="select(projectName)"
						class="form-control" uib-datepicker-popup="{{format}}"
						ng-model="dt" is-open="popup1.opened"
						datepicker-options="dateOptions" ng-required="true"
						close-text="Close" alt-input-formats="altInputFormats" /> <span
						class="input-group-btn">
						<button type="button" class="btn btn-default" ng-click="open1()">
							<i class="glyphicon glyphicon-calendar"></i>
						</button>
					</span>
				</p>
			</div>

				<sec:ifAnyGranted roles="ROLE_LEAD,ROLE_MANAGER">
					<div class="col-md-3" style="padding-left: 20px;">
						<select ng-model='resourceName'
							ng-change="filterOnBasisOfResource()" class="form-control"
							ng-options='resource for resource in resourceList'>
							<option value="" label="-- Select Resource --" disabled selected="selected">  </option>
							</select>
					</div>
				</sec:ifAnyGranted>
			</div>
			

			<form>

				<table id="tblMain" class="table table-striped table-condensed">
					<tr class="bg-info">
						<th>Id</th>
						<th>Ticket-id</th>
						<th>Summary</th>
						<th>Assignee</th>
						<th>Work_done by</th>
						<th>Impediments</th>
						<th>Work Hours for day</th>
						<th>Updated_Date</th>
						<th>Status</th>
					</tr>

					<tr ng-repeat="ticket in ticketList">
						<td>{{$index+1}}</td>
						<td>{{ticket.ticket_id}}</td>
						<td>{{ticket.summary}}</td>
						<td>{{ticket.assignee}}</td>
						<td>{{ticket.workDoneBy}}</td>
						<td>{{ticket.impediments}}</td>
						<td>{{ticket.todaysWorkHrs}}</td>
						<td>{{ticket.updateDate|date:'dd/MM/yyyy' }}</td>
						<td>{{ticket.updatedStatus}}</td>

					</tr>
				</table>
			</form>
		</div>
	</div>
	

</body>
</html>