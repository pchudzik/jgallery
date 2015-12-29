'use strict';

import angular from 'angular';

import layoutRoute from './layout.route';
import galleryRoute from './gallery.route';
import routeConfig from './route-config.route';

const routeModule = angular.module('gallery.route', [
	'ui.router',
	'gallery.constants'
]);

routeModule
	.config(layoutRoute)
	.config(galleryRoute)
	.config(routeConfig);

export default routeModule;
