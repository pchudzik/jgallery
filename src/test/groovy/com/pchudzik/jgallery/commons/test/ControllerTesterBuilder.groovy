package com.pchudzik.jgallery.commons.test

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.http.converter.ByteArrayHttpMessageConverter
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.ResourceHttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter
import org.springframework.http.converter.xml.SourceHttpMessageConverter
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import javax.xml.transform.Source

class ControllerTesterBuilder {
	private List<HttpMessageConverter> customMessageConverters = []
	private Object[] controllers

	MockMvc build() {
		MockMvcBuilders
				.standaloneSetup(controllers)
				.setMessageConverters(createMessageConverters())
				.build()
	}

	ControllerTesterBuilder controllers(Object... controllers) {
		this.controllers = controllers
		return this
	}

	ControllerTesterBuilder messageConverters(HttpMessageConverter... messageConverter) {
		customMessageConverters = messageConverter.toList()
		return this
	}

	private HttpMessageConverter[] createMessageConverters() {
		(defaultMessageConverters() + customMessageConverters).toArray()
	}

	private List<HttpMessageConverter> defaultMessageConverters() {
		def objectMapper = new ObjectMapper()
		objectMapper.registerModules(new Jdk8Module(), new JavaTimeModule())
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

		[
				new ByteArrayHttpMessageConverter(),
				new StringHttpMessageConverter(writeAcceptCharset: false),
				new ResourceHttpMessageConverter(),
				new SourceHttpMessageConverter<Source>(),
				new AllEncompassingFormHttpMessageConverter(),
				new MappingJackson2HttpMessageConverter(objectMapper: objectMapper)
		]
	}
}
