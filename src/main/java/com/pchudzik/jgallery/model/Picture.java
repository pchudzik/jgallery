package com.pchudzik.jgallery.model;

import com.pchudzik.jgallery.commons.db.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by pawel on 04.01.16.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Picture extends BaseEntity {
	String picturePath;

	String name;

	String pictureType;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	Gallery gallery;

	LocalDate creationDate;

	@OneToOne(optional = false, fetch = FetchType.LAZY)
	PictureFile miniPicture;

	@OneToOne(optional = false, fetch = FetchType.LAZY)
	PictureFile smallPicture;

	@OneToOne(optional = false, fetch = FetchType.LAZY)
	PictureFile normalPicture;

	@OneToOne(optional = false, fetch = FetchType.LAZY)
	PictureFile bigPicture;

	@Transient
	PictureFile originalPicture;

	public PictureFile getOriginalPicture() {
		return Optional
				.ofNullable(originalPicture)
				.orElseGet(() -> new DiskPictureFile(new File(getGallery().getGalleryLocation(), picturePath)));
	}

	public static PictureBuilder builder() {
		return new PictureBuilder();
	}

	public static class PictureBuilder extends BaseEntityBuilder<Picture, PictureBuilder> {
		protected PictureBuilder() {
			super(Picture::new);
		}

		public PictureBuilder picturePath(String picturePath) {
			return addOperation(p -> p.picturePath = picturePath);
		}

		public PictureBuilder name(String name) {
			return addOperation(p -> p.name = name);
		}

		public PictureBuilder pictureType(String type) {
			return addOperation(p -> p.pictureType = type);
		}

		public PictureBuilder gallery(Gallery gallery) {
			return addOperation(p -> p.gallery = gallery);
		}

		public PictureBuilder creationDate(LocalDate creationDate) {
			return addOperation(p -> p.creationDate = creationDate);
		}

		public PictureBuilder miniPicture(PictureFile pictureFile) {
			return addOperation(p -> p.miniPicture = pictureFile);
		}

		public PictureBuilder smallPicture(PictureFile pictureFile) {
			return addOperation(p -> p.smallPicture = pictureFile);
		}

		public PictureBuilder normalPicture(PictureFile pictureFile) {
			return addOperation(p -> p.normalPicture = pictureFile);
		}

		public PictureBuilder bigPicture(PictureFile pictureFile) {
			return addOperation(p -> p.bigPicture = pictureFile);
		}

		public PictureBuilder originalPicture(PictureFile pictureFile) {
			return addOperation(p -> p.originalPicture = pictureFile);
		}
	}
}
