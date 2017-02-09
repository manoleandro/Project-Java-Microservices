(function() {
    'use strict';
    angular
        .module('onsPortalApp')
        .factory('CadastroUsina', CadastroUsina);

    CadastroUsina.$inject = ['$resource'];

    function CadastroUsina ($resource) {
        var resourceUrl =  'onsexemploread/' + 'api/cadastro-usinas/:id';

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
            'getApuracoes': { 
            	method: 'GET',
            	url: resourceUrl + '/apuracoes',
            	isArray: true
            }
        });
    }
})();
