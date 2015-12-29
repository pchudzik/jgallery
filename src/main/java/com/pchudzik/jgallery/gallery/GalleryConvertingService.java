package com.pchudzik.jgallery.gallery;

import com.pchudzik.jgallery.gallery.dto.GalleryDto;
import com.pchudzik.jgallery.gallery.dto.GalleryDtoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by pawel on 15.12.15.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class GalleryConvertingService {
	private final GalleryDtoFactory galleryDtoFactory;
	private final GalleryService galleryService;

	public List<GalleryDto> listGalleries() {
		return galleryService
				.findGalleries()
				.stream()
				.map(galleryDtoFactory::toDto)
				.collect(toList());
	}
}
