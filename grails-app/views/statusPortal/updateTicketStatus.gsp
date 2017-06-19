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
	
		
		<div class="container">
			<div class="row">
				<div class="col-md-10">

				
				
					
				<div class="col-lg-5">
					<g:form controller="StatusPortal" action="updateTodaysTicket">			
								<label for="ticket_ID">TicketID</label></td>
								<input type="text" class="form-control" id="ticket_id"	name="ticket_id" value="${ticketInfo.ticket_id}" readonly="">
							
							
								<label for="summary">Summary</label>
								<input type="text" class="form-control" id="summary"name="summary" value="${ticketInfo.summary}" readonly="">
							
							
								<label for="assignee">Assignee</label>
								<input type="text" class="form-control" id="assignee"name="assignee" value="${ticketInfo.assignee}" readonly="">
							
								<label for="workdone">Work done</label>
								<input type="text" class="form-control" id="workdone" name="workdone">
								
								<label for="todaysWorkHrs">Todays Work Hrs</label>
								<input type="text"  class="form-control" id="todaysWorkHrs" name="todaysWorkHrs" value="">
				
							
								<label for="impediments">Impediments</label>
								<input type="text" class="form-control" id="impediments" name="impediments">
							
								<label for="updateDate">Updated Date</label></td>
								<input type="text" class="form-control" id="updateDate" name="updateDate"placeholder="MM/dd/YYYY" jqdatepicker >
								
								
								<g:submitButton name="Save" value="Update" class="btn btn-primary" style="margin-top:10px" />
							
								
						</g:form>
			
				</div>

			</div>
		</div>			

</body>
</html>