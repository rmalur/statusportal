app.controller('ticketController',function($scope,$http,$filter){
	
	$scope.projectName
	$scope.projectNameForOneProject
	$scope.ticketList=[]
	$scope.projectList=[]
	$scope.resourceList=[]
	$scope.projectListHidden=true

		//load all info tickets,projects,resources initially
		$scope.allTicketsOfUser=function(){
		
		//fetching all tickets of realted to user
		$http.get("/StatusPortal/test/loadAllTicketsOfuser/").then(
				function(response) {
					$scope.ticketList=response.data
					$scope.$apply
				});
		
		//fetching the list of projects	
		$http.get("/StatusPortal/test/getProjectListOfUser")
		.then(function(response) {
			$scope.projectList=response.data
			if($scope.projectList.length>1){
				$scope.projectListHidden=false
			}else{
				$scope.projectNameForOneProject=$scope.projectList[0].projectName
			}
			
		})
		
		
		//fetching all resources reealted to user(manager /lead)
		$http.get("/StatusPortal/test/getResourcesList/").then(
				function(response) {
					$scope.resourceList=response.data
					$scope.$apply
				});	
	
	}
		
	//load tickets on basis of user and selected project
		$scope.loadTicketsOfProject=function(projectNameFromFunction){
			var projectName
			if(projectNameFromFunction!=null){
				projectName=projectNameFromFunction
			}else{
				projectName=$scope.projectName.projectName
			}
			
			$http.get("/StatusPortal/test/getTicketListOfProject/"+projectName).then(
					function(response) {
						$scope.ticketList=response.data
						$scope.$apply
					});
			
			$scope.loadResources(projectName)
			
		}
	//load the resources realted to project
	$scope.loadResources=function(projectName){
		$http.get("/StatusPortal/test/getResourcesList/"+projectName).then(
				function(response) {
					$scope.resourceList=response.data
					$scope.$apply
				});	
		
		}
		//filtering the tickets on basis of resources
		$scope.filterOnBasisOfResource=function(){
			var projectName
			/*if($scope.projectNameForOneProject!=null){
				console.log("projectNameForOneProject="+$scope.projectNameForOneProject)
				projectName=$scope.projectNameForOneProject
			}else{
				console.log("$scope.projectName.projectName="+$scope.projectName.projectName)
				projectName=$scope.projectName.projectName
			}*/
			
			$http({
				method :"POST",
				url:"/StatusPortal/test/getTicktetsOnBasisOfResources",
				data:{resourceName:$scope.resourceName}
				
			}).then(function(response){
				//console.log("response="+response);
				$scope.ticketList=response.data
				
				//$scope.allTickets=response;
			});
		}
		 
	
});

