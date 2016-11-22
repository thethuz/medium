(function() {
    'use strict';

    angular
        .module('mediumApp')
        .controller('DraftDetailController', DraftDetailController);

    DraftDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Draft'];

    function DraftDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Draft) {
        var vm = this;

        vm.draft = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('mediumApp:draftUpdate', function(event, result) {
            vm.draft = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
