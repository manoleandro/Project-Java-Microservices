(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('DispChartController', DispChartController);

    DispChartController.$inject = ['$scope', '$rootScope', '$stateParams', '$timeout', '$uibModalInstance', 'entity', '$filter', '$interval'];

    function DispChartController($scope, $rootScope, $stateParams, $timeout, $uibModalInstance, entity, $filter, $interval) {
        var vm = this;
        
        var oper = [];
        var comer = [];
        var eletro = [];

        vm.clear = clear;
        vm.entity = entity;
        
        vm.equipSelected = vm.entity.disp_datas.equipamentos[0].id;
        
        vm.displayResults = function(){
        	vm.colunas = new Object();
        	oper = [];
            comer = [];
            eletro = [];
        	
        	var equipSelectedCodigoOns;
        	
        	for(var i=0; i< vm.entity.disp_datas.equipamentos.length; i++){
        		if (vm.entity.disp_datas.equipamentos[i].id == vm.equipSelected){
        			equipSelectedCodigoOns = vm.entity.disp_datas.equipamentos[i].codigoOns;
        		}
        	}
            
            
        	var keysColumns = Object.keys(vm.entity.disp_datas.colunas)
    		for(var k=0; k < keysColumns.length; k++){
    			if (keysColumns[k].indexOf(equipSelectedCodigoOns) >= 0 || keysColumns[k].indexOf('Data') >= 0){
    				vm.colunas[keysColumns[k]] = vm.entity.disp_datas.colunas[keysColumns[k]];
    			}
    		} 
            	
            vm.dataEquip = [];
            
            for(var i=0; i < vm.entity.disp_datas.disponibilidades.length; i++){
            	var dispo = new Object();
            	dispo.data = vm.entity.disp_datas.disponibilidades[i].data;
            	dispo.valores = new Object();
            	
            	var keys = Object.keys(vm.entity.disp_datas.disponibilidades[i].valores)
        		for(var k=0; k < keys.length; k++){
    				if (keys[k].indexOf((vm.equipSelected + '_')) >= 0 ){
//        				dispo.valores[keys[k]] = keys[k];
        				dispo.valores[keys[k]] = vm.entity.disp_datas.disponibilidades[i].valores[keys[k]];
        				if ( keys[k].indexOf('_O') >= 0 ){
        					oper.push(vm.entity.disp_datas.disponibilidades[i].valores[keys[k]]);
        				}else if ( keys[k].indexOf('_C') >= 0 ){
        					comer.push(vm.entity.disp_datas.disponibilidades[i].valores[keys[k]]);
        				}else if ( keys[k].indexOf('_E') >= 0 ){
        					eletro.push(vm.entity.disp_datas.disponibilidades[i].valores[keys[k]]);
        				}
        			}
        		} 
            	vm.dataEquip.push(dispo);
            }
            gerarGrafico();
        }

        vm.displayResults();
        
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
        
    	function gerarGrafico() {
    		$scope.labels = [];
    		for (var i=0; i < vm.dataEquip.length; i++){
    			$scope.labels.push($filter('date')(vm.dataEquip[i].data, "dd/MM/yyyy hh:mm"));	    	   
    		}
    		
    		$scope.series = ['Operacional', 'Comercial', 'Eletromecânica'];

    		$scope.data = [];	 
    		$scope.data.push(oper);
    		$scope.data.push(comer);
    		$scope.data.push(eletro);
    		
//    		$scope.colors = [
//	                 {backgroundColor: 'rgba(34,34,34,0.2)'},
//	                 {backgroundColor: "rgba(70, 157, 224, 1)"}, 
//	                 {backgroundColor: "rgba(206, 98, 90, 1)"}, 
//	                 {backgroundColor: 'rgba(221, 221, 221, 1)'},
//	             ];
	       
			
	         $scope.type = 'Line';
	      
	           $scope.options = {
	             tooltips: {
	               enabled: true
	             },
	             scales: {
	                 xAxes: [
	                     {
	                         gridLines: {
	                             display:true
	                         },  
	                         stacked: true,
	                     },
	                 ],
	                 yAxes: [
	                	 {
	                         id: 'y-axis-1',
	                         type: 'linear',
	                         display: true,
	                         position: 'left'
	                     }
	                 ]
	             }
	         };
        }
    }
})();
