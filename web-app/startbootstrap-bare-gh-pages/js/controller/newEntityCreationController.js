app.controller('newEntityCreationController',function($scope,$http){
	$scope.project={}
	$scope.startDate=undefined
	$scope.managerName
	$scope.success=false
	$scope.failure=false
	
	
	
	/*++++++++++++++======================================*/
	
	$scope.today = function() {
						
						 $scope.dt = new Date(); $scope.end= new Date();
						 };
					$scope.today();

					$scope.clear = function() {
						$scope.dt = null;
						$scope.end = null;
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

					$scope.formats = [ 'dd/MM/yyyy' ];
					$scope.format = $scope.formats[0];

					/* $scope.altInputFormats = ['M!/d!/yyyy']; */
					$scope.options = {
					          
					          maxDate: new Date(),
					          showWeeks: true
					        };
					     
					    
					$scope.popup1 = {
						opened : false
					};

					
					
					$scope.select = function() {
						console.log("Datepicker date=" + $scope.dt);
						var today = new Date($scope.dt);
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
						$scope.projectCreationDate=today
						console.log(today);	
					}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*=====================================================*/
	$scope.init=function(){
		$scope.managerList=[]
		$scope.leadList=[]
		$scope.projectList=[]
		$scope.projectListOfUser=[]
		$scope.projectName=undefined
		
		//loading managers List
		$http.get("/StatusPortal/ticketData/getManagerList")
		.then(function(response) {
			$scope.managerList=response.data
		});
		
		//load lead list of
			$http.get("/StatusPortal/ticketData/getLeadList")
		.then(function(response) {
			$scope.leadList=response.data
		});
		
		//load project list of user
		$http.get("/StatusPortal/ticketData/getProjectListOfUser/").then(
				function(response) {
					$scope.projectListOfUser=response.data
				});
		//load methodology list for project
		$http.get("/StatusPortal/ticketData/getMethodologyList/").then(
				function(response) {
					$scope.methodologyList=response.data
				});
		
	};
	
	
	//function for saving new project
	$scope.saveProject=function(){
		$scope.project.projectStartDate=$scope.projectCreationDate
		$http({
			method: "POST",
		    url: "/StatusPortal/ticketData/addProjectInfo",
		    data: $scope.project
		}).then(function (response) {
	        if(response.data==1){
	        	$scope.success = true
	        }else{
	        	$scope.failure = true
	        }
	      // window.location.reload()
	    });
		
	}
	
	
	//loading the projectList for manager
	$scope.loadProjectList=function(managerName){
		var managerName=$scope.managerName
		$http.get("/StatusPortal/ticketData/getProjectList/"+managerName).then(
				function(response) {
					$scope.projectList=response.data
				});
		
	}
	
	
	
	//function for saving new user
	$scope.saveUser=function(){
		
		$scope.employee.managerName=$scope.managerName
		$scope.employee.project=$scope.projectName
		$scope.employee.lead=$scope.leadName
		$http({
			method: "POST",
		    url: "/StatusPortal/ticketData/saveUser",
		    data: $scope.employee
		}).then(function (response) {	        
	        if(response.data==1){
	        	$scope.success = true
	        }else{
	        	$scope.failure = true
	        }
	       
	    });
		
	}
	
	//reloading the page
	$scope.reload=function(){
		location.reload();
	}
});