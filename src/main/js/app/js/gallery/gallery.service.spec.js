'use strict';

import angular from 'angular';

describe('gallery.service.spec.js', () => {
	const galleryCoverWidth = '?width=360';
	let $httpBackend;
	let apiUrl;

	let galleryService;

	beforeEach(() => {
		angular.mock.module('gallery.gallery');
		angular.mock.inject((_$httpBackend_, _galleryService_, _apiUrl_) => {
			$httpBackend = _$httpBackend_;
			galleryService = _galleryService_;
			apiUrl = _apiUrl_;
		});
	});

	afterEach(() => {
		$httpBackend.verifyNoOutstandingExpectation();
		$httpBackend.verifyNoOutstandingRequest();
	});

	it('should query galleries', () => {
		expectGalleriesListRequest([]);

		galleryService.listGalleries();

		$httpBackend.flush();
	});

	it('should append coverUrl to gallery', () => {
		let galleryName = 'galleryName';
		expectGalleriesListRequest([sampleGallery(galleryName)]);

		galleryService
			.listGalleries()
			.then(result => {
				expect(result.length).toEqual(1);
				expect(result[0].coverUrl).toEqual(apiUrl(`/galleries/${galleryName}/cover${galleryCoverWidth}`));
			});

		$httpBackend.flush();
	});

	it('should encode gallery cover url', () => {
		let galleryName = 'gallery name';
		let encodedGalleryName = encodeURIComponent(galleryName);
		expectGalleriesListRequest([sampleGallery(galleryName)]);

		galleryService
			.listGalleries()
			.then(result => {
				expect(result[0].coverUrl).toEqual(apiUrl(`/galleries/${encodedGalleryName}/cover${galleryCoverWidth}`));
			});

		$httpBackend.flush();
	});

	function sampleGallery(name) {
		return {
			galleryName: name
		};
	}

	function expectGalleriesListRequest(response) {
		$httpBackend
			.expectGET(apiUrl('/galleries'))
			.respond(200, response);
	}
});
