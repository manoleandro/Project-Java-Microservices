(function() {
    'use strict';
    angular
        .module('onsPortalApp')
        .factory('InstalacaoTaxas', InstalacaoTaxas);

    InstalacaoTaxas.$inject = ['$resource'];

    function InstalacaoTaxas ($resource) {
    	
    	var resourceUrl =  'onssagerread/api/instalacaoTaxas/:id';

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
            }
        });
    }

})();

