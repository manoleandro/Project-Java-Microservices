(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('TimelineController', TimelineController);

    TimelineController.$inject = ['$scope', '$state', 'Timeline'];

    function TimelineController ($scope, $state, Timeline) {
        var vm = this;
        
        vm.timelines = [];

        loadAll();

        function loadAll() {
            Timeline.query(function(result) {
                vm.timelines = result;
            });
        }
    }
})();
