(function() {
    'use strict';

    angular
        .module('onsPortalApp', [
            'ngStorage', 
            'tmh.dynamicLocale',
            'pascalprecht.translate', 
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar',
            'sagerApp.instalacaoChooserExclusive2',
            'sagerApp.platformScheduleList',
            'ui.tree',
            'ngMaterial', 
            'smart-table',
            'xeditable',
            'chart.js',
            'angular-timeline',
            'angular-scroll-animate',
            'ui.grid', 
            'ui.grid.pagination',
            'ui.grid.resizeColumns',
            'ui.grid.moveColumns', 
            'ui.grid.autoResize', 
            'ui.grid.pinning',
            'ui.grid.exporter',
            'ui.grid.edit', 'ui.grid.rowEdit', 'ui.grid.cellNav', 'ui.grid.expandable',
            'ds.objectDiff'
           ,'ngMockE2E'
        ])
        .run(run);

//    run.$inject = ['stateHandler', 'translationHandler'];
//
//    function run(stateHandler, translationHandler) {
//        stateHandler.initialize();
//        translationHandler.initialize();
//    }
    run.$inject = ['stateHandler', 'translationHandler','mockHandler'];
    
    function run(stateHandler, translationHandler,mockHandler) {
    	stateHandler.initialize();
    	translationHandler.initialize();
    	mockHandler.initialize();
    }
    
    // Desativando warnings do Aria (incluso no Material Design)
    angular
    .module('onsPortalApp')
    .config(['$mdAriaProvider', function ($mdAriaProvider) {
        $mdAriaProvider.disableWarnings();
    }]);
    
})();
