(function() {
    'use strict';
    angular
        .module('mediumApp')
        .factory('Usercomment', Usercomment);

    Usercomment.$inject = ['$resource', 'DateUtils'];

    function Usercomment ($resource, DateUtils) {
        var resourceUrl =  'api/usercomments/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.timeCommented = DateUtils.convertDateTimeFromServer(data.timeCommented);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
