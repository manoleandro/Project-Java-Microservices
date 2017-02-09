(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('ManterParamController', ManterParamController);

    ManterParamController.$inject = ['$window', '$scope', 'ManterParamService', '$mdDialog', '$q'];

    function ManterParamController ($window, $scope, ManterParamService, $mdDialog, $q) {
        var vm = this;
        
        $scope.listaErro =[];
        $scope.listaSucesso = [];
        
        vm.rowsEdited = [];
        
        var actionButton =
        	"<form ng-show=\"row.entity.isActive\">" +
        		"<button id=\"btnSalvar\" type=\"button\" class=\"btn btn-primary\" ng-click=\"grid.appScope.saveRow(row.entity);row.entity.isActive = !row.entity.isActive\" >" +
        			"<span class=\"fa fa-check\"></span>Salvar" +
    			"</button>" +
    			"<button class=\"btn btn-warn\" type=\"button\" ng-click=\"grid.appScope.cancelRow(row.entity);row.entity.isActive = !row.entity.isActive\" style=\"background: #ec971f; border-color: #d58512;\">" +
    				"<span class=\"fa fa-times\"></span>Cancelar" +
				"</button>" +
			"</form>" +
        	"<div class=\"btn-group\"  ng-show=\"!row.entity.isActive\">" +
        		"<button type=\"button\" class=\"btn btn-u btn-u-yellow dropdown-toggle\" data-toggle=\"dropdown\" aria-expanded=\"false\">Ação" +
        			"<i class=\"fa fa-angle-down\"></i>" +
    			"</button>" +
    			"<ul class=\"dropdown-menu\" role=\"menu\">" +
    				"<li ng-click=\"row.entity.isActive = !row.entity.isActive\" ng-model=\"row.entity.isActive\"><a><i class=\"fa fa-edit\"></i> Alterar</a></li>" +
    				"<li ng-click=\"grid.appScope.deleteThisRow(row.entity)\"><a><i class=\"fa fa-trash-o\"></i> Excluir </a></li>" +
				"</ul>" +
			"</div>";
        
        var diaPicker =
        	"<form name=\"form\" class=\"css-form\">" +
        		"<input name=\"dia\" type=\"number\" min=\"0\" max=\"18\" required restrict=\"reject\" ui-grid-editor ng-model=\"MODEL_COL_FIELD\" class=\"ng-touched\" ng-class=\"\'colt\' + col.uid\">" +
        		"<p ng-show=\"!form.dia.$valid\" >Dia Inválido.</p>" +
    		"</form>";
        
        var horaPicker = 
        	"<form name=\"form\" class=\"css-form\">" +
        		"<input name=\"hora\" type=\"text\" mask=\"29:59\" required restrict=\"reject\" ui-grid-editor ng-model=\"MODEL_COL_FIELD\" placeholder=\"HH:mm\" ng-pattern=\"\/([01][0-9]:[0-5][0-9])|(2[0-3]:[0-5][0-9])\/\" class=\"ng-touched\"  ng-class=\"\'colt\' + col.uid\">" +
        		"<p ng-show=\"!form.hora.$valid\" >Hora Inválida.</p>" +
    		"</form>";
        
        
         $scope.gridOptionsParams = {
	        enableFiltering: true,
	        showGridFooter: true,
	        showColumnFooter: true,
	        rowHeight: 100,
	        enableCellEdit: true, // set all columns to non-editable unless otherwise specified; cellEditableCondition won't override that
    	    cellEditableCondition: function($scope) {
    	      // put your enable-edit code here, using values from $scope.row.entity and/or $scope.col.colDef as you desire
    	      return $scope.row.entity.isActive; // in this example, we'll only allow active rows to be edited
    	    },
	        columnDefs: [
	          { name: 'id', enableCellEdit: false,  cellTemplate: actionButton},
	          { name: 'dia', enableCellEditOnFocus: true, displayName: 'Dia útil', type: 'number', editableCellTemplate: diaPicker
	        	  ,cellTemplate: "<div ng-class=\"{'editableCell': row.entity.isActive}\">{{COL_FIELD}}</div>"
	          },
	          { field: 'hora', enableCellEditOnFocus: true, cellFilter: 'date:"HH:mm"',
	        	  editableCellTemplate: horaPicker
	        	  ,cellTemplate: "<div ng-class=\"{'editableCell': row.entity.isActive}\">{{COL_FIELD}}</div>"
	          }
	        ]
	      };
         
         $scope.gridOptionsParams.onRegisterApi = function(gridApi) {
        	$scope.gridParamApi = gridApi;
        	gridApi.edit.on.beginCellEdit($scope, function (rowEntity) {
        		if (vm.rowsEdited[rowEntity.id] == undefined || vm.rowsEdited[rowEntity.id] == null){
        			vm.rowsEdited[rowEntity.id] = {};
        			vm.rowsEdited[rowEntity.id].id = rowEntity.id;
        			vm.rowsEdited[rowEntity.id].dia = rowEntity.dia;
        			vm.rowsEdited[rowEntity.id].hora = rowEntity.hora;
        		}
            });
        	gridApi.edit.on.afterCellEdit($scope, function() {
        		$scope.$apply();
     	    });
        	 
        	gridApi.rowEdit.on.saveRow($scope, function (rowEntity) {
        		var promise = $q.defer();
    	        gridApi.rowEdit.setSavePromise(rowEntity, promise.promise);
    	        promise.resolve();
            });
     	  };
     	  
   		 $scope.cancelRow = function(rowEntity) {
   			 
   			if (rowEntity.id == undefined || rowEntity.id == null){
   				var index = $scope.gridOptionsParams.data.indexOf(rowEntity);
   				$scope.gridOptionsParams.data.splice(index, 1);
   			}
   			 
   			 
   			if (vm.rowsEdited[rowEntity.id] == undefined || vm.rowsEdited[rowEntity.id] == null)
   				return;
   			 
   			for (var i=0; i < $scope.gridOptionsParams.data.length; i++){
   				if ($scope.gridOptionsParams.data[i].id == vm.rowsEdited[rowEntity.id].id){
   					$scope.gridOptionsParams.data[i].hora = vm.rowsEdited[rowEntity.id].hora;
   					$scope.gridOptionsParams.data[i].dia = vm.rowsEdited[rowEntity.id].dia;
   				}
   			}
   			vm.rowsEdited[rowEntity.id] = null;
 		 };
 		 
         $scope.addAgendamento=function()
         {
        	 $scope.gridOptionsParams.data.push({
   		      //id: null
   		      dia: '',
   		      hora: '',
   		      isActive: true
   		    } );
         };
        
        loadParametrizacoes();
    	
        function loadParametrizacoes() {
        	ManterParamService.query(null, onSuccessQuery, onErrorQuery);
        	
        	function onSuccessQuery(data) {
        		for (var i=0; i < data.length; i++){
        			data[i].isActive = false;
        			
                  	var time = data[i].hora.split(':');
                  	data[i].hora = time[0] + ":" + time[1];
        		}
        		$scope.gridOptionsParams.data = data;
        	}
        	function onErrorQuery(error) {
        		$scope.cleanStatusList();
				$scope.listaErro.push({"erroCod":error.data.description,"critica":"", "mensagem":""+error.data.message});
				$window.scrollTo(0, $('mensagemErro').offsetTop);
            }
        }
        
		$scope.cleanStatusList = function(){
			$scope.listaSucesso = [];
			$scope.listaErro = [];
		};
		  
		$scope.deleteThisRow = function(rowEntity, ev) {
			var confirm = $mdDialog.confirm()
			  	.title('Excluir parametrização')
			  	.textContent('Tem certeza que deseja excluir essa parametrização? ')
			  	.ariaLabel('Excluir Parametrização')
			  	.targetEvent(ev)
			  	.ok('Sim')
			  	.cancel('Cancelar');
			  
			$mdDialog.show(confirm).then(function() {
				  ManterParamService.del({id:rowEntity.id}, onSuccessDel, onErrorDel);
			});

			function onSuccessDel() {
				$scope.cleanStatusList();
				  
				var index = $scope.gridOptionsParams.data.indexOf(rowEntity);
				$scope.gridOptionsParams.data.splice(index, 1);
				  
				$scope.listaSucesso.push({"SucessoCod":"RS_MENS_001","critica":"", "mensagem":"Operação realizada com sucesso"});
				$window.scrollTo(0, $('mensagemSucesso').offsetTop);
			}
			  
			function onErrorDel(error){
				$scope.cleanStatusList();
				$scope.listaErro.push({"erroCod":error.data.description,"critica":"", "mensagem":""+error.data.message});
				$window.scrollTo(0, $('mensagemErro').offsetTop);
			}
		};
		  
		$scope.saveRow = function(rowEntity) {
			if (rowEntity.id != null){
				ManterParamService.update(null,rowEntity, onSuccessUpdate, onErrorUpdate);
			} else {
				ManterParamService.save(null,rowEntity, onSuccessSave, onErrorSave);
			}
			function onSuccessUpdate() {
				$scope.listaSucesso.push({"SucessoCod":"RS_MENS_001","critica":"", "mensagem":"Operação realizada com sucesso"});
				$window.scrollTo(0, $('mensagemSucesso').offsetTop);
			}
			
			function onErrorUpdate(error){
				$scope.listaErro.push({"erroCod":error.data.description,"critica":"", "mensagem":""+error.data.message});
				loadParametrizacoes();
				$window.scrollTo(0, $('mensagemErro').offsetTop);
			}
			function onSuccessSave(data) {
				var index = $scope.gridOptionsParams.data.indexOf(rowEntity);
				$scope.gridOptionsParams.data[index].id = data.id;
				$scope.listaSucesso.push({"SucessoCod":"RS_MENS_001","critica":"", "mensagem":"Operação realizada com sucesso"});
				$window.scrollTo(0, $('mensagemErro').offsetTop);
			}
			
			function onErrorSave(error){
				$scope.listaErro.push({"erroCod":error.data.description,"critica":"", "mensagem":""+error.data.message});
				loadParametrizacoes();
				$window.scrollTo(0, $('mensagemErro').offsetTop);
			}
		};
    }
})();
