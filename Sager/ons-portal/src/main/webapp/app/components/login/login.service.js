(function() {
	'use strict';

	angular.module('onsPortalApp').factory('LoginService', LoginService);

	LoginService.$inject = [ '$window', '$http', 'Principal' ];

	function LoginService($window, $http, Principal) {

		var promise;
		function getPopPortalUrl() {
			if (!promise) {
				promise = $http.get('api/auth/portal')
					.success(function(response) {
							return response;
					})
					.error(function(response) {
						return response;
					});
			}
			return promise;
		}

		var onsPortalUrl = encodeURIComponent(window.location.protocol + '//'
				+ window.location.host + '/onsportal');

		var service = {
			login : login,
			logout : logout
		};

		return service;

		function login() {
			var popPortalUrl;
			getPopPortalUrl()
			.then(function(url) {
					popPortalUrl = url.data;
				}, function(url) {
					popPortalUrl = url.data;
				})
				.then(function(){
					$window.location.href = popPortalUrl
						+ '/ons.pop.federation/?wa=wsignin1.0&wtrealm='
						+ onsPortalUrl + '&wctx=' + onsPortalUrl
						+ '&wreply=' + onsPortalUrl;
				});
		}

		function logout() {
			Principal.authenticate(null);

			var popPortalUrl;
			getPopPortalUrl()
				.then(function(url) {
					popPortalUrl = url.data;
				}, function(url) {
					popPortalUrl = url.data;
				})
				.then(function(){
					$window.location.href = popPortalUrl
						+ '/pop/SignOut.aspx?ReturnUrl='
						+ onsPortalUrl;
				});
		}
	}
})();
