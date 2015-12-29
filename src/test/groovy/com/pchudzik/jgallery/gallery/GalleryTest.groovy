package com.pchudzik.jgallery.gallery

import org.apache.commons.io.FileUtils
import spock.lang.Specification

import java.nio.file.Files

import static com.google.common.collect.Sets.newHashSet
/**
 * Created by pawel on 28.11.15.
 */
class GalleryTest extends Specification {
	def images = ["img.jpg", "img.jpeg", "img.png"]
	def notImages = ["file.txt", "img.doc", "img.pdf"]

	def "should list only image files located in directory"() {
		given:
		def allFiles = images + notImages
		def imagesUpperCase = images.collect { it.toUpperCase() }
		def allFilesUpperCase = allFiles.collect { it.toUpperCase() }
		def tmpDir = Files.createTempDirectory("jgallery").toFile()
		def nestedFolder = new File(tmpDir, "nested folder")
		nestedFolder.mkdirs()
		(allFiles + allFilesUpperCase).forEach {
			createFile(tmpDir, it)
			createFile(tmpDir, it.toUpperCase())
		}

		def gallery = new Gallery(tmpDir)

		when:
		def resultImages = gallery.getPictures()

		then:
		newHashSet(resultImages*.name) == newHashSet(images + imagesUpperCase)

		cleanup:
		FileUtils.deleteQuietly(tmpDir)
	}

	void createFile(File parent, String name) {
		FileUtils.writeByteArrayToFile(new File(parent, name), "file content".bytes)
	}

	File fileWithName(String name) {
		Mock(File) { getName() >> name }
	}
}
