(function () {
    'use strict';

    angular
        .module('onsPortalApp')
        .factory('ScheduleRetificacoesService', ScheduleRetificacoesService);

    ScheduleRetificacoesService.$inject = ['$resource'];

    function ScheduleRetificacoesService ($resource) {
    	
    	var resourceUrl =  'onssageragendamento/api/agendamentos-retificacoes-by-date/:id';	
    	
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
            'getAgendamentos':{
	            method: 'POST',
	            isArray: true,
	            transformRequest: function (data) {
	                return angular.toJson(data);
	            }
            }
        });
        



        return service;
    }
})();
