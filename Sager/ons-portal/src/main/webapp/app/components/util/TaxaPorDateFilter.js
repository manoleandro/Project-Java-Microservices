(function() {
	
	angular.module('onsPortalApp').filter("TaxaPorDateMesInicial", function(){
		return function(input, mesInicial){
			if(mesInicial == undefined){
				return input;
			}else{
				var taxas = input.filter(function(taxa){
					var dataEntrada = new Date(taxa.data);
					var dataInicio = new Date(mesInicial);
					
					if(dataInicio.getUTCMonth()+1 <=9){
						var mes = dataInicio.getUTCMonth()+1;
						dataInicio = new Date(dataInicio.getUTCFullYear()+"-0"+mes)
					}else{
						var mes = dataInicio.getUTCMonth()+1;
						dataInicio = new Date(dataInicio.getUTCFullYear()+"-"+mes)
					}
					
					if(dataEntrada.getTime() >= dataInicio.getTime()){
						return taxa;
					}
				});
				return taxas;
			}
			
		}
	});

	
	angular.module('onsPortalApp').filter("TaxaPorDateMesFinal", function(){
		return function(input, mesFinal){
			if(mesFinal == undefined){
				return input;
			}else{
				var taxas = input.filter(function(taxa){
					
					dataEntrada = new Date(taxa.data);
					dataInicio = new Date(mesFinal);
					if(dataEntrada.getTime() <= dataInicio.getTime()){
						return taxa;
					}
				});
				return taxas;
			}
			
			
		}
	});


})();