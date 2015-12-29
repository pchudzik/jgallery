package com.pchudzik.jgallery.gallery

import com.pchudzik.jgallery.commons.test.MockMvcHelper
import com.pchudzik.jgallery.gallery.dto.GalleryDto
import com.pchudzik.jgallery.picture.Picture
import com.pchudzik.jgallery.picture.io.PictureWriter
import com.pchudzik.jgallery.thumbnail.AsyncImageProcessor
import com.pchudzik.jgallery.thumbnail.AsyncImageProcessor.ThumbnailRequest
import groovy.json.JsonOutput
import spock.lang.Specification

import static com.pchudzik.jgallery.commons.test.MockMvcHelper.httpHelper
import static com.pchudzik.jgallery.commons.test.MockMvcHelper.jsonResponse
import static com.pchudzik.jgallery.gallery.TestGalleryFactory.createGallery
import static org.springframework.http.MediaType.IMAGE_JPEG
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

/**
 * Created by pawel on 29.11.15.
 */
class GalleryControllerTest extends Specification {
	def galleryService = Mock(GalleryService)
	def galleryConvertingService = Mock(GalleryConvertingService)
	def imageProcessor = Mock(AsyncImageProcessor)

	def controllerTester = MockMvcHelper.controllerTester(new GalleryController(galleryService, new PictureDtoConverter(), galleryConvertingService, imageProcessor))

	def "should list all galleries defined in root folder"() {
		given:
		galleryConvertingService.listGalleries() >> [new GalleryDto(galleryName: "first"), new GalleryDto(galleryName: "second")]

		when:
		def request = controllerTester.perform(httpHelper().get("/galleries"))

		then:
		request
				.andExpect(status().isOk())
				.andExpect(jsonResponse().isEqual([[galleryName: "first"], [galleryName: "second"]]))
	}

	def "should generate gallery cover"() {
		given:
		def pictureWriter = new PictureWriter()
		byte[] responseBytes = [1, 2, 3]

		def picture = Mock(Picture) {
			getInputStream() >> new ByteArrayInputStream(responseBytes)
			getContentType() >> IMAGE_JPEG_VALUE
			getSize() >> responseBytes.length
		}
		galleryService.findGalleryByName("gallery") >> createGallery("gallery", [picture])
		imageProcessor.generateImageThumbnail(_) >> { ThumbnailRequest request ->
			request.getDeferredResult().setResult(pictureWriter.write(picture))
		}

		when:
		def request = asyncDispatch(controllerTester.perform(httpHelper().get('/galleries/gallery/cover'))
				.andExpect(request().asyncStarted())
				.andReturn())

		then:
		controllerTester.perform(request)
				.andExpect(content().bytes(responseBytes))
				.andExpect(content().contentType(IMAGE_JPEG))
				.andExpect(status().isOk())
	}

	def "should list all pictures in gallery"() {
		given:
		def pic1 = new Picture("pic1.png", 10, { new ByteArrayInputStream(new byte[0]) })
		def pic2 = new Picture("pic2.png", 10, { new ByteArrayInputStream(new byte[0]) })
		def pic3 = new Picture("pic3.png", 10, { new ByteArrayInputStream(new byte[0]) })
		def gallery = Mock(Gallery) {
			getName() >> "gallery"
			getPictures() >> [pic1, pic2, pic3,]
		}
		galleryService.findGalleryByName(gallery.name) >> gallery

		when:
		def request = controllerTester.perform(httpHelper().get("/galleries/${gallery.name}/pictures"))

		then:
		request
				.andExpect(status().isOk())
				.andExpect(jsonResponse().isEqual(JsonOutput.toJson([
				[name: pic1.name, galleryName: gallery.name],
				[name: pic2.name, galleryName: gallery.name],
				[name: pic3.name, galleryName: gallery.name]
		])))
	}
}
