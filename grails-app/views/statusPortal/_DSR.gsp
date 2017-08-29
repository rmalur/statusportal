<!DOCTYPE html>
<%@ page contentType="text/html" %>
<html>
<head>
<meta name="layout" content="A" />
<title>Status Update Portal</title>

</head>
<body>
 <b> Hello All,</b><br>
 
 <p>Today's DSR is</p>

		<table id="tblMain" border="1" class="table table-hover">
			<tr class="bg-info">
				<th>Id</th>
				<th>Ticket-id</th>
				<th>Summary</th>
				<th>Assignee</th>
				<th>Status</th>
				

			</tr>
			<g:each in="${results}" var="res" status="i">
				<tr>
					<td>
						${i+1}.
					</td>
					<td>
						${res.ticket_id}
					</td>
					<td>
						${res.summary}
					</td>
					<td>
						${res.assignee}
					</td>
					<td>
						${res.status}
					</td>
					
					
				</tr>
				</tbody>
			</g:each>
		</table>
	
</body>
</html>