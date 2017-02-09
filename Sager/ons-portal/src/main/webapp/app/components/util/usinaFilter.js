(function() {
	
angular.module('onsPortalApp').filter("usinaSelecionada", function(){
	return function(input){
		var usinasSelecionadas = input.filter(function(usina){
			if(usina.selected){
				return usina;
			}
		});
		return usinasSelecionadas;
	}
});



})();