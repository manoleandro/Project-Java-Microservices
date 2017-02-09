(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('record', {
            parent: 'entity',
            url: '/record',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'onsPortalApp.record.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'onsplatform/app/entities/record/records.html',
                    controller: 'RecordController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('record-detail', {
            parent: 'entity',
            url: '/record/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'onsPortalApp.record.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'onsplatform/app/entities/record/record-detail.html',
                    controller: 'RecordDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Record', function($stateParams, Record) {
                    return Record.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('record.new', {
            parent: 'record',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'onsplatform/app/entities/record/record-dialog.html',
                    controller: 'RecordDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                correlationId: null,
                                type: null,
                                name: null,
                                handlerName: null,
                                handlerVersion: null,
                                timelineDate: null,
                                creationDate: null,
                                content: null,
                                contentContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('record', null, { reload: true });
                }, function() {
                    $state.go('record');
                });
            }]
        })
        .state('record.edit', {
            parent: 'record',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'onsplatform/app/entities/record/record-dialog.html',
                    controller: 'RecordDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Record', function(Record) {
                            return Record.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('record', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('record.delete', {
            parent: 'record',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'onsplatform/app/entities/record/record-delete-dialog.html',
                    controller: 'RecordDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Record', function(Record) {
                            return Record.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('record', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
