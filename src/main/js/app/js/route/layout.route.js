'use strict';

function layoutRoute($stateProvider, states) {
	$stateProvider
		.state('root', {
			url: '/jg',
			abstract: true,
			templateUrl: 'layout/layout.html'
		})
		.state(states.layout, {
			abstract: true,
			parent: 'root',
			views: {
				header: {
					templateUrl: 'layout/header.html'
				},
				body: {
					template: '<div ui-view=""></div>'
				}
			}
		});
}

export default layoutRoute;
