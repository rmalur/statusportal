<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Status Update Portal</title>

<!-- Bootstrap Core CSS -->
<link href="${request.contextPath}/startbootstrap-bare-gh-pages/css/bootstrap.min.css" rel="stylesheet">
	
<!-- Script loaded form controller.js file -->
		
	
<script	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/startbootstrap-bare-gh-pages/js/controller/myController.js" ></script>	
<script	src="${request.contextPath}/startbootstrap-bare-gh-pages/js/jquery-1.11.2.min.js"></script>
<link rel="stylesheet"	href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css" />
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css" />
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
				<g:link class="navbar-brand" controller="StatusPortal"
					action="index">
					<h2>Status Update Portal</h2>
				</g:link>
			</div>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><g:link controller="StatusPortal" action="todaysTickets">Today</g:link></li>
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
	<div ng-controller="myController">

		<g:layoutBody />
	</div>

	<div >
		<footer class="foot">

			<b> Contact information:</b> <a href="mailto:rishabht@cybage.com">rishabht@cybage.com</a>.

			<p align="center">
				<a href="#">Status Update Portal</a>
			</p>
		</footer>
	</div>

</body>



</html>
