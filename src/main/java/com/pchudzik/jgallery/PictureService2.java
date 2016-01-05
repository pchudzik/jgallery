package com.pchudzik.jgallery;

import com.pchudzik.jgallery.model.Picture;
import com.pchudzik.jgallery.model.PictureFile;
import com.pchudzik.jgallery.model.PictureType;
import com.pchudzik.jgallery.model.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by pawel on 05.01.16.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PictureService2 {
	private final PictureRepository pictureRepository;

	@Transactional(readOnly = true)
	public ResponseEntity<Resource> getPictureImage(String id, PictureType pictureType) {
		final Picture picture = pictureRepository.load(id);
		final PictureFile pictureFile = pictureType.getPictureFile(picture);

		return ResponseEntity.ok()
				.contentType(pictureFile.getContentType())
				.contentLength(pictureFile.getContentLength())
				.body(new InputStreamResource(pictureFile.getPictureFile()));
	}
}
