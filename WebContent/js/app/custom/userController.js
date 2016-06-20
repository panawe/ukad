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
									moment ) {
					            //function to open links in new tabs
					            $scope.openInNewTab = function(link){
					                    $window.open(link, '_blank');
					                };
					                
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
							         	

							         $scope.positions=[{id:1,name:'Membre'},
							                          {id:2,name:'President'}, 
							                          {id:3,name:'Secretaire General'},
							                          {id:4,name:'Secretaire General Adjoint'},
							                          {id:5,name:'Tresorier'},
							                          {id:6,name:'Tresorier Adjoint'},
							                          {id:7,name:"Charge de la communication"},
							                          {id:8,name:"Charge a l'organisation et de la discipline"},
							                          {id:9,name:'Commissaire aux comptes'} 
							                         ];
							         
							 		$scope.chunk = function(arr, size) {
										 var newArr = [];
										  for (var i=0; i<arr.length; i+=size) {
										    newArr.push(arr.slice(i, i+size));
										  }
										 return newArr;
									}

							          /**
							                 * Start create Payment
							                 */
							                $scope.makeUserPayment = function() {  
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
							                     $scope.drawPayments();
							                     $scope.drawContributions();
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
							                     $scope.drawPayments();
							                     $scope.drawContributions();
							                     if(data=='Success'){
							                    	 $scope.thePaymentMessage='Depense sauvegardee succes';
							                    	 
							                     }else{
							                    	 $scope.thePaymentMessage='Sauvegarde Depense a echoue';
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
							            $( "#bar-example" ).empty(); 
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
															$scope.loginMessage="Nom d'utilisateur/mot de passe invalide.";
														}

														$cookieStore
																.put('theUser',
																		data);
														$scope.theUser = data;
														$log.info($scope);
														if($scope.theUser.fee>0.0){
															$scope.theUser.status=0;
															$window.location.href="http://localhost:8080/ukadtogo/#/pages/fees";
														}

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
														$scope.loginMessage="La connection a echoue.";
														$log.info($scope);
													});

								};
								/**
								 * End Log in
								 */

								/**
								 * Send password
								 */
								$scope.sendPassword = function() {
									$scope.loginMessage='';
									var User = {
										"userName" : $scope.userName,
										"password" : $scope.password
									};

									$http(
											{
												method : 'POST',
												url : 'http://localhost:8080/ukadtogo/service/user/sendPassword',
												data : User
											})
											.success(
													function(data, status,
															headers, config) {
														$log
																.info("Call Successful"); 
														if(data=='Success'){
															
															$scope.loginMessage="Votre Mot de passe vous a ete envoye.";
														}else{
															$scope.loginMessage="Cet e-mail n'existe pas.";
														}
														
														$scope.failedLogin = true;
   

													})
											.error(
													function(data, status,
															headers, config) {
														$log
																.info("Call Failed");
														$scope.loginMessage="Erreur systeme.";
														$cookieStore.put(
																'theUser', '');
														$scope.theUser = null;
														$scope.failedLogin = true; 
													});

								};
								/**
								 * End send password
								 */
								
								/**
								 * Start Logout
								 */
								$scope.logout=function(){
							        $http({ method: 'POST', url: 'http://localhost:8080/ukadtogo/service/user/logout' }).
							        success(function (data, status, headers, config) {
							        	$log.info("Call Successful"); 
							            $cookieStore.put('theUser',data);
							            $scope.theUser=data;
							            $log.info($scope);
							                 
							        }).error(function (data, status, headers, config) {
							            $log.info("Call Failed");
							        });
							    	
							        $cookieStore.put('theUser','');
							        $scope.theUser='';
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
														if($scope.theUser.fee>0.0){
															$scope.theUser.status=0;
															$window.location.href="http://localhost:8080/ukadtogo/#/pages/fees";
														}

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
								$scope.saveUser = function(aUser) {
									$scope.saveUserSubmitted = true;
									$http(
											{
												method : 'POST',
												url : 'http://localhost:8080/ukadtogo/service/user/saveUser',
												data : aUser
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

								/**
								 * Save Profile
								 */
								$scope.saveAUser = function(aUser) {
									$scope.saveUserSubmitted = true;
									$http(
											{
												method : 'POST',
												url : 'http://localhost:8080/ukadtogo/service/user/saveUser',
												data : aUser
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

														$log.info($scope);
														$scope.theUserMessage = 'Sauvegarde avec succes';

													})
											.error(
													function(data, status,
															headers, config) {
														$log
																.info("Call Failed");
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
												$scope.userArrays = $scope.chunk(data, 6);
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
									$scope.searchResult =null;
									// $( "#usersearchList" ).refresh(); 
									
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
														
														$log.info("Call find Members Successful");
														 $scope.searchResultArrays = $scope.chunk(data, 6);
														$log.info($scope.searchResult);
														$location.url('/pages/searchResults');	
														if($cookieStore.get('searchText')!=$scope.searchText){
															$cookieStore.put('searchText',$scope.searchText);
															window.location.reload();
														}

														
											}).error(
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
												$log.info($scope.pendingUsers);
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
								
								/**
								 * Start get All users Get the list of members
								 */
								$scope.getLeaders = function() {

									$http(
											{
												method : 'POST',
												url : 'http://localhost:8080/ukadtogo/service/user/getLeaders',
												data : null
											}).success(
											function(data, status, headers,
													config) {
												$log.info("Call getLeaders Successful");
												$scope.leaders = data;
												$log.info($scope);
											}).error(
											function(data, status, headers,
													config) {
												$log.info("Call getLeaders Failed");
												$log.info($scope);
											});

								};
								/**
								 * End get all users
								 */
								/**
								 * Start get All expenses
								 */
								$scope.getAllExpenses = function() {

									$http(
											{
												method : 'POST',
												url : 'http://localhost:8080/ukadtogo/service/user/getAllExpenses',
												data : null
											}).success(
											function(data, status, headers,
													config) {
												$log.info("Call expenses Successful");
												$scope.expenses = data;
												$log.info($scope.expenses);
											}).error(
											function(data, status, headers,
													config) {
												$log.info("Call expenses Failed");
												$log.info($scope);
											});

								};
								/**
								 * End get all expenses
								 */		
								
								/**
								 * Start Delete Expense
								 * 
								 */
								$scope.deleteExpense = function(exp) {
									$http(
											{
												method : 'POST',
												url : 'http://localhost:8080/ukadtogo/service/user/deleteExpense',
												data : exp
											})
											.success(
													function(data, status,
															headers, config) {
														$scope.getAllExpenses();
														$scope.drawPayments();
														$log
																.info("Call deleteEvent Successful");
														
														$log.info($scope);
													})
											.error(
													function(data, status,
															headers, config) {
														$log
																.info("Call deleteEvent Failed");
														$log.info($scope);

													});

								};
								/**
								 * End Delete
								 */

						        /**
					             * End clear Payment
					             */
					        $scope.drawContributions = function() {
					        	
					        	$http({ method: 'POST', url: 'http://localhost:8080/ukadtogo/service/user/getContributions', data: null }).
					   success(function (data, status, headers, config) {
					            $log.info("Call get getContributions"); 
					            $( "#contribution-bar" ).empty(); 
					   	      Morris.Bar({
						  	  	  	  element: 'contribution-bar',
						  	  	  	  data:data,
						  	  	  	  xkey: 'member',
						  	  	  	  ykeys: ['amount'],
						  	  	  	  labels:['Contribution']
						  	  	  	});
					           
					   }).error(function (data, status, headers, config) {
						   		 
					            $log.info("Call get getYearlySummary Failed");
					            $log.info($scope);
					   });	  	  	  	  	    

					        };
					        

							$scope.getContributions = function() {

								$http(
										{
											method : 'POST',
											url : 'http://localhost:8080/ukadtogo/service/user/getContributions',
											data : null
										})
										.success(
												function(data, status,
														headers, config) {
													$log.info("Call get getContributions");
													$scope.contributions = data;

												})
										.error(
												function(data, status,
														headers, config) {

													$log.info("Call get getContributions Failed");
													$log.info($scope);
												});

							};


							/**
							 * Start get Events Get the list of Events
							 */
							$scope.submitDonation = function() {

								$http(
										{
											method : 'POST',
											url : 'http://localhost:8080/ukadtogo/service/payment/createPayment',
											data : {
											    "intent": "sale",
											    "payer": {
											        "payment_method": "paypal"
											    },
											    "transactions": [
											        {
											            "amount": {
											                "currency": "USD",
											                "total": $scope.amount
											            },
											            "description": "Don pour l'Association des Gabonais de Washington, D.C et ses Environs (A.G.W.E)."
											        }
											    ],
											    "redirectUrls": {
											        return_url: "http://localhost:8080/ukadtogo/#/pages/donate",
											        cancel_url: "http://localhost:8080/ukadtogo/#/pages/cancelDonate"
											    }
											}
										})
										.success(
												function(data, status,
														headers, config) {
													$log.info("Call get Create Payment Successful");													 
													$log.info(data); 
													$window.location.href=data;

												})
										.error(
												function(data, status,
														headers, config) {
													$log.info("Call Create payment Failed");
													$log.info($scope); 
													$window.location.href="http://localhost:8080/ukadtogo/#/pages/cancelDonate";

												});

							};

							
							/**
							 * Start Submit Fee
							 */
							$scope.submitFee = function() {

								$http(
										{
											method : 'POST',
											url : 'http://localhost:8080/ukadtogo/service/payment/submitFee',
											data : {
											    "intent": "sale",
											    "payer": {
											        "payment_method": "paypal"
											    },
											    "transactions": [
											        {
											            "amount": {
											                "currency": "USD",
											                "total": $scope.theUser.fee
											            },
											            "description": "Frais Annuel de membre pour l'Association des Gabonais de Washington, D.C et ses Environs (A.G.W.E)."
											        }
											    ],
											    "redirectUrls": {
											        return_url: "http://localhost:8080/ukadtogo/#/pages/fees",
											        cancel_url: "http://localhost:8080/ukadtogo/#/pages/cancelDonate"
											    }
											}
										})
										.success(
												function(data, status,
														headers, config) {
													$log.info("Call get Create Payment Successful");													 
													$log.info(data); 
													$window.location.href=data;

												})
										.error(
												function(data, status,
														headers, config) {
													$log.info("Call Create payment Failed");
													$log.info($scope); 
													$window.location.href="http://localhost:8080/ukadtogo/#/pages/cancelDonate";

												});

							};

							/**
							 * Start get Events Get the list of Events
							 */
							$scope.makePayment = function() {

								$http(
										{
											method : 'POST',
											url : 'http://localhost:8080/ukadtogo/service/payment/makePayment',
											data : {													
												paymentId:$scope.paymentId,
												token:$scope.token,
												PayerID:$scope.PayerID,
												userId:typeof $scope.theUser=='undefined'?1:$scope.theUser.id 
											}
										})
										.success(
												function(data, status,
														headers, config) {
													$log.info("Call get Make Payment Successful");	
													$scope.pay=data;
													$log.info(data); 
													//$window.location.href=data;
												})
										.error(
												function(data, status,
														headers, config) {
													$log.info("Call Make payment Failed");
													$log.info($scope); 
													$window.location.href="http://localhost:8080/ukadtogo/#/pages/cancelDonate";
												});
							};

							/**
							 * Start get Events Get the list of Events
							 */
							$scope.payFee = function() {

								$http(
										{
											method : 'POST',
											url : 'http://localhost:8080/ukadtogo/service/payment/payFee',
											data : {													
												paymentId:$scope.paymentId,
												token:$scope.token,
												PayerID:$scope.PayerID,
												userId:typeof $scope.theUser=='undefined'?1:$scope.theUser.id 
											}
										})
										.success(
												function(data, status,
														headers, config) {
													$log.info("Call get Make Payment Successful");	
													$scope.pay=data;
													$log.info(data); 
													//$window.location.href=data;
												})
										.error(
												function(data, status,
														headers, config) {
													$log.info("Call Make payment Failed");
													$log.info($scope); 
													$window.location.href="http://localhost:8080/ukadtogo/#/pages/cancelDonate";
												});
							};
							
							$scope.getContributions = function() {

								$http(
										{
											method : 'POST',
											url : 'http://localhost:8080/ukadtogo/service/user/getContributions',
											data : null
										})
										.success(
												function(data, status,
														headers, config) {
													$log.info("Call get getContributions");
													$scope.contributions = data;

												})
										.error(
												function(data, status,
														headers, config) {

													$log.info("Call get getContributions Failed");
													$log.info($scope);
												});

							};
							
							//simple seach
							$scope.userSearch = function() {
								$scope.searchResult =null;
								// $( "#usersearchList" ).refresh(); 
								
								$http(
										{
											method : 'POST',
											url : 'http://localhost:8080/ukadtogo/service/user/findMembers',
											data : {
												searchText : $scope.searchCrit
											}
										})
										.success(
												function(data, status,
														headers, config) {
													
													$log.info("Call find Members Successful");
													 $scope.searchResults = data; 

													
										}).error(
												function(data, status,
														headers, config) {
													$log
															.info("Call Find Members Failed");
													$scope.searchResult = ''; 
												});

							};
							
							
					        $scope.drawDonations = function() {
					        	
					        	$http({ method: 'POST', url: 'http://localhost:8080/ukadtogo/service/user/getDonations', data: null }).
					   success(function (data, status, headers, config) {
					            $log.info("Call get getDonation"); 
					            $( "#donation-bar" ).empty(); 
					   	      Morris.Bar({
						  	  	  	  element: 'donation-bar',
						  	  	  	  data:data,
						  	  	  	  xkey: 'description',
						  	  	  	  ykeys: ['donation', 'goal'],
						  	  	  	  labels: ['Actuel', 'Goal']
						  	  	  	});
					           
					   }).error(function (data, status, headers, config) {						   		 
					            $log.info("Call get getDonation");
					            $log.info($scope);
					   });	  	  	  	  	    

					        };

							
							
					        $scope.drawDonations();
							//$scope.getContributions();
							//Fix for refresh
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
									$scope.drawContributions();
									$scope.getAllExpenses();
								}else if(url=='/pages/approveMembers'){
									$scope.getPendingMembers();
								}else if(url=='/pages/main'){									
									$scope.getLeaders();
								}else if(url.startsWith('/pages/donate')){									
									$scope.paymentId = $location.search().paymentId;
									$scope.token = $location.search().token; 
									$scope.PayerID = $location.search().PayerID; 
									
									$log.info('PayerID='+$scope.PayerID);
									$log.info('paymentId='+$scope.paymentId);
									$log.info('token='+$scope.token);
									$scope.makePayment();
								}else if(url.startsWith('/pages/fees')){
									$scope.paymentId = $location.search().paymentId;
									$scope.token = $location.search().token; 
									$scope.PayerID = $location.search().PayerID; 
									$log.info('PayerID='+$scope.PayerID);
									$log.info('paymentId='+$scope.paymentId);
									$log.info('token='+$scope.token);
									$log.info('theUser='+$scope.theUser);
									if(typeof $scope.paymentId!='undefined'){
										$scope.payFee();
									}
									
								}

							} ])
})();