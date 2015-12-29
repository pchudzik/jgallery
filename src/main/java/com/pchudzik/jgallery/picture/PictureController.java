package com.pchudzik.jgallery.picture;

import com.pchudzik.jgallery.gallery.GalleryService;
import com.pchudzik.jgallery.thumbnail.AsyncImageProcessor;
import com.pchudzik.jgallery.thumbnail.AsyncImageProcessor.ThumbnailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.pchudzik.jgallery.JGalleryConstatns.API_URL;

/**
 * Created by pawel on 29.11.15.
 */
@RestController
@RequestMapping(API_URL + "/pictures")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class PictureController {
	private final GalleryService galleryService;
	private final AsyncImageProcessor imageProcessor;

	@RequestMapping(value = "/{galleryName}/{pictureName:.+}", method = RequestMethod.GET)
	public DeferredResult<ResponseEntity<Resource>> picture(
			@PathVariable String galleryName,
			@PathVariable String pictureName,
			@RequestParam(required = false) Optional<Integer> width,
			@RequestParam(required = false) Optional<Integer> height) {

		final DeferredResult<ResponseEntity<Resource>> thumbnailDeferred = new DeferredResult<>();

		imageProcessor.generateImageThumbnail(ThumbnailRequest.builder()
				.deferredResult(thumbnailDeferred)
				.picture(galleryService
						.findGalleryByName(galleryName)
						.findPicture(pictureName)
						.orElseThrow(() -> new NoSuchElementException("No Picture " + pictureName + " in gallery " + galleryName)))
				.width(width)
				.height(height)
				.build());

		return thumbnailDeferred;
	}
}
