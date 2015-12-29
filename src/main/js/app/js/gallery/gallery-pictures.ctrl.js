'use strict';

function GalleryPicturesController(pictures, $state, states) {
	let vm = this;

	vm.openImage = openImage;
	vm.pictures = pictures;

	function openImage(picture) {
		$state.go(
			states.pictureView,
			{
				galleryName: picture.galleryName,
				pictureName: picture.name
			});
	}
}

export default GalleryPicturesController;
