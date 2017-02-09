(function() {
    'use strict';
    angular
        .module('onsPortalApp')
        .factory('InstalacaoTaxasByInstalacaoPeriodo', InstalacaoTaxasByInstalacaoPeriodo);

    InstalacaoTaxasByInstalacaoPeriodo.$inject = ['$resource'];

    function InstalacaoTaxasByInstalacaoPeriodo ($resource) {
    	
    	var resourceUrl =  'onssagerread/api/instalacaoTaxasByInstalacaoPeriodo/:id';
    	
        return $resource(resourceUrl,{}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    return angular.toJson(data);
                }
            },
            'getByInstalacaoPeriodo': {
	            method: 'POST',
	            isArray: true,
	            transformRequest: function (data) {
	                return angular.toJson(data);
	            }
            },
            'taxaByIdJob': {
            	method: 'GET',
            	isArray: true,
            	transformRequest: function (data) {
	                return angular.toJson(data);
	            },
	            url:"onssagerread/api/taxaByIdJob/:id"
            }
        });
    }

})();

