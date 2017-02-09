(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('TimelineDetailController', TimelineDetailController);

    TimelineDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Timeline', 'Record'];

    function TimelineDetailController($scope, $rootScope, $stateParams, entity, Timeline, Record) {
        var vm = this;

        vm.timeline = entity;

        var unsubscribe = $rootScope.$on('onsPortalApp:timelineUpdate', function(event, result) {
            vm.timeline = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
    
})();
