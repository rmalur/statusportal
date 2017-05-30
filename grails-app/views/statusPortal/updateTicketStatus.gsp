<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="A" />
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

<title>Status Update Portal</title>
</head>
<body>
	<g:form controller="StatusPortal" action="updateTodaysTicket">
		<table>
			<tr>
				<td><label for="ticket_ID">TicketID</label></td>
				<td><input type="text" class="Text1" id="ticket_id"
					name="ticket_id" value="${ticketInfo.ticket_id}" readonly=""></td>
			</tr>
			
			<tr>
				<td><label for="summary">Summary</label></td>
				<td><input type="text" class="Text1" id="summary"
					name="summary" value="${ticketInfo.summary}" readonly=""></td>
			</tr>
	
			<tr>
				<td><label for="assignee">Assignee</label></td>
				<td><input type="text" class="Text1" id="assignee"
					name="assignee" value="${ticketInfo.assignee}" readonly=""></td>
			</tr>
	
			<tr>
				<td><label for="workdone">Work done</label></td>
				<td><input type="text" id="workdone" name="workdone"
					></td>
			</tr>

			<tr>
				<td><label for="todaysWorkHrs">Todays Work Hrs</label></td>
				<td><input type="text" id="todaysWorkHrs" name="todaysWorkHrs" value=""></td>
			</tr>

			<tr>
				<td><label for="impediments">Impediments</label></td>
				<td><input type="text" id="impediments" name="impediments"
					></td>
			</tr>
		
			<tr>
				<td><label for="updateDate">Updated Date</label></td>
				<td><input type="text" id="updateDate" name="updateDate"
					placeholder="MM/dd/YYYY"></td>
			</tr>

			<tr>
				<td></td>
				<td><g:submitButton name="Save" value="Update" /></td>
			</tr>
</table>
</g:form>
			

</body>
</html>