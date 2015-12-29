package com.pchudzik.jgallery.picture.io;

import com.pchudzik.jgallery.picture.Picture;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

/**
 * Created by pawel on 29.11.15.
 */
@Service
public class PictureWriter {
	public ResponseEntity<Resource> write(Picture picture) {
		return ResponseEntity.ok()
				.contentLength(picture.getSize())
				.contentType(MediaType.parseMediaType(picture.getContentType()))
				.body(new RepeatableInputStream(picture.getSize(), picture::getInputStream));
	}

	@RequiredArgsConstructor
	private static class RepeatableInputStream extends AbstractResource {
		final long size;
		final Supplier<InputStream> inputStreamSupplier;

		@Override
		public String getDescription() {
			return "in memory resource";
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return inputStreamSupplier.get();
		}

		@Override
		public boolean isReadable() {
			return true;
		}

		@Override
		public boolean isOpen() {
			return true;
		}
	}
}
