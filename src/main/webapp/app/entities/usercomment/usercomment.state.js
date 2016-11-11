(function() {
    'use strict';

    angular
        .module('mediumApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('usercomment', {
            parent: 'entity',
            url: '/usercomment',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Usercomments'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/usercomment/usercomments.html',
                    controller: 'UsercommentController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('usercomment-detail', {
            parent: 'entity',
            url: '/usercomment/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Usercomment'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/usercomment/usercomment-detail.html',
                    controller: 'UsercommentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Usercomment', function($stateParams, Usercomment) {
                    return Usercomment.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'usercomment',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('usercomment-detail.edit', {
            parent: 'usercomment-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/usercomment/usercomment-dialog.html',
                    controller: 'UsercommentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Usercomment', function(Usercomment) {
                            return Usercomment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('usercomment.new', {
            parent: 'usercomment',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/usercomment/usercomment-dialog.html',
                    controller: 'UsercommentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                storyID: null,
                                commentContent: null,
                                userCommentID: null,
                                userCommentName: null,
                                timeCommented: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('usercomment', null, { reload: 'usercomment' });
                }, function() {
                    $state.go('usercomment');
                });
            }]
        })
        .state('usercomment.edit', {
            parent: 'usercomment',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/usercomment/usercomment-dialog.html',
                    controller: 'UsercommentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Usercomment', function(Usercomment) {
                            return Usercomment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('usercomment', null, { reload: 'usercomment' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('usercomment.delete', {
            parent: 'usercomment',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/usercomment/usercomment-delete-dialog.html',
                    controller: 'UsercommentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Usercomment', function(Usercomment) {
                            return Usercomment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('usercomment', null, { reload: 'usercomment' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
