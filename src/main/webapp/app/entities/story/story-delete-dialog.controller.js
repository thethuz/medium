(function() {
    'use strict';

    angular
        .module('mediumApp')
        .controller('StoryDeleteController',StoryDeleteController);

    StoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'Story'];

    function StoryDeleteController($uibModalInstance, entity, Story) {
        var vm = this;

        vm.story = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Story.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
