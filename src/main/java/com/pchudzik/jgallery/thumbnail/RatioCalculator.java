package com.pchudzik.jgallery.thumbnail;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * Created by pawel on 01.12.15.
 */
@RequiredArgsConstructor
class RatioCalculator {
	private final int srcImgWidth;
	private final int srcImgHeight;
	private final Optional<Integer> thumbnailWidth;
	private final Optional<Integer> thumbnailHeight;

	public int getWidth() {
		if (nothingToScale()) {
			return srcImgWidth;
		}

		return thumbnailWidth.orElseGet(this::calculateWidth);
	}

	public int getHeight() {
		if (nothingToScale()) {
			return srcImgHeight;
		}

		return thumbnailHeight.orElseGet(this::calculateHeight);
	}

	private int calculateWidth() {
		int requestedHeight = thumbnailHeight.get();
		double ratio = srcImgWidth / (double) srcImgHeight;
		return (int) (requestedHeight * ratio);
	}

	private int calculateHeight() {
		final int requestedWidth = thumbnailWidth.get();
		double ratio = srcImgHeight / (double) srcImgWidth;
		return (int) (requestedWidth * ratio);
	}

	private boolean nothingToScale() {
		return !thumbnailWidth.isPresent() && !thumbnailHeight.isPresent();
	}
}
