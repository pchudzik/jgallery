package com.pchudzik.jgallery.gallery;

import com.pchudzik.jgallery.picture.Picture;

import java.util.List;
import java.util.Optional;

/**
 * Created by pawel on 15.12.15.
 */
public interface IGallery {
	String getName();

	List<Picture> getPictures();

	Picture getCover();

	boolean hasPictures();

	Optional<Picture> findPicture(String pictureName);
}
