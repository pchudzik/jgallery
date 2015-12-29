package com.pchudzik.jgallery.gallery;

import com.pchudzik.jgallery.picture.Picture;
import org.springframework.stereotype.Service;

/**
 * Created by pawel on 25.12.15.
 */
@Service
class PictureDtoConverter {
	public PictureDto toDto(IGallery gallery, Picture picture) {
		return PictureDto.builder()
				.name(picture.getName())
				.galleryName(gallery.getName())
				.build();
	}
}
