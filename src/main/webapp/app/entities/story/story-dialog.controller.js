(function() {
    'use strict';

    angular
        .module('mediumApp')
        .controller('StoryDialogController', StoryDialogController);

    StoryDialogController.$inject = ['$state','$timeout', '$scope', '$stateParams','$resource', '$uibModalInstance', 'DataUtils', 'entity', 'Story'];

    function StoryDialogController ($state, $timeout, $scope, $stateParams, $resource, $uibModalInstance, DataUtils, entity, Story) {
        var vm = this;
		//console.log(loadOwner().onSuccess());
        vm.story = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            //$uibModalInstance.dismiss('cancel');
        }

        function save () {
          var contentLength=vm.story.content.length;
          var titleLength=vm.story.title.length;
          if ((contentLength<5*titleLength)||(titleLength<1)||(contentLength<300));
		var User = $resource('api/account',{},{'charge':{method:'GET'}});
		//console.log(User);
		$scope.user=User.get({activated: true});
		$scope.user.$promise.then(function(data){
			/**/
			vm.story.author=data.login;
			vm.story.authorName=data.firstName+" "+data.lastName;
			//vm.story.timeCreated=Date();
			vm.story.imglink='xxx.com';
			vm.story.timeToRead=(contentLength/270).toFixed(0);
			console.log(vm.story);
			vm.isSaving = true;
            if (vm.story.id !== null) {

                Story.update(vm.story, onSaveSuccess, onSaveError);
				console.log(vm.story);
            } else {
                Story.save(vm.story, onSaveSuccess, onSaveError);
				console.log(vm.story);
            }
			function onSaveSuccess (result) {
				$scope.$emit('mediumApp:storyUpdate', result);
				$uibModalInstance.close(result);
				$state.go('story', null, { reload: 'story' });
				//vm.isSaving = false;
        	}

			function onSaveError () {
				vm.isSaving = false;
			}
			console.log(data);
		});
        }
        vm.datePickerOpenStatus.timeCreated = false;
        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

    }
})();
