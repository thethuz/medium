(function() {
    'use strict';

    angular
        .module('mediumApp')
        .controller('UsercommentController', UsercommentController);

    UsercommentController.$inject = ['$scope', '$state', 'DataUtils', 'Usercomment'];

    function UsercommentController ($scope, $state, DataUtils, Usercomment) {
        var vm = this;
        
        vm.usercomments = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Usercomment.query(function(result) {
                vm.usercomments = result;
            });
        }
    }
})();
