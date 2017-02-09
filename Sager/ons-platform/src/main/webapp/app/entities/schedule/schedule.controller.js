(function() {
	'use strict';

	angular.module('onsPortalApp').controller('ScheduleController',
			ScheduleController);

	ScheduleController.$inject = [ '$scope', '$state', 'Schedule' ];

	function ScheduleController($scope, $state, Schedule) {
		var vm = this;

		vm.calculoSchedules = [];
		vm.reprocessSchedules = [];
		vm.scenarioSchedules = [];
		vm.commands = [];
		vm.datePickerOpenStatus = {};
		vm.openCalendar = openCalendar;
		vm.search = search;
		vm.searchCommand = null;
		vm.searchStartDate = null;
		vm.searchEndDate = null;

		loadCommands();

		function search() {
			Schedule.query({
				commandName : vm.searchCommand.className,
				startDate : vm.searchStartDate,
				endDate : vm.searchEndDate
			}, function(result) {
				vm.schedules = result;
			});
		}

		vm.datePickerOpenStatus.searchStartDate = false;
		vm.datePickerOpenStatus.searchEndDate = false;

		function openCalendar(date) {
			vm.datePickerOpenStatus[date] = true;
		}

		function loadCommands() {
			vm.commands = [ {
				description : 'Cálculo de Taxas',
				className : 'br.org.ons.sager.CalcularTaxasCommand'
			}, {
				description : 'Retificação',
				className : 'br.org.ons.platform.ReprocessCommand'
			}, {
				description : 'Construção de Cenário',
				className : 'br.org.ons.platform.CalcularTaxasCommand'
			} ];
		}
	}
})();
