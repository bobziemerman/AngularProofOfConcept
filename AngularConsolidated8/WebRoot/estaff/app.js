angular.module('main', ['ngTable', 'ngResource', 'ui.bootstrap', 'ng-sortable', 'ngDragDrop','ngRoute']);

//data table
angular.module('main').controller('TableController', function ($scope, $filter, $resource, ngTableParams) {
    var PAGE_SIZE = 10;

    $scope.tableParams = new ngTableParams({
        page: 1, // show first page
        count: PAGE_SIZE, // count per page
        sorting: {
            A: 'asc'     	// initial sorting
        }
    }, {
        counts: [], //removes variable length page selector
        getData: function ($defer, params) {
            //build url to query the db
            var URI = "http://localhost:8080/AngularConsolidated8/rest/database?"; //base url
            URI += "start=" + ((params.page() - 1) * PAGE_SIZE) + "&"; //start location
            URI += "sort=" + Object.keys(params.sorting()) + "&"; //sort field
            URI += "direction=" + params.sorting()[Object.keys(params.sorting())] + ""; //sort direction

            //create DB query connection
            var db = $resource(URI,
                    {
                        callback: "JSON_CALLBACK"
                    },
            {
                getRows: {
                    method: "JSONP",
                    params: {
                        start: (params.page() - 1) * PAGE_SIZE,
                        sort: Object.keys(params.sorting()),
                        direction: params.sorting()[Object.keys(params.sorting())]
                    }
                }
            });

            //wait for data to return
            db.getRows().$promise.then(function (rows) {
                params.total(rows["total"]); //set total
                $defer.resolve(rows["data"]); //set data
            });
        }
    });
});

//drag and drop
angular.module('main').controller('dragNdrop', ['$scope', function ($scope) {
        $scope.items = [
            {name: 'E', title: "This is E's alt text"},
            {name: 'F', title: "This is F's alt text"},
            {name: 'H', title: "This is H's alt text"},
            {name: 'I', title: "This is I's alt text"},
            {name: 'A', title: "This is A's alt text"},
            {name: 'D', title: "This is D's alt text"},
            {name: 'G', title: "This is G's alt text"},
            {name: 'B', title: "This is B's alt text"},
            {name: 'C', title: "This is C's alt text"},
        ];

    }]);
angular.module('main').controller('oneCtrl', function($scope, $timeout) {
      $scope.list1 = [];
      $scope.list2 = [
        { 'title': 'a', 'drag': true },
        { 'title': 'b', 'drag': true },
        { 'title': 'c', 'drag': true },
        { 'title': 'd', 'drag': true },
        { 'title': 'e', 'drag': true },
        { 'title': 'f', 'drag': true },
        { 'title': 'g', 'drag': true },
        { 'title': 'h', 'drag': true },
        { 'title': 'i', 'drag': true }
      ];
      angular.forEach($scope.list2, function(val, key) {
              $scope.list1.push($scope.list2);
      });

      $scope.startCallback = function(event, ui, title) {
        console.log('You started draggin: ' + title.title);
        $scope.draggedTitle = title.title;
      };

      $scope.stopCallback = function(event, ui) {
        console.log('Why did you stop draggin me?');
      };

      $scope.dragCallback = function(event, ui) {
        console.log('hey, look I`m flying');
      };

      $scope.dropCallback = function(event, ui) {
        console.log('hey, you dumped me :-(' , $scope.draggedTitle);
      };

      $scope.overCallback = function(event, ui) {
        console.log('Look, I`m over you');
      };

      $scope.outCallback = function(event, ui) {
        console.log('I`m not, hehe');
      };
    });

//onStart actions
angular.module('main').run(function ($rootScope) {
    //make the dragNdrop boxes square and dynamic
    var square = function () {
        $('.list-group-item').css({
            'height': $('.list-group-item').width() + 'px'
        });
    };
    square();
    //resize the squares when the window size changes
    window.onresize = square;
});