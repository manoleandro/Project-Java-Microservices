(function () {
    'use strict';

    angular
        .module('onsPortalApp')
        .factory('ScheduleServiceByInstalacao', ScheduleServiceByInstalacao);

    ScheduleServiceByInstalacao.$inject = ['$resource'];

    function ScheduleServiceByInstalacao ($resource) {
    	
    	var resourceUrl =  'onssageragendamento/api/agendamentos-calculo-by-instalacao/:id';	
    	
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
            'getAgendamentos':{ method: 'POST', isArray: true}
        });
        



        return service;
    }
})();
