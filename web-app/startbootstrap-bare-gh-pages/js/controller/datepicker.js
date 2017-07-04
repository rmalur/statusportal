app.controller('DatepickerPopupDemoCtrl', function ($scope,$http,$filter) {
	$scope.ticketList=[]
  $scope.today = function() {
    $scope.dt = new Date();
  };
  $scope.today();

  $scope.clear = function() {
    $scope.dt = null;
  };

  // Disable weekend selection
  function disabled(data) {
    var date = data.date,
      mode = data.mode;
    return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
  }


  $scope.open1 = function() {
	  

    $scope.popup1.opened = true;
  };
  $scope.setDate = function(year, month, day) {
    $scope.dt = new Date().format('dd/MM/yyyy');
    console.log($scope.dt);
  };

  $scope.formats = ['dd/MM/yyyy'];
  $scope.format = $scope.formats[0];
 
  /*$scope.altInputFormats = ['M!/d!/yyyy'];*/

  $scope.popup1 = {
    opened: false
  };
  
  $scope.select=function(){
	  console.log("Datepicker date="+$scope.dt);
	  var today = new Date($scope.dt);
	  console.log(" date="+today);
	
	  var dd = today.getDate();
	  var mm = today.getMonth()+1; //January is 0!

	  var yyyy = today.getFullYear();
	/*  var dateee = dateq.getDate() + '/' + (dateq.getMonth() + 1) + '/' +  dateq.getFullYear();*/
	  if(dd<10){
		    dd='0'+dd;
		} 
		if(mm<10){
		    mm='0'+mm;
		} 
		var today =dd+'/'+mm+'/'+yyyy;
	  
	  console.log(today);
	  $http.get("/StatusPortal/statusPortal/getAllTicketsOfUser?id="+today).then(
	  		function(response) {
	  			console.log(response.data)
	  			$scope.ticketList=response.data
	  			console.log($scope.ticketList)
	  			//$scope.dt=response.data
	  		});
	  
  }
  
});