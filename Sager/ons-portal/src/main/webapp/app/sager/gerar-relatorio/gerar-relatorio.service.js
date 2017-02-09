(function() {
    'use strict';
    angular
        .module('onsPortalApp')
        .factory('GerarRelatorioService', GerarRelatorioService);

    GerarRelatorioService.$inject = ['$resource', 'DateUtils'];

    function GerarRelatorioService ($resource, DateUtils) {
        //var resourceUrl =  '/api/disp/v1/:id';
    	var resourceUrl =  'onssagerread/api/relatorio/';

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
            'getGerarRelatorio':{
            	method: 'POST',
                responseType: 'arraybuffer',
                isArray: true,
                transformResponse: function (data, headers) {
            		var mensagensStr = String.fromCharCode.apply(null, new Uint8Array(data));
            		if (mensagensStr.indexOf("Access Denied")>0)
        			{
            			alert("Sessão expirada");
            			return;
        			}
                	var content = headers('Content-Disposition');
                	var fileName = content.substring(content.indexOf("=")+1);
                	if (!(fileName == "mensagensErro.log" || fileName == "semDados.log"))
            		{                		
	    				var file = new Blob([data], {type: 'application/zip'});
	    				var fileURL = URL.createObjectURL(file);    				
	    				saveAs(file, fileName);
            			alert("Download efetuado com sucesso");
            			return;
            		}
                	else{
                		return angular.fromJson(mensagensStr);
                	}
                    return file;
                }

            }
        });
    }
})();
