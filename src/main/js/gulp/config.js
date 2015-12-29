'use strict';

const buildDir = '../resources/static';

export default {

	browserPort: 3000,
	UIPort: 3001,

	sourceDir: './app/',
	buildDir: buildDir,

	styles: {
		src: 'app/styles/**/*.scss',
		dest: buildDir + '/css',
		prodSourcemap: false,
		sassIncludePaths: []
	},

	scripts: {
		src: 'app/js/**/*.js',
		dest: buildDir + '/js'
	},

	images: {
		src: 'app/images/**/*',
		dest: buildDir + '/images'
	},

	fonts: {
		src: ['app/fonts/**/*', 'node_modules/bootstrap-sass/assets/fonts/bootstrap/*'],
		dest: buildDir + '/fonts'
	},

	angularAppRootUrl: '/jg',
	assetExtensions: [
		'js',
		'css',
		'png',
		'jpe?g',
		'gif',
		'svg',
		'eot',
		'otf',
		'ttc',
		'ttf',
		'woff2?'
	],

	backend: {
		apiUrl: 'http://localhost:8080/api',
		apiRoute: '/api'
	},

	views: {
		index: 'app/index.html',
		src: 'app/js/**/*.html',
		dest: 'app/js'
	},

	gzip: {
		src: buildDir + '/**/*.{html,xml,json,css,js,js.map,css.map}',
		dest: buildDir + '/',
		options: {}
	},

	browserify: {
		bundleName: 'main.js',
		prodSourcemap: false
	},

	test: {
		karma: 'karma.conf.js',
		singleRung: false
	},

	init: function () {
		this.views.watch = [
			this.views.index,
			this.views.src
		];

		return this;
	}

}.init();
