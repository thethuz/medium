(function() {
    'use strict';

    angular
        .module('mediumApp')
        .controller('StoryDialogController', StoryDialogController);

    StoryDialogController.$inject = ['$timeout', '$scope', '$stateParams','$resource', '$uibModalInstance', 'DataUtils', 'entity', 'Story'];

    function StoryDialogController ($timeout, $scope, $stateParams, $resource, $uibModalInstance, DataUtils, entity, Story) {
        var vm = this;
		//console.log(loadOwner().onSuccess());
        vm.story = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
		     // vm.author={}
          /**
          *
          var User = $resource('/user/:userId', {userId:'@id'});
User.get({userId:123}, function(user, getResponseHeaders){
  user.abc = true;
  user.$save(function(user, putResponseHeaders) {
    //user => saved user object
    //putResponseHeaders => $http header getter
  });
});

You can also access the raw $http promise via the $promise property on the object returned

var User = $resource('/user/:userId', {userId:'@id'});
User.get({userId:123})
    .$promise.then(function(user) {
      $scope.user = user;
    });
    *
    **/
		//vm.author=loadOwner();


		//console.log(StoryOwner.onSuccess);
		//loadOwner();
		//console.log(vm.author);
		//console.log(Object.prototype.toString.apply(vm.author));
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
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
      vm.story.timeCreated=Date();
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
				vm.isSaving = false;
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
