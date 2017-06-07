app.controller('changePasswordResultController',function($scope, $uibModalInstance,params){
		
			console.log(params.flag[0]);
			$scope.message=undefined
			$scope.init=function(){
				
				if(params.flag[0]==1){
					 $scope.message="Password Changeed Successfully"
					 
				 }else{
						 $scope.message="Unable to change password "
						 
					 }
			}
			
			$scope.ok = function() {
		            
				 
				 $uibModalInstance.dismiss('cancel');
		        };

		        
		});