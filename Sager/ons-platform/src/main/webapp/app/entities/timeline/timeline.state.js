(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('timeline', {
            parent: 'entity',
            url: '/timeline',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'onsPortalApp.timeline.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'onsplatform/app/entities/timeline/timelines.html',
                    controller: 'TimelineController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('timeline-detail', {
            parent: 'entity',
            url: '/timeline/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'onsPortalApp.timeline.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'onsplatform/app/entities/timeline/timeline-detail.html',
                    controller: 'TimelineDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Timeline', function($stateParams, Timeline) {
                    return Timeline.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('timeline.new', {
            parent: 'timeline',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'onsplatform/app/entities/timeline/timeline-dialog.html',
                    controller: 'TimelineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                aggregateId: null,
                                version: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('timeline', null, { reload: true });
                }, function() {
                    $state.go('timeline');
                });
            }]
        })
        .state('timeline.edit', {
            parent: 'timeline',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'onsplatform/app/entities/timeline/timeline-dialog.html',
                    controller: 'TimelineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Timeline', function(Timeline) {
                            return Timeline.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('timeline', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('timeline.delete', {
            parent: 'timeline',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'onsplatform/app/entities/timeline/timeline-delete-dialog.html',
                    controller: 'TimelineDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Timeline', function(Timeline) {
                            return Timeline.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('timeline', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
