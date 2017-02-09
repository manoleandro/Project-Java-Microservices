(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('RecordDetailController', RecordDetailController);

    RecordDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'Record', 'Timeline'];

    function RecordDetailController($scope, $rootScope, $stateParams, DataUtils, entity, Record, Timeline) {
        var vm = this;

        vm.record = entity;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('onsPortalApp:recordUpdate', function(event, result) {
            vm.record = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
