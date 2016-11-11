(function() {
    'use strict';

    angular
        .module('mediumApp')
        .controller('UsercommentDialogController', UsercommentDialogController);

    UsercommentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Usercomment'];

    function UsercommentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Usercomment) {
        var vm = this;

        vm.usercomment = entity;
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
            vm.isSaving = true;
            if (vm.usercomment.id !== null) {
                Usercomment.update(vm.usercomment, onSaveSuccess, onSaveError);
            } else {
                Usercomment.save(vm.usercomment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mediumApp:usercommentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.timeCommented = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
