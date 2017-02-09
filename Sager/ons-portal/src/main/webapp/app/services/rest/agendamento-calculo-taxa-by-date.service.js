(function() {
	'use strict';
	angular
		.module('onsPortalApp')
		.factory('AgendamentoCalculoTaxaByDate', AgendamentoCalculoTaxaByDate);
	
	
	AgendamentoCalculoTaxaByDate.$inject = ['$resource'];
	
	function AgendamentoCalculoTaxaByDate ($resource) {
		
		var resourceUrl =  'onssageragendamento/api/agendamentos-calculo-by-date/:id';
		
		return $resource(resourceUrl,{}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'save': { method:'POST' },
            'update': { method:'PUT' },
            'delete':{ method:'DELETE'},
            'getByInstalacaoPeriodo': {
	            method: 'POST',
	            isArray: true,
	            transformRequest: function (data) {
	                return angular.toJson(data);
	            }
            },
            'agendamentosCalculoById':{
            	method:'GET',
            	transformRequest: function (data) {
	                return angular.toJson(data);
	            }
            }
        });
        
        return service;
	}
})();