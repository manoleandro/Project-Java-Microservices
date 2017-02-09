(function() {
    'use strict';
    angular
        .module('onsPortalApp')
        .factory('TaxaRef', TaxaRef);

    TaxaRef.$inject = ['$resource', 'DateUtils'];

    function TaxaRef ($resource) {
    	
    	var resourceUrl =  'onssagerread/api/taxaReferenciaByTipoInstalacao/:id';
    	
        return $resource(resourceUrl,{}, {
            'query': { method: 'GET', isArray: true},
        });
    }

})();

