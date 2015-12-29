package com.pchudzik.jgallery.thumbnail

import com.pchudzik.jgallery.picture.Picture
import com.pchudzik.jgallery.picture.TestPictureFactory
import com.pchudzik.jgallery.picture.io.PictureWriter
import com.pchudzik.jgallery.thumbnail.AsyncImageProcessor.ThumbnailRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.context.request.async.DeferredResult
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

/**
 * Created by pawel on 01.12.15.
 */
class AsyncImageProcessorTest extends Specification {
	private static final def noWidth = Optional.empty()
	private static final def noHeight = Optional.empty()
	private static final def sampleImage = TestPictureFactory.samplePicture()

	def thumbnailService = Mock(ThumbnailService)
	def responseEntity = Mock(ResponseEntity)
	def pictureWriter = Mock(PictureWriter) { write(_) >> responseEntity }

	@Subject
	def imgProcessor = new AsyncImageProcessor(thumbnailService, pictureWriter)

	def "should return original image if width and height are missing"() {
		when:
		imgProcessor.generateImageThumbnail(thumbnailRequest(noWidth, noHeight))

		then:
		0 * thumbnailService.generateThumbnail(*_)
		1 * pictureWriter.write(sampleImage)
	}

	@Unroll
	def "should generate thumbnail if width is #isWidthPresent and height #isHeightPresent"() {
		given:
		def thumbnail = Mock(Picture)

		when:
		imgProcessor.generateImageThumbnail(thumbnailRequest(width, height))

		then:
		1 * thumbnailService.generateThumbnail(sampleImage, width, height) >> thumbnail
		1 * pictureWriter.write(thumbnail)

		where:
		width            | height
		Optional.of(300) | noHeight
		noWidth          | Optional.of(300)
		Optional.of(300) | Optional.of(300)

		isWidthPresent = width.map({ "present" }).orElse("not present")
		isHeightPresent = height.map({ "present" }).orElse("not present")
	}

	private static ThumbnailRequest thumbnailRequest(width, height) {
		def deferredResult = new DeferredResult()
		ThumbnailRequest.builder()
				.picture(sampleImage)
				.deferredResult(deferredResult)
				.width(width)
				.height(height)
				.build()
	}
}
