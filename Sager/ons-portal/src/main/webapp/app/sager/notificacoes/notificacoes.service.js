(function() {
    'use strict';
    angular
        .module('onsPortalApp')
        .factory('NotificacaoService', NotificacaoService);

    NotificacaoService.$inject = ['$resource'];

    function NotificacaoService ($resource) {
        var resourceUrl =  'onssagerread/' + 'api/notificacao';

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
            'update': { method:'PUT' },
            'getNotificacoes': { 
            	method: 'GET',
            	transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                },
            	url: 'onssagerread/' + 'api/notificacaoByRoles',
            	isArray: true
            },
            'getNotificacoesLidasByUser': { 
            	method: 'GET',
            	transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                },
            	url: 'onssagerread/' + 'api/getNotificacoesLidasByUser',
            	isArray: true
            },
            'lerNotificacao':{
            	method: 'POST',
            	url: 'onssagerread/' + 'api/lerNotificacao',
            }
        });
    }
})();