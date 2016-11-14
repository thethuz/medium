(function() {
    'use strict';

    angular
        .module('mediumApp')
        .controller('StoryDetailController', StoryDetailController);

    StoryDetailController.$inject = ['$state','$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Story','$resource','Usercomment'];

    function StoryDetailController($state,$scope, $rootScope, $stateParams, previousState, DataUtils, entity, Story, $resource, Usercomment) {
		/*function save () {
          var contentLength=vm.story.content.length;
          var titleLength=vm.story.title.length;
          if ((contentLength<5*titleLength)||(titleLength<1)||(contentLength<300));
      		var User = $resource('api/account',{},{'charge':{method:'GET'}});
      		//console.log(User);
      		$scope.user=User.get({activated: true});
      		$scope.user.$promise.then(function(data){
      			/*
      			
      			vm.isSaving = true;
            if (vm.comment.id !== null) {
                Usercomment.update(vm.comment, onSaveSuccess, onSaveError);
				console.log(vm.comment);
            } else {
                Usercomment.save(vm.comment, onSaveSuccess, onSaveError);
				console.log(vm.comment);
            }
			function onSaveSuccess (result) {
				$scope.$emit('mediumApp:usercommentUpdate', result);
				//$uibModalInstance.close(result);
				$state.go('comment', null, { reload: 'comment' });
				//vm.isSaving = false;
        	}
			function onSaveError () {vm.isSaving = false;			}
			console.log(data);
		});
        }*/
		
        var vm = this;
		vm.save=save;
    vm.isSaving=
		//if(vm.page) console.log("vm.page");else console.log("undefined");
		vm.page=page();
		//vm.page=page();
		console.log(vm.page);
		function page(){
			if(vm.page) {
				console.log(vm.page);
				return vm.page++;
			}
			if(vm.page==0) {
				console.log(vm.page);
				return vm.page++;
			}
			else {
				console.log(vm.page);
				return vm.page=0;
			}
		}
        vm.story = entity;
        //console.log(vm.story);
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.commment = null;
		function save(){
			var contentLength=vm.comment.commentContent.length;
			if (contentLength>0);
			var User = $resource('api/account',{},{'charge':{method:'GET'}});
      		//console.log(User);
      		$scope.user=User.get({activated: true});
      		$scope.user.$promise.then(function(data){
				vm.comment.userCommentName=data.firstName+' '+data.lastName;
				vm.comment.userCommentID=data.id;
				vm.comment.storyID=vm.story.id;
				console.log(vm.comment);
				console.log(data);
				vm.isSaving = true;
            if (vm.comment.id !== null) {
                Usercomment.update(vm.comment, onSaveSuccess, onSaveError);
				console.log(vm.comment);
            } else {
                Usercomment.save(vm.comment, onSaveSuccess, onSaveError);
				console.log(vm.comment);
            }
			function onSaveSuccess (result) {
				$scope.$emit('mediumApp:usercommentUpdate', result);
				//$uibModalInstance.close(result);
				//$state.go('story/'+vm.story.id, {}, { reload: true });
				$state.go('story-detail', null, { reload: true });
				//vm.isSaving = false;
        	}
			function onSaveError () {vm.isSaving = false;			}
			console.log(data);
			}
		);}
		var comment= $resource('api/usercomments/story/'+vm.story.id,{});
		console.log('xxx');
		comment.query({activated:true}).$promise.then(function(data){
			//Chuyển data từ dạng mảng về dạng object
			 vm.comments=data;
			 console.log(vm.comments);
		});
		//console.log(vm.comments);
		//loadComment();
		function loadComment(){
			var usercomAPI='api/story/comment'+vm.story.id;
            var comment= $resource(usercomAPI,{});
            comment.query({activated:true}).$promise.then(function(data){
            	vm.story=data;
				console.log('Success');
            });
		}
        var unsubscribe = $rootScope.$on('mediumApp:storyUpdate', function(event, result) {
            //console.log('story detail');
			vm.story = result;

        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
