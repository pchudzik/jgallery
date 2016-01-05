package com.pchudzik.jgallery.commons.test

import groovy.json.JsonOutput
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

import static java.net.URLEncoder.encode
import static java.nio.charset.StandardCharsets.UTF_8
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8

class HttpHelper {
	String apiUrl = ""

	MockHttpServletRequestBuilder postWithContent(String url, Map content) {
		MockMvcRequestBuilders
				.post(apiUrl + url)
				.contentType(APPLICATION_JSON_UTF8)
				.content(JsonOutput.toJson(content))
	}

	MockHttpServletRequestBuilder delete(String url) {
		MockMvcRequestBuilders
				.delete(apiUrl + url)
				.contentType(APPLICATION_JSON_UTF8)
	}

	MockHttpServletRequestBuilder putWithContent(String url, Map content) {
		MockMvcRequestBuilders
				.put(apiUrl + url)
				.contentType(APPLICATION_JSON_UTF8)
				.content(JsonOutput.toJson(content))
	}

	MockHttpServletRequestBuilder get(String url) {
		get(url, [:])
	}

	MockHttpServletRequestBuilder get(String url, Map<String, String> params) {
		MockMvcRequestBuilders
				.get(generateParams(apiUrl + url, params))
				.contentType(APPLICATION_JSON_UTF8)
	}

	private String generateParams(String url, Map<String, String> params) {
		url + "?" + params
				.collect { name, value -> encode(name, UTF_8.toString()) + "=" + encode(value, UTF_8.toString()) }
				.join("&")
	}
}
