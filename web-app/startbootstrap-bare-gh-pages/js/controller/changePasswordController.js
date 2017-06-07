app.controller('changePasswordController',function($scope, $http,$uibModal,$uibModalInstance){
			
		$scope.currentPassword=undefined
		$scope.newPassword=undefined
		$scope.confirmPassword=undefined
		$scope.password={}
		$scope.passwordFlag=false
		
			 $scope.ok = function() {
		            
			console.log("ok function")
			if($scope.newPassword==$scope.confirmPassword){
							$scope.password.currentPassword=$scope.currentPassword;
							$scope.password.newPassword=$scope.newPassword;
							$scope.password.confirmPassword=$scope.confirmPassword;
							
							console.log("password="+$scope.password)
							var password=$scope.password
							$http({
								method: "POST",
							    url: "/StatusPortal/ForgetPassword/changePassword",
							    data: {password}
							}).then(function (response) {
						       
						        console.log("response data flag=="+response.data)
						        var modalInstance=$uibModal.open({
									animation:true,
									templateUrl:'result.html',
									controller:'changePasswordResultController',
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
						}else{
							
								$scope.passwordFlag=true
							
						}
						

							
		            
				 
				 
		        };

		        $scope.cancel = function() {
		        	console.log("cancel function")
		        	$uibModalInstance.dismiss('cancel');
		        };
		});