(function() {
    'use strict';

    angular
        .module('mediumApp')
        .controller('LoveDialogController', LoveDialogController);

    LoveDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Love'];

    function LoveDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Love) {
        var vm = this;

        vm.love = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.love.id !== null) {
                Love.update(vm.love, onSaveSuccess, onSaveError);
            } else {
                Love.save(vm.love, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mediumApp:loveUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.time = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
