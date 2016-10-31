(function() {
    'use strict';

    angular
        .module('mediumApp')
        .controller('StoryController', StoryController);
	//////console.log("goi StoryController.inject");
    StoryController.$inject = ['$scope', '$state', 'DataUtils','Principal', 'LoginService', 'Story', 'ParseLinks', 'AlertService','$resource'];
	////console.log("goi StoryController");
	////console.log(StoryController);
    function StoryController ($scope, $state, DataUtils, Principal, LoginService, Story, ParseLinks, AlertService, $resource) {

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
        //svar vm.user.login;
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        loadAll();
		getAccount();
/**
*
*
*	CREATE A USER UNKNOWN
*
*
**/
        function loadAll () {
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
				console.log(Principal);
				console.log(vm.account);
				console.log(vm.isAuthenticated);
            });

			//console.log(vm.isAuthenticated());
        }
        function reset () {
            vm.page = 0;
            vm.stories = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
	//////console.log(Story);
		//////console.log(StoryOwner);
    }
})();
