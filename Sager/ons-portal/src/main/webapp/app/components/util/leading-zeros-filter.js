(function() {
    'use strict';
    
    angular
        .module('onsPortalApp')
        .filter('leadingZeros', leadingZeros);
    
    function leadingZeros () {
        return function(input, n) {
    		if(input === undefined)
    			input = ""
    		if(input.length >= n)
    			return input
    		var zeros = "0".repeat(n);
    		return (zeros + input).slice(-1 * n)
    	};
    }
/**
 * Description:
 *     preenche com 0's a esquerda para preencher a qtde de casas (NÃO DECIMAIS) n
 * Usage:
 *   {{some_text | leadingZeros:4}}
 */
    
	
})();
