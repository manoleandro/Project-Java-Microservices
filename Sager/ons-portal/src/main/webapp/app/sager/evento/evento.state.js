(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('evento', {
            parent: 'entity',
            url: '/evento?page&sort&search',
            data: {
                authorities: ['ROLE_USER','CNOS','ROLE_CNOS'],
                pageTitle: 'sagerApp.evento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/sager/evento/eventos.html',
                    controller: 'EventoController',
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
                    $translatePartialLoader.addPart('evento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        }).state('eventoFromConsultarMemoriaCalculo', {
            parent: 'entity',
            url: '/evento/:idEvento',
            data: {
                authorities: ['ROLE_USER','CNOS','ROLE_CNOS'],
                pageTitle: 'sagerApp.evento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/sager/evento/eventos.html',
                    controller: 'EventoController',
                    controllerAs: 'vm'
                }
            },
            params: {
            	idEvento: null,
            	instalacao:null,
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
                
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('evento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        });
    }

})();
