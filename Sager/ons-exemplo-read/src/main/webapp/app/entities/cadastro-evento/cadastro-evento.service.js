(function() {
    'use strict';
    angular
        .module('onsPortalApp')
        .factory('CadastroEvento', CadastroEvento);

    CadastroEvento.$inject = ['$resource', 'DateUtils'];

    function CadastroEvento ($resource, DateUtils) {
        var resourceUrl =  'onsexemploread/' + 'api/cadastro-eventos/:id';
        
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dataVerificada = DateUtils.convertDateTimeFromServer(data.dataVerificada);
                        data.timelineDate = DateUtils.convertDateTimeFromServer(data.timelineDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'queryByUsinaId': { 
            	method: 'GET', 
            	params: { usinaId : null },
            	isArray: true,
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        for (var i = 0; i < data.length; i++) {
                        	data[i].dataVerificada = DateUtils.convertDateTimeFromServer(data[i].dataVerificada);
                        	data[i].timelineDate = DateUtils.convertDateTimeFromServer(data[i].timelineDate);
                        }
                    }
                    return data;
                }
            },
            'saveApuracao': {
            	method: 'POST', 
            	url: 'onsexemploread/' + 'api/cadastro-eventos/apuracao',
            	params: { usinaId : null, usinaVersion : null, mesAnoApuracao : null }
            },
            'saveRetificacao': {
            	method: 'POST', 
            	url: 'onsexemploread/' + 'api/cadastro-eventos/retificacao',
            	params: { usinaId : null, mesAnoApuracao : null }
            }
        });
    }
})();
