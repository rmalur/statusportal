<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="A" />

<title>History</title>
	
</head>
<body>

	<div ng-controller="ticketController" ng-init="allTicketsOfUser()">
		<div class="col-lg-11" style="width:96%">
			<div class="col-lg-11">
				<div class="col-sm-3" style="padding-right: 70px" style="margin-left: 100px">
					<select ng-model='projectName' placeholder="select your beverage"
						ng-disabled="projectListHidden" ng-change="loadTicketsOfProject()"
						class="form-control"
						ng-options='project.projectName for project in projectList'>
						<option value="" label="-- Select Project --" disabled
							selected="selected"></option>
					</select>
				</div>

				<div class="col-md-3" style="padding-right: 40px" style="margin-left:-60px">
					<p class="input-group" style="display: inline-flex;">
						<label style="padding-top: 7px;">From:</label> <input type="text"
							ng-change="select(projectName)" class="form-control"
							uib-datepicker-popup="{{format}}" ng-model="dt"
							is-open="popup1.opened" datepicker-options="dateOptions"
							ng-required="true" close-text="Close"
							alt-input-formats="altInputFormats" /> <span
							class="input-group-btn" >
							<button type="button" class="btn btn-default" ng-click="open1()">
								<i class="glyphicon glyphicon-calendar"></i>
							</button>
						</span>
					</p>
				</div>

				<div class="col-md-3" style="padding-left: 60px" style="margin-left: 100px">
					<p class="input-group" style="display: inline-flex;">
						<label style="padding-top: 7px;">To:</label> <input type="text"
							ng-change="select(projectName)" class="form-control"
							uib-datepicker-popup="{{format}}" ng-model="end"
							is-open="popup2.opened" datepicker-options="dateOptions"
							ng-required="true" close-text="Close"
							alt-input-formats="altInputFormats" /> <span
							class="input-group-btn">
							<button type="button" class="btn btn-default" ng-click="open2()">
								<i class="glyphicon glyphicon-calendar"></i>
							</button>
						</span>
					</p>
				</div>
				<div class="col-md-2" style="padding-left: 20px">
				<div class="dropdown" style="margin-left: 40px">
					<button class="btn btn-primary dropdown-toggle" type="button"
						data-toggle="dropdown">
						Export Data <span class="caret"></span>
					</button>
					<ul class="dropdown-menu">
						<li><g:link controller="statusPortal" action="exportData"
								params="[extension:'PDF']">PDF </g:link></li>
						<li><g:link controller="statusPortal" action="exportData"
								params="[extension:'xls']">XSL </g:link></li>
						<li><g:link controller="statusPortal" action="exportData"
								params="[extension:'CSV']">CSV </g:link></li>
					</ul>
				</div>

			</div>
	

				<sec:ifAnyGranted roles="ROLE_LEAD,ROLE_MANAGER">
					<div class="col-md-3" style="padding-left: 80px;">
						<select ng-model='resourceName'
							ng-change="filterOnBasisOfResource()" class="form-control"
							ng-options='resource for resource in resourceList'>
							<option value="" label=" Select Resource " disabled
								selected="selected"></option>
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

				<tr
					ng-repeat="ticket in ticketList|  filter:q | startFrom:currentPage*pageSize | limitTo:pageSize">
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
			<button class="btn btn-primary" ng-disabled="currentPage == 0"
				ng-click="currentPage=currentPage-1">Previous</button>
			{{currentPage+1}}/{{numberOfPages()}}
			<button class="btn btn-primary"
				ng-disabled="currentPage >= getData().length/pageSize - 1"
				ng-click="currentPage=currentPage+1">Next</button>
		</form>
	</div>
	</div>
	

</body>
</html>