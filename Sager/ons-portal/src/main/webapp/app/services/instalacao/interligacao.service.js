(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .factory('InterligacaoService', InterligacaoService);

    InterligacaoService.$inject = ['$resource'];

    function InterligacaoService ($resource) {
        var service = $resource('/interligacao/v1/:id', {}, {
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
            'delete':{ method:'DELETE'}
        });
        

        return service;
    }
})();
