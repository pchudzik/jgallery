package com.pchudzik.jgallery.gallery;

import com.pchudzik.jgallery.gallery.dto.GalleryDto;
import com.pchudzik.jgallery.thumbnail.AsyncImageProcessor;
import com.pchudzik.jgallery.thumbnail.AsyncImageProcessor.ThumbnailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.Optional;

import static com.pchudzik.jgallery.JGalleryConstatns.API_URL;
import static java.util.stream.Collectors.toList;

/**
 * Created by pawel on 29.11.15.
 */
@RestController
@RequestMapping(API_URL + "/galleries")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class GalleryController {
	private final GalleryService galleryService;
	private final PictureDtoConverter pictureDtoConverter;
	private final GalleryConvertingService convertingService;
	private final AsyncImageProcessor asyncImageProcessor;

	@RequestMapping(method = RequestMethod.GET)
	public List<GalleryDto> listGalleries() {
		return convertingService.listGalleries();
	}

	@RequestMapping("/{galleryName}/cover")
	public DeferredResult<ResponseEntity<Resource>> galleryCover(
			@PathVariable String galleryName,
			@RequestParam(required = false) Optional<Integer> width,
			@RequestParam(required = false) Optional<Integer> height) {
		final DeferredResult<ResponseEntity<Resource>> result = new DeferredResult<>();

		final IGallery gallery = galleryService.findGalleryByName(galleryName);

		asyncImageProcessor.generateImageThumbnail(ThumbnailRequest.builder()
				.width(width)
				.height(height)
				.picture(gallery.getCover())
				.deferredResult(result)
				.build());

		return result;
	}

	@RequestMapping("/{galleryName}/pictures")
	public List<PictureDto> picturesInGallery(@PathVariable String galleryName) {
		final IGallery gallery = galleryService.findGalleryByName(galleryName);
		return gallery
				.getPictures()
				.stream()
				.map(pic -> pictureDtoConverter.toDto(gallery, pic))
				.collect(toList());
	}
}
