(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cadastro-evento', {
            parent: 'entity',
            url: '/cadastro-evento',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'onsPortalApp.cadastroEvento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'onsexemploread/app/entities/cadastro-evento/cadastro-eventos.html',
                    controller: 'CadastroEventoController',
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
        });
    }

})();
