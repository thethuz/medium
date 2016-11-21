(function() {
    'use strict';

    angular
        .module('mediumApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('love', {
            parent: 'entity',
            url: '/love',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Loves'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/love/loves.html',
                    controller: 'LoveController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('love-detail', {
            parent: 'entity',
            url: '/love/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Love'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/love/love-detail.html',
                    controller: 'LoveDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Love', function($stateParams, Love) {
                    return Love.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'love',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('love-detail.edit', {
            parent: 'love-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/love/love-dialog.html',
                    controller: 'LoveDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Love', function(Love) {
                            return Love.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('love.new', {
            parent: 'love',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/love/love-dialog.html',
                    controller: 'LoveDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                storyId: null,
                                userId: null,
                                userName: null,
                                time: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('love', null, { reload: 'love' });
                }, function() {
                    $state.go('love');
                });
            }]
        })
        .state('love.edit', {
            parent: 'love',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/love/love-dialog.html',
                    controller: 'LoveDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Love', function(Love) {
                            return Love.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('love', null, { reload: 'love' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('love.delete', {
            parent: 'love',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/love/love-delete-dialog.html',
                    controller: 'LoveDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Love', function(Love) {
                            return Love.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('love', null, { reload: 'love' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
