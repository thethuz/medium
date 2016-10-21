(function() {
    'use strict';

    angular
        .module('mediumApp')
        .controller('StoryDialogController', StoryDialogController);

    StoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Story'];

    function StoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Story) {
        var vm = this;

        vm.story = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;


        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
          console.log(vm.story);
          console.log(entity);
            vm.isSaving = true;
            if (vm.story.id !== null) {
                Story.update(vm.story, onSaveSuccess, onSaveError);
            } else {
                Story.save(vm.story, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mediumApp:storyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.timeCreated = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
