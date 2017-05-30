<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Status Update Portal</title>
<link
	href="${request.contextPath}/startbootstrap-bare-gh-pages/css/bootstrap.min.css"
	rel="stylesheet">
<script data-require="angular.js@1.3.9" data-semver="1.3.9"
	src="https://code.angularjs.org/1.3.9/angular.js"></script>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css" />
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<script
	src="${request.contextPath}/startbootstrap-bare-gh-pages/js/Jspdf.js"></script>
<script
	src="${request.contextPath}/startbootstrap-bare-gh-pages/js/tableExport.js"></script>
<script
	src="${request.contextPath}/startbootstrap-bare-gh-pages/js/Sprintf.js"></script>
<script
	src="${request.contextPath}/startbootstrap-bare-gh-pages/js/base64.js"></script>

<style>
body {
	padding-top: 70px;
	/* Required padding for .navbar-fixed-top. Remove if using .navbar-static-top. Change if height of navigation changes. */
}
</style>

</head>
<body ng-app="myApp">
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
	</nav>
	<!-- Navigation -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<g:link class="navbar-brand" controller="updateStatus"
					action="index">
					<h2>Status Update Portal</h2>
				</g:link>
			</div>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><g:link controller="StatusPortal" action="todaysTickets">Today</g:link>
					</li>
					<li><g:link controller="StatusPortal"
							action="getAllTicketsOfUser">Previous</g:link></li>
					<li><g:link controller="StatusPortal" action="ticketHistory">Download</g:link></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container -->
	</nav>
	<div>
		<br>
	</div>
	<div>
		<br />
	</div>
	<form name="logout" method="POST"
		action="${createLink(controller:'logout') }">
		<div class row>
			<div class="small-6 text-right columns">
				<input type="submit" value="logout" class="btn btn-info btn-lg">
			</div>
		</div>
	</form>
	
	<div ng-controller="myController">

<g:if test="${flash.message}">
		<div class="alert alert-info alert-dismissable">
			<a href="" class="close" data-dismiss="alert" aria-label="close">X</a>
			<strong>Info!</strong>
			${flash.message}

		</div>
	</g:if>

		<h3>

			<table>
				<tr>
					<td><label for="ticket_ID">TicketID</label></td>
					<td><input id="tickets" name="ticket_id" ng-model="ticketData.ticket_id">
						<span>
							<button name="Search" ng-click="showTicketInfo(ticketData.ticket_id)">Search</button>
					</span></td>
				</tr>

				<tr>
					<td><label for="summary">Summary</label></td>
					<td><input type="text" class="Text1" id="summary"
						name="summary" ng-model="ticketData.summary" ></td>
				</tr>

				<tr>
					<td><label for="assignee">Assignee</label></td>
					<td><input type="text" class="Text1" id="assignee"
						name="assignee" ng-model="ticketData.assignee" ></td>
				</tr>

				<tr>
					<td><label for="workdone">Work done</label></td>
					<td><input type="text" id="workdone" name="workdone"
						ng-model="ticketData.workDone"></td>
				</tr>

				<tr>
					<td><label for="ETA">Todays Work Hrs</label></td>
					<td><input type="text" id="todaysWorkHrs" name="todaysWorkHrs"
						ng-model="ticketData.todaysWorkHrs"></td>
				</tr>

				<tr>
					<td><label for="issue">Issue</label></td>
					<td><input type="text" id="issue" name="issue"
						ng-model="ticketData.impediments"></td>
				</tr>

				<tr>
					<td><label for="updatedDate">Creation Date</label></td>
					<td><input type="text" id="updated_Date" name="updatedDate"
						ng-model="ticketData.creationDate" placeholder="dd/MM/YYYY"></td>
				</tr>

				<tr>
					<td><label for="status">Status</label></td>
					<td><select id="status" name="status"
						ng-model="ticketData.status">
							<option>Open</option>
							<option>In progress</option>
							<option>Closed</option>
					</select></td>
					<%--<td><input type="text" id="updated_Date" name="updatedDate" ng-model="ticketData.creationDate" placeholder="dd/MM/YYYY"></td>
					--%>
				</tr>

				<tr>
					<td></td>
					<td><button name="updateTicket" ng-click="updateTicketInfo()">Update</button>
					</td>
				</tr>
			</table>
	</div>





	<footer>

		<p>
			Contact information: <a href="mailto:rishabht@cybage.com">rishabht@cybage.com</a>.
		</p>
		<p align="center">
			<a href="#">Status Update Portal</a>
		</p>
	</footer>
	<script>
		var app = angular.module('myApp', []);
		
		app
				.controller(
						'myController',
						function($scope, $http, $timeout,$window) {
							$scope.ticketData = undefined;
							$http.get("http://localhost:8080/StatusPortal/StatusPortal/ticketIds.json")
									.then(function(response) {
										$("#tickets").autocomplete({
											source : response.data
										})
									})

							//for loading the table entries			
							$scope.showTicketInfo = function(ticket_ID) {
								console.log(ticket_ID);
								$http.get(
										"http://localhost:8080/StatusPortal/StatusPortal/getTicketInfo/"
												+ ticket_ID).then(
										function(response) {
											$scope.ticketData = response.data;
											//console.log($scope.ticketData);
										});
							};

							$scope.updateTicketInfo=function(){
								
								/*var data = $.param({
						            json: JSON.stringify({
						                ticketInfo: $scope.ticketData,
						                ticket_id:$scope.ticket_ID
						            })
						        });*/
								
								var ticketData=$scope.ticketData;
								$http({
								    method: "POST",
								    url: "http://localhost:8080/StatusPortal/StatusPortal/updateTodaysTicket",
								    data: {ticketData}
								}).then(function (response) {
							        $scope.myWelcome = response.data;
							        console.log($scope.myWelcome);
							       // $window.location.reload();
							    });
									
								}

							
						});
	</script>
</body>

</html>
