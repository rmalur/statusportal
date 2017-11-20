<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="A" />
<title>Status Update Portal</title>
<style type="text/css">
#table{
border: 0px #000;
}

</style>
</head>
<body>
  
		
			<div class="small-6 text-right columns">
				<g:link class="navbar-brand" controller="StatusPortal"	action="index">
					<input type="button" value="Add" class="btn btn-primary">
				</g:link>
			</div>
			
			<div class="small-6 text-right columns navbar-brand" ng-controller="ticketController">
			<input type="button" value="Publish" class="btn btn-primary"  ng-click="sendMail()" >
				
				
	
			</div>
			</br>	
		<table id="tblMain" class="table table-hover">
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
						${res.updatedStatus}
					</td>
					<td>
							<g:link id="${res.id}" controller="StatusPortal"	action="updateTicketStatus"  >
								<input type="button" value="Edit" class="btn btn-primary">
							</g:link>
								
						</button></td>
					
				</tr>
				</tbody>
			</g:each>
		</table>
	<script type="text/ng-template" id="mail.html">
<b> <i>This will send mail to Lead and Manager.</i></b><br>
<p> Do you want to Continue?</p>

      <div class="modal-footer">

        <button class="btn btn-primary" type="button" ng-click="ok()">OK</button>
 
       <button class="btn btn-warning" type="button" ng-click="cancel()">Cancel</button>
      </div>
    </script>
    <script type="text/ng-template" id="mailResult.html">


      <div class="modal-header" ng-init="init()">
        <h3 class="modal-title">Info</h3>
      </div>
      <div class="modal-body">
        <form ng-submit="ok()">
          <div class="input-group animated fadeOut">
			<label class="control-label">Info:</label>
        		{{message}}
          </div>
        </form>

      </div>
      <div class="modal-footer">
        <button class="btn btn-primary" type="button" ng-click="ok()">OK</button>
  
      </div>
    </script>
		
</body>
</html>