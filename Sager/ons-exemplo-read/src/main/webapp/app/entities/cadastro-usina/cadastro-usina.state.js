(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cadastro-usina', {
            parent: 'entity',
            url: '/cadastro-usina',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'onsPortalApp.cadastroUsina.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'onsexemploread/app/entities/cadastro-usina/cadastro-usinas.html',
                    controller: 'CadastroUsinaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cadastroUsina');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cadastro-usina-detail', {
            parent: 'entity',
            url: '/cadastro-usina/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'onsPortalApp.cadastroUsina.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'onsexemploread/app/entities/cadastro-usina/cadastro-usina-detail.html',
                    controller: 'CadastroUsinaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cadastroUsina');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CadastroUsina', function($stateParams, CadastroUsina) {
                    return CadastroUsina.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cadastro-usina',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cadastro-usina-detail.edit', {
            parent: 'cadastro-usina-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'onsexemploread/app/entities/cadastro-usina/cadastro-usina-dialog.html',
                    controller: 'CadastroUsinaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CadastroUsina', function(CadastroUsina) {
                            return CadastroUsina.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cadastro-usina.new', {
            parent: 'cadastro-usina',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'onsexemploread/app/entities/cadastro-usina/cadastro-usina-dialog.html',
                    controller: 'CadastroUsinaDialogController',
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
                    $state.go('cadastro-usina', null, { reload: true });
                }, function() {
                    $state.go('cadastro-usina');
                });
            }]
        })
        .state('cadastro-usina.edit', {
            parent: 'cadastro-usina',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'onsexemploread/app/entities/cadastro-usina/cadastro-usina-dialog.html',
                    controller: 'CadastroUsinaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CadastroUsina', function(CadastroUsina) {
                            return CadastroUsina.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cadastro-usina', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cadastro-usina.delete', {
            parent: 'cadastro-usina',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'onsexemploread/app/entities/cadastro-usina/cadastro-usina-delete-dialog.html',
                    controller: 'CadastroUsinaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CadastroUsina', function(CadastroUsina) {
                            return CadastroUsina.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cadastro-usina', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
