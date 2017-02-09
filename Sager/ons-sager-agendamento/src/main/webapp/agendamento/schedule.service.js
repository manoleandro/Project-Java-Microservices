(function () {
    'use strict';

    angular
        .module('onsPortalApp')
        .factory('ScheduleService', ScheduleService);

    ScheduleService.$inject = ['$resource'];

    function ScheduleService ($resource) {
        var service = $resource('/platform/schedule/:id', {}, {
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
