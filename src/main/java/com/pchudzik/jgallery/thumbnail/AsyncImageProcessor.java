package com.pchudzik.jgallery.thumbnail;

import com.pchudzik.jgallery.picture.Picture;
import com.pchudzik.jgallery.picture.io.PictureWriter;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Optional;

/**
 * Created by pawel on 01.12.15.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AsyncImageProcessor {
	private final ThumbnailService thumbnailService;
	private final PictureWriter pictureWriter;

	@Async
	public void generateImageThumbnail(ThumbnailRequest thumbnailRequest) {
		final Picture thumbnailPicture = thumbnailRequest.width.isPresent() || thumbnailRequest.height.isPresent()
				? thumbnailService.generateThumbnail(thumbnailRequest.picture, thumbnailRequest.width, thumbnailRequest.height)
				: thumbnailRequest.picture;

		thumbnailRequest.deferredResult.setResult(pictureWriter.write(thumbnailPicture));
	}

	@Getter
	@Builder
	public static class ThumbnailRequest {
		private final Picture picture;
		private final Optional<Integer> width;
		private final Optional<Integer> height;
		private final DeferredResult<ResponseEntity<Resource>> deferredResult;
	}
}
