package com.pchudzik.jgallery;

import com.pchudzik.jgallery.model.PictureType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by pawel on 05.01.16.
 */
@Controller
@RequestMapping(JGalleryConstatns.API_URL + "/pictures")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PictureController2 {
	private final PictureService2 pictureService;

	@RequestMapping("/{id}/file")
	public ResponseEntity<Resource> gePictureFile(@PathVariable String id, @RequestParam(defaultValue = "normal") PictureType pictureType) {
		return pictureService.getPictureImage(id, pictureType);
	}
}
