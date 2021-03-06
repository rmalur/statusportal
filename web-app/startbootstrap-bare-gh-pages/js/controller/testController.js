app.controller('testController',function($scope,$http){
	$scope.project={}
	$scope.startDate=undefined
	$scope.managerName
	$scope.success=false
	$scope.failure=false
	
	$scope.init=function(){
		$scope.managerList=[]
		$scope.leadList=[]
		$scope.projectList=[]
		$scope.projectListOfUser=[]
		$scope.projectName=undefined
		
		//loading managers List
		$http.get("/StatusPortal/test/getManagerList")
		.then(function(response) {
			$scope.managerList=response.data
		});
		
		//load lead list of
			$http.get("/StatusPortal/test/getLeadList")
		.then(function(response) {
			$scope.leadList=response.data
		});
		
		//load project list of user
		$http.get("/StatusPortal/test/getProjectListOfUser/").then(
				function(response) {
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
		$http.get("/StatusPortal/test/getProjectList/"+managerName).then(
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
		    url: "/StatusPortal/test/saveUser",
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