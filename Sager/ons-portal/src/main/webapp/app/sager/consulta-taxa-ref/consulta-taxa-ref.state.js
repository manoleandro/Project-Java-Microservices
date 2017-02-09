(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('consulta-taxa-ref', {
            parent: 'sager',
            url: '/consulta-taxa-ref?page&sort&search',
            data: {
                authorities: ['ROLE_ADMIN','CNOS','COSR-NE','COSR-S','COSR-SE','ROLE_CNOS'],
                pageTitle: 'onsPortalApp.calculoTaxa.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/sager/consulta-taxa-ref/consulta-taxa-ref.html',
                    controller: 'ConsultaTaxaRefController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('taxaRef');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })      
    }
    
})();
