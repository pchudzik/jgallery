'use strict';

function galleryService(apiUrl, httpClient, _) {
	return {
		listGalleries: findGalleries
	};

	function findGalleries() {
		return httpClient
			.get('/galleries')
			.then(_.partial(_.map, _, assignCoverToGallery));
	}

	function assignCoverToGallery(gallery) {
		return _.assign(
			gallery,
			{coverUrl: apiUrl('/galleries/' + encodeURIComponent(gallery.galleryName) + '/cover?width=560')});
	}
}

export default galleryService;
