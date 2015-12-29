'use strict';

import angular from 'angular';
import 'angular-ui-router';
import 'angular-bootstrap-npm';

import './templates';
import './gallery';
import './route';
import './constants';
import './commons';

angular
	.module('gallery', [
		'templates',
		'ui.bootstrap',
		'gallery.route',
		'gallery.constants',
		'gallery.commons',
		'gallery.gallery'
	]);

angular.bootstrap(document, ['gallery']);
