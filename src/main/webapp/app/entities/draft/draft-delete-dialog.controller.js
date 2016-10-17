(function() {
    'use strict';

    angular
        .module('mediumApp')
        .controller('DraftDeleteController',DraftDeleteController);

    DraftDeleteController.$inject = ['$uibModalInstance', 'entity', 'Draft'];

    function DraftDeleteController($uibModalInstance, entity, Draft) {
        var vm = this;

        vm.draft = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Draft.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
