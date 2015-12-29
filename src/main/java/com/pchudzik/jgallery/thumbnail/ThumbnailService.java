package com.pchudzik.jgallery.thumbnail;

import com.google.common.base.Stopwatch;
import com.pchudzik.jgallery.picture.Picture;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.util.Optional;

/**
 * Created by pawel on 29.11.15.
 */
@Slf4j
@Service
public class ThumbnailService {
	private static final String THUMBNAIL_FORMAT = "jpg";

	@SneakyThrows
	public Picture generateThumbnail(Picture picture, Optional<Integer> width, Optional<Integer> height) {
		final Stopwatch stopwatch = Stopwatch.createStarted();

		final BufferedImage sourceImage = ImageIO.read(picture.getInputStream());
		final BufferedImage thumbnail = scaleImage(sourceImage, width, height);
		final byte[] result = writeImage(thumbnail);

		log.debug("thumbnail ({}x{} - {} bytes) for image {} ({}x{} - {} bytes) generated in {} size compression {}",
				thumbnail.getWidth(), thumbnail.getHeight(), result.length,
				picture.getName(), sourceImage.getWidth(), sourceImage.getHeight(), picture.getSize(),
				stopwatch.stop(),
				getSizeCompressionPercentage(result.length, picture.getSize()));

		return new Picture(picture.getName() + ".min." + THUMBNAIL_FORMAT, result.length, () -> new ByteArrayInputStream(result));
	}

	@SneakyThrows
	private BufferedImage scaleImage(BufferedImage sourceImage, Optional<Integer> width, Optional<Integer> height) {
		final RatioCalculator ratioCalculator = new RatioCalculator(sourceImage.getWidth(), sourceImage.getHeight(), width, height);
		return Scalr.resize(sourceImage, Method.AUTOMATIC, Mode.FIT_EXACT, ratioCalculator.getWidth(), ratioCalculator.getHeight());
	}

	@SneakyThrows
	private byte[] writeImage(BufferedImage thumbnail) {
		final ByteArrayOutputStream result = new ByteArrayOutputStream();
		ImageIO.write(thumbnail, THUMBNAIL_FORMAT, result);
		return result.toByteArray();
	}

	private String getSizeCompressionPercentage(long thumbnailSize, long sourceSize) {
		return NumberFormat.getPercentInstance().format(1 - thumbnailSize / (double) sourceSize);
	}
}
