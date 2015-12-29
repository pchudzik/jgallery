'use strict';

import _ from 'lodash';

function TestRunner(params) {
	return {
		it: _.partial(runTest, it),
		fit: _.partial(runTest, fit),
		xit: _.partial(runTest, xit)
	};

	function runTest(itFn, testNameGenerator, testFn) {
		let index = 0;
		_.each(params, param => {
			let testName = resolveTestName(testNameGenerator, param, index);
			let testCase = () => testFn.apply(this, param);

			itFn(testName, testCase);

			index += 1;
		});
	}

	function resolveTestName(testNameOrFn, param, testCaseIndex) {
		let testName = testNameOrFn;

		if (_.isFunction(testNameOrFn)) {
			testName = testNameOrFn.apply(this, param);
		}

		return testName + ' [' + testCaseIndex + ']';
	}
}

function withParams(params) {
	return new TestRunner(params);
}

export default withParams;
