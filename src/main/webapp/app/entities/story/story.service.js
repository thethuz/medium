(function() {
    'use strict';
    angular
        .module('mediumApp')
        .factory('Story', Story);

    Story.$inject = ['$resource', 'DateUtils'];
    //
    function Story ($resource, DateUtils) {
        var resourceUrl =  'api/stories/:id';
		      console.log("story.service da dc goi");
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                      //console.log(data);
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
