package com.pchudzik.jgallery.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * Created by pawel on 29.11.15.
 */
@Configuration
public class JGalleryConfiguration {
	@Value("${rootDirectory}")
	private String rootDirectory;

	@Bean
	public File rootDirectory() {
		return new File(rootDirectory);
	}
}
