(function() {
    'use strict';

    angular
        .module('mediumApp')
        .controller('UsercommentDetailController', UsercommentDetailController);

    UsercommentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Usercomment'];

    function UsercommentDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Usercomment) {
        var vm = this;

        vm.usercomment = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('mediumApp:usercommentUpdate', function(event, result) {
            vm.usercomment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
