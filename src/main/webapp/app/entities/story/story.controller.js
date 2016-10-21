(function() {
    'use strict';

    angular
        .module('mediumApp')
        .controller('StoryController', StoryController);

    StoryController.$inject = ['$scope', '$state', 'DataUtils', 'Story', 'ParseLinks', 'AlertService'];

    function StoryController ($scope, $state, DataUtils, Story, ParseLinks, AlertService) {
        var vm = this;

        vm.stories = [];
		vm.StoryOwner=[];
        vm.loadPage = loadPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

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
                console.log(vm.predicate);
		console.log(result);
                return result;
            }
            //Function này lấy dữ liệu từ data và nạp vào story.
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                for (var i = 0; i < data.length; i++) {
                    vm.stories.push(data[i]);
                }
                console.log(vm.stories[0]);
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
	console.log("xxxx"+Story);
    }
})();
