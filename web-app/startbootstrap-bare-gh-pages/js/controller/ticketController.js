app
		.controller(
				'ticketController',
				function($scope, $http, $filter, $window, $uibModal) {
					app.config([ '$qProvider', function($qProvider) {
						$qProvider.errorOnUnhandledRejections(false);
					} ]);
app.config(['$provide', function ($provide) {
    $provide.decorator("$exceptionHandler", ['$delegate', '$injector', function ($delegate, $injector) {
        return function (exception, cause) {
            var exceptionsToIgnore = ['Possibly unhandled rejection: backdrop click', 'Possibly unhandled rejection: cancel', 'Possibly unhandled rejection: escape key press']
            if (exceptionsToIgnore.indexOf(exception) >= 0) {
                return;
            }
            $delegate(exception, cause);                    
        };
    }]);
}]);
					$scope.projectName
					$scope.ticket_id
					$scope.projectNameForOneProject
					$scope.ticketList = []
					$scope.projectList = []
					$scope.resourceList = []
					$scope.projectListHidden = true
					$scope.showMessageForResource=false

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
					     
					     $scope.maxDateOptions={
					       
					       maxDate:new Date(),
					       showWeeks:true
					       
					       
					     }
					     $scope.setMaxDateOptions=function(){
					       
					       $scope.maxDateOptions.maxDate=new Date($scope.dt.getFullYear(),$scope.dt.getMonth()+3,$scope.dt.getDate())
					       
					     }
					$scope.popup1 = {
						opened : false
					};

					$scope.open2 = function() {

						$scope.popup2.opened = true;
					};
					$scope.setDate = function(year, month, day) {
						$scope.end = new Date().format('dd/MM/yyyy');
						console.log("EnD DAte=" + $scope.end)
					};

					$scope.formats = [ 'dd/MM/yyyy' ];
					$scope.format = $scope.formats[0];

					/* $scope.altInputFormats = ['M!/d!/yyyy']; */

					$scope.popup2 = {
						opened : false
					};

					$scope.select = function() {
					
						var today = new Date($scope.dt);
					
						var end = new Date($scope.end);
					

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

					

						var dd = end.getDate();
						var mm = end.getMonth() + 1; // January is 0!

						var yyyy = end.getFullYear();

						if (dd < 10) {
							dd = '0' + dd;
						}
						if (mm < 10) {
							mm = '0' + mm;
						}
						var end = dd + '/' + mm + '/' + yyyy;

					

						var projectName
						if ($scope.projectName) {
							projectName = $scope.projectName
						} else {
							projectName = null
						}
						if (today != null && end != null) {
							console.log("Today="+today)
							console.log("end="+end)
							$http({
								method : "POST",
								url : "/StatusPortal/ticketData/getAllTicketsOfDate",
								data : {
									todaysDate : today,
									endDate : end,
									projectName : projectName
								}

							}).then(function(response) {
					
								$scope.ticketList = response.data
								if($scope.ticketList.length==0){
									$scope.showMessageForResource=true
								}
							});
						}
					}
					// load all info tickets,projects,resources initially
					$scope.allTicketsOfUser = function() {

						var projectId
						if ($scope.project) {
							projectId = $scope.project.project_id
						}

						// fetching all tickets of realted to user
						$http.get("/StatusPortal/ticketData/loadAllTicketsOfuser/")
								.then(
										function(response) {

											$scope.ticketList = response.data[0]
											if($scope.ticketList.length==0){
												$scope.showMessageForResource=true
											}
											
											$scope.$apply
											$("#ticketIds").autocomplete({
																source : response.data[1],
																select : function(
																		event,ui) {
																	$scope.showTicketHistory(ui.item.value)
																}
															})

										});

						// fetching the list of projects
						$http.get("/StatusPortal/ticketData/getProjectListOfUser")
								.then(
										function(response) {
											$scope.projectList = response.data
											if ($scope.projectList.length > 1) {
												$scope.projectListHidden = false
											} else {
												$scope.projectNameForOneProject = $scope.projectList[0].projectName
											}

										})

						// fetching all resources reealted to user(manager
						// /lead)
						$http.get("/StatusPortal/ticketData/getResourcesList/").then(
								function(response) {
									$scope.resourceList = response.data
									$scope.$apply
								});
						/*// fetching all resources related to manager
						
						$http.get("/StatusPortal/ticketData/getResourceListforManager/").then(
								function(response) {
									$scope.resourceList = response.data
									$scope.$apply
								});*/
						
					}
					
						// for deleting the ticket
					$scope.deleteTicket=function(rowid){
						
						var modalInstance=$uibModal.open({
						
							animation:true,
							templateUrl:'delete.html',
							controller:'deleteDataController',
							size:'md',
							resolve : {
								params : function() {
									return {
										row :rowid
									}
								}
							}
						})
						
					};
					
					
					//for sending the mail
					$scope.sendMail=function(){
						
						var modalInstance=$uibModal.open({
							animation:true,
							templateUrl:'mail.html',
							controller:'mailController',
							size:'md'
						})
						
					};
				
					
					// for loading all data related to selected project
					$scope.loadAllData = function() {
						var projectId
						if ($scope.project) {
							projectId = $scope.project.project_id
							
						}
					}
					// for loading the table entries
					$scope.showTicketHistory = function(ticket_ID) {
								console.log(ticket_ID)
						$http.get(
								"/StatusPortal/ticketData/showTicketData/"
										+ ticket_ID).then(function(response) {
							$scope.ticketList = response.data;
							
							$scope.$apply
						});
					};

					// load tickets on basis of user and selected project
					$scope.loadTicketsOfProject = function(
							projectNameFromFunction) {
						var projectName
						if (projectNameFromFunction != null) {
							projectName = projectNameFromFunction
						} else {
							projectName = $scope.projectName.projectName
						}

						$http.get(
								"/StatusPortal/ticketData/getTicketListOfProject/"
										+ projectName).then(function(response) {
							$scope.ticketList = response.data
							$scope.$apply
						});

						$scope.loadResources(projectName)
						
					}
					// load the resources realted to project
					$scope.loadResources = function(projectName) {
						$http.get(
								"/StatusPortal/ticketData/getResourcesList/"
										+ projectName).then(function(response) {
							$scope.resourceList = response.data
							$scope.$apply
						});

					}

					// filtering the tickets on basis of resources
					$scope.filterOnBasisOfResource = function() {
						var projectName

						$http(
								{
									method : "POST",
									url : "/StatusPortal/ticketData/getTicktetsOnBasisOfResources",
									data : {
										resourceName : $scope.resourceName
									}

								}).then(function(response) {
									if($scope.resourceName == "All"){
										$scope.ticketList=response.data[0]
										
									}else{
										$scope.ticketList = response.data
									}
							
					
							if($scope.ticketList.length==0){
								$scope.showMessageForResource=true
							}

						});
					}

					// ============================= For
					// pegination======================================
					$scope.currentPage = 0;
					$scope.pageSize = 15;
					$scope.q = '';

					$scope.getData = function() {
						// needed for the pagination calc
						// https://docs.angularjs.org/api/ng/filter/filter
						return $filter('filter')($scope.ticketList, $scope.q)
						/*
						 * // manual filter // if u used this, remove the filter
						 * from html, remove above line and replace data with
						 * getData()
						 * 
						 * var arr = []; if($scope.q == '') { arr = $scope.data; }
						 * else { for(var ea in $scope.data) {
						 * if($scope.data[ea].indexOf($scope.q) > -1) {
						 * arr.push( $scope.data[ea] ); } } } return arr;
						 */
					}

					$scope.numberOfPages = function() {
						return Math.ceil($scope.getData().length
								/ $scope.pageSize);
					}

					for (var i = 0; i < 65; i++) {
						$scope.ticketList.push("Item " + i);
					}

					//===================================================================================

				});

	
