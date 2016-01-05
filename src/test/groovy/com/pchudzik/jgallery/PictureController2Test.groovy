package com.pchudzik.jgallery

import com.pchudzik.jgallery.commons.test.MockMvcHelper
import com.pchudzik.jgallery.model.PictureType
import org.springframework.core.io.InputStreamResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import spock.lang.Specification
import spock.lang.Unroll

import static com.pchudzik.jgallery.commons.test.MockMvcHelper.httpHelper
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Created by pawel on 05.01.16.
 */
class PictureController2Test extends Specification {
	def pictureService = Mock(PictureService2)
	def controllerTester = MockMvcHelper.controllerTester(new PictureController2(pictureService))

	@Unroll
	def "should load picture #pictureType"() {
		given:
		def contentType = MediaType.IMAGE_JPEG
		def resultBytes = [1, 2, 3] as byte[]
		def pictureId = "pictureId"

		when:
		def request = controllerTester.perform(httpHelper().get("/pictures/$pictureId/file", [pictureType: pictureType.toString()]))

		then:
		1 * pictureService.getPictureImage(pictureId, pictureType) >> ResponseEntity
				.ok()
				.contentLength(resultBytes.length)
				.contentType(contentType)
				.body(new InputStreamResource(new ByteArrayInputStream(resultBytes)))
		request
				.andExpect(status().isOk())
				.andExpect(content().bytes(resultBytes))
				.andExpect(content().contentType(contentType))

		where:
		pictureType << PictureType.values()
	}
}
