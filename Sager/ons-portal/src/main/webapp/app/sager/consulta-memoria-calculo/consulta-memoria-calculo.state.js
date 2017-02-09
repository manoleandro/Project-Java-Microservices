(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('consulta-memoria-calculo', {
            parent: 'sager',
            url: '/consulta-memoria-calculo',
            data: {
                authorities: ['ROLE_USER','ROLE_CNOS','CORS_NE,Agente_NE'],
                pageTitle: 'onsPortalApp.consultaTaxa.home.title'
            },
            params: {
            	dataRef: null,
            	taxa: null,
            	taxaSelecionada:null,
            	tipoInstalacao:null,
            	instalacao:null,
            	idInstalacao:null,
            	regulamentacao:null,
            	tipoTaxa:null,
            	versaoTaxa:null,
            	versaoCenario:null,
            	taxasSelecionadas:null,
            	usinasGo:null,
            	periodo:null,
            	idBusca:null
            },
            reloadOnSearch: true,
            views: {
                'content@': {
                    templateUrl: 'app/sager/consulta-memoria-calculo/consulta-memoria-calculo.html',
                    controller: 'ConsultaMemoriaCalculoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                	$translatePartialLoader.addPart('memoria-calculo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
