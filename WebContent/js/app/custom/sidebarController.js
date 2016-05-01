(function() {
	'use strict';

	angular
			.module('app')
			.controller(
					'sideBarCtrl',
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
								 * Start get announces
								 */
								$scope.getActiveAnnounces = function() {

									$http(
											{
												method : 'POST',
												url : 'http://localhost:8080/ukadtogo/service/announce/getActiveAnnounces',
												data : null
											})
											.success(
													function(data, status,
															headers, config) {
														$log
																.info("Call get Active Announces Successful");
														$scope.activeAnnounces = data;
														$log.info($scope);
													})
											.error(
													function(data, status,
															headers, config) {
														$log
																.info("Call get Active Announces Failed");
														$log.info($scope);
													});

								};

								
								
								/**
								 * Start get Events Get the list of Events
								 */
								$scope.getFutureEvents = function() {

									$http(
											{
												method : 'POST',
												url : 'http://localhost:8080/ukadtogo/service/event/getFutureEvents',
												data : null
											})
											.success(
													function(data, status,
															headers, config) {
														$log
																.info("Call get Future Events Successful");
														$scope.futureEvents = data;
														$log.info($scope);
														$log.info($scope.events);
														// $cookieStore.put('events',data);

													})
											.error(
													function(data, status,
															headers, config) {
														$log
																.info("Call get Future Event Failed");
														$log.info($scope);
														// $cookieStore.put('events',null);
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
								 * Begin Show Modal
								 */
								$scope.showUserModal = function(event) {
									$scope.currEvent = event;
									$('#myModalLabel').text(event.title);
									$('#myModal').modal('show');
								}
								/**
								 * End get all Events
								 * /
						
								/**
								 * Begin Show Modal
								 */
								$scope.showAnnounceModal = function(announce) {
									$scope.currAnnounce = announce;
									$('#myAnnounceModalLabel').text(announce.title);
									$('#myAnnounceModal').modal('show');
								}
								/**
								 * End get all Events
								 */

								
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
																
								$scope.getFutureEvents();
								$scope.getContributions();
								$scope.getActiveAnnounces();
								
								//Fix for refresh
								var url = $location.url();
								$log.info('URL='+url);
								
								if(url.startsWith('/pages/donate')){									
									$scope.paymentId = $location.search().paymentId;
									$scope.token = $location.search().token; 
									$scope.PayerID = $location.search().PayerID; 
									
									$log.info('PayerID='+$scope.PayerID);
									$log.info('paymentId='+$scope.paymentId);
									$log.info('token='+$scope.token);
									$scope.makePayment();
								}

							} ])
})();