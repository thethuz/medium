(function() {
    'use strict';
    angular
        .module('mediumApp')
        .factory('Draft', Draft);

    Draft.$inject = ['$resource', 'DateUtils'];

    function Draft ($resource, DateUtils) {
        var resourceUrl =  'api/drafts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.timeCreate = DateUtils.convertDateTimeFromServer(data.timeCreate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
