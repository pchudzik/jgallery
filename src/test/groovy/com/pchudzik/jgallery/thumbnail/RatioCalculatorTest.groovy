package com.pchudzik.jgallery.thumbnail

import spock.lang.Specification

/**
 * Created by pawel on 01.12.15.
 */
class RatioCalculatorTest extends Specification {
	private static final def width = 400;
	private static final def height = 300;

	def "should return original image width if thumbnail width and size not specified"() {
		given:
		def calc = new RatioCalculator(width, height, Optional.empty(), Optional.empty())

		expect:
		width == calc.getWidth()
		height == calc.getHeight()
	}

	def "should return requested image width and height if specified"() {
		given:
		def requestedWidth = 30
		def requestedHeight = 40
		def calc = new RatioCalculator(width, height, Optional.of(requestedWidth), Optional.of(requestedHeight))

		expect:
		requestedWidth == calc.getWidth()
		requestedHeight == calc.getHeight()
	}

	def "should calculate width to requested height"() {
		given:
		def requestedHeight = 30
		def calc = new RatioCalculator(width, height, Optional.empty(), Optional.of(requestedHeight))

		expect:
		40 == calc.getWidth()
		requestedHeight == calc.getHeight()
	}

	def "should calculate height to requested width"() {
		given:
		def requestedWidth = 40
		def calc = new RatioCalculator(width, height, Optional.of(requestedWidth), Optional.empty())

		expect:
		requestedWidth == calc.getWidth()
		30 == calc.getHeight()
	}
}
