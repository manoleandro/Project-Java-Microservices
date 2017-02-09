(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('calculo-taxa', {
            parent: 'sager',
            url: '/calculo-taxa',
            data: {
                authorities: ['ROLE_USER','CNOS','ROLE_CNOS'],
                pageTitle: 'onsPortalApp.calculoTaxa.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/sager/calculo-taxa/calculo-taxa.html',
                    controller: 'CalculoTaxaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            	translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('calculoTaxa');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('calculo-taxa-detail.edit', {
            parent: 'calculo-taxa-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER','CNOS','ROLE_CNOS']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/sager/calculo-taxa/calculo-taxa-dialog.html',
                    controller: 'CalculoTaxaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CalculoTaxa', function(CalculoTaxa) {
                            return CalculoTaxa.get({id : $stateParams.id}).$promise;
                        }],
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart('calculoTaxa');
                            $translatePartialLoader.addPart('global');
                            return $translate.refresh();
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('calculo-taxa.new', {
            parent: 'calculo-taxa',
            url: '/new',
            params: { 
            	usinasSelecionadas: ['1','2'],
            	mesInicial: '112012',
            	mesFinal: '122012'
            },            
            data: {
                authorities: ['ROLE_USER','CNOS','ROLE_CNOS']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/sager/calculo-taxa/calculo-taxa-dialog.html',
                    controller: 'CalculoTaxaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                nomeCurto: null,
                                tipoUsina: null,
                                potenciaCalculo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('calculo-taxa', null, { reload: true });
                }, function() {
                    $state.go('calculo-taxa');
                });
            }]
        })
        .state('calculo-taxa.edit', {
            parent: 'calculo-taxa',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER','CNOS','ROLE_CNOS']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/sager/calculo-taxa/calculo-taxa-dialog.html',
                    controller: 'CalculoTaxaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CalculoTaxa', function(CalculoTaxa) {
                            return CalculoTaxa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('calculo-taxa', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('calculo-taxa.delete', {
            parent: 'calculo-taxa',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER','CNOS','ROLE_CNOS']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/sager/calculo-taxa/calculo-taxa-delete-dialog.html',
                    controller: 'CalculoTaxaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CalculoTaxa', function(CalculoTaxa) {
                            return CalculoTaxa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('calculo-taxa', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }
})();
