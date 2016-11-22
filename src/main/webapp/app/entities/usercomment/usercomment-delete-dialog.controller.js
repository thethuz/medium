(function() {
    'use strict';

    angular
        .module('mediumApp')
        .controller('UsercommentDeleteController',UsercommentDeleteController);

    UsercommentDeleteController.$inject = ['$uibModalInstance', 'entity', 'Usercomment'];

    function UsercommentDeleteController($uibModalInstance, entity, Usercomment) {
        var vm = this;

        vm.usercomment = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Usercomment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
