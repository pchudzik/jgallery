package com.pchudzik.jgallery.model;

import com.pchudzik.jgallery.commons.db.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;

/**
 * Created by pawel on 04.01.16.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Gallery extends BaseEntity {
	private String galleryLocation;
	private String name;
	private LocalDate creationDate;

	@OneToOne
	private Picture cover;

	public static GalleryBuilder builder() {
		return new GalleryBuilder();
	}

	public static class GalleryBuilder extends BaseEntityBuilder<Gallery, GalleryBuilder> {
		protected GalleryBuilder() {
			super(Gallery::new);
		}

		public GalleryBuilder name(String name) {
			return addOperation(g -> g.name = name);
		}

		public GalleryBuilder galleryLocation(String location) {
			return addOperation(g -> g.galleryLocation = location);
		}

		public GalleryBuilder creationDate(LocalDate creationDate) {
			return addOperation(g -> g.creationDate = creationDate);
		}

		public GalleryBuilder cover(Picture cover) {
			return addOperation(g -> g.cover = cover);
		}
	}
}
