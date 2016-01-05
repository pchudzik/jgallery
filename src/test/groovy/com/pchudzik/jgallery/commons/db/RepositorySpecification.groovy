package com.pchudzik.jgallery.commons.db

import com.pchudzik.jgallery.context.JgalleryApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import javax.persistence.EntityManager

/**
 * Created by pawel on 05.01.16.
 */
@Transactional
@WebAppConfiguration
@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = JgalleryApplication.class)
abstract class RepositorySpecification extends Specification {
	@Autowired
	EntityManager em

	def flush() {
		em.flush()
		em.clear()
	}
}
