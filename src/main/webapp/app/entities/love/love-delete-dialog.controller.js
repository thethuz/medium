(function() {
    'use strict';

    angular
        .module('mediumApp')
        .controller('LoveDeleteController',LoveDeleteController);

    LoveDeleteController.$inject = ['$uibModalInstance', 'entity', 'Love'];

    function LoveDeleteController($uibModalInstance, entity, Love) {
        var vm = this;

        vm.love = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Love.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
