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
								 * Begin Show Modal
								 */
								$scope.showUserModal = function(event) {
									$scope.currEvent = event;
									$('#myModalLabel').text(event.title);
									$('#myModal').modal('show');
								}
								/**
								 * End show Modal
								 * 
								 */

								$scope.getFutureEvents();
								$scope.getContributions();
								


							} ])
})();