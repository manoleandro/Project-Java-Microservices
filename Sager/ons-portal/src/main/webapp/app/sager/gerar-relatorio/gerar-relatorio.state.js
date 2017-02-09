(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('gerar-relatorio', {
            parent: 'sager',
            url: '/gerar-relatorio',
            data: {
                authorities: ['ROLE_USER','CNOS','ROLE_CNOS'],
                pageTitle: 'onsPortalApp.gerarRelatorio.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/sager/gerar-relatorio/gerar-relatorio.html',
                    controller: 'GerarRelatorioController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            	translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('gerarRelatorio');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('gerar-relatorio-detail',{
        	parent: 'sager',
        	url: '/gerar-relatorio/{id}',
        	data: {
                authorities: ['ROLE_USER','CNOS','ROLE_CNOS'],
                pageTitle: 'onsPortalApp.gerarRelatorio.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/sager/gerar-relatorio/gerar-relatorio-detail.html',
                    controller: 'GerarRelatorioDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('gerarRelatorio');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'GerarRelatorio', function($stateParams, CadastroUsina) {
                    return GerarRelatorio.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'gerar-relatorio',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('gerar-relatorio-detail.edit', {
            parent: 'gerar-relatorio-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER','CNOS','ROLE_CNOS']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/sager/gerar-relatorio/gerar-relatorio-dialog.html',
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
        .state('gerar-relatorio.new', {
            parent: 'gerar-relatorio',
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
                    templateUrl: 'app/sager/gerar-relatorio/gerar-relatorio-dialog.html',
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
                    $state.go('gerar-relatorio', null, { reload: true });
                }, function() {
                    $state.go('gerar-relatorio');
                });
            }]
        })
        .state('gerar-relatorio.edit', {
            parent: 'gerar-relatorio',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER','CNOS','ROLE_CNOS']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/sager/gerar-relatorio/gerar-relatorio-dialog.html',
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
                    $state.go('gerar-relatorio', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gerar-relatorio.delete', {
            parent: 'gerar-relatorio',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER','CNOS','ROLE_CNOS']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/sager/gerar-relatorio/gerar-relatorio-delete-dialog.html',
                    controller: 'CalculoTaxaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CalculoTaxa', function(CalculoTaxa) {
                            return CalculoTaxa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gerar-relatorio', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }
})();
