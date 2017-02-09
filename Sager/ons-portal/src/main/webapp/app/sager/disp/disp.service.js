(function() {
    'use strict';
    angular
        .module('onsPortalApp')
        .factory('DispService', DispService);

    DispService.$inject = ['$resource', 'DateUtils'];

    function DispService ($resource, DateUtils) {
        //var resourceUrl =  '/api/disp/v1/:id';
    	var resourceUrl =  'onssagerread/' + 'api/disp/';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dtRef = DateUtils.convertLocalDateFromServer(data.dtRef);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dtRef = DateUtils.convertLocalDateToServer(data.dtRef);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dtRef = DateUtils.convertLocalDateToServer(data.dtRef);
                    return angular.toJson(data);
                }
            },
            'getDisps':{
            	method: 'POST'
            },
            'checkDispInstalacoes' : {
            	method: 'PUT'
            }
        });
    }
})();
