package com.pchudzik.jgallery.model;

import com.pchudzik.jgallery.commons.db.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;

import javax.persistence.Entity;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by pawel on 04.01.16.
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DatabasePictureFile extends BaseEntity implements PictureFile {
	private byte[] thumbnail;
	private String fileType;

	@Override
	public MediaType getContentType() {
		return new MediaType("image", fileType);
	}

	@Override
	public InputStream getPictureFile() {
		return new ByteArrayInputStream(thumbnail);
	}

	@Override
	public long getContentLength() {
		return thumbnail.length;
	}
}
