app.controller('editTicketController', function($scope, $http, $filter,$window) {
	$scope.selectedDate=undefined
	$scope.today = function() {
			
			 $scope.updateDate= new Date(); 
			 var today = new Date($scope.updateDate);
			
		
			var dd = today.getDate();
			var mm = today.getMonth() + 1; // January is 0!

			var yyyy = today.getFullYear();

			if (dd < 10) {
				dd = '0' + dd;
			}
			if (mm < 10) {
				mm = '0' + mm;
			}
			var today = dd + '/' + mm + '/' + yyyy;
			
			$scope.selectedDate=today
			 };
	$scope.today();

	$scope.clear = function() {
		$scope.updateDate = null;
		
	};

	// Disable weekend selection
	function disabled(data) {
		var date = data.date, mode = data.mode;
		return mode === 'day'
				&& (date.getDay() === 0 || date.getDay() === 6);
	}

	$scope.open1 = function() {

		$scope.popup1.opened = true;
	};
	$scope.setDate = function(year, month, day) {
		$scope.updateDate = new Date().format('dd/MM/yyyy');
	};

	$scope.options = {
	          
	          maxDate: new Date(),
	          showWeeks: true
	        };
	$scope.formats = [ 'dd/MM/yyyy' ];
	$scope.format = $scope.formats[0];

		$scope.popup1 = {
		opened : false
	};

	
	
	$scope.select = function(updateDate) {
		var selectedDate;
		if(updateDate){
			selectedDate=updateDate
			
		}
		
		if($scope.updateDate){
		 selectedDate= new Date($scope.updateDate);
		 	console.log("selected Date="+selectedDate)
	
		var dd = selectedDate.getDate();
		var mm = selectedDate.getMonth() + 1; // January is 0!

		var yyyy = selectedDate.getFullYear();

		if (dd < 10) {
			dd = '0' + dd;
		}
		if (mm < 10) {
			mm = '0' + mm;
		}
		 selectedDate = dd + '/' + mm + '/' + yyyy;
		}
		
		
		//console.log(today)
		$scope.selectedDate=selectedDate
	}
		
	
	
	$scope.ticketData = {}
	
	$scope.update = undefined
	$scope.init = function(ticket_id, summary, assignee,workDoneForToday,todaysWorkHrs,impediments,updateDate) {
		$scope.ticketInfo={}
		$scope.ticketData.ticket_id = ticket_id
		$scope.ticketData.summary = summary
		$scope.ticketData.assignee = assignee
		$scope.ticketData.workDone=workDoneForToday
		
		var hrsAndMinutes=todaysWorkHrs.split(".")
		$scope.ticketData.workingHrs=hrsAndMinutes[0]
		$scope.ticketData.workingMinutes=hrsAndMinutes[1]
		
		
		
		$scope.ticketData.impediments=impediments
		console.log("updateDate in init function="+updateDate)
		$scope.select(updateDate)
		$scope.$apply

	}


	$scope.updateTicketInfo = function(id) {
		
		
		console.log("selected date="+$scope.selectedDate)
		$scope.ticketData.creationDate=$scope.selectedDate;
		$scope.ticketData.idInStatusUpdateDb=id
		var ticketData=$scope.ticketData;
		
		$scope.success=false
		$scope.failure=false
		
		$http({
			method: "POST",
		    url: "/StatusPortal/StatusPortal/saveUpdate",
		    data: {ticketData}
		}).then(function (response) {
		
			console.log(response.data)
			if(response.data == 'true'){
				$scope.success=response.data
			}
			if(response.data == 'false'){
				
				$scope.failure = true
			}
	       
	    },function(response){
	    	$scope.failure=true;
	    });

	}
	
	$scope.pageRedirect=function(){
		
		$window.location.href='${g.createLink(controller="statusPortal" action="todaysTickets")}';
	}
	
	$scope.reload=function(){
		
		$window.location.reload()
		$scope.$apply
	}

})