package com.pchudzik.jgallery.picture;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.function.Supplier;

import static org.jooq.lambda.Unchecked.supplier;

/**
 * Created by pawel on 28.11.15.
 */
public class Picture {
	private final String name;
	private final long size;
	private final Supplier<InputStream> inputStreamSupplier;

	public Picture(String name, long size, Supplier<InputStream> inputStreamSupplier) {
		this.name = name;
		this.size = size;
		this.inputStreamSupplier = inputStreamSupplier;
	}

	public Picture(File file) {
		this(
				file.getName(),
				file.length(),
				supplier(() -> new FileInputStream(file)));
	}

	public String getName() {
		return name;
	}

	public long getSize() {
		return size;
	}

	public String getContentType() {
		return URLConnection.guessContentTypeFromName(getName());
	}

	@JsonIgnore
	public InputStream getInputStream() {
		return inputStreamSupplier.get();
	}
}
