
	
	
    angular.module('onsPortalApp')
    .directive("uiData",function(){
    	return{
    		templateUrl:"app/components/instalacoes/data.html",
    		restrict:"AE",
    		scope:{
    			valor:"="
    		},
    		 controller: ['$scope',  function InstalacoesControlerDirective($scope){
    			 
    			 $scope.dtTeste = new Date();
    	    	 $scope.datePickerOpenStatus = {};
    	         $scope.openCalendar = openCalendar;

    	         $scope.datePickerOpenStatus.teste = false;
    	         
    	         $scope.datepickerOptionsTeste = {
    	                 mode: 'month',
    	                 minMode: 'month',
    	                 maxMode: 'year'
    	             };
    	         
    	         
    	         
    	         function openCalendar (date) {
    	             $scope.datePickerOpenStatus[date] = true;
    	             var unwatchMinMaxValues = $scope.$watch(function() {
    	                 return [$scope.datepickerOptionsFinal, $scope.datepickerOptionsInicial];
    	             }, function() {
    	                 // min max datesc
    	                 $scope.datepickerOptionsInicial.maxDate = $scope.dtFim;
    	                 $scope.datepickerOptionsFinal.minDate = $scope.dtInicio;
    	                 if($scope.dtFim > new Date()){
    	                 	
    	                 }
    	                 if ($scope.dtInicio && $scope.dtFim) {
    	                     var diff = $scope.dtInicio.getTime() - $scope.dtFim.getTime();
    	                     $scope.dayRange = Math.round(Math.abs(diff/(1000*60*60*24)))
    	                 } else {
    	                     $scope.dayRange = 'n/a';
    	                 }


    	             }, true);
    	         }
    	    	
    	    	
    		 }]
    	}
    });
