app.controller('changePasswordController',function($scope, $http,$uibModal,$uibModalInstance){
			
		$scope.currentPassword=undefined
		$scope.newPassword=undefined
		$scope.confirmPassword=undefined
		$scope.password={}
		$scope.passwordFlag=false
		
			 $scope.ok = function() {
				if($scope.newPassword==$scope.confirmPassword){
							$scope.password.currentPassword=$scope.currentPassword;
							$scope.password.newPassword=$scope.newPassword;
							$scope.password.confirmPassword=$scope.confirmPassword;
							var password=$scope.password
							$http({
								method: "POST",
							    url: "/StatusPortal/ForgetPassword/changePassword",
							    data: {password}
							}).then(function (response) {
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
		        	$uibModalInstance.dismiss('cancel');
		        };
		});