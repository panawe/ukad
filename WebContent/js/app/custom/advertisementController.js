(function () {
    'use strict';
    
    angular.module('app').controller('advertisementCtrl', [ '$scope', '$state','$window','$cookieStore','$log','$http','FileUploader','$location','moment', 
                          function ($scope, $state,$window, $cookieStore,$log,$http,FileUploader,$location,moment) {
  	 	   /**
  	        * Start create Project
  	        */
  	 	    
  	 	    $scope.advertisementSelected=false;
  	        $scope.advertisementSaved=false;
  	        $scope.advertisementSaveSubmitted=false;
  	        $scope.theUser = $cookieStore.get('theUser');
  	         
  	         var advertisementUploader = $scope.advertisementUploader = new FileUploader({
  	 	            url: 'http://www.agwedc.com/service/advetisement/receiveFile'
  	 	        });
  	 	        
  	 	
  	 	        // FILTERS

  	 	      advertisementUploader.filters.push({
  	 	            name: 'customFilter',
  	 	            fn: function(item /*{File|FileLikeObject}*/, options) {
  	 	                return this.queue.length < 10;
  	 	            }
  	 	        });

  	 	        // CALLBACKS
  	 	    advertisementUploader.onBeforeUploadItem = function(item) {
  	 	            console.info('onBeforeUploadItem', item);
  	 	            item.formData.push({advertisementId: $scope.theAdvertisement.id});
  	 	            $log.info(item);
  	 	            
  	 	        };
  	 	        
  	 	        
  	 	    $scope.createAdvertisement = function() {  
  	 	      	$scope.advertisementSaveSubmitted=true;	      	
  	 	      	$http({ method: 'POST', url: 'http://www.agwedc.com/service/advertisement/createAdvertisement', data: this.theAdvertisement }).
  	 	      	success(function (data, status, headers, config) {
  	                    $log.info("Call Create Advertisement Successful"); 
  	                    $scope.advertisementSelected=true;
  	                    $scope.advertisementSaved=true;
  	                    //this.theEvent='';
  	                  	$cookieStore.put('theAdvertisement',data);
  	                 	$scope.theAdvertisement=data;
  	                 	$scope.theAdvertisementMessage='Publicite enregistree avec succes';
  	                    $log.info($scope);
  	                             
  	            }).error(function (data, status, headers, config) {
  	                    $log.info("Call Create Advertisement Failed");
  	                    $cookieStore.put('theAdvertisement','');
  	                    $scope.theAdvertisementMessage='Publicite ne peut etre enregistrer. Essayer plus tard';
  	                    $scope.theAdvertisement=null; 
  	                    $scope.advertisementSaved=false;
  	                    $scope.advertisementSelected=false;
  	                    $log.info($scope);
  	                   
  	                });
  	   
  	           };
  	           /**
  	 	       * End Create Advertisement
  	 	       */
  	        
  	     /**
     * Start select Advertisement
     * 
     */
          $scope.selectAdvertisement = function(aAdvertisement) {
        	  
        	  $scope.theAdvertisement = aAdvertisement;
        	  
        	  
        	  $scope.advertisementSelected=true;
              $log.info($scope.theAdvertisement.status); 
             	  	              
          };
	 /**
	  * End select Advertisement
	  */
  	        
  	/**
     * Start clear Advertisement
     * 
     */
          $scope.clearAdvertisement = function() {
            	 
        	  $scope.theAdvertisement = '';  	  	   	  	  	                    	  
        	  $scope.advertisementSelected = false;
              $log.info($scope); 
             	  	              
          };
	 /**
	  * End clear Advertisement
	  */
                   
 	  	  	                      
/**
 * Start get Advertisements
 * Get the list of Advertisements
 */
  $scope.getAllAdvertisements = function() {
        
       $http({ method: 'POST', url: 'http://www.agwedc.com/service/advertisement/getAllAdvertisements', data: null }).
       success(function (data, status, headers, config) {
                $log.info("Call get All Advertisements Successful"); 
            	$scope.advertisements = data;
                $log.info($scope.advertisements);
             //$cookieStore.put('projects',data);
                
       }).error(function (data, status, headers, config) {
                $log.info("Call get All Advertisement Failed");
                $log.info($scope);
                //$cookieStore.put('advertisements',null);
       });

  };
 /**
  * End get all Advertisements
  */
  
               
 /**
  * End get all Events album photos or report
  */
   var url = $location.url();
	$log.info('URL='+url);
	
	if(url=='/pages/advertisements'){
		$scope.getAllAdvertisements();
	}  	  	  	                  
}])

})();