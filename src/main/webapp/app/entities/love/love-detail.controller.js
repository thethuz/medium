(function() {
    'use strict';

    angular
        .module('mediumApp')
        .controller('LoveDetailController', LoveDetailController);

    LoveDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Love'];

    function LoveDetailController($scope, $rootScope, $stateParams, previousState, entity, Love) {
        var vm = this;

        vm.love = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mediumApp:loveUpdate', function(event, result) {
            vm.love = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
