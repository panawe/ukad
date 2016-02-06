(function () {
    'use strict';

    angular
        .module('app').controller('userController',[ '$scope','$log','$http',
                                  function ($scope, $log,$http){
	
    /**
     * Start get All users
     * Get the list of members
     */
          $scope.getAllUsers = function() {
                
               $http({ method: 'POST', url: 'http://localhost:8080/ukadtogo/service/user/getUsers', data: null }).
               success(function (data, status, headers, config) {
                        $log.info("Call Successful"); 
                    	$scope.users=data;
                        $log.info($scope);
                        
               }).error(function (data, status, headers, config) {
                        $log.info("Call Failed");
                        $log.info($scope);
               });
  
          };
	 /**
	  * End get all users
	  */
	         	
	
}])})();