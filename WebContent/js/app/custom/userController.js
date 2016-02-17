(function() {
	'use strict';

	angular
			.module('app')
			.controller(
					'userCtrl',
					[
							'$scope',
							'$state',
							'$window',
							'$cookieStore',
							'$log',
							'$http',
							'FileUploader',
							'$location',
							'moment',
							function($scope, $state, $window, $cookieStore,
									$log, $http, FileUploader, $location,
									moment) {

								$scope.$state = $state;
								$scope.theUser = $cookieStore.get('theUser');
								$scope.submitted = false;
							     $scope.paymentTypes=[{id:0,name:''},
							                             {id:1, name:'Cotisation mensuelle'},
							                             {id:2,name:'Cotisation Annuelle'},
							                             {id:3,name:'Don'}
							                             ];

							         $scope.expenseTypes=[{id:0,name:''},
							                                 {id:4, name:'Organisation Rencontre'},
							                                 {id:5,name:'Projet'},
							                                 {id:6,name:'Donation'},
							                                 {id:7,name:'Frais de fonctionnement'},
							                                 {id:8,name:'Autre'}
							                                 ];
							         
							         $scope.years=[{id:0,name:''},
							                       {id:1,name:2016},
							                       {id:2,name:2017},
							                       {id:3,name:2018}, 
							                       {id:4,name:2019}, 
							                       {id:5,name:2020}];
							         
							         $scope.months=[{name:''},
							                        {name:'Janvier'}, {name:'Fevrier'}, {name:'Mars'}, {name:'Avril'}, {name:'Mai'}, {name:'Juin'},
							                        {name:'Juillet'},{name:'Aout'},{name:'Septembre'},{name:'October'},{name:'Novembre'},{name:'Decembre'}];
							         	


							          /**
							                 * Start create Payment
							                 */
							                $scope.makePayment = function() {  
							                	var transaction={amount:this.amount,
							                			comment:this.comment,
							                			year:this.year,
							                			month:this.month,
							                			modifiedBy:$scope.theUser.id,
							                			io:1,
							                			rebate:0.0,
							                			user:$scope.currUser,
							                			paymentType: {id:this.paymentType}
							                			
							                	};
							                	$scope.paymentSaveSubmitted=true;
							                     $http({ method: 'POST', url: 'http://localhost:8080/ukadtogo/service/user/makePayment', data: transaction }).
							                     success(function (data, status, headers, config) {
							                     $log.info("Call makePayment Successful");  
							                     $scope.paymentSaved=true; 
							                     if(data=='Success'){
							                    	 $scope.thePaymentMessage='Payement Effectue succes';
							                    	 
							                     }else{
							                    	 $scope.thePaymentMessage='Le payement a echoue';
							                    	 $scope.paymentSaved=false;
							                     }
							                    
							                     $log.info($scope);
							                              
							                              
							                     }).error(function (data, status, headers, config) {
							                        $log.info("Call makePayment Failed");
							                        $scope.thePaymentMessage='Le payement a echoue';
							                        $scope.paymentSaved=false;
							                        $log.info($scope);
							                        
							                     });
							        
							                };
							                /**
							                 * End Create Payment
							                 */

							        /**
							                 * Start create Expense
							                 */
							                $scope.saveExpense = function() {  
							                	var transaction={amount:this.amount,
							                			comment:this.comment,
							                			year:this.year,
							                			month:this.month,
							                			modifiedBy:$scope.theUser.id,
							                			io:0,
							                			rebate:0.0,
							                			paymentType: {id:this.expenseType}
							                			
							                	};
							                	$scope.paymentSaveSubmitted=true;
							                     $http({ method: 'POST', url: 'http://localhost:8080/ukadtogo/service/user/saveExpense', data: transaction }).
							                     success(function (data, status, headers, config) {
							                     $log.info("Call makePayment Successful");  
							                     $scope.paymentSaved=true; 
							                     if(data=='Success'){
							                    	 $scope.thePaymentMessage='Payement Effectue succes';
							                    	 
							                     }else{
							                    	 $scope.thePaymentMessage='Le payement a echoue';
							                    	 $scope.paymentSaved=false;
							                     }
							                    
							                     $log.info($scope);
							                              
							                              
							                     }).error(function (data, status, headers, config) {
							                        $log.info("Call makePayment Failed");
							                        $scope.thePaymentMessage='Le payement a echoue';
							                        $scope.paymentSaved=false;
							                        $log.info($scope);
							                        
							                     });
							        
							                };
							                /**
							                 * End Create Expense
							                 */
							                
							                /**
							                 * Start clear Payment
							                 */
							                $scope.clearPayment = function() {   
							                		$log.info('Clear Payment Called');
							                		 $scope.paymentSaveSubmitted=false;
							                    	 $scope.thePaymentMessage='';
							                    	 $scope.month='';
							                    	 $scope.year='';
							                    	 $scope.comment='';
							                    	 $scope.amount='';
							                    	 $scope.paymentType='';	  	
							                    	 $scope.paymentSaved=false;
							                    	 $log.info($scope);
							                };
							                /**
							                 * End clear Payment
							                 */
							                
							                $scope.clearExpense = function() {   
							            		$log.info('Clear Expense Called');
							            		 $scope.paymentSaveSubmitted=false;
							                	 $scope.thePaymentMessage='';
							                	 $scope.month='';
							                	 $scope.year='';
							                	 $scope.comment='';
							                	 $scope.amount='';
							                	 $scope.expenseType='';	  	
							                	 $scope.paymentSaved=false;
							                	 $log.info($scope);
							            };
							            /**
							             * End clear Payment
							             */
							        $scope.drawPayments = function() {
							        	
							        	$http({ method: 'POST', url: 'http://localhost:8080/ukadtogo/service/user/getYearlySummary', data: null }).
							   success(function (data, status, headers, config) {
							            $log.info("Call get getYearlySummary"); 
							   	      Morris.Bar({
								  	  	  	  element: 'bar-example',
								  	  	  	  data:data,
								  	  	  	  xkey: 'year',
								  	  	  	  ykeys: ['in', 'out'],
								  	  	  	  labels: ['Cotisations', 'Depenses']
								  	  	  	});
							           
							   }).error(function (data, status, headers, config) {
								   		 
							            $log.info("Call get getYearlySummary Failed");
							            $log.info($scope);
							   });	  	  	  	  	    

							        };

								/**
								 * Start File uploader
								 */

								var userProfileUploader = $scope.userProfileUploader = new FileUploader(
										{
											url : 'http://localhost:8080/ukadtogo/service/user/receiveFile'
										});

								// FILTERS

								userProfileUploader.filters.push({
									name : 'customFilter',
									fn : function(
											item /* {File|FileLikeObject} */,
											options) {
										return this.queue.length < 10;
									}
								});

								// CALLBACKS
								userProfileUploader.onBeforeUploadItem = function(
										item) {
									console.info('onBeforeUploadItem', item);
									item.formData.push({
										userId : $scope.theUser.id
									});
									$log.info(item);

								};

								/**
								 * Start log on function Get userName and
								 * Password and return pass or failed login If
								 * log in sucessful, put user in session cookie
								 * and use it on the UI.
								 */
								$scope.login = function() {
									// $log.info("login successful");
									// $log.info("UserName="+$scope.userName+",
									// password="+$scope.password);
									var User = {
										"userName" : $scope.userName,
										"password" : $scope.password
									};

									$http(
											{
												method : 'POST',
												url : 'http://localhost:8080/ukadtogo/service/user/login',
												data : User
											})
											.success(
													function(data, status,
															headers, config) {
														$log
																.info("Call Successful");
														if (data != null
																&& data != '') {
															$scope.failedLogin = false;

														} else {
															$scope.failedLogin = true;
														}

														$cookieStore
																.put('theUser',
																		data);
														$scope.theUser = data;
														$log.info($scope);

													})
											.error(
													function(data, status,
															headers, config) {
														$log
																.info("Call Failed");
														$cookieStore.put(
																'theUser', '');
														$scope.theUser = null;
														$scope.failedLogin = true;
														$log.info($scope);
													});

								};
								/**
								 * End Log in
								 */

								/**
								 * Start Logout
								 */
								$scope.logout = function() {
									$cookieStore.put('theUser', '');
									$scope.theUser = '';
									$log.info($scope);
								};
								/**
								 * End Log out
								 */

								/**
								 * Start create User
								 */
								$scope.createUser = function() {

									$http(
											{
												method : 'POST',
												url : 'http://localhost:8080/ukadtogo/service/user/createUser',
												data : $scope.newUser
											})
											.success(
													function(data, status,
															headers, config) {
														$log
																.info("Call Successful");
														if (data != null
																&& data != '') {
															$scope.failedLogin = false;

														} else {
															$scope.failedLogin = true;
														}

														$cookieStore
																.put('theUser',
																		data);
														$scope.theUser = data;
														$log.info($scope);

													})
											.error(
													function(data, status,
															headers, config) {
														$log
																.info("Call Failed");
														$cookieStore.put(
																'theUser', '');
														$scope.theUser = null;
														$scope.failedLogin = true;
														$log.info($scope);
													});

								};
								/**
								 * End Create users
								 */

								/**
								 * Save Profile
								 */
								$scope.saveUser = function() {
									$scope.saveUserSubmitted = true;
									$http(
											{
												method : 'POST',
												url : 'http://localhost:8080/ukadtogo/service/user/saveUser',
												data : $scope.theUser
											})
											.success(
													function(data, status,
															headers, config) {
														$log
																.info("Call Successful");
														if (data != null
																&& data != '') {
															$scope.failedLogin = false;

														} else {
															$scope.failedLogin = true;
														}

														$cookieStore
																.put('theUser',
																		data);
														$scope.theUser = data;
														$log.info($scope);
														$scope.theUserMessage = 'Sauvegarde avec succes';

													})
											.error(
													function(data, status,
															headers, config) {
														$log
																.info("Call Failed");
														$cookieStore.put(
																'theUser', '');
														$scope.theUser = null;
														$scope.failedLogin = true;
														$log.info($scope);
														$scope.theUserMessage = 'Sauvegarde Echouee. Reessayer plus tard';
													});

								};

								$scope.resetUserMessage = function() {
									$scope.saveUserSubmitted = false;
									$scope.theUserMessage = '';
								}
								/**
								 * End save user profile
								 */

								/**
								 * Start get All users Get the list of members
								 */
								$scope.getAllUsers = function() {

									$http(
											{
												method : 'POST',
												url : 'http://localhost:8080/ukadtogo/service/user/getAllMembers',
												data : null
											}).success(
											function(data, status, headers,
													config) {
												$log.info("Call Successful");
												$scope.users = data;
												$log.info($scope);
											}).error(
											function(data, status, headers,
													config) {
												$log.info("Call Failed");
												$log.info($scope);
											});

								};
								/**
								 * End get all users
								 */
								/**
								 * Begin Find a user
								 * 
								 */
								$scope.findMembers = function() {
									$http(
											{
												method : 'POST',
												url : 'http://localhost:8080/ukadtogo/service/user/findMembers',
												data : {
													searchText : $scope.searchText
												}
											})
											.success(
													function(data, status,
															headers, config) {
														$cookieStore.put('searchText',$scope.searchText);
														$log.info("Call find Members Successful");
														$scope.searchResult = data;
														$log.info($scope);
														// $cookieStore.put('searchResult',data);
														$location
																.path('pages.searchResults');
														$state
																.go('pages.searchResults');

													})
											.error(
													function(data, status,
															headers, config) {
														$log
																.info("Call Find Members Failed");
														$scope.searchResult = '';
														// $cookieStore.put('searchResult','');
													});

								};
								/**
								 * End Find a user
								 */

								/**
								 * Start approve Member
								 * 
								 */
								$scope.approveMember = function(aUser) {

									$http(
											{
												method : 'POST',
												url : 'http://localhost:8080/ukadtogo/service/user/approveMember',
												data : aUser
											}).success(
											function(data, status, headers,
													config) {

												$log.info("Call Successful");
												aUser.status = 1;
												$log.info($scope);
											}).error(
											function(data, status, headers,
													config) {
												$log.info("Call Failed");
												$log.info($scope);
											});

								};
								/**
								 * End get approve Member
								 */

								/**
								 * Start Reject Member
								 * 
								 */
								$scope.rejectMember = function(aUser) {

									$http(
											{
												method : 'POST',
												url : 'http://localhost:8080/ukadtogo/service/user/rejectMember',
												data : aUser
											}).success(
											function(data, status, headers,
													config) {

												$log.info("Call Successful");
												aUser.status = 2;
												$log.info($scope);
											}).error(
											function(data, status, headers,
													config) {
												$log.info("Call Failed");
												$log.info($scope);
											});

								};
								/**
								 * End get Pending users
								 */

								/**
								 * Start get Pending users Get the list of
								 * members
								 */
								$scope.getPendingMembers = function() {

									$http(
											{
												method : 'POST',
												url : 'http://localhost:8080/ukadtogo/service/user/getPendingMembers',
												data : $scope.mailContent
											}).success(
											function(data, status, headers,
													config) {

												$log.info("Call Successful");
												$scope.pendingUsers = data;
												$log.info($scope);
												// $cookieStore.put('pendingMembers',data);

											}).error(
											function(data, status, headers,
													config) {
												$log.info("Call Failed");
											});

								};
								/**
								 * End get Pending users
								 */
							      /**
							       * Start send Mail
							       *  
							       */
							            $scope.sendMail = function() { 
							            $scope.submitted=true;
							          $scope.theMessage='';
							             var email={body:this.emailBody, 
							      			  subject:this.emailSubject,
							      			  sender:$scope.theUser};
							             if(this.emailBody=='' || this.emailSubject==''){
							          	 $scope.theMessage='Le Sujet et le message sont obligatoires';
							          		 $scope.mailSent=false;
							             } else{

							                 $http({ method: 'POST', url: 'http://localhost:8080/ukadtogo/service/user/sendMail', data: email}).
							                 success(function (data, status, headers, config) {
							                          $log.info("Call Successful"); 
							                        $scope.email=null;
							                        if(data=='Success'){
							                      	 $scope.mailSent=true;
							                      	 $scope.emailBody='';
							                           $scope.emailSubject='';
							                           $scope.theMessage='Votre annonce a ete envoye avec success';
							                        }else{
							                      	 $scope.mailSent=false;
							                      	$scope.theMessage='Votre annonce ne peut etre envoyer en ce moment. Reessayer plus tard';
							                        }
							                       
							                     
							                        $log.info($scope);
							                 }).error(function (data, status, headers, config) {
							                          $log.info("Call Failed");
							                          $log.info($scope);
							                          $log.info($scope.email);
							                        $scope.mailSent=false;
							                      $scope.theMessage='Votre annonce ne peut etre envoyer en ce moment. Reessayer plus tard';
							                 });
							             }
							            };
							  	 /**
							  	  * End Send Mail
							  	  */
								
								/**
								 * Begin Show Modal
								 */
								$scope.showUserModal = function(user) {
									$scope.currUser = user;
									$('#myModalLabel').text(
											user.firstName + ' '
													+ user.lastName);
									$('#myModal').modal('show');
								}
								/**
								 * End Show Modal
								 */
								
								var url = $location.url();
								$log.info('URL='+url);
								
								if(url=='/pages/membres'){									
									$scope.getAllUsers();
								}else if(url=='/pages/searchResults'){
								 //		
									$scope.searchText= $cookieStore.get('searchText');
									if($scope.searchText!=null &&$scope.searchText!=''){
										$scope.findMembers();
									}									
								}else if(url=='/pages/cotiser'){
									$scope.getAllUsers();
									$scope.drawPayments();
								}else if(url=='/pages/approveMembers'){
									$scope.getPendingMembers();
								}

							} ])
})();