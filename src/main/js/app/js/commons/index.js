'use strict';

import angular from 'angular';

import httpClient from './httpClient';
import lodash from './lodash';

const commonsModule = angular
	.module('gallery.commons', ['gallery.constants'])
	.service('httpClient', httpClient)
	.service('_', lodash);

export default commonsModule;
