app.controller('ticketController',function($scope,$http){
	
	$scope.projectName
	$scope.projectNameForOneProject
	$scope.ticketList=[]
	$scope.projectList=[]
	$scope.resourceList=[]
	$scope.projectListHidden=true
	
	//
		
		//load all info tickets,projects,resources initially
		$scope.allTicketsOfUser=function(){
		
		//fetching all tickets of realted to user
		$http.get("/StatusPortal/test/loadAllTicketsOfuser/").then(
				function(response) {
					$scope.ticketList=response.data
					console.log("ticketList="+$scope.ticketList)
					$scope.$apply
				});
		
		//fetching the list of projects	
		$http.get("/StatusPortal/test/getProjectListOfUser")
		.then(function(response) {
			$scope.projectList=response.data
			console.log("length of projectList="+$scope.projectList.length)
			if($scope.projectList.length>1){
				$scope.projectListHidden=false
				//$scope.loadResources($scope.projectNameForOneProject)
			}else{
				$scope.projectNameForOneProject=$scope.projectList[0].projectName
				//?$scope.loadResources($scope.projectNameForOneProject)
			}
			
		})
		
		
		//fetching all resources reealted to user(manager /lead)
		$http.get("/StatusPortal/test/getResourcesList/").then(
				function(response) {
					console.log("getResourceList function ="+response.data)
					$scope.resourceList=response.data
					$scope.$apply
				});	
	
		
	}
		
	//load tickets on basis of user and selected project
		$scope.loadTicketsOfProject=function(projectNameFromFunction){
			console.log("load tickets of project function")
			var projectName
			if(projectNameFromFunction!=null){
			
				console.log("projectName if length is 1="+projectNameFromFunction)
				projectName=projectNameFromFunction
			}else{
				console.log("else part of loadTicketList")
				projectName=$scope.projectName.projectName
			}
			
			$http.get("/StatusPortal/test/getTicketListOfProject/"+projectName).then(
					function(response) {
						console.log(response)
						$scope.ticketList=response.data
						console.log("ticketList="+$scope.ticketList)
						$scope.$apply
					});
			
			$scope.loadResources(projectName)
			
			
		}
	//load the resources realted to project
	$scope.loadResources=function(projectName){
		console.log("projectname in loadresources="+projectName)
		$http.get("/StatusPortal/test/getResourcesList/"+projectName).then(
				function(response) {
					console.log(response.data)
					$scope.resourceList=response.data
					$scope.$apply
				});	
		
		}
		//filtering the tickets on basis of resources
		$scope.filterOnBasisOfResource=function(){
			console.log("filter on the basis of resource function")
			console.log("resourceName="+$scope.resourceName)
			
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
				if($scope.ticketList.length==0){
					console.log("ticketList is empty")
				}else{
					
					console.log("$scope.ticketList="+$scope.ticketList)
				}
				
				//$scope.allTickets=response;
			});
		}
		 
	
});