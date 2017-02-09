(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('manter-param', {
            parent: 'sager',
            url: '/manter-param?page&sort&search',
            data: {
                authorities: ['ROLE_USER', 'CNOS', 'ROLE_CNOS']
//                pageTitle: 'onsPortalApp.consultaTaxa.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/sager/manter-param/manter-param.html',
                    controller: 'ManterParamController',
                    controllerAs: 'vm'
                }
            },
            params: {
            	page: {
            		value: '1',
            		squash: true
            	},
            	sort: {
                    value: 'dia,asc',
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
                    $translatePartialLoader.addPart('manterParam');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
