		var app = angular.module('myApp', ['ui.bootstrap']);
		
		app.directive('jqdatepicker', function () {
		    return {
		        restrict: 'A',
		        require: '^?ng-model',
		         link: function (scope, element, attrs, ngModelCtrl) {
		            $(element).datepicker({
		            	changeYear:true,
		                changeMonth:true,
		                dateFormat: 'dd/mm/yy',
		                maxDate: new Date(),
		                onSelect: function (creationDate) {
		                    scope.creationDate = creationDate;
		                  
		                    scope.$apply();
		                }
		            });
		        }
		    };
		});
		app.controller('myController',function($scope, $http, $timeout,$window,$uibModal,$filter) {
							$scope.assigneeList=[];
							$scope.alltickets=undefined;
							$scope.ticketData={}
							$scope.projectName=undefined
							$scope.projectListHidden=true
							$scope.SummaryDisabled=false
							$scope.creationDate=$filter('date')(new Date(), "dd/MM/yyyy");    
							
							$http.get("/StatusPortal/test/getProjectListOfUser/").then(
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
								    url: "/StatusPortal/test/fetchMethodology",
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
								$http.get("/StatusPortal/StatusPortal/getTicketInfo/"+ ticket_ID).then(
										function(response) {
											$scope.ticketData = response.data[0];
											$scope.ticketData.status=response.data[1]
											$scope.assignee=$scope.ticketData.assignee;
											$scope.totalWorkHrs=response.data[2];
										});
							};

							
							//updating the ticket info
							$scope.updateTicketInfo=function(){
								$scope.success=false;
								$scope.failure=false;								
								$scope.showData=true
								$scope.showTicket_id=false
								$scope.showSummary=false
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
								
								
								
								if($scope.showData){
								
								$scope.ticketData.creationDate=$scope.creationDate;
								$scope.ticketData.assignee=$scope.assignee;
								$scope.ticketData.workDoneBy=$scope.workDoneBy
								$scope.ticketData.project=$scope.project
								var ticketData=$scope.ticketData;
								
								$http({
									method: "POST",
								    url: "/StatusPortal/StatusPortal/updateTodaysTicket",
								    data: {ticketData}
								}).then(function (response) {
									if(response.data == 'true'){
										$scope.success = response.data;
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

						
							//reloading the page
							$scope.reload=function(){
								location.reload();
							}
							
						});
		
		