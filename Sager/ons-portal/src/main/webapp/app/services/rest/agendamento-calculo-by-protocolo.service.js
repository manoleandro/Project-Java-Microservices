(function() {
	'use strict';
	angular
		.module('onsPortalApp')
		.factory('AgendamentoCalculoByProtocolo', AgendamentoCalculoByProtocolo);
	
	
	AgendamentoCalculoByProtocolo.$inject = ['$resource'];
	
	function AgendamentoCalculoByProtocolo ($resource) {
		
		var resourceUrl =  'onssageragendamento/api/agendamentos-by-protocolo/:id';
		
		return $resource(resourceUrl,{}, {
            'agendamentosCalculoByProtocolo':{
            	method:'GET',
            	transformRequest: function (data) {
	                return angular.toJson(data);
	            }
            }
        });
        
        return service;
	}
})();