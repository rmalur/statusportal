		var app = angular.module('myApp', []);
		
		app
				.controller(
						'myController',
						function($scope, $http, $timeout,$window) {
							$scope.ticketData = undefined;
							$scope.success=false;
							$scope.failure=false;
							//function for getting the ticktIds
							$http.get("/StatusPortal/StatusPortal/ticketIds.json")
									.then(function(response) {
										$("#tickets").autocomplete({
											source : response.data
										})
									})

							//for loading the table entries			
							$scope.showTicketInfo = function(ticket_ID) {
								console.log(ticket_ID);
								$http.get(
										"/StatusPortal/StatusPortal/getTicketInfo/"
												+ ticket_ID).then(
										function(response) {
											$scope.ticketData = response.data;
											//console.log($scope.ticketData);
										});
							};

							$scope.updateTicketInfo=function(){
								
								var ticketData=$scope.ticketData;
								$http({
								    method: "POST",
								    url: "/StatusPortal/updateTodaysTicket",
								    data: {ticketData}
								}).then(function (response) {
							        $scope.success = true;
							        console.log( $scope.success);
							       // $window.location.reload();
							    },function(response){
							    	
							    	$scope.failure=true;
							    	 console.log( $scope.failure);
							    }
									
								);
									
								}

							
						});