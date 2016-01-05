package com.pchudzik.jgallery.model.repository

import com.pchudzik.jgallery.commons.db.RepositorySpecification
import com.pchudzik.jgallery.model.DatabasePictureFile
import com.pchudzik.jgallery.model.Gallery
import com.pchudzik.jgallery.model.Picture
import com.pchudzik.jgallery.model.PictureFile
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDate

import static org.apache.commons.io.IOUtils.toByteArray

/**
 * Created by pawel on 05.01.16.
 */
class PictureRepositoryTest extends RepositorySpecification {
	private final LocalDate now = LocalDate.now()

	@Autowired
	PictureRepository pictureRepository

	def "should save picture"() {
		given:
		def thumbnailBytes = toByteArray(anyPictureFile().pictureFile)
		def picToSave = anyPicture()

		when:
		pictureRepository.save(picToSave)
		flush()

		then:
		def savedPicture = pictureRepository.load(picToSave.id)
		savedPicture != null
		savedPicture.id == picToSave.id
		savedPicture.creationDate == now
		toByteArray(savedPicture.miniPicture.pictureFile) == thumbnailBytes
		toByteArray(savedPicture.smallPicture.pictureFile) == thumbnailBytes
		toByteArray(savedPicture.normalPicture.pictureFile) == thumbnailBytes
		toByteArray(savedPicture.bigPicture.pictureFile) == thumbnailBytes
	}

	private Picture anyPicture() {
		def gallery = anyGallery()
		em.persist(gallery)

		Picture.builder()
				.name("picture name")
				.creationDate(now)
				.picturePath("path/to/picture")
				.miniPicture(anyPictureFile())
				.smallPicture(anyPictureFile())
				.normalPicture(anyPictureFile())
				.bigPicture(anyPictureFile())
				.originalPicture(anyPictureFile())
				.gallery(gallery)
				.build()
	}

	Gallery anyGallery() {
		Gallery.builder()
				.creationDate(now)
				.name("gallery name")
				.galleryLocation("path/to/gallery")
				.build()
	}

	PictureFile anyPictureFile() {
		new DatabasePictureFile([1, 2, 3] as byte[], "png")
	}
}
