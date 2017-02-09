(function() {
    'use strict';
    angular
        .module('onsPortalApp')
        .factory('Interligacoes', Interligacoes);

    Interligacoes.$inject = ['$resource', 'DateUtils'];

    function Interligacoes ($resource, DateUtils) {
    	
    	var resourceUrl =  'onssagerread/api/interligacoes/:id';
    	
        return $resource(resourceUrl,{}, {
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
            }
        });
    }

})();

