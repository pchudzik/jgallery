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

		picture.miniUrl = thumbnailUrl + '?height=220';
		picture.smallUrl = thumbnailUrl + '?height=460';
		picture.bigUrl = thumbnailUrl + '?height=800';
		picture.hugeUrl = thumbnailUrl + '?height=1200';

		return picture;
	}
}

export default PictureService;
