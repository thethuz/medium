(function() {
    'use strict';

    angular
        .module('mediumApp')
        .controller('UserInfoDeleteController',UserInfoDeleteController);

    UserInfoDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserInfo'];

    function UserInfoDeleteController($uibModalInstance, entity, UserInfo) {
        var vm = this;

        vm.userInfo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserInfo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
