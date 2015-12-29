package com.pchudzik.jgallery.gallery;

import lombok.Builder;
import lombok.Getter;

/**
 * Created by pawel on 25.12.15.
 */
@Getter
@Builder
public class PictureDto {
	private String galleryName;
	private String name;
}
