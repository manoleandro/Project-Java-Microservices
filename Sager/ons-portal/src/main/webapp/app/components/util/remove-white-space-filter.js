(function() {
    'use strict';
    
    angular
        .module('onsPortalApp')
        .filter('nospace', nospace);
    
    function nospace () {
        return nospaceFilter;
/**
 * Description:
 *     removes white space from text. useful for html values that cannot have spaces
 * Usage:
 *   {{some_text | nospace}}
 */
    function nospaceFilter(value) {
	        return (!value) ? '' : value.replace(/ /g, '');
	    };
	}
	
})();