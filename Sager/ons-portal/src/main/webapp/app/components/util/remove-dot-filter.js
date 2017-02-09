(function() {
    'use strict';
    
    angular
        .module('onsPortalApp')
        .filter('nodot', nodot);
    
    function nodot () {
        return nodotFilter;
/**
 * Description:
 *     removes white space from text. useful for html values that cannot have spaces
 * Usage:
 *   {{some_text | nodot}}
 */
    function nodotFilter(value) {
	        return (!value) ? '' : value.replace(/\./g, '');
	    };
	}
	
})();