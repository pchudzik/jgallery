'use strict';

import config from '../config';
import url from 'url';
import browserSync from 'browser-sync';
import gulp from 'gulp';
import proxy from 'proxy-middleware';

function assetsMiddleware(req, res, next) {
	const DEFAULT_FILE = 'index.html';
	const ASSET_EXTENSION_REGEX = new RegExp(`\\b(?!\\?)\\.(${config.assetExtensions.join('|')})\\b(?!\\.)`, 'i');
	const fileHref = url.parse(req.url).href;

	if (!ASSET_EXTENSION_REGEX.test(fileHref) || fileHref.startsWith(config.angularAppRootUrl)) {
		req.url = '/' + DEFAULT_FILE;
	}

	return next();
}

const proxyOptions = url.parse(config.backend.apiUrl);
proxyOptions.route = config.backend.apiRoute;

gulp.task('browserSync', function () {
	browserSync.init({
		open: false,
		server: {
			baseDir: config.buildDir,
			middleware: [proxy(proxyOptions), assetsMiddleware]
		},
		port: config.browserPort,
		ui: {
			port: config.UIPort
		}
	});
});
