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
				

			</tr>
			<g:each in="${results}" var="res" status="i">
				<tr>
					
					<td >
						${res.ticket.ticket_id}
					</td>
					<td>
						${res.ticket.summary}
					</td>
					<td>
						${res.ticket.assignee}
					</td>
					<td>
					${res.workDoneForToday }
					</td>
					<td>
						${res.ticket.status}
					</td>
					<td>
					${res.impediments }
					
					
				</tr>
				</tbody>
			</g:each>
		</table>
	
</body>
</html>