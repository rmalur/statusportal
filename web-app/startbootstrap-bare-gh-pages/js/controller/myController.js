		var app = angular.module('myApp', ['ui.bootstrap']);
		
		
		app.controller('myController',function($scope, $http, $timeout,$window,$uibModal,$filter) {
							$scope.assigneeList=[];
						
							$scope.alltickets=undefined;
							$scope.ticketData={}
							$scope.projectName=undefined
							$scope.projectListHidden=true
							$scope.SummaryDisabled=false
							$scope.showReasons=true
							$scope.editEta=false
							$scope.button=true
							$scope.reason=null
						
							
							//for fetching reasonList
							$http({
								method:"GET",
								url:"/StatusPortal/statusPortal/fetchReasonList"
							}).then(function(response){
								$scope.reasonList=response.data
								
							})
							
							//Getting ProjectList related to user
							$http.get("/StatusPortal/ticketData/getProjectListOfUser/").then(
									function(response) {										
										if(response.data.length>1){
											$scope.projectListHidden=false
										}else{
											
											$scope.loadAllData()
										}
										$scope.projectListOfUser=response.data
										
									});
							
						    
							//for loading all data related to selected project
							$scope.loadAllData=function(){
								
								
								var projectId
								if($scope.project){
								 projectId=$scope.project.project_id
								}
								
								// getting the ticktIds related to user and project
									$http({
									method: "POST",
								    url: "/StatusPortal/StatusPortal/ticketIds",
								    data: {projectId}
								}).then(function (response) {
									if(response.data){										
										$("#tickets").autocomplete({
											source : response.data,
											select:function(event,ui){
												$scope.showTicketInfo(ui.item.value)
												}
											})
										}else{
										}
											
									})
										
									
								
									// getting the assigneeList related to project	
										
								$http({
									method: "POST",
								    url: "/StatusPortal/StatusPortal/assigneeList",
								    data: {projectId}
								}).then(function(response) {
											$scope.assigneeList=response.data											
										})
										
										
									//fetching methodology of project
								$http({
									method: "POST",
								    url: "/StatusPortal/ticketData/fetchMethodology",
								    data: {projectId}
								}).then(function(response) {
											$scope.methodology=response.data
										})	
									
									if ($scope.methodology == 'SCRUM') {
										$scope.isHideCheck = !$scope.isHideCheck;
					                    
									} else{
										$scope.isHideSave = !$scope.isHideSave;
					                   
										}
									
								
	
							}
									
							// for loading the table entries
							$scope.showTicketInfo = function(ticket_ID) {
								$scope.SummaryDisabled=true
								$scope.editEta=true;
								$scope.button=false
								$http.get("/StatusPortal/StatusPortal/getTicketInfo/"+ ticket_ID).then(
										function(response) {
											$scope.ticketData = response.data[0];
											$scope.ticketData.status=response.data[1]
											$scope.assignee=$scope.ticketData.assignee;
											
											$scope.totalWorkHrs=response.data[2];
											$scope.eta=response.data[3]
										
											
										});
								
							};

							
							//updating the ticket info
							$scope.updateTicketInfo=function(){
								$scope.success=false;
								$scope.failure=false;								
								$scope.showData=true
								$scope.showTicket_id=false
								$scope.showSummary=false
								$scope.showeta=false
								$scope.showAssignee=false
								$scope.showworkdoneBy=false
								$scope.showImpediments=false
								$scope.showCreationDate=false
								$scope.showStatus=false
								$scope.showError=false;
								
								
								if($scope.ticketData.ticket_id==null){
									
									$scope.showTicket_id=true
									$scope.showData=false;
								}
								
								
								
								if($scope.ticketData.summary==null){
									$scope.showSummary=true
									$scope.showData=false
								}
								
								
								
								
								
								if($scope.assignee==null){
									$scope.showAssignee=true
									$scope.showData=false
								}
								
								
								if($scope.workDoneBy==null){
									$scope.showworkdoneBy=true
									$scope.showData=false
								}
								
								
								if($scope.ticketData.workingHrs==null){
									$scope.showTodaysWrkHrs=true
									$scope.showData=false
								}
								
								if($scope.ticketData.workingMinutes==null){
									$scope.showTodaysWrkHrs=true
									$scope.showData=false
								}
								
								
								if($scope.creationDate==null){
									$scope.showCreationDate=true
									$scope.showData=false
								}
								
								
								if($scope.ticketData.status==null){
									$scope.showStatus=true
									$scope.showData=false
								}
								
								
								if($scope.eta==null){
									$scope.eta="0.00"
									
								}
								
								
								
							if($scope.button){
									if($scope.reason==null){
										console.log($scope.reason)
									$scope.showData=false
									}else{
									$scope.showData=true
									}
								}
								
								
								if($scope.showData){
								
								$scope.ticketData.creationDate=$scope.creationDate;
								$scope.ticketData.assignee=$scope.assignee;
								$scope.ticketData.workDoneBy=$scope.workDoneBy
								$scope.ticketData.project=$scope.project
								$scope.ticketData.eta=$scope.eta
								$scope.ticketData.reason=$scope.reason
								
								var ticketData=$scope.ticketData;
								
								$http({
									method: "POST",
								    url: "/StatusPortal/StatusPortal/saveTicketEntry",
								    data: {ticketData}
								}).then(function (response) {
									if(response.data == 'true'){
										$scope.success = response.data;
									}else{
										$scope.failure=true;
									}
							
							    },function(response){
							    	$scope.failure=true;
							    });
								}else{
									
									$scope.showError=true;
								}		
							};
							
							
							
							$scope.searchRecords=function(startDate,endDate){
								$http({
									method :"POST",
									url:"/StatusPortal/StatusPortal/getAllTicketHistory",
									data:{startDate:startDate,endDate:endDate}
								}).then(function(response){
									$scope.allTickets=response;
								},function(response){
									
								});
										
							};
							
							
							
							//for getting all records from db
							$scope.allRecords=function(){
								$http({
									method :"GET",
									url:"/StatusPortal/StatusPortal/getAllTicketHistory.json",
									
								}).then(function(response){
									$scope.allTickets=response;
								},function(response){
								
								});

								
							};
							
							//for changing the password
							$scope.changePassword=function(){
								
								var modalInstance=$uibModal.open({
									animation:true,
									templateUrl:'changePassword.html',
									controller:'changePasswordController',
									size:'lg'
								})
								
							};
						
							//for creating new ticket
							$scope.createNewTicket=function(){
								var modalInstance=$uibModal.open({
									animation:true,
									templateUrl:'createNewTicket.html',
									controller:'createNewTicketController',
									size:'lg'
								})
								
							};
							
							//for displaying reasons
							$scope.toggle=function(){
								
								$scope.editEta=false;
								$scope.showReasons=false;
								$scope.button=true
								
							}
						
							//reloading the page
							$scope.reload=function(){
								location.reload();
							}
					
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
		
		$scope.today = function() {
			
			 $scope.dt= new Date(); 
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
			console.log(today)
			$scope.creationDate=today
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

		$scope.options = {
		          
		          maxDate: new Date(),
		          showWeeks: true
		        };
		$scope.formats = [ 'dd/MM/yyyy' ];
		$scope.format = $scope.formats[0];

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
			console.log(today)
			$scope.creationDate=today
		}
		
		
		});