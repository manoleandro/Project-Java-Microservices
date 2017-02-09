(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('consulta-taxa', {
            parent: 'sager',
            url: '/consulta-taxa',
            data: {
                authorities: ['ROLE_USER','ROLE_CNOS','CORS_NE,Agente_NE'],
                pageTitle: 'onsPortalApp.consultaTaxa.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/sager/consulta-taxa/consulta-taxa.html',
                    controller: 'ConsultaTaxaController',
                    controllerAs: 'vm'
                }
            },
            reloadOnSearch: false,
            params: {
            	taxasSelecionadas: null,
            	usinasBack: null,
            	periodoBack:null,
            	idBusca:null
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                	$translatePartialLoader.addPart('consultaTaxa');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
