'use strict';

function PictureService(httpClient, _, apiUrl) {
	return {
		findPictures: findPictures
	};

	function findPictures(galleryName) {
		return httpClient
			.get('/galleries/' + galleryName + '/pictures')
			.then(_.partial(_.map, _, addPictureUrl));
	}

	function addPictureUrl(picture) {
		let thumbnailUrl = apiUrl(
			'/pictures/' +
			encodeURIComponent(picture.galleryName) + '/' +
			encodeURIComponent(picture.name)
		);

		picture.miniUrl = thumbnailUrl + '?height=120';
		picture.smallUrl = thumbnailUrl + '?height=260';
		picture.bigUrl = thumbnailUrl + '?height=600';
		picture.hugeUrl = thumbnailUrl + '?height=1200';

		return picture;
	}
}

export default PictureService;
