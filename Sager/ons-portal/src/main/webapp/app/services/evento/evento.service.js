(function() {
    'use strict';
    angular
        .module('onsPortalApp')
        .factory('EventoService', EventoService);

    EventoService.$inject = ['$resource', 'DateUtils'];

    function EventoService ($resource, DateUtils) {
        var resourceUrl =  'onssagerread/api/eventos/:id';
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
//                        data.datahora = DateUtils.convertDateTimeFromServer(data.datahora);
                    }
                    return data;
                }
            },
            'getById': {
                method: 'PUT',
                isArray: true,
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'getByInstalacoes': {
            	method: 'POST',
            	isArray: true,
            	transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            }
        });
    }
})();
