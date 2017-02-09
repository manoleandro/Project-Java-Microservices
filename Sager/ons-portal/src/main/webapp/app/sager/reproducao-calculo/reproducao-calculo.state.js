(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('reproducao-calculo', {
            parent: 'sager',
            url: '/reproducao-calculo',
            data: {
                authorities: ['ROLE_USER','ROLE_CNOS','CORS_NE,Agente_NE']
            },
            views: {
                'content@': {
                    templateUrl: 'app/sager/reproducao-calculo/reproducao-calculo.html',
                    controller: 'ReproducaoCalculoController',
                    controllerAs: 'vm'
                }
            },
            reloadOnSearch: false,
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
            	idBusca:null,
            	instalacaoEquip:null
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
