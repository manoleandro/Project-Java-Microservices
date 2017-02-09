(function() {
    'use strict';

    angular
        .module('onsPortalApp')
        .controller('ScenarioDetailController', ScenarioDetailController);

    ScenarioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Record', 'Scenario'];

    function ScenarioDetailController($scope, $rootScope, $stateParams, previousState, entity, Record, Scenario) {
        var vm = this;

        vm.scenario = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('onsPortalApp:scenarioUpdate', function(event, result) {
            vm.scenario = result;
        });
        $scope.$on('$destroy', unsubscribe);
        
    	var gitGraph = new GitGraph( graphConfig() );

    	var branches = {};
    	var snapshots = {};
    	
    	Scenario.query({ aggregateId: vm.scenario.aggregateId }).$promise.then(function(scenarios) {
        	for (var i = 0; i < scenarios.length; i++) {
        		var scenario = scenarios[i];
        		if (!scenario.parentId) {
        			branches[scenario.id] = gitGraph.branch( scenario.description );
        		} else {
        			branches[scenario.id] = gitGraph.branch({ 
        				parentBranch: branches[scenario.parentId], 
        				parentCommit: snapshots[scenario.parentId + '.' + scenario.firstMinorVersion], 
        				name: scenario.description });
        		}
        		Record.query({ timelineId: scenario.id }).$promise.then(function(records) {
    	        	for (var j = 0; j < records.length; j++) {
    	        		var record = records[j];
    	        		var commitConfig = {
	      		    	  message: record.type + ': ' + record.payloadType.substr(record.payloadType.lastIndexOf('.') + 1),
	      		    	  tag: scenario.majorVersion + '.' + record.minorVersion,
	      		    	  date: record.recordDate,
	      		    	  tooltipDisplay: true,
	      		    	  author: 'admin'
	      		    	};
    	        		if (record.type == 'SNAPSHOT') {
    	        			commitConfig.dotSize = 10;
    	        			commitConfig.dotStrokeWidth = 10;
    	        			commitConfig.dotStrokeColor = 'black';
    	        			snapshots[scenario.id + '.' + record.minorVersion] = branches[scenario.id].commit( commitConfig );
    	        		} else if (record.type == 'COMMAND') {
    	        			commitConfig.dotColor = 'white';
    	        			commitConfig.dotSize = 10;
    	        			commitConfig.dotStrokeWidth = 10;
    	        			branches[scenario.id].commit( commitConfig );
    	        		} else if (record.type == 'EVENT') {
    	        			commitConfig.dotSize = 10;
    	        			branches[scenario.id].commit( commitConfig );
    	        		}
    	    		}
    	    	});
        	}
        });
    	
        function graphConfig() {
        	var templateConfig = {
        	  //colors: [ '#F00', '#0F0', '#00F' ], // branches colors, 1 per column
        	  branch: {
        	    lineWidth: 8,
        	    spacingX: 50,
        	    showLabel: true
        	  },
        	  commit: {
        	    spacingY: -80,
        	    dot: {
        	      size: 12
        	    },
        	    message: {
        	      displayAuthor: true,
        	      displayBranch: false,
        	      displayHash: false,
        	      font: 'normal 12pt Arial'
        	    },
        	    shouldDisplayTooltipsInCompactMode: true, // default = true
        	    tooltipHTMLFormatter: function ( commit ) {
        	      return '<b>' + commit.sha1 + '</b>' + ': ' + commit.message;
        	    }
        	  }
        	};
        	var template = new GitGraph.Template( templateConfig );

        	var config = {
        	  template: template       // could be: 'blackarrow' or 'metro' or `myTemplate` (custom Template object)
        	  //, reverseArrow: true  // to make arrows point to ancestors, if displayed
        	  //, orientation: 'vertical-reverse'
        	  //, mode: 'compact'     // special compact mode : hide messages & compact graph
        	};
        	return config;
        }
    }
})();
