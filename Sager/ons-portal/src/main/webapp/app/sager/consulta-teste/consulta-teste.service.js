//(function() {
//    'use strict';
//    angular
//        .module('onsPortalApp')
//        .factory('ConsultaTeste', ConsultaTeste);
//
//    ConsultaTeste.$inject = ['$resource'];
//
//    function ConsultaTeste ($resource) {
//        var resourceUrl =  'onssagerread/' + 'api/consulta-teste';
//
//        return $resource(resourceUrl, {}, {
//        	'query': { method: 'GET', isArray: true},
//            'get': {
//                method: 'GET',
//                transformResponse: function (data) {
//                    if (data) {
//                        data = angular.fromJson(data);
//                    }
//                    return data;
//                }
//            },
//            'update': { method:'PUT' },
//            'getInstalacoes': { 
//            	method: 'GET',
//            	url: 'onssagerread/' + 'api/instalacoes',
//            	isArray: true
//            }
//        });
//    }
//})();
