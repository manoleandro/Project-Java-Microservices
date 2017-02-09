(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .factory('authExpiredInterceptor', authExpiredInterceptor);

    
    authExpiredInterceptor.$inject = ['$rootScope', '$q', '$injector'];

    function authExpiredInterceptor($rootScope, $q, $injector) {
        var service = {
            responseError: responseError
        };

        return service;

        function responseError(response) {
            if (response.status === 401 || response.status === 403) {
                var LoginService = $injector.get('LoginService');
                LoginService.login();
            }
            return $q.reject(response);
        }
    }
})();
