package com.pchudzik.jgallery.commons.test

import org.springframework.test.web.servlet.MockMvc

import static com.pchudzik.jgallery.JGalleryConstatns.API_URL

/**
 * Created by pawel on 06.12.15.
 */
class MockMvcHelper {
	static ControllerTesterBuilder controllerTester() {
		new ControllerTesterBuilder()
	}

	static MockMvc controllerTester(Object... controller) {
		controllerTester()
				.controllers(controller)
				.build()
	}

	static HttpHelper httpHelper() {
		return new HttpHelper(apiUrl: API_URL)
	}

	static JsonContentMatcher jsonResponse() {
		return new JsonContentMatcher()
	}
}
