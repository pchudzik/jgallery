package com.pchudzik.jgallery.gallery

import org.apache.commons.io.FileUtils
import spock.lang.Specification

/**
 * Created by pawel on 20.12.15.
 */
class GalleryServiceTest extends Specification {
	File rootDirectory
	GalleryService galleryService

	def setup() {
		rootDirectory = File.createTempDir("jgallery", ".tmp-dir")
		galleryService = new GalleryService(rootDirectory)
	}

	def cleanup() {
		FileUtils.deleteDirectory(rootDirectory)
	}

	def "should return only directories when searching for galleries"() {
		given:
		def directory1 = "directory1"
		def directory2 = "directory2"

		and:
		addFile("file1")
		addGallery(directory1)
		addGallery(directory2)

		expect:
		galleryService.findGalleries()*.name == [directory1, directory2]
	}

	def "should exclude hidden files and directories when searching for galleries"() {
		given:
		addGallery(".hidden")
		addFile(".hidden-file")

		expect:
		galleryService.findGalleries().isEmpty()
	}

	def "should return empty list if gallery root location does not exists"() {
		given:
		def nonExistingFile = new File("non existing directory sjdh827")

		expect:
		[] == new GalleryService(nonExistingFile).findGalleries()
	}

	def "returned galleries should be sorted by name case insensitive"() {
		given:
		def directory1 = "directory1"
		def directory2 = "DIRectory2"
		def directory3 = "dirECtory3"

		and:
		[directory1, directory2, directory3].each { addGallery(it) }

		expect:
		[directory1, directory2, directory3] == galleryService.findGalleries()*.name
	}

	def "should skip folders without images inside"() {
		given:
		def notGallery = "not gallery"
		def gallery = "gallery"

		and:
		addFolder(notGallery)
		addGallery(gallery)

		expect:
		[gallery] == galleryService.findGalleries()*.name
	}

	private File addFolder(String name) {
		def gallery = new File(rootDirectory, name)
		gallery.mkdir()
		return gallery
	}

	private void addGallery(String name) {
		def gallery = addFolder(name)
		addFile(gallery, "image.png")
	}

	private void addFile(File parent, String name) {
		def file = new File(parent, name)
		FileUtils.write(file, "content")
	}

	private void addFile(String name) {
		addFile(rootDirectory, name)
	}
}
