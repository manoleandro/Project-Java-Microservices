(function() {
    'use strict';
    angular
        .module('onsPortalApp')
        .factory('ReproducaoCalculoService', ReproducaoCalculoService);

    ReproducaoCalculoService.$inject = ['$resource'];

    function ReproducaoCalculoService ($resource) {
    	
//    	var resourceUrl =  'onssagerread/api/reproduzirTaxa/:id';
    	var resourceUrl =  'onssagerread/api/reproduzirTaxa';

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
	            isArray: false,
	            transformRequest: function (data) {
	                return angular.toJson(data);
	            }
            }
        });
    }

})();