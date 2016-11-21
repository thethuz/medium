(function() {
    'use strict';
    angular
        .module('mediumApp')
        .factory('Love', Love);

    Love.$inject = ['$resource', 'DateUtils'];

    function Love ($resource, DateUtils) {
        var resourceUrl =  'api/loves/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.time = DateUtils.convertDateTimeFromServer(data.time);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
