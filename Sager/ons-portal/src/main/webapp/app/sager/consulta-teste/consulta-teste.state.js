//(function() {
//    'use strict';
//
//    angular
//        .module('onsPortalApp')
//        .config(stateConfig);
//
//    stateConfig.$inject = ['$stateProvider'];
//
//    function stateConfig($stateProvider) {
//        $stateProvider
//        .state('consulta-teste', {
//            parent: 'sager',
//            url: '/consulta-teste',
//            data: {
//                authorities: ['ROLE_USER']
////                pageTitle: 'onsPortalApp.consultaTaxa.home.title'
//            },
//            views: {
//                'content@': {
//                    templateUrl: 'app/sager/consulta-teste/consulta-teste.html',
//                    controller: 'ConsultaTesteController',
//                    controllerAs: 'vm'
//                }
//            },
//            resolve: {
//                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
//                    $translatePartialLoader.addPart('consultaTeste');
//                    $translatePartialLoader.addPart('global');
//                    return $translate.refresh();
//                }]
//            }
//        });
//    }
//})();
