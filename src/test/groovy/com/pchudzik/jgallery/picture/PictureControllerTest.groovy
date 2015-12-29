package com.pchudzik.jgallery.picture
import com.pchudzik.jgallery.gallery.GalleryService
import com.pchudzik.jgallery.gallery.IGallery
import com.pchudzik.jgallery.picture.io.PictureWriter
import com.pchudzik.jgallery.thumbnail.AsyncImageProcessor
import com.pchudzik.jgallery.thumbnail.AsyncImageProcessor.ThumbnailRequest
import spock.lang.Specification
import spock.lang.Subject

import static com.pchudzik.jgallery.commons.test.MockMvcHelper.controllerTester
import static com.pchudzik.jgallery.commons.test.MockMvcHelper.httpHelper
import static org.springframework.http.MediaType.IMAGE_JPEG
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
/**
 * Created by pawel on 29.12.15.
 */
class PictureControllerTest extends Specification {
	def galleryService = Mock(GalleryService)
	def imageProcessor = Mock(AsyncImageProcessor)

	@Subject
	def controllerTester = controllerTester()
			.controllers(new PictureController(galleryService, imageProcessor))
			.build()

	def "should generate picture thumbnail"() {
		given:
		def pictureWriter = new PictureWriter()
		byte[] responseBytes = [1, 2, 3]

		def picture = Mock(Picture) {
			getName() >> "picture"
			getInputStream() >> new ByteArrayInputStream(responseBytes)
			getContentType() >> IMAGE_JPEG_VALUE
			getSize() >> responseBytes.length
		}
		def gallery = Mock(IGallery) {
			getName() >> "gallery"
			findPicture(picture.name) >> Optional.of(picture)
		}
		galleryService.findGalleryByName(gallery.name) >> gallery
		imageProcessor.generateImageThumbnail(_) >> { ThumbnailRequest request ->
			request.getDeferredResult().setResult(pictureWriter.write(picture))
		}

		when:
		def request = asyncDispatch(controllerTester.perform(httpHelper().get("/pictures/${gallery.name}/${picture.name}"))
				.andExpect(request().asyncStarted())
				.andReturn())

		then:
		controllerTester.perform(request)
				.andExpect(content().bytes(responseBytes))
				.andExpect(content().contentType(IMAGE_JPEG))
				.andExpect(status().isOk())
	}
}
