package com.pchudzik.jgallery.gallery

import com.pchudzik.jgallery.picture.Picture
import lombok.RequiredArgsConstructor

/**
 * Created by pawel on 15.12.15.
 */
class TestGalleryFactory {
	static IGallery createGallery(String name, Picture cover, List<Picture> pictures) {
		new GalleryStub(
				name: name,
				cover: cover,
				pictures: pictures)
	}

	static IGallery createGallery(String name, List<Picture> pictures) {
		createGallery(name, pictures.first(), pictures)
	}

	static IGallery createGallery(String name) {
		createGallery(name, [])
	}

	@RequiredArgsConstructor
	private static class GalleryStub implements IGallery {
		String name
		Picture cover
		List<Picture> pictures

		@Override
		boolean hasPictures() {
			!pictures.isEmpty()
		}

		@Override
		Optional<Picture> findPicture(String pictureName) {
			Optional.empty()
		}
	}

}
