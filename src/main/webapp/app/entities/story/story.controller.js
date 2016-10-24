(function() {
    'use strict';

    angular
        .module('mediumApp')
        .controller('StoryController', StoryController);
	//////console.log("goi StoryController.inject");
    StoryController.$inject = ['$scope', '$state', 'DataUtils', 'Story', 'ParseLinks', 'AlertService','StoryOwner'];
	////console.log("goi StoryController");
	////console.log(StoryController);
    function StoryController ($scope, $state, DataUtils, Story, ParseLinks, AlertService, StoryOwner) {
        var vm = this;
	////console.log("story controller");
        vm.stories = [];
	      vm.storyOwner=[];
        vm.loadPage = loadPage;
        vm.page = 0;
        vm.pageO = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();
        loadOwner();

        function loadAll () {
            Story.query({
                page: vm.page,
                size: 20,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                ////console.log(vm.predicate);
				////console.log(result);
                return result;
            }
            //Function này lấy dữ liệu từ data và nạp vào story.
            function onSuccess(data, headers) {
                ////console.log("xxx");
                ////console.log(vm);
                ////console.log("vmlinks before parse");
                ////console.log(vm.links);
                ////console.log(ParseLinks);
                vm.links = ParseLinks.parse(headers('link'));
                ////console.log("vmlink after parse");
                ////console.log(vm.links);
                vm.totalItems = headers('X-Total-Count');
                for (var i = 0; i < data.length; i++) {
                    vm.stories.push(data[i]);
                }
                /*  vm.storyOwner=[];
                  vm.loadPage = loadPage;
                  vm.page = 0;
                  vm.pageO = 0;
                  vm.links = {
                      last: 0
                  };
                  vm.predicate = 'id';
                  vm.reset = reset;
                  vm.reverse = true;
                  vm.openFile = DataUtils.openFile;
                  vm.byteSize = DataUtils.byteSize;*/
                  ////console.log("VMpredicate");
                  ////console.log(vm.stories[0]);
                  ////console.log(vm.predicate);
                  ////console.log(vm.page);
                  //////console.log(vm.page0);
                  ////console.log(vm.links);
                  ////console.log(vm.reset);
                  ////console.log(vm.reverse);
                  ////console.log(vm.openFile);
                  ////console.log(vm.byteSize);
                  ////console.log(StoryController.$inject);
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
        /**
        * Load User Owner
        **/
        function loadOwner(){
          StoryOwner.query({
            page: vm.page,
            size: 20,
            sort: sort()
          }, onSuccess, onError);
          function sort() {
              var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
              if (vm.predicate !== 'id') {
                  result.push('id');
              }
              //////console.log(vm.predicate);
              //////console.log(result);
              return result;
          }
          function onSuccess(data, headers){
            //vm.links = ParseLinks.parse(headers('link'));
            //vm.totalItems = headers('X-Total-Count');
            //for (var i = 0;i< data.length; i++){
            //  vm.storyOwner.push(data[i]);
           // }
			  ////console.log(data);
            ////console.log(vm.storyOwner);
          }
          function onError(error) {
              AlertService.error(error.data.message);
          }
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
