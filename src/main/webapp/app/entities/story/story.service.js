(function() {
    'use strict';
    angular
        .module('mediumApp')
        .factory('Story', Story);
	StoryOwner.$inject=['$resource','DateUtils'];
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
                      console.log(data);
                        data = angular.fromJson(data);
                        data.timeCreated = DateUtils.convertDateTimeFromServer(data.timeCreated);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
    function StoryOwner ($resource, DateUtils){
    var resourceUrl=	'api/users/:login';
	console.log("storyOwner1");
    return $resource(resourceUrl, {}, {
      'query':{ method: 'GET', isArray:true},
      'get':{
        method		:'GET',
        transformResponse:function (data) {
          if (data) {
            data = angular.fromJson(data);
            data.timeCreated = DateUtils.convertDataTimeFromServer(data.timeCreates);
            console.log("this is data");
            console.log(data);
          }
        return data;
        }
      },
      'update':{method:'PUT' }
    });
    }
})();
(function() {
    'use strict';
    angular
        .module('mediumApp')
        .factory('StoryOwner', StoryOwner);

    //Story.$inject = ['$resource', 'DateUtils'];
    StoryOwner.$inject=['$resource','DateUtils'];

    function StoryOwner ($resource, DateUtils){
	var retturn;
    var resourceUrl=	'api/account';
	console.log("StoryOwne2");
    return $resource(resourceUrl, {}, {
      'query':{ method: 'GET', isArray:false},
      'get':{
        method		:'GET',
        transformResponse:function (data) {
          if (data) {
			         retturn=data;
            data = angular.fromJson(data);
            data.timeCreated = DateUtils.convertDataTimeFromServer(data.timeCreates);
          }
			console.log("this is data");
        return data;
        }
      },
      'update':{method:'PUT' }
    });
    }
  }
)();
//()();
