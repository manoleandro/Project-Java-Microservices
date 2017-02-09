//'use strict';
//
//describe('Controller Tests', function() {
//
//    describe('CadastroUsina Management Detail Controller', function() {
//        var $scope, $rootScope;
//        var MockEntity, MockPreviousState, MockCadastroUsina;
//        var createController;
//
//        beforeEach(inject(function($injector) {
//            $rootScope = $injector.get('$rootScope');
//            $scope = $rootScope.$new();
//            MockEntity = jasmine.createSpy('MockEntity');
//            MockPreviousState = jasmine.createSpy('MockPreviousState');
//            MockCadastroUsina = jasmine.createSpy('MockCadastroUsina');
//            
//
//            var locals = {
//                '$scope': $scope,
//                '$rootScope': $rootScope,
//                '$stateParams': {id: 'AAAAA'},
//                'entity': MockEntity,
//                'previousState': MockPreviousState,
//                'CadastroUsina': MockCadastroUsina
//            };
//            createController = function() {
//                $injector.get('$controller')("CadastroUsinaDetailController", locals);
//            };
//        }));
//
//
//        describe('Root Scope Listening', function() {
//            it('Unregisters root scope listener upon scope destruction', function() {
//                var eventType = 'onsPortalApp:cadastroUsinaUpdate';
//
//                createController();
//                expect($rootScope.$$listenerCount[eventType]).toEqual(1);
//
//                $scope.$destroy();
//                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
//            });
//        });
//    });
//
//});
