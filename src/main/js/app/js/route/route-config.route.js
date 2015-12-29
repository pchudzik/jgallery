'use strict';

function routerConfig($urlRouterProvider, $locationProvider) {
	$urlRouterProvider.otherwise('/jg');
	$locationProvider.html5Mode(true);
}

export default routerConfig;
