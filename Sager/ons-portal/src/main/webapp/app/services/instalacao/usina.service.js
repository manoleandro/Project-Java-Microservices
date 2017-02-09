(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .factory('UsinaService', UsinaService);

    UsinaService.$inject = ['$resource'];

    function UsinaService ($resource) {
        var service = $resource('/usinas/v1/:id', {}, {
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
