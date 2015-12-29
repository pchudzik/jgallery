'use strict';

function httpClient($http, apiUrl) {
	return {
		'get': httpGet,
		post: httpPost
	};

	function httpGet(url, params) {
		return $http.get(apiUrl(url), params)
			.then(response => response.data);
	}

	function httpPost(url, data) {
		return $http.post(apiUrl(url), data)
			.then(response => response.data);
	}
}

export default httpClient;
