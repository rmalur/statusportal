<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="A" />
<title>Status Update Portal</title>
</head>
<body >

	<div>
		<%--<button class="btn btn-info btn-lg" ng-click="allRecords()">Load
			All Records</button>

		--%><g:if test="${flash.message}">
			<div class="alert alert-info alert-dismissable">
				<a href="" class="close" data-dismiss="alert" aria-label="close">×</a>
				<strong>Info!</strong>
				${flash.message}

			</div>
		</g:if>
		

	
			<table id="tblMain" border="0" class= "table table-hover table-responsive" >
			<tr class="bg-info">
				<th>No.</th>
				<th>Ticket Id</th>
				<th>Work done by</th>
				<th>Issue</th>
				<th>Work Hours for day</th>
				<th>Updated_Date</th>
				<th>Status</th>
				<th>Project Name</th>
			</tr>
			<g:each in="${allTickets}" var="res" status="i">
					<tr >
						<td>
							${i+1 }
						</td>	
						<td>
							${res.ticket.ticket_id }
						
						</td>	
						<td>
							${res.user.username}
						</td>
						<td>
							${res.impediments}
						</td>
					
						<td>
							${res.todaysWorkHrs}
						</td>
						
						<td>
							${res.updateDate}
						</td>
						<td>
							${res.updatedStatus}
						</td>
						<td>
							${res.ticket.project.projectName}
						
						</td>
					</tr>
			</tbody>
			</g:each>
			</table>

	
		<button class="btn btn-info btn-lg"  ng-click="exportToExcel('#tblMain')">Export Record In Excel Format</button>
	</div>
</body>
</html>