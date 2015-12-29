'use strict';

import angular from 'angular';
import withParams from '../../test/params';

describe('picture.service.spec.js', () => {
	let picturesService;

	let $httpBackend;
	let apiUrl;
	let _;

	beforeEach(angular.mock.module('gallery.gallery'));
	beforeEach(() => {
		angular.mock.inject((_pictureService_, _$httpBackend_, _apiUrl_, ___) => {
			picturesService = _pictureService_;

			$httpBackend = _$httpBackend_;
			apiUrl = _apiUrl_;
			_ = ___;
		});
	});

	afterEach(() => {
		$httpBackend.verifyNoOutstandingRequest();
		$httpBackend.verifyNoOutstandingExpectation();
	});

	it('should find all pictures', () => {
		let galleryName = 'gallery';
		expectPicturesGet(galleryName, []);

		picturesService.findPictures(galleryName);

		$httpBackend.flush();
	});

	withParams([
		['mini'],
		['small'],
		['big'],
		['huge']
	])
		.it(
			imageType => `should encode ${imageType} picture name`,
			(imageType) => {
				let name = 'picture name';
				let encodedName = encodeURIComponent(name);
				expectPicturesGet(name, [sampleImage(name)]);

				picturesService
					.findPictures(name)
					.then(result => {
						let pictureUrl = result[0][imageType + 'Url'];
						expect(_.startsWith(pictureUrl, apiUrl(`/pictures/${encodedName}/${encodedName}`))).toEqual(true);
					});

				$httpBackend.flush();
			});

	withParams([
		['mini', 120],
		['small', 260],
		['big', 600],
		['huge', 1200]
	])
		.it(type => `should add picture ${type} url`, (type, size) => {
			let name = 'picture';
			expectPicturesGet(name, [sampleImage(name)]);

			picturesService
				.findPictures(name)
				.then(result => {
					let pic = _.first(result);
					let url = pic[type + 'Url'];
					expect(url).toEqual(apiUrl(`/pictures/${name}/${name}?height=${size}`));
				});

			$httpBackend.flush();
		});

	function expectPicturesGet(galleryName, response) {
		$httpBackend
			.expectGET(apiUrl(`/galleries/${galleryName}/pictures`))
			.respond(200, response);
	}

	function sampleImage(name) {
		return {
			galleryName: name,
			name: name
		};
	}
});
