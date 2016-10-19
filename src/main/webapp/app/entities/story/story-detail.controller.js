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
	console.log("VM.story");
	
	console.log(vm.story);
		console.log("story");
	console.log(story);
        var unsubscribe = $rootScope.$on('mediumApp:storyUpdate', function(event, result) {
            vm.story = result;
		//console.log("vm.story"+vm.story+"vm.story.id"+cm.story.id);
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
