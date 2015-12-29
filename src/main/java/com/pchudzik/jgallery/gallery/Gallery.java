package com.pchudzik.jgallery.gallery;

import com.google.common.collect.Iterables;
import com.pchudzik.jgallery.picture.Picture;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.io.FileUtils.listFiles;
import static org.apache.commons.io.IOCase.INSENSITIVE;
import static org.apache.commons.io.filefilter.FileFilterUtils.falseFileFilter;

/**
 * Created by pawel on 28.11.15.
 */
@RequiredArgsConstructor
class Gallery implements IGallery {
	private static final List<String> imageFilesExtensions = asList(".jpg", ".jpeg", ".png");
	private static final IOFileFilter imagesOnly = FileFilterUtils.or(
			imageFilesExtensions.stream()
					.map(fileSuffix -> FileFilterUtils.suffixFileFilter(fileSuffix, INSENSITIVE))
					.toArray(size -> new IOFileFilter[size]));

	private final File galleryLocation;

	@Override
	public String getName() {
		return galleryLocation.getName();
	}

	@Override
	public List<Picture> getPictures() {
		return listFiles(galleryLocation, imagesOnly, falseFileFilter())
				.stream()
				.map(Picture::new)
				.collect(toList());
	}

	@Override
	public Picture getCover() {
		return Iterables.getFirst(getPictures(), null);
	}

	@Override
	public boolean hasPictures() {
		return !getPictures().isEmpty();
	}

	@Override
	public Optional<Picture> findPicture(String pictureName) {
		return Optional.of(new Picture(new File(galleryLocation, pictureName)));
	}
}
