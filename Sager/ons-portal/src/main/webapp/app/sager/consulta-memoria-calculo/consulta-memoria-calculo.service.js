(function() {
    'use strict';
    angular
        .module('onsPortalApp')
        .factory('ConsultaTaxa', ConsultaTaxa);

    ConsultaTaxa.$inject = ['$resource'];

    function ConsultaTaxa ($resource) {
        var resourceUrl =  'onssagerread/' + 'api/consulta-memoria-calculo';

        return $resource(resourceUrl, {}, {
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
            'update': { method:'PUT' }
            
        });
    }
})();
