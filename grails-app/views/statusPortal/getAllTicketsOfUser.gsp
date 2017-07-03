<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="A" />
<title>History</title>

</head>
<body>
<div class="container">
			<div ng-controller="ticketController" ng-init="allTicketsOfUser()">
						
					
				<div class="col-sm-6">
					<div class="col-lg-10">
						<div class="col-sm-6" style="padding-left: 20px">
			 				<select ng-model='projectName' ng-disabled="projectListHidden"  ng-change="loadTicketsOfProject()"  class= "form-control" ng-options='project.projectName for project in projectList' ></select>
						</div>
						
       					 <sec:ifAnyGranted roles="ROLE_LEAD,ROLE_MANAGER">
						<div class="col-sm-6" style="padding-left: 20px;">
			 				<select ng-model='resourceName'  ng-change="filterOnBasisOfResource()" class= "form-control" ng-options='resource for resource in resourceList' ></select>
						</div>
						</sec:ifAnyGranted>
					</div>	
					<form>
	
						<table id="tblMain"  class= "table table-striped table-condensed" >
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
								<td>{{ticket.updateDate}}</td>
								<td>{{ticket.updatedStatus}}</td>
					
							</tr>
						</table>
					</form>
					</div>	
				
			</div>	
		
	</div>	
</body>
</html>