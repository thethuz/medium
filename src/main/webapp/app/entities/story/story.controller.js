(function() {
    'use strict';

    angular
        .module('mediumApp')
        .controller('StoryController', StoryController);

    StoryController.$inject = ['$scope', '$state', '$timeout', '$stateParams' ,'DataUtils','Principal', 'LoginService', 'Story', 'ParseLinks', 'AlertService','$resource'];

    function StoryController ($scope, $state,  $timeout, $stateParams, DataUtils, Principal, LoginService, Story, ParseLinks, AlertService, $resource) {
		//$state, $timeout, $scope, $stateParams, $resource, $uibModalInstance, DataUtils, entity, Story
		function toObject(arr) {
		  var rv = {};
		  for (var i = 0; i < arr.length; ++i)
			rv[i] = arr[i];
		  return rv;
		}
		var vm = this;
		vm.stories = [];
        vm.loadPage = loadPage;
        vm.page = 0;
        vm.pageO = 0;
        vm.links = {
            last: 0
        };
		vm.account = null;
        vm.isAuthenticated = null;
        //vm.login = LoginService.open;
        //vm.register = register;
        $scope.$on('authenticationSuccess', function() {
          getAccount();
    			console.log(vm.account);
    			console.log(vm.isAuthenticated);
    			console.log(vm.login);
        });

        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        loadAll();
        getAccount();
		console.log(vm.page);
          function loadAll ()
		      {

            console.log("load");
            Story.query({
                  page: vm.page,
                  size: 20,
                  sort: sort()
              }, onSuccess, onError);
				function sort() {
				  var result = [vm.predicate + ',' + (vm.reverse ? 'desc' : 'asc')];
				  if (vm.predicate !== 'id') {
					  result.push('id');
				  }
							console.log("sort");
				  return result;
				}
				function onSuccess(data, headers) {
						 vm.links = ParseLinks.parse(headers('link'));
							  vm.totalItems = headers('X-Total-Count');
					  for (var i = 0; i < data.length; i++) {
						vm.stories.push(data[i]);
				}
					console.log(vm.stories);
					var StorybyAuthor = $resource('api/stories/author/admin',{});
					$scope.storyByAuthor=StorybyAuthor.query({activated: true});
					$scope.storyByAuthor.$promise.then(function(data){
					  //console.log(toObject(data));
					});
					console.log("onSuccess");
				}

              function onError(error) {
                  AlertService.error(error.data.message);
                  console.log(error.data.message);
              }

          }
		function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
        				//console.log(Principal);
        				//console.log(vm.account);
        				//console.log(vm.isAuthenticated);
            });
        }
		      vm.clear = clear;
        vm.datePickerOpenStatus = {};
        //vm.openCalendar = openCalendar;
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
				//$uibModalInstance.close(result);
				$state.go('story', null, { reload: 'story' });
				//vm.isSaving = false;
        	}

			function onSaveError () {
				vm.isSaving = false;
			}
			console.log(data);
		});
        }
        //vm.datePickerOpenStatus.timeCreated = false;

        function reset () {
            vm.page = 0;
            vm.stories = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
			console.log(page);
            loadAll();
        }

    }
})();
