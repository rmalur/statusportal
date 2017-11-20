<!DOCTYPE html>
<%@ page contentType="text/html" %>
<html>
<head>
<meta name="layout" content="A" />
<title>Status Update Portal</title>
<style type="text/css">
table, th, td{
border: 0px solid black;
}
</style>
</head>

<body>


 <b> Hello All,</b><br>
 
 <p>Today's DSR is</p>

		<table id="tblMain"  class="table table-bordered"  border="0">
			<tr class="bg-info" >
				
				<th>Ticket-id</th>
				<th>Summary of Ticket</th>
				<th>Assignee</th>
				<th>Work Done Today</th>
				<th>Status</th>
				<th>Impediments</th>
				<th>TimeSpent</th>
				<th>Total WorkHrs </th>
				

			</tr>
			<g:each in="${result}" var="res" status="i">
				<tr>
					
					<td >
						${res.ticket_id}
					</td>
					<td>
						${res.summary}
					</td>
					<td>
						${res.assignee}
					</td>
					<td>
					${res.workDoneForToday }
					</td>
					<td>
						${res.status}
					</td>
					<td>
					${res.impediments }
					</td>
					<td>
					${res.todaysWorkHrs }
					</td>
					<td>
					${res.totalHrs }
					</td>
				</tr>
				</tbody>
			</g:each>
		</table>
	
</body>
</html>