<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="A" />
<title>Status Update Portal</title>

</head>
<body>
		
			<div class="small-6 text-right columns">
				<g:link class="navbar-brand" controller="StatusPortal"	action="index">
					<input type="button" value="Add" class="btn btn-primary">
				</g:link>
			</div>
			
			<div class="small-6 text-right columns">
				<g:link class="navbar-brand" controller="ticketData" action="sendDSR">
					<input type="button" value="Publish" class="btn btn-primary">
				</g:link>
			</div>
			</br>	
		<table id="tblMain" border="0" class="table table-hover">
			<tr class="bg-info">
				<th>Id</th>
				<th>Ticket-id</th>
				<th>Summary</th>
				<th>Assignee</th>
				<th>Status</th>
				<th></th>

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
					<td>
							<g:link id="${res.ticket_id}" controller="StatusPortal"	action="updateTicketStatus"  >
								<input type="button" value="Edit" class="btn btn-primary">
							</g:link>
								
						</button></td>
					
				</tr>
				</tbody>
			</g:each>
		</table>
	
</body>
</html>