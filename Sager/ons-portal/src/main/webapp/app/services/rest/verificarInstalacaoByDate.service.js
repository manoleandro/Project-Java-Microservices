(function() {
	'use strict';
	angular
		.module('onsPortalApp')
		.factory('VerificarInstalacaoByDate', VerificarInstalacaoByDate);
	
	
	VerificarInstalacaoByDate.$inject = ['$resource'];
	
	function VerificarInstalacaoByDate ($resource) {
		
		var resourceUrl =  'onssageragendamento/api/agendamentos-verificarSituacaoInstalacao/:id';
		
		return $resource(resourceUrl,{}, {
            'verificar': {
	            method: 'POST',
	            isArray: true,
	            transformRequest: function (data) {
	                return angular.toJson(data);
	            }
            }
        });
        
        return service;
	}
})();