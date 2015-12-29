'use strict';

import angular from 'angular';
import 'angular-lazy-image';

import GalleryService from './gallery.service';
import PictureService from './picture.service';

import GalleryListController from './gallery-list.ctrl';
import GalleryPicturesController from './gallery-pictures.ctrl';
import PictureViewController from './picture-view.ctrl';

const galleryModule = angular
	.module('gallery.gallery', [
		'afkl.lazyImage',
		'ui.router',
		'gallery.commons'
	])

	.service('galleryService', GalleryService)
	.service('pictureService', PictureService)

	.controller('GalleryListController', GalleryListController)
	.controller('GalleryPicturesController', GalleryPicturesController)
	.controller('PictureViewController', PictureViewController);

export default galleryModule;
