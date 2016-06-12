(function () {
    'use strict';
    
    angular.module('app').controller('announceCtrl', [ '$scope', '$state','$window','$cookieStore','$log','$http','FileUploader','$location','moment','$interval', 
                          function ($scope, $state,$window, $cookieStore,$log,$http,FileUploader,$location,moment,$interval) {
  	 	   /**
  	        * Start create Announce
  	        */
  	 	    
  	 	    $scope.announceSelected=false;
  	        $scope.announceSaved=false;
  	        $scope.announceSaveSubmitted=false;
  	        $scope.theUser = $cookieStore.get('theUser');
  	        
  	      /**
			 * Delay
			 */
			
			$interval(function(){ 
				$scope.ready=true;
			},1000);
  	         
  	         var announceUploader = $scope.announceUploader = new FileUploader({
  	 	            url: 'http://localhost:8080/ukadtogo/service/announce/receiveFile'
  	 	        });
  	 	        
  	 	
  	 	        // FILTERS

  	 	      announceUploader.filters.push({
  	 	            name: 'customFilter',
  	 	            fn: function(item /*{File|FileLikeObject}*/, options) {
  	 	                return this.queue.length < 10;
  	 	            }
  	 	        });

  	 	        // CALLBACKS
  	 	    announceUploader.onBeforeUploadItem = function(item) {
  	 	            console.info('onBeforeUploadItem', item);
  	 	            item.formData.push({announceId: $scope.theAnnounce.id});
  	 	            $log.info(item);
  	 	            
  	 	        };
  	 	        
  	 	        
  	 	    $scope.createAnnounce = function() {  
  	 	      	$scope.announceSaveSubmitted=true;	      	
  	 	      	$http({ method: 'POST', url: 'http://localhost:8080/ukadtogo/service/announce/createAnnounce', data: this.theAnnounce }).
  	 	      	success(function (data, status, headers, config) {
  	                    $log.info("Call Create Announce Successful"); 
  	                    $scope.announceSelected=true;
  	                    $scope.announceSaved=true;
  	                  	$cookieStore.put('theAnnounce',data);
  	                 	$scope.theAnnounce = data;
  	                 	$scope.theAnnounceMessage='Announce enregistree avec succes';
  	                    $log.info($scope);
  	                             
  	            }).error(function (data, status, headers, config) {
  	                    $log.info("Call Create Announce Failed");
  	                    $cookieStore.put('theAnnounce','');
  	                    $scope.theAnnounceMessage='Announce ne peut etre enregistrer. Essayer plus tard';
  	                    $scope.theAnounce = null; 
  	                    $scope.announceSaved = false;
  	                    $scope.announceSelected = false;
  	                    $log.info($scope);
  	                   
  	                });
  	   
  	           };
  	           /**
  	 	       * End Create Announce
  	 	       */
  	        
  	     /**
     * Start select Announce
     * 
     */
          $scope.selectAnnounce = function(aAnnounce) {
        	  
        	  $scope.theAnnounce = aAnnounce;
        	  
        	  
        	  $scope.announceSelected=true;
              $log.info($scope.theAnnounce.status); 
             	  	              
          };
	 /**
	  * End select Announce
	  */
  	        
  	/**
     * Start clear Announce
     * 
     */
          $scope.clearAnnounce = function() {
            	 
        	  $scope.theAnnounce = '';  	  	   	  	  	                    	  
        	  $scope.announceSelected = false;
              $log.info($scope); 
             	  	              
          };
	 /**
	  * End clear Announce
	  */
    
          /**
			 * Start Delete Announce
			 * 
			 */
			$scope.deleteAnnounce = function(aAnnounce) {
				aAnnounce.deleted = false;
				$http(
						{
							method : 'POST',
							url : 'http://localhost:8080/ukadtogo/service/announce/deleteAnnounce',
							data : aAnnounce
						})
						.success(
								function(data, status,
										headers, config) {
									$log
											.info("Call deleteAnnounce Successful");
									aAnnounce.deleted = true;
									$log.info($scope);
								})
						.error(
								function(data, status,
										headers, config) {
									$log
											.info("Call deleteAnnounce Failed");
									$log.info($scope);

								});

			};
			/**
			 * End Delete
			 */
			
			
 	  	  	                      
/**
 * Start get Announces
 * Get the list of Announces
 */
  $scope.getAllAnnounces = function() {
        
       $http({ method: 'POST', url: 'http://localhost:8080/ukadtogo/service/announce/getAllAnnounces', data: null }).
       success(function (data, status, headers, config) {
                $log.info("Call get All Announces Successful"); 
            	$scope.announces = data;
                $log.info($scope.announces);
                
       }).error(function (data, status, headers, config) {
                $log.info("Call get All Announce Failed");
                $log.info($scope);
       });

  };
 /**
  * End get all Announces
  */
  
  
	   /**
       * Start get Announces with Album
       * Get the list of Announces
       */
        $scope.getAllAnnouncesWithAlbum = function() {
              
             $http({ method: 'POST', url: 'http://localhost:8080/ukadtogo/service/announce/getAllAnnouncesWithAlbum', data: null }).
             success(function (data, status, headers, config) {
                      $log.info("Call get All Announces with album Successful"); 
                  	$scope.announcesWithAlbum=data;
                      $log.info($scope);
                      
             }).error(function (data, status, headers, config) {
                      $log.info("Call get All Announce with album Failed");
                      $log.info($scope);
             });

        };
  	 /**
  	  * End get all Announces with Album
  	  */

        
		/**
		 * Start get Album Photo Get the list photo of
		 * Announces
		 */
		$scope.getAnnounceAlbum = function(aAnnounce) {
			//report too big for cookies
			
			$cookieStore.remove("theAnnounce");
			$cookieStore.put('theAnnounce', aAnnounce);
			$http(
					{
						method : 'POST',
						url : 'http://localhost:8080/ukadtogo/service/announce/getAnnounceAlbum',
						data : aAnnounce
					})
					.success(
							function(data, status,
									headers, config) {
								$log
										.info("Call get All Announces photo Successful");
								$scope.AnnouncePictures = data;
								$log.info($scope);
								$scope.theAnnounce = aAnnounce;			
								//$scope.apply();

							})
					.error(
							function(data, status,
									headers, config) {
								$log
										.info("Call get All Announce phot Failed");
								$log.info($scope);
							});

		};
		/**
		 * End get all Announces album photos
		 */
		
		/**
		 * Start get Album Photo Get the list photo of
		 * Announces
		 */
		$scope.getNextAnnounceAlbum = function(id) {
			var aAnnounce = {
					id : id,
					};
			
			$cookieStore.remove("theAnnounce");
			$http(
					{
						method : 'POST',
						url : 'http://localhost:8080/ukadtogo/service/announce/getNextAnnounceAlbum',
						data : aAnnounce
					})
					.success(
							function(data, status,
									headers, config) {
								$log
										.info("Call get All Announces photo Successful");
								if (data != "") {
									$scope.theAnnounce = data;
									$cookieStore.put('theAnnounce', data);		
									window.location.reload();
								}

							})
					.error(
							function(data, status,
									headers, config) {
								$log
										.info("Call get All Announce phot Failed");
								$log.info($scope);
							});

		};
		/**
		 * End get all Announces album photos
		 */
		
		/**
		 * Start get Album Photo Get the list photo of
		 * Announces
		 */
		$scope.getPreviousAnnounceAlbum = function(id) {
			var aAnnounce = {
					id : id,
					};
			
			$cookieStore.remove("theAnnounce");
			
			$http(
					{
						method : 'POST',
						url : 'http://localhost:8080/ukadtogo/service/announce/getPreviousAnnounceAlbum',
						data : aAnnounce
					})
					.success(
							function(data, status,
									headers, config) {
								$log
										.info("Call get All Announces photo Successful");
								if (data != "") {
									$scope.theAnnounce = data;
									$cookieStore.put('theAnnounce', data);	
									window.location.reload();
								}

							})
					.error(
							function(data, status,
									headers, config) {
								$log
										.info("Call get All Announce phot Failed");
								$log.info($scope);
							});

		};
		/**
		 * End get all Announces album photos
		 */
		
		
		var url = $location.url();
		$log.info('URL='+url);
		
		if(url=='/pages/main') {
			$scope.getAllAnnouncesWithAlbum();
			$cookieStore.remove("announceAlbum_reload");
		}
		else if(url=='/pages/announces'){
			$scope.getAllAnnounces();
		}  
		else if(url=='/pages/announceAlbum' &&  ($scope.AnnouncePictures == null || $scope.AnnouncePictures == '')){								
			if ($cookieStore.get('announceAlbum_reload'))
				$scope.getAnnounceAlbum($cookieStore.get('theAnnounce'));		
												
			if (!$cookieStore.get('announceAlbum_reload')) {
				$cookieStore.put('announceAlbum_reload', "true");
				window.location.reload();
			}
		}
		
}])

})();