(function() {
	
angular.module('onsPortalApp').filter("filtrarTaxaPorUsina", function(){
	return function(input, usina){
		var taxas = input.filter(function(taxa){
			if(usina.nome == taxa.instalacao){
				return taxa;
			}				
		});
		return taxas;
	}
});

angular.module('onsPortalApp').filter("filtrarTaxaPorInterligacao", function(){
	return function(input, usina){
		var taxas = input.filter(function(taxa){
			if(usina.nome == taxa.nome){
				return taxa;
			}				
		});
		return taxas;
	}
});



})();