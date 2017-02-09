(function() {
    'use strict';
    angular
        .module('onsPortalApp')
        .factory('MemoriaCalculo', MemoriaCalculo);

    MemoriaCalculo.$inject = ['$resource'];

    function MemoriaCalculo ($resource) {
    	
    	var resourceUrl =  'onssagerread/api/memoriacalculo/:id';

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
            'getByInstalacaoDataTaxa': {
	            method: 'POST',
	            isArray: true,
	            transformRequest: function (data) {
	                return angular.toJson(data);
	            }
            }
        });
    }

})();

