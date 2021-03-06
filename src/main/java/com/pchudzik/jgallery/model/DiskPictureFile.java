package com.pchudzik.jgallery.model;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;

/**
 * Created by pawel on 04.01.16.
 */
@RequiredArgsConstructor
public class DiskPictureFile implements PictureFile {
	final File file;

	@Override
	public MediaType getContentType() {
		return MediaType.parseMediaType("image/" + URLConnection.guessContentTypeFromName(file.getName()));
	}

	@Override
	@SneakyThrows
	public InputStream getPictureFile() {
		return new FileInputStream(file);
	}

	@Override
	public long getContentLength() {
		return file.length();
	}
}
