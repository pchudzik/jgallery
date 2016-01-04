package com.pchudzik.jgallery.gallery;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileFilter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by pawel on 29.11.15.
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GalleryService {
	private static final Comparator<? super File> fileNameComparator = (f1, f2) -> {
		final String f1Filename = f1.getName().toLowerCase();
		final String f2Filename = f2.getName().toLowerCase();
		return f1Filename.compareTo(f2Filename);
	};

	private final File rootDirectory;

	public List<Gallery> findGalleries() {
		return findFilesInRootDirectory()
				.stream()
				.sorted(fileNameComparator)
				.map(Gallery::new)
				.filter(g -> g.hasPictures())
				.collect(Collectors.toList());
	}

	public IGallery findGalleryByName(String galleryName) {
		return new Gallery(new File(rootDirectory, galleryName));
	}

	private List<File> findFilesInRootDirectory() {
		log.info("Listing galleries in directory {}", rootDirectory.getAbsolutePath());
		final FileFilter filters = FileFilterUtils.and(
				FileFilterUtils.directoryFileFilter(),
				HiddenFileFilter.VISIBLE);
		final File[] result = rootDirectory.listFiles(filters);
		return Optional
				.ofNullable(result)
				.map(Arrays::asList)
				.orElse(Collections.emptyList());
	}
}
