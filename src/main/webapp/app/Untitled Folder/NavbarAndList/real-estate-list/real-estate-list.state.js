(function() {
    'use strict';

    angular
        .module('giasanApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('list', {
            parent: 'real-estate',
            url: '/list',
            data: {
                authorities: []
            },
            views: {
                'real-estate-content': {
                    templateUrl: 'app/layouts/content/real-estate/real-estate-list/real-estate-list.html',
                    controller: 'RealEstateListController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                deps: ['$ocLazyLoad',
                    function ($ocLazyLoad) {
                        return $ocLazyLoad.load('app/layouts/content/real-estate/real-estate-list/real-estate-list.controller.js')
                    }
                ],
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
