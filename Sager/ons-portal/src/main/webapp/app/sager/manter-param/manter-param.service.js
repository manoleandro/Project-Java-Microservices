(function() {
    'use strict';
    angular
        .module('onsPortalApp')
        .factory('ManterParamService', ManterParamService);

    ManterParamService.$inject = ['$resource'];

    function ManterParamService ($resource) {
        var resourceUrl =  'onssagerparametrizacao/' + 'api/parametrizacao-retificacoes/:id';
        
        return $resource(resourceUrl, {}, {
        	'query': {method: 'GET', isArray: true},
        	'get': {
        		method: 'GET',
        		transformResponse: function(data) {
        			data = angular.fromJson(data);
        			return data;
        		}
        	},
        	'save': {method: 'POST'},
        	'update': {method: 'PUT'},
        	'del': {method: 'DELETE'}
        });
        
        return service;
    }
})();
