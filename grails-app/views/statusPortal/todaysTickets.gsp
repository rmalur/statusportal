<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="A" />
<title>Status Update Portal</title>
</head>
<body>
	<form name="logout" method="POST"
		action="${createLink(controller:'logout') }">
		<div class row>
			<div class="small-6 text-right columns">
				<input type="submit" value="logout" class="btn btn-info btn-lg">
			</div>
		</div>
	</form>
<g:if test="${flash.message}">
		<div class="alert alert-info alert-dismissable">
			<a href="" class="close" data-dismiss="alert" aria-label="close">×</a>
			<strong>Info!</strong>
			${flash.message}

		</div>
	</g:if>
	<g:form controller="UpdateStatus" action="getResults" >

<table id="tblMain" border="0" class= "table table-hover">
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
						<td><button type="button" id="Button" class="use-address"
								name="Button"><g:link id="${res.ticket_id}" controller="StatusPortal" action="updateTicketStatus" >Update</g:link></button></td>
					</tr>
			</tbody>
			</g:each>
			</table>
			</g:form>
</body>
</html>