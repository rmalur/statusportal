app.controller('editTicketController', function($scope, $http, $filter,$window) {

	$scope.today = function() {
		
		 $scope.dt= new Date(); 
		
		 };
	$scope.today();

	$scope.clear = function() {
		$scope.dt = null;
		
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
		$scope.dt = new Date().format('dd/MM/yyyy');
		console.log($scope.dt);

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
		var today;
		if(updateDate){
			today= new Date(updateDate);
			
		}else{
		 today= new Date($scope.dt);
		}
		
		console.log(" date=" + today);
	
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
		console.log(today)
		$scope.selectedDate=today
	}
		
	
	
	$scope.ticketData = {}
	
	$scope.update = undefined
	$scope.init = function(ticket_id, summary, assignee,workDoneForToday,todaysWorkHrs,impediments,updateDate) {
		$scope.ticketInfo={}
		$scope.ticketData.ticket_id = ticket_id
		$scope.ticketData.summary = summary
		$scope.ticketData.assignee = assignee
		$scope.ticketData.workDone=workDoneForToday
		console.log(todaysWorkHrs)
		var hrsAndMinutes=todaysWorkHrs.split(".")
		$scope.ticketData.workingHrs=hrsAndMinutes[0]
		$scope.ticketData.workingMinutes=hrsAndMinutes[1]
		console.log("hrs="+$scope.ticketData.workingHrs)
		console.log("mnts="+$scope.ticketData.workingMinutes)
		
		
		$scope.ticketData.impediments=impediments
		$scope.select(updateDate)
		$scope.$apply

	}


	$scope.updateTicketInfo = function(id) {
		console.log("ticketId="+id)
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