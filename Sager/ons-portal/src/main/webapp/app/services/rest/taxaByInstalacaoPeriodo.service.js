(function() {
    'use strict';
    angular
        .module('onsPortalApp')
        .factory('TaxaByInstalacaoPeriodo', TaxaByInstalacaoPeriodo);

    TaxaByInstalacaoPeriodo.$inject = ['$resource', 'DateUtils'];

    function TaxaByInstalacaoPeriodo ($resource, DateUtils) {
    	
    	var resourceUrl =  'onssagerread/api/taxaByInstalacaoPeriodo/:id';
    	
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
            },
            'getByInstalacaoPeriodo': {
	            method: 'POST',
	            isArray: true,
	            transformRequest: function (data) {
	                return angular.toJson(data);
	            }
            }
        });
    }

})();

