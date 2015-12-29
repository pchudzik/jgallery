package com.pchudzik.jgallery.commons.test

import groovy.json.JsonOutput
import org.json.JSONException
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.skyscreamer.jsonassert.JSONParser
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultMatcher

class JsonContentMatcher {
	public ResultMatcher isEqual(Object resultJson) {
		isEqual(JsonOutput.toJson(resultJson))
	}

	public ResultMatcher isEqual(String expectedJson) {
		{ MvcResult result ->
			def actualJson = result.getResponse().getContentAsString()
			checkIfParsable("expected json", expectedJson)
			checkIfParsable("actual json", actualJson)
			try {
				JSONAssert.assertEquals(actualJson, expectedJson, JSONCompareMode.STRICT)
			} catch (AssertionError err) {
				System.err.println("Expected Json:\n$expectedJson")
				System.err.println("Actual Json:\n$actualJson")
				throw err
			}
		} as ResultMatcher
	}

	def checkIfParsable(String jsonDescription, String jsonString) {
		try {
			JSONParser.parseJSON(jsonString)
		} catch (JSONException ex) {
			System.err.println("Can not parse $jsonDescription:\n$jsonString")
			throw ex
		}
	}
}
