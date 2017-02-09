(function() {
    'use strict';
    angular
        .module('onsPortalApp')
        .factory('Schedule', Schedule);

    Schedule.$inject = ['$resource', 'DateUtils'];

    function Schedule ($resource, DateUtils) {
        var resourceUrl =  'onsplatform/' + 'api/schedules/:id';

        return $resource(resourceUrl, {}, {
            'query': { 
            	method: 'GET', 
            	isArray: true,
            	params: { commandName : null, startDate : null, endDate : null }
            },
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.scheduleDate = DateUtils.convertDateTimeFromServer(data.scheduleDate);
                        data.creationDate = DateUtils.convertDateTimeFromServer(data.creationDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
