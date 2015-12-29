package com.pchudzik.jgallery.context;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.pchudzik.jgallery")
public class JgalleryApplication {

	public static void main(String[] args) {
		System.setProperty("rootDirectory", "/home/pawel/Pictures");
		SpringApplication.run(JgalleryApplication.class, args);
	}
}

