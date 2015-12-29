package com.pchudzik.jgallery.gallery.dto;

import com.pchudzik.jgallery.gallery.IGallery;
import org.springframework.stereotype.Component;

/**
 * Created by pawel on 15.12.15.
 */
@Component
public class GalleryDtoFactory {
	public GalleryDto toDto(IGallery gallery) {
		final GalleryDto galleryDto = new GalleryDto();

		galleryDto.setGalleryName(gallery.getName());

		return galleryDto;
	}
}
