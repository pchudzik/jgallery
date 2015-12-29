package com.pchudzik.jgallery.picture

import org.apache.commons.io.FileUtils
import org.springframework.core.io.ClassPathResource

import javax.imageio.ImageIO
import java.awt.image.BufferedImage
/**
 * Created by pawel on 29.11.15.
 */
class TestPictureFactory {
	static final def imageBytes = FileUtils.readFileToByteArray(new ClassPathResource("/sample-image.png").file)

	static def Picture samplePicture() {
		new Picture("sample-image.png", imageBytes.length, { new ByteArrayInputStream(imageBytes) })
	}

	static BufferedImage bufferedImage(Picture picture) {
		ImageIO.read(picture.inputStream)
	}
}
