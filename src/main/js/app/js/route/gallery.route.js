'use strict';

function galleryRoute($stateProvider, states) {
	let picturesInGallery = (pictureService, $stateParams) => pictureService.findPictures($stateParams.galleryName);

	$stateProvider
		.state(states.home, {
			url: '',
			parent: states.layout,
			controller: 'GalleryListController as ctrl',
			templateUrl: 'gallery/gallery-list.ctrl.html',
			resolve: {
				galleries: galleryService => galleryService.listGalleries()
			}
		})
		.state(states.pictureList, {
			url: '/gallery/{galleryName}',
			parent: states.layout,
			templateUrl: 'gallery/gallery-pictures.ctrl.html',
			controller: 'GalleryPicturesController as ctrl',
			resolve: {
				pictures: picturesInGallery
			}
		})
		.state(states.pictureView, {
			url: '/gallery/{galleryName}/{pictureName}',
			parent: states.layout,
			templateUrl: 'gallery/picture-view.ctrl.html',
			controller: 'PictureViewController as ctrl',
			resolve: {
				pictures: picturesInGallery
			}
		});
}

export default galleryRoute;
