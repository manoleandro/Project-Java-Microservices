(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('disp', {
            parent: 'sager',
            url: '/disp?page&sort&search',
            data: {
                authorities: ['ROLE_USER', 'ROLE_CNOS', 'ROLE_COSR_SE'],
                pageTitle: 'sagerApp.disp.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/sager/disp/disp.html',
                    controller: 'DispController',
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
                    $translatePartialLoader.addPart('disp');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('disp-chart', {
        	parent: 'disp',
        	url: '/disp-chart',
        	data: {
                authorities: ['ROLE_USER', 'ROLE_CNOS', 'ROLE_COSR_SE'],
            },
            params: {
            	disp_datas: null
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/sager/disp/disp-chart.html',
                    controller: 'DispChartController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                            	disp_datas: $stateParams.disp_datas,
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('disp', null, { reload: true });
                }, function() {
                    $state.go('disp');
                });
            }]
        })
        .state('disp-comentarios', {
        	parent: 'disp',
        	url: '/disp-comentarios',
        	data: {
                authorities: ['ROLE_USER', 'ROLE_CNOS', 'ROLE_COSR_SE'],
            },
            params: {
            	comentariosAviso: null,
            	comentariosErro: null,
            	comentarios: null
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/sager/disp/disp-comentarios.html',
                    controller: 'DispComentariosController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                            	comentariosAviso: $stateParams.comentariosAviso,
                            	comentariosErro: $stateParams.comentariosErro,
                            	comentarios: $stateParams.comentarios
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('disp', null, { reload: true });
                }, function() {
                    $state.go('disp');
                });
            }]
        });
    }

})();
