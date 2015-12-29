'use strict';

let istanbul = require('browserify-istanbul');
let isparta = require('isparta');

module.exports = function (config) {

	config.set({
		frameworks: ['jasmine', 'browserify'],
		preprocessors: {
			'app/js/**/*.js': ['browserify', 'coverage']
		},
		browsers: ['PhantomJS'],
		reporters: ['progress', 'coverage'],

		logLevel: config.LOG_DEBUG,
		autoWatch: true,
		singleRun: false,

		browserify: {
			debug: true,
			extensions: ['.js', '.jsx'],
			transform: [
				'babelify',
				'bulkify',
				istanbul({
					instrumenter: isparta,
					ignore: ['**/node_modules/**', 'app/js/**/*.spec.js']
				})
			]
		},

		proxies: {
			'/': 'http://localhost:9876/'
		},

		urlRoot: '/__karma__/',

		files: [
			// 3rd-party resources
			'node_modules/angular/angular.js',
			'node_modules/angular-mocks/angular-mocks.js',

			// app-specific code
			'app/js/main.js',

			// test files
			'app/js/**/*.spec.js'
		]
	});
};
