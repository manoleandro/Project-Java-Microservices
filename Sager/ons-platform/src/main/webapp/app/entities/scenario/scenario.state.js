(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('scenario', {
            parent: 'entity',
            url: '/scenario',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Scenarios'
            },
            views: {
                'content@': {
                    templateUrl: 'onsplatform/app/entities/scenario/scenarios.html',
                    controller: 'ScenarioController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('scenario-detail', {
            parent: 'entity',
            url: '/scenario/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Scenario'
            },
            views: {
                'content@': {
                    templateUrl: 'onsplatform/app/entities/scenario/scenario-detail.html',
                    controller: 'ScenarioDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Scenario', function($stateParams, Scenario) {
                    return Scenario.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'scenario',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('scenario-detail.edit', {
            parent: 'scenario-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'onsplatform/app/entities/scenario/scenario-dialog.html',
                    controller: 'ScenarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Scenario', function(Scenario) {
                            return Scenario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('scenario.new', {
            parent: 'scenario',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'onsplatform/app/entities/scenario/scenario-dialog.html',
                    controller: 'ScenarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                aggregateId: null,
                                aggregateName: null,
                                timelineId: null,
                                description: null,
                                type: null,
                                status: null,
                                startDate: null,
                                endDate: null,
                                creationDate: null,
                                userId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('scenario', null, { reload: true });
                }, function() {
                    $state.go('scenario');
                });
            }]
        })
        .state('scenario.edit', {
            parent: 'scenario',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'onsplatform/app/entities/scenario/scenario-dialog.html',
                    controller: 'ScenarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Scenario', function(Scenario) {
                            return Scenario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('scenario', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('scenario.end', {
        	parent: 'scenario',
        	url: '/{id}/end?{makeDefault}',
        	data: {
        		authorities: ['ROLE_USER']
        	},
        	onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        		$uibModal.open({
        			templateUrl: 'onsplatform/app/entities/scenario/scenario-end-dialog.html',
        			controller: 'ScenarioEndController',
        			controllerAs: 'vm',
        			backdrop: 'static',
        			size: 'lg',
        			resolve: {
        				entity: ['Scenario', function(Scenario) {
        					return Scenario.get({id : $stateParams.id}).$promise;
        				}],
        				items: function() {
        					return {
        						makeDefault: $stateParams.makeDefault
    						};
    					}
        			}
        		}).result.then(function() {
        			$state.go('scenario', null, { reload: true });
        		}, function() {
        			$state.go('^');
        		});
        	}]
        })
        .state('scenario.delete', {
            parent: 'scenario',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'onsplatform/app/entities/scenario/scenario-delete-dialog.html',
                    controller: 'ScenarioDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Scenario', function(Scenario) {
                            return Scenario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('scenario', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
