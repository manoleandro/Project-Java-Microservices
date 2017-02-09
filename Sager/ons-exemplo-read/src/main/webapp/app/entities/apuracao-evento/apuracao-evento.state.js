(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('apuracao-evento', {
            parent: 'entity',
            url: '/apuracao-evento',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'onsPortalApp.cadastroEvento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'onsexemploread/app/entities/apuracao-evento/apuracao-eventos.html',
                    controller: 'ApuracaoEventoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cadastroEvento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('apuracao-evento.apuracao', {
            parent: 'apuracao-evento',
            url: '/apuracao-evento/{id}',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'onsexemploread/app/entities/apuracao-evento/apuracao-evento-apuracao.html',
                    controller: 'ApuracaoEventoApuracaoController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CadastroUsina', function(CadastroUsina) {
                            return CadastroUsina.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('apuracao-evento', null, { reload: true });
                }, function() {
                    $state.go('apuracao-evento');
                });
            }]
        });
    }
})();
