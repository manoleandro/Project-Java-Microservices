(function() {
    'use strict';
    angular
        .module('onsPortalApp')
        .factory('Timeline', Timeline);

    Timeline.$inject = ['$resource'];

    function Timeline ($resource) {
        var resourceUrl =  'onsplatform/' + 'api/timelines/:id';

        return $resource(resourceUrl, {}, {
            'query': { 
            	method: 'GET', 
            	params: { aggregateId : null },
            	isArray: true
        	},
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
