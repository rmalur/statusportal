		var app = angular.module('myApp', []);
		app.factory('Excel',function($window) {
					var uri = 'data:application/vnd.ms-excel;base64,', template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>', base64 = function(s) {
						return $window.btoa(unescape(encodeURIComponent(s)));
					}, format = function(s, c) {
						return s.replace(/{(\w+)}/g, function(m, p) {
							return c[p];
						})
					};
					return {
						tableToExcel : function(tableId, worksheetName) {
							var table = $(tableId), ctx = {
								worksheet : worksheetName,
								table : table.html()
							}, href = uri
									+ base64(format(template, ctx));
							return href;
						}
					};
				});
		
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
		app.controller('myController',function(Excel,$scope, $http, $timeout,$window) {
							$scope.assigneeList=[];
							$scope.alltickets=undefined;
							$scope.ticketData={}
							$scope.projectName=undefined
							// function for getting the ticktIds
							$http.get("/StatusPortal/StatusPortal/ticketIds.json")
									.then(function(response) {
										$("#tickets").autocomplete({
											source : response.data,
											select:function(event,ui){
												//alert(ui.item.value)
												$scope.showTicketInfo(ui.item.value)
											}
										})
									})
									
							
								// function for getting the assigneeList	
								$http.get("/StatusPortal/StatusPortal/assigneeList")
									.then(function(response) {
										
										$scope.assigneeList=response.data
										console.log("assigneeList="+$scope.assigneeList)
									})
									
										//load project list of user
										$http.get("/StatusPortal/test/getProjectListOfUser").then(
												function(response) {
												console.log(response)
												$scope.projectListOfUser=response.data
												});
									
									
							// for loading the table entries
							$scope.showTicketInfo = function(ticket_ID) {
								console.log(ticket_ID);
								$http.get("/StatusPortal/StatusPortal/getTicketInfo/"+ ticket_ID).then(
										function(response) {
											$scope.ticketData = response.data;
											 //console.log($scope.ticketData);
											$scope.creationDate=$scope.ticketData.creationDate;
											$scope.assignee=$scope.ticketData.assignee;
											console.log();
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
								$scope.showWorkDone=false
								$scope.showTodaysWrkHrs=false
								$scope.showImpediments=false
								$scope.showCreationDate=false
								$scope.showStatus=false
								
								$scope.showError=false;
								
								console.log($scope.ticketData)
								if($scope.ticketData.ticket_id==null){
									console.log("ticket id is null")
									$scope.showTicket_id=true
									$scope.showData=false;
								}
								if($scope.ticketData.summary==null){
									console.log("summary is null")
									$scope.showSummary=true
									$scope.showData=false
								}
								
								if($scope.assignee==null){
									console.log("assignee  is null")
									$scope.showAssignee=true
									$scope.showData=false
								}
								if($scope.ticketData.workDone==null){
									console.log("workDone is null")
									$scope.showWorkDone=true
									$scope.showData=false
								}
								if($scope.ticketData.todaysWorkHrs==null){
									console.log("todaysWorkHrs is null")
									$scope.showTodaysWrkHrs=true
									$scope.showData=false
								}
								if($scope.ticketData.impediments==null){
									console.log("impedimentss is null")
									$scope.showImpediments=true
									$scope.showData=false
									
								}
								if($scope.creationDate==null){
									console.log("creationDate is null")
									$scope.showCreationDate=true
									$scope.showData=false
								}
								if($scope.ticketData.status==null){
									console.log("status is null")
									$scope.showStatus=true
									$scope.showData=false
								}
								
								
								
								if($scope.showData){
								
								$scope.ticketData.creationDate=$scope.creationDate;
								$scope.ticketData.assignee=$scope.assignee;
								
								
								
								$scope.ticketData.projectName=$scope.projectName
								var ticketData=$scope.ticketData;
								console.log("ticketdata="+ticketData);
								$http({
									method: "POST",
								    url: "/StatusPortal/StatusPortal/updateTodaysTicket",
								    data: {ticketData}
								}).then(function (response) {
									console.log("response="+response.data)
									if(response.data == 'true'){
										$scope.success = response.data;
									}
							       
							        console.log("success="+$scope.success);
							       
							    },function(response){
							    	console.log("response="+response.data)
							    	$scope.failure=true;
							    	 console.log( "failure="+$scope.failure);
							    });
								}else{
									
									$scope.showError=true;
								}		
							};
							
							$scope.searchRecords=function(startDate,endDate){
								console.log("searchRecords");
								
								$http({
									method :"POST",
									url:"/StatusPortal/StatusPortal/getAllTicketHistory",
									data:{startDate:startDate,endDate:endDate}
								}).then(function(response){
									console.log(response);
									$scope.allTickets=response;
								},function(response){
									console.log(response);
									
								});
										
							};
							
							
							//for getting all records from db
							$scope.allRecords=function(){
									console.log("allRecords");
								
								$http({
									method :"GET",
									url:"/StatusPortal/StatusPortal/getAllTicketHistory.json",
									
								}).then(function(response){
									console.log(response);
									$scope.allTickets=response;
								},function(response){
									console.log(response);
									
								});

								
							};
							
							//for changing the password
							$scope.changePassword=function(){
								console.log("ChangePasswordController")
								/*var modalInstance=$uibModal.open({
									animation:true,
									template:'changePassword.html'
									controller:ChangePasswordController,
									size:'lg'
									
								})*/
								
								
							};
						
							
							//for exporting the table in excel sheet
							$scope.exportToExcel = function(tableId) { // ex: '#my-table'
								console.log("exportToExcel");
								console.log(tableId)
								var exportHref = Excel.tableToExcel(tableId,
										'TicketHistory');
								$timeout(function() {
									location.href = exportHref;
								}, 100); // trigger download
							};

							
						});
		
		/*app.controller('ChangePasswordController',function($scope, $uibModalInstance){
			
			 $scope.ok = function() {
		            
				 console.log("ok function")
				 //$uibModalInstance.close($scope.theThingIWantToSave);
		        };

		        $scope.cancel = function() {
		        	console.log("cancel function")
		        	$uibModalInstance.dismiss('cancel');
		        };
		})*/