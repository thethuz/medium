(function() {
    'use strict';

    angular
        .module('mediumApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('draft', {
            parent: 'entity',
            url: '/draft?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Drafts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/draft/drafts.html',
                    controller: 'DraftController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }]
            }
        })
        .state('draft-detail', {
            parent: 'entity',
            url: '/draft/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Draft'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/draft/draft-detail.html',
                    controller: 'DraftDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Draft', function($stateParams, Draft) {
                    return Draft.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'draft',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('draft-detail.edit', {
            parent: 'draft-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/draft/draft-dialog.html',
                    controller: 'DraftDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Draft', function(Draft) {
                            return Draft.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('draft.new', {
            parent: 'draft',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/draft/draft-dialog.html',
                    controller: 'DraftDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                authorID: null,
                                timeCreate: null,
                                title: null,
                                content: null,
                                category: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('draft', null, { reload: 'draft' });
                }, function() {
                    $state.go('draft');
                });
            }]
        })
        .state('draft.edit', {
            parent: 'draft',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/draft/draft-dialog.html',
                    controller: 'DraftDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Draft', function(Draft) {
                            return Draft.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('draft', null, { reload: 'draft' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('draft.delete', {
            parent: 'draft',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/draft/draft-delete-dialog.html',
                    controller: 'DraftDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Draft', function(Draft) {
                            return Draft.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('draft', null, { reload: 'draft' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
