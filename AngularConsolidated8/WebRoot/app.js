(function () {
    var app = angular.module('main', ['ngTable', 'ngResource', 'ui.bootstrap', 'ng-sortable']);

    //data table
    app.controller('TableController', function ($scope, $filter, $resource, ngTableParams) {
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
    app.controller('dragNdrop', ['$scope', function ($scope) {
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

    //onStart actions
    app.run(function ($rootScope) {
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

})();