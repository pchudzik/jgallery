package com.pchudzik.jgallery.commons;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by pawel on 07.11.15.
 */
public abstract class ObjectBuilder<T, B extends ObjectBuilder> {
	private final Supplier<T> objectCreator;
	private final List<Consumer<T>> builders = new LinkedList<>();

	protected ObjectBuilder(Supplier<T> objectCreator) {
		this.objectCreator = objectCreator;
	}

	public T build() {
		final T result = objectCreator.get();
		builders.forEach(c -> c.accept(result));
		return result;
	}

	protected B addOperation(Consumer<T> operation) {
		this.builders.add(operation);
		return (B) this;
	}
}
