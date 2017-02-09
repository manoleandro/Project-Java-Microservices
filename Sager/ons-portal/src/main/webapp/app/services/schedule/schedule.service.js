(function () {
    'use strict';

    angular
        .module('onsPortalApp')
        .factory('ScheduleService', ScheduleService);

    ScheduleService.$inject = ['$resource'];

    function ScheduleService ($resource) {
    	
    	var resourceUrl =  'onssageragendamento/api/agendamentos-calculo/:id';	
    	
        return $resource(resourceUrl,{}, {
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
            'delete':{ method:'DELETE'},
            'getAgendamentos':{ method: 'POST'}
        });
        return service;
    }
})();
