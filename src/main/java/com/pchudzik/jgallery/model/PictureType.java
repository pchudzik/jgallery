package com.pchudzik.jgallery.model;

import java.util.function.Function;

public enum PictureType {
	mini(Picture::getMiniPicture),
	small(Picture::getSmallPicture),
	normal(Picture::getNormalPicture),
	big(Picture::getBigPicture),
	original(Picture::getOriginalPicture);

	private final Function<Picture, PictureFile> pictureFileSupplier;

	PictureType(Function<Picture, PictureFile> pictureFileSupplier) {
		this.pictureFileSupplier = pictureFileSupplier;
	}

	public PictureFile getPictureFile(Picture picture) {
		return pictureFileSupplier.apply(picture);
	}
}
