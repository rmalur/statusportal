<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Status Update Portal</title>
<style type='text/css' media='screen'>
#login {
	margin: 15px 0px;
	padding: 0px;
	text-align: center;
}

#login .inner {
	width: 700px;
	height: 330px;
	padding-bottom: 6px;
	margin: 60px auto;
	text-align: center;
	-moz-box-shadow: 2px 2px 2px #eee;
	-webkit-box-shadow: 2px 2px 2px 2px #443838;
	-khtml-box-shadow: 2px 2px 2px #eee;
	box-shadow: 2px 2px 2px 2px #443838;
}

#login .inner .fheader {
	padding: 8px 20px 8px 20px;
	background-color: #908d8d;
	margin: 0px 0 14px 0;
	color: rgba(6, 6, 6, 0.99);
	font-size: 18px;
	font-weight: bold;
	text-align: center;
}

#login .inner .cssform p {
	clear: left;
	margin: 0;
	padding: 4px 0 3px 0;
	padding-left: 105px;
	margin-bottom: 20px;
	height: 1%;
}

#login .inner .cssform input[type='text'] {
	width: 500px;
}

#login .inner .cssform label {
	font-weight: bold;
	float: left;
	text-align: right;
	margin-left: -105px;
	width: 110px;
	padding-top: 3px;
	padding-right: 10px;
}

#login #remember_me_holder {
	padding-left: 120px;
}

#login #submit {
	margin-left: 15px;
}

#login #remember_me_holder label {
	float: none;
	margin-left: 0;
	text-align: left;
	width: 200px
}

#login .inner .login_message {
	padding: 6px 25px 20px 25px;
	color: #c33;
}

#login .inner .text_ {
	width: 500px;
}

#login .inner .chk {
	height: 12px;
}

body {
	height: 100%;
	margin: 0px;
}

html {
	background-color: #999;
	margin: 0px;
	height: 100%;
}

#container {
	min-height: 100%;
	background-color: #666;
	position: relative;
}

#content {
	overflow: auto;
	background-color: #333;
}

#footer {
	background-color: #000;
	position: absolute;
	bottom: 0px;
	left: 0px;
	width: 100%;
	height: 100px;
	overflow: hidden;
}

#imginthefooter {
	background: url(/StatusPortal/web-app/images/cybage-logo.jpg);
	width: 100px;
	height: 300px;
	z-index: 300;
	bottom: 0px;
	top: -108px;
	right: -150px;
	position: relative;
}
</style>

		


<style>
body {
	padding-top: 70px;
	/
	Required
	padding
	for
	.navbar-fixed-top.
	Remove
	if
	using
	.navbar-static-top.
	Change
	if
	height
	of
	navigation
	changes.
	/
}
</style>

<link	href="${request.contextPath}/startbootstrap-bare-gh-pages/css/bootstrap.min.css"	rel="stylesheet">
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<script	src="${request.contextPath}/startbootstrap-bare-gh-pages/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/startbootstrap-bare-gh-pages/js/controller/loginController.js" ></script>
</head>

<body >
	
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
				<a class="navbar-brand" href="#"><h2>Status Update Portal</h2></a>
			</div>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><a href="#"></a></li>
					<li><a href="#"></a></li>
					<li><g:link controller="test" action="createUser" >New User</g:link> </li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container -->
	</nav>



	<%--<div>
		<br>
	</div>
	<div>
		<br />
	</div>

	--%><div>
	<g:layoutBody />
	
	
	</div>


</body>

</html>