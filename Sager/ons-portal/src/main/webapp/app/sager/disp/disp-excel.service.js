(function() {
    'use strict';
    angular
        .module('onsPortalApp')
        .factory('DispExcelService', DispExcelService);

    DispExcelService.$inject = ['$resource', 'DateUtils'];

    function DispExcelService ($resource, DateUtils) {
        //var resourceUrl =  '/api/disp/v1/:id';
    	var resourceUrl =  'onssagerread/' + 'api/excel-disp/';

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
            'getExcelGeral':{
            	method: 'POST',
            	responseType: 'arraybuffer',
                transformResponse: function (data, headers) {
                    if (data) {
                    	var content = headers('Content-Disposition');
                    	var fileName = content.substring(content.indexOf("=")+1);
                    	
                    	var file = new Blob([data], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});
	    				var fileURL = URL.createObjectURL(file);    				
	    				saveAs(file, fileName);
                    }
                    return file;
                }
            },
            'checkDispInstalacoes' : {
            	method: 'PUT'
            }
        });
    }
})();
