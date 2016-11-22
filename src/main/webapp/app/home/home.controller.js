(function() {
    'use strict';

    angular
        .module('mediumApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function HomeController ($scope, Principal, LoginService, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
			console.log(vm.account);
			console.log(vm.isAuthenticated);
			console.log(vm.login);
        });

        getAccount();


        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
				console.log(Principal);
				console.log(vm.account);
				console.log(vm.isAuthenticated);
            });

			//console.log(vm.isAuthenticated());
        }
        function register () {
            $state.go('register');
        }
    }
})();