app.controller("mailController",function($scope,$http,$uibModal,$uibModalInstance){
	
	 $scope.ok = function() {
			$http({
					method: "POST",
				    url: "/StatusPortal/ticketData/sendDSR",
				   
				}).then(function (response) {
			        var modalInstance=$uibModal.open({
						animation:true,
						templateUrl:'mailResult.html',
						controller:'mailResultController',
						size:'md',
						resolve : {
							params : function() {
								return {
									flag :response.data
								}
							}
						}
					});
			        $uibModalInstance.dismiss('cancel');
			        	
				});

	       
};
	
	 $scope.cancel = function() {
		 $uibModalInstance.dismiss('cancel');
     };
});

app.controller('mailResultController',function($scope, $uibModalInstance,params){
	
	
	$scope.message=undefined
	$scope.init=function(){
		
		if(params.flag[0]==1){
			 $scope.message="Mail Sent Successfully"
			 
		 }else{
				 $scope.message="Unable to sent mail "
				 
			 }
	}
	
	$scope.ok = function() {
            
		 
		 $uibModalInstance.dismiss('cancel');
        };

        
});
app.filter('startFrom', function() {
	return function(input, start) {
		start = +start; //parse to int
		return input.slice(start);
	}
});

app.controller("deleteDataController",function($scope,$http,$uibModal,$uibModalInstance,params){
	 var row = params.row;	
	 console.log("rowid="+params.row)
	
	 $scope.ok = function() {
		
		
			$http({
					method: "POST",
				    url: "/StatusPortal/StatusPortal/deleteTicket",
				    data:{row}
				}).then(function (response) {
			        var modalInstance=$uibModal.open({
						animation:true,
						templateUrl:'deleteResult.html',
						controller:'deleteDataResultController',
						size:'md',
						
						resolve : {
							params : function() {
								return {
									flag :response.data
								}
							}
						}
					});
			        $uibModalInstance.dismiss('cancel');
			        	
				});

	       
};
	
	 $scope.cancel = function() {
		 $uibModalInstance.dismiss('cancel');
    };
});

app.controller('deleteDataResultController',function($scope, $window, $uibModalInstance,params){
	
	
	$scope.message=undefined
	$scope.init=function(){
	
		if(params.flag[0]==1){
			 $scope.message="Data Deleted Successfully"
			 
		 }else{
				 $scope.message="Unable to delete data "
				 
			 }
	}
	
	$scope.ok = function() {
    
		 $window.location.reload();
		 
       };

       
});


