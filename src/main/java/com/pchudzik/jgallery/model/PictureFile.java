package com.pchudzik.jgallery.model;

import org.springframework.http.MediaType;

import java.io.InputStream;

/**
 * Created by pawel on 04.01.16.
 */
public interface PictureFile {
	MediaType getContentType();

	InputStream getPictureFile();

	long getContentLength();
}
