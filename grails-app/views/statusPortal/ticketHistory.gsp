<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="A" />
<title>Status Update Portal</title>
</head>
<body ng-app="myApp">
	<form name="logout" method="POST"
		action="${createLink(controller:'logout') }">
		<div class row>
			<div class="small-6 text-right columns">
				<input type="submit" value="logout" class="btn btn-info btn-lg">
			</div>
		</div>
	</form>
	<div ng-contgroller="myController">
	
	Hi->	{{welcome}}
	
	
	</div>
</body>
</html>