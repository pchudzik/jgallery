'use strict';

import _ from 'lodash';
import PictureViewController from './picture-view.ctrl';

describe('picture-view.ctrl.spec.js', () => {
	const firstPicture = 0;
	const secondPicture = 1;
	const thumbnailCount = 6;

	let stateMock;

	beforeEach(() => {
		stateMock = {
			current: {name: 'current state name'},
			params: {},
			go: jasmine.createSpy('stateMock.go')
		};
	});

	it('should initialize controller', () => {
		let pics = pictures(10);

		//when
		let ctrl = createController(pics);

		//then
		expect(ctrl.currentBigPictureIndex).toEqual(firstPicture);
		expect(ctrl.bigPicture).toEqual(pics[firstPicture]);
		expect(ctrl.thumbnails).toEqual(_.slice(pics, firstPicture, firstPicture + thumbnailCount));
	});

	it('should go to next image', () => {
		let pics = pictures(10);
		let ctrl = createController(pics);

		//when
		ctrl.next();

		//then
		expect(ctrl.currentBigPictureIndex).toEqual(secondPicture);
		expect(ctrl.bigPicture).toEqual(pics[secondPicture]);
	});

	it('should go to previous image', () => {
		let pics = pictures(10);
		let ctrl = createController(pics);

		ctrl.next();

		//when
		ctrl.previous();

		//then
		expect(ctrl.currentBigPictureIndex).toEqual(firstPicture);
		expect(ctrl.bigPicture).toEqual(pics[firstPicture]);
	});

	it('should detect if has next image', () => {
		let pics = pictures(2);

		//when
		let ctrl = createController(pics);

		//then
		expect(ctrl.hasNext()).toEqual(true);

		//when
		ctrl.next();

		//then
		expect(ctrl.hasNext()).toEqual(false);
	});

	it('should detect if has previous image', () => {
		let pics = pictures(2);

		//when
		let ctrl = createController(pics);

		//then
		expect(ctrl.hasPrevious()).toEqual(false);

		//when
		ctrl.next();

		//then
		expect(ctrl.hasPrevious()).toEqual(true);
	});

	it('should detect if current thumbnail is active', () => {
		let pics = pictures(2);

		//when
		let ctrl = createController(pics);

		//then
		expect(ctrl.isActive(pics[firstPicture])).toEqual(true);
		expect(ctrl.isActive(pics[secondPicture])).toEqual(false);
	});

	it('should load thumbnail as big picture', ()=> {
		let pics = pictures(2);
		let ctrl = createController(pics);

		//when
		ctrl.openBig(pics[secondPicture]);

		//then
		expect(ctrl.currentBigPictureIndex).toEqual(secondPicture);
		expect(ctrl.bigPicture).toEqual(pics[secondPicture]);
	});

	it('should load initial picture on ctrl load', () => {
		let pics = pictures(3);
		stateMock.params = {
			pictureName: pics[secondPicture].name
		};

		//when
		let ctrl = createController(pics);

		//then
		expect(ctrl.bigPicture).toEqual(pics[secondPicture]);
		expect(ctrl.currentBigPictureIndex).toEqual(secondPicture);
	});

	it('should update state url after picture change', ()=> {
		let pics = pictures(2);
		stateMock.params = {
			pictureName: pics[firstPicture].name,
			galleryName: 'gallery'
		};
		let ctrl = createController(pics);

		//when
		ctrl.next();

		//then
		expect(stateMock.go).toHaveBeenCalledWith(
			stateMock.current.name,
			{
				galleryName: stateMock.params.galleryName,
				pictureName: stateMock.params.pictureName
			},
			{
				notify: false
			});
	});

	describe('keyboard support', () => {
		const rightArrowEvent = {keyCode: 39};
		const leftArrowEvent = {keyCode: 37};

		it('should switch to next picture if has more and arrow right', ()=> {
			let pics = pictures(2);
			let ctrl = createController(pics);

			//when
			ctrl.switchImage(rightArrowEvent);

			//then
			expect(ctrl.bigPicture).toEqual(pics[secondPicture]);
		});

		it('should switch to previous picture if has previous and arrow left', () => {
			let pics = pictures(2);
			let ctrl = createController(pics);
			ctrl.openBig(pics[secondPicture]);

			//when
			ctrl.switchImage(leftArrowEvent);

			//then
			expect(ctrl.bigPicture).toEqual(pics[firstPicture]);
		});

		it('should do nothing if has no previous image and arrow left', () => {
			let pics = pictures(1);
			let ctrl = createController(pics);

			//when
			ctrl.switchImage(leftArrowEvent);

			//then
			expect(ctrl.bigPicture).toEqual(pics[firstPicture]);
		});

		it('should do nothing if has no next image and arrow right', () => {
			let pics = pictures(1);
			let ctrl = createController(pics);

			//when
			ctrl.switchImage(rightArrowEvent);

			//then
			expect(ctrl.bigPicture).toEqual(pics[firstPicture]);
		});
	});

	describe('thumbnails view', () => {
		const middlePicture = 10;
		const beforeLastPicture = 18;
		const lastPicture = 19;

		let pics;
		let ctrl;

		beforeEach(() => {
			pics = pictures(20);
			ctrl = createController(pics);
		});

		it('should display 6 thumbnails', () => {
			//when
			ctrl.openBig(pics[middlePicture]);

			//then
			expect(ctrl.thumbnails).toEqual(_.slice(pics, middlePicture - 1, middlePicture + 5));
		});

		it('should display first 6 images if current big picture is first', () => {
			//when
			ctrl.openBig(pics[firstPicture]);

			//then
			expect(ctrl.thumbnails).toEqual(_.slice(pics, firstPicture, firstPicture + thumbnailCount));
		});

		it('should display first 6 images if current big picture is second', () => {
			//when
			ctrl.openBig(pics[secondPicture]);

			//then
			expect(ctrl.thumbnails).toEqual(_.slice(pics, firstPicture, firstPicture + thumbnailCount));
		});

		it('should display last 6 images if current picture is before last', () => {
			//when
			ctrl.openBig(pics[beforeLastPicture]);

			//then
			expect(ctrl.thumbnails).toEqual(_.slice(pics, pics.length - thumbnailCount, pics.length));
		});

		it('should display last 6 images if current picture is last', () => {
			//when
			ctrl.openBig(pics[lastPicture]);

			//then
			expect(ctrl.thumbnails).toEqual(_.slice(pics, pics.length - thumbnailCount, pics.length));
		});

		it('should display all thumbnails if images count is smaller then thumbnail count', () => {
			let pics = pictures(thumbnailCount - 1);

			//when
			let ctrl = createController(pics);

			//then
			expect(ctrl.thumbnails).toEqual(pics);
		});
	});

	function createController(pictures) {
		return new PictureViewController(_, stateMock, pictures);
	}

	function pictures(count) {
		return _.range(0, count).map(index => {
			return {name: 'picture_' + index};
		});
	}
});
