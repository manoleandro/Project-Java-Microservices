(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('RecordController', RecordController);

    RecordController.$inject = ['$scope', '$state', 'DataUtils', 'Record'];

    function RecordController ($scope, $state, DataUtils, Record) {
        var vm = this;
        
        vm.records = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Record.query(function(result) {
                vm.records = result;
            });
        }
    }
})();
