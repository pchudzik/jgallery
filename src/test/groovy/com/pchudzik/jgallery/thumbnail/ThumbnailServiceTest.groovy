package com.pchudzik.jgallery.thumbnail

import spock.lang.Specification

import static com.pchudzik.jgallery.picture.TestPictureFactory.bufferedImage
import static com.pchudzik.jgallery.picture.TestPictureFactory.samplePicture

/**
 * Created by pawel on 01.12.15.
 */
class ThumbnailServiceTest extends Specification {
	def thumbnailService = new ThumbnailService()

	def "should generate thumbnail of image"() {
		given:
		def width = 50
		def height = 50

		when:
		def thumbnail = thumbnailService.generateThumbnail(samplePicture(), Optional.of(width), Optional.of(height))

		then:
		def thumbnailBufferedImage = bufferedImage(thumbnail)
		thumbnailBufferedImage.width == width
		thumbnailBufferedImage.height == height
	}
}
