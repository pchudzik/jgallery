package com.pchudzik.jgallery.picture.io
import org.apache.commons.io.IOUtils
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import spock.lang.Specification

import static com.pchudzik.jgallery.picture.TestPictureFactory.samplePicture
/**
 * Created by pawel on 29.11.15.
 */
class PictureWriterTest extends Specification {
	def pictureWriter = new PictureWriter()

	def "should create InputStreamEntity from picture"() {
		given:
		def picture = samplePicture()

		when:
		def result = pictureWriter.write(picture)

		then:
		result.statusCode == HttpStatus.OK
		result.headers.getContentType() == MediaType.IMAGE_PNG
		IOUtils.toByteArray(result.body.inputStream) == IOUtils.toByteArray(samplePicture().inputStream)
	}
}
