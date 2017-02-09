(function() {
    'use strict';
    angular
        .module('onsPortalApp')
        .factory('Scenario', Scenario);

    Scenario.$inject = ['$resource', 'DateUtils'];

    function Scenario ($resource, DateUtils) {
        var resourceUrl =  'onsplatform/' + 'api/scenarios/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startDate = DateUtils.convertDateTimeFromServer(data.startDate);
                        data.endDate = DateUtils.convertDateTimeFromServer(data.endDate);
                        data.creationDate = DateUtils.convertDateTimeFromServer(data.creationDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'end': {
            	method: 'POST',
            	url: resourceUrl + '/end',
            	params: { id: null, makeDefault: null }
            }
        });
    }
})();
