app.controller('testController',function($scope,$http){
	$scope.project={}
	$scope.startDate=undefined
	$scope.managerName
	
	$scope.init=function(){
		
		console.log("testController");
		$scope.managerList=[]
		$scope.projectList=[]
		$scope.projectListOfUser=[]
		$scope.projectName=undefined
		
		//loading managers List
		$http.get("/StatusPortal/test/getManagerList")
		.then(function(response) {
			console.log(response.data)
			$scope.managerList=response.data
		})
		//load project list of user
		$http.get("/StatusPortal/test/getProjectListOfUser/").then(
				function(response) {
					console.log(response)
					$scope.projectListOfUser=response.data
				});
		
	};
	
	
	//function for saving new project
	$scope.saveProject=function(){
	
		$scope.project.projectStartDate=$scope.creationDate
		$http({
			method: "POST",
		    url: "/StatusPortal/test/addProjectInfo",
		    data: $scope.project
		}).then(function (response) {
	        $scope.success = true;
	        console.log( $scope.success);
	       
	    },function(response){
	    	
	    	$scope.failure=true;
	    	 console.log( $scope.failure);
	    });
	}
	
	//loading the projectList for manager
	$scope.loadProjectList=function(managerName){
		console.log($scope.managerName)
		var managerName=$scope.managerName
		$http.get("/StatusPortal/test/getProjectList/"+managerName).then(
				function(response) {
					console.log(response)
					$scope.projectList=response.data
				});
		
	}
	
	
	
	//function for saving new user
	$scope.saveUser=function(){
		$scope.employee.managerName=$scope.managerName
		$scope.employee.project=$scope.projectName
		
		
		$http({
			method: "POST",
		    url: "/StatusPortal/test/saveUser",
		    data: $scope.employee
		}).then(function (response) {
	        $scope.success = true;
	        console.log("success="+ $scope.success);
	       
	    },function(response){
	    	
	    	$scope.failure=true;
	    	 console.log( "fail="+$scope.failure);
	    });
		
	}
});