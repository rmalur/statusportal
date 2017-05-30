<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="A" />
<title>History</title>

</head>
<body>
	<form name="logout" method="POST" action="${createLink(controller:'logout') }">
		<div class row>
			<div class="small-6 text-right columns">
				<input type="submit" value="logout" class="btn btn-info btn-lg">
			</div>
		</div>
	</form>
	
	<g:if test="${flash.message}">
		<div class="alert alert-info alert-dismissable fade in">
			<a href="" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			<strong>Info!</strong>
			${flash.message}

		</div>
	</g:if>
		<table id="tblMain" border="0" class= "table table-hover table-responsive" >
			<tr class="bg-info">
				<th>Id</th>
				<th>Ticket-id</th>
				<th>Summary</th>
				<th>Assignee</th>
				<th>Work_done</th>
				<th>Issue</th>
				<th>Work Hours for day</th>
				<th>Updated_Date</th>
				<th>Status</th>
			</tr>
			<g:each in="${hist}" var="res" status="i">
					<tr >
						<td>
							${i+1}.
						</td>
						<td>
							${res.ticket.ticket_id}
						</td>
						<td>
							${res.ticket.summary}
						</td>
						<td>
							${res.ticket.assignee}
						</td>
						<td>
							${res.workDoneForToday}
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
					
					</tr>
			</tbody>
			</g:each>
			</table>

</body>
</html>