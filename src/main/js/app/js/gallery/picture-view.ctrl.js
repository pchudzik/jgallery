'use strict';

function PictureViewController(_, $state, pictures) {
	const rightArrowKeyCode = 39;
	const leftArrowKeyCode = 37;
	let vm = this;

	vm.currentBigPictureIndex = 0;
	vm.bigPicture = null;
	vm.thumbnails = [];

	initialize();

	vm.next = nextImage;
	vm.hasNext = hasNextImage;
	vm.previous = previousImage;
	vm.hasPrevious = hasPreviousImage;
	vm.isActive = isActiveThumbnail;
	vm.openBig = openBigImage;
	vm.switchImage = switchImage;

	function initialize() {
		if (!_.isEmpty($state.params.pictureName)) {
			vm.bigPicture = _.find(pictures, {name: $state.params.pictureName});
			vm.currentBigPictureIndex = _.indexOf(pictures, vm.bigPicture);
		}

		loadImages();
	}

	function switchImage($event) {
		if ($event.keyCode === rightArrowKeyCode && hasNextImage()) {
			nextImage();
		} else if ($event.keyCode === leftArrowKeyCode && hasPreviousImage()) {
			previousImage();
		}
	}

	function openBigImage(thumbnail) {
		vm.currentBigPictureIndex = _.indexOf(pictures, thumbnail);
		loadImages();
	}

	function isActiveThumbnail(thumbnail) {
		return thumbnail.name === vm.bigPicture.name;
	}

	function hasNextImage() {
		return vm.currentBigPictureIndex < pictures.length - 1;
	}

	function hasPreviousImage() {
		return vm.currentBigPictureIndex > 0;
	}

	function nextImage() {
		vm.currentBigPictureIndex += 1;
		loadImages();
	}

	function previousImage() {
		vm.currentBigPictureIndex -= 1;
		loadImages();
	}

	function loadImages() {
		const thumbnailsCount = 4;
		vm.bigPicture = pictures[vm.currentBigPictureIndex];

		let start = vm.currentBigPictureIndex - 1;
		let end = vm.currentBigPictureIndex + 3;
		if (start < 0) {
			start = 0;
			end = thumbnailsCount;
		}
		if (end > pictures.length) {
			end = pictures.length;
			start = end - thumbnailsCount;
		}

		if(thumbnailsCount >= pictures.length) {
			start = 0;
			end = pictures.length;
		}

		vm.thumbnails = _.slice(pictures, start, end);
		$state.go(
			$state.current.name,
			{galleryName: $state.params.galleryName, pictureName: vm.bigPicture.name},
			{notify: false});
	}

}

export default PictureViewController;
