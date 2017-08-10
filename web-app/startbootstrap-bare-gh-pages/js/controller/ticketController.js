app
		.controller(
				'ticketController',
				function($scope, $http, $filter) {
					app.config([ '$qProvider', function($qProvider) {
						$qProvider.errorOnUnhandledRejections(false);
					} ]);

					$scope.projectName
					$scope.ticket_id
					$scope.projectNameForOneProject
					$scope.ticketList = []
					$scope.projectList = []
					$scope.resourceList = []
					$scope.projectListHidden = true
					$scope.showMessageForResource=false

					$scope.today = function() {
						/*
						 * $scope.dt = new Date(); $scope.end= new Date();
						 */};
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
						console.log("Datepicker date=" + $scope.dt);
						var today = new Date($scope.dt);
						console.log(" date=" + today);
						var end = new Date($scope.end);
						console.log("End date=" + end);

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

						console.log(today);

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

						console.log(end);

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
								// console.log("response="+response);
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
											$("#tickets").autocomplete({
																source : response.data[1],
																select : function(
																		event,ui) {
																	$scope.showTicketInfo(ui.item.value)
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

					}
					// for loading all data related to selected project
					$scope.loadAllData = function() {
						var projectId
						if ($scope.project) {
							projectId = $scope.project.project_id
							console.log("ProjectID=" + $scope.projectId);
						}
					}
					// for loading the table entries
					$scope.showTicketInfo = function(ticket_ID) {

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
						// fetching all tickets of related to user
						/*
						 * $http.get("/StatusPortal/test/loadAllTicketsOfuser/").then(
						 * function(response) { $scope.ticketList=response.data
						 * $scope.$apply });
						 */

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

							$scope.ticketList = response.data
					
							if($scope.ticketList.length==0){
								$scope.showMessageForResource=true
							}

						});
					}

					// ============================= For
					// pegination======================================
					$scope.currentPage = 0;
					$scope.pageSize = 3;
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

app.filter('startFrom', function() {
	return function(input, start) {
		start = +start; //parse to int
		return input.slice(start);
	}
});