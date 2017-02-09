(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('manter-agendamento', {
            parent: 'sager',
            url: '/schedule',
            data: {
                authorities: ['ROLE_USER', 'ROLE_CNOS', 'ROLE_COSR_S', 'ROLE_COSR_NE'],
                pageTitle: 'Schedules'
            },
            views: {
                'content@': {
                    templateUrl: 'app/sager/manter-agendamento/schedules.html',
                    controller: 'ScheduleController',
                    controllerAs: 'vm'
                }
            },
            params: {
            	idBusca:null,
            	page: {
            		value: '1',
            		squash: true
            	},
            	sort: {
                    value: 'nome,asc',
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
                    $translatePartialLoader.addPart('manter-agendamento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
       .state('schedule-detail', {
            parent: 'calculo-taxa-schedule',
            url: '/edit',
            params: {
            	instalacoesReq: [1]
            },
            data: {
                authorities: ['ROLE_USER', 'ROLE_CNOS', 'ROLE_COSR_SE'],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/sager/manter-agendamento/schedule-detail.html',
                    controller: 'ScheduleDetailController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                    	entity: function () {
                            return {
                            	protocolo: null,
                            	idInstalacao: null,
                            	dataAgendamento: null,
                            	mesInicial: null,
                            	mesFinal: null,
                            	solicitante: null,
                            	situacao: null,
                            	instalacoesReq: null
                            };
                        },
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        }) // END DETAIL CALCULO TAXA
        .state('calculo-taxa-schedule', {
        	parent: 'manter-agendamento',
        	url: '/calculo-taxa-schedule?page&sort&search',
        	params: {
            	dates: "",
            	instalacoesSelecionadas: [],
            	data: {
            		authorities: ['ROLE_USER', 'ROLE_CNOS', 'ROLE_COSR_S', 'ROLE_COSR_NE'],
            	},
            	page: {
            		value: '1',
            		squash: true
            	},
            	sort: {
                    value: 'nome,asc',
                    squash: true
                },
            	search: null
            },
        	views: {
        		'calculo-taxa-schedule': {
        			templateUrl: 'app/sager/manter-agendamento/calculo-taxa-schedule.html',
        			controller: 'CalcTaxaScheduleController',
                    controllerAs: 'vm',
                    resolve: {
                    	entity: function () {
                            return {
                            	dates: null,
                            	instalacoesReq: null
                            };
                        },
                    }
        		}
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
            		$translatePartialLoader.addPart('manter-agendamento');
            		$translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        }) // END CALCULO TAXA
        .state('retificacao-schedule', { // RETIFICACAO
        	parent: 'manter-agendamento',
        	url: '/retificacao-schedule?page&sort&search',
        	params: {
            	dates: "",
            	instalacoesReq: [1],
            	data: {
            		authorities: ['ROLE_USER', 'ROLE_CNOS', 'ROLE_COSR_S', 'ROLE_COSR_NE'],
            	},
            	page: {
            		value: '1',
            		squash: true
            	},
            	sort: {
                    value: 'nome,asc',
                    squash: true
                },
            	search: null
            },
        	views: {
        		'calculo-taxa-schedule': {
        			templateUrl: 'app/sager/manter-agendamento/retificacao-schedule.html',
        			controller: 'RetificacaoScheduleController',
                    controllerAs: 'vm',
                    resolve: {
                    	entity: function () {
                            return {
                            	dates: null,
                            	instalacoesReq: null
                            };
                        },
                    }
        		}
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
            		$translatePartialLoader.addPart('manter-agendamento');
            		$translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        }) // END RETIFICACAO
        .state('retificacao-detail', {
            parent: 'retificacao-schedule',
            url: '/edit',
            params: {
            	instalacoesReq: [1]
            },
            data: {
                authorities: ['ROLE_USER', 'ROLE_CNOS', 'ROLE_COSR_SE'],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/sager/manter-agendamento/retificacao-detail.html',
                    controller: 'RetificacaoDetailController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                    	entity: function () {
                            return {
                            	protocolo: null,
                            	idInstalacao: null,
                            	dataAgendamento: null,
                            	mesInicial: null,
                            	mesFinal: null,
                            	solicitante: null,
                            	situacao: null,
                            	instalacoesReq: null
                            };
                        },
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cenario-schedule', {
        	parent: 'manter-agendamento',
        	url: '/cenario-schedule?page&sort&search',
        	params: {
            	dates: "",
            	instalacoesReq: [1],
            	data: {
            		authorities: ['ROLE_USER', 'ROLE_CNOS', 'ROLE_COSR_S', 'ROLE_COSR_NE'],
            	},
            	page: {
            		value: '1',
            		squash: true
            	},
            	sort: {
                    value: 'nome,asc',
                    squash: true
                },
            	search: null
            },
        	views: {
        		'calculo-taxa-schedule': {
        			templateUrl: 'app/sager/manter-agendamento/cenario-schedule.html',
        			controller: 'CenarioScheduleController',
                    controllerAs: 'vm',
                    resolve: {
                    	entity: function () {
                            return {
                            	dates: null,
                            	instalacoesReq: null
                            };
                        },
                    }
        		}
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
            		$translatePartialLoader.addPart('manter-agendamento');
            		$translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })// END CENARIO
        .state('cenario-detail', {
            parent: 'cenario-schedule',
            url: '/edit',
            params: {
            	instalacoesReq: [1]
            },
            data: {
                authorities: ['ROLE_USER', 'ROLE_CNOS', 'ROLE_COSR_SE'],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/sager/manter-agendamento/cenario-detail.html',
                    controller: 'CenarioDetailController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                    	entity: function () {
                            return {
                            	protocolo: null,
                            	idInstalacao: null,
                            	dataAgendamento: null,
                            	mesInicial: null,
                            	mesFinal: null,
                            	solicitante: null,
                            	situacao: null,
                            	instalacoesReq: null
                            };
                        },
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        }); // END DETAIL CENARIO
    }

})();
