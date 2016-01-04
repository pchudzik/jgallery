package com.pchudzik.jgallery.commons.db;

import com.pchudzik.jgallery.commons.ObjectBuilder;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Created by pawel on 04.01.16.
 */
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public abstract class BaseEntity {
	@Id
	@Getter
	protected String id = UUID.randomUUID().toString();;

	@Version
	protected Long version;

	protected abstract static class BaseEntityBuilder<T extends BaseEntity, B extends BaseEntityBuilder> extends ObjectBuilder<T, B> {
		protected BaseEntityBuilder(Supplier<T> objectCreator) {
			super(objectCreator);
		}

		public B id(String id) {
			return addOperation(entity -> entity.id = id);
		}

		public B version(Long version) {
			return addOperation(entity -> entity.version = version);
		}
	}
}
