(function() {
    'use strict';

    angular
        .module('mediumApp')
        .controller('StoryDetailController', StoryDetailController);

    StoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Story'];

    function StoryDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Story) {
        var vm = this;

        vm.story = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('mediumApp:storyUpdate', function(event, result) {
            vm.story = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
