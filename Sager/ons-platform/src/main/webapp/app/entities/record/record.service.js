(function() {
    'use strict';
    angular
        .module('onsPortalApp')
        .factory('Record', Record);

    Record.$inject = ['$resource', 'DateUtils'];

    function Record ($resource, DateUtils) {
        var resourceUrl =  'onsplatform/' + 'api/records/:id';

        return $resource(resourceUrl, {}, {
            'query': {
            	method: 'GET',
            	params: { timelineId : null },
            	isArray: true
            },
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.recordDate = DateUtils.convertDateTimeFromServer(data.recordDate);
                        data.creationDate = DateUtils.convertDateTimeFromServer(data.creationDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
