(function() {
    'use strict';
    angular
        .module('onsPortalApp')
        .factory('CalculoTaxaService', CalculoTaxaService);

    CalculoTaxaService.$inject = ['$resource'];

    function CalculoTaxaService ($resource) {
    	
    	var resourceUrl =  'onssageragendamento/api/all-agendamentos-calculo/:id';

        return $resource(resourceUrl,{}, {
            'query': { method: 'POST', 
            	isArray: true, 
            	transformRequest: function (data) {
                return angular.toJson(data);
            }},
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

