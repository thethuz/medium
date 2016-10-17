(function() {
    'use strict';
    angular
        .module('mediumApp')
        .factory('Story', Story);

    Story.$inject = ['$resource', 'DateUtils'];

    function Story ($resource, DateUtils) {
        var resourceUrl =  'api/stories/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.timeCreated = DateUtils.convertDateTimeFromServer(data.timeCreated);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
