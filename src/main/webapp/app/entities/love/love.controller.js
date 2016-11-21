(function() {
    'use strict';

    angular
        .module('mediumApp')
        .controller('LoveController', LoveController);

    LoveController.$inject = ['$scope', '$state', 'Love'];

    function LoveController ($scope, $state, Love) {
        var vm = this;
        
        vm.loves = [];

        loadAll();

        function loadAll() {
            Love.query(function(result) {
                vm.loves = result;
            });
        }
    }
})();
