'use strict';

import angular from 'angular';

import apiUrl from './api.constants';
import states from './states.constants';

const constModule = angular
	.module('gallery.constants', [])
	.constant('apiUrl', apiUrl)
	.constant('states', states);

export default constModule;
