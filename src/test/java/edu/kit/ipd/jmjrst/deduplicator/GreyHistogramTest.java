package edu.kit.ipd.jmjrst.deduplicator;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the grey histogram calculator.
 *
 */
public class GreyHistogramTest {
	
	/**
	 * Test files.
	 */
	GreyHistogram white;
	GreyHistogram black;
	GreyHistogram gradient;
	GreyHistogram color;

	/**
	 * Load all test image files.
	 * @throws Exception this might fail
	 */
	@Before
	public void setUp() throws Exception {
		white = new GreyHistogram();
		white.buildFrom(new File("src/test/resources/white.png"));
		black = new GreyHistogram();
		black.buildFrom(new File("src/test/resources/black.png"));
		gradient = new GreyHistogram();
		gradient.buildFrom(new File("src/test/resources/gradient.png"));
		color = new GreyHistogram();
		color.buildFrom(new File("src/test/resources/colors.png"));
	}

	/**
	 * Simple case: fully white image.
	 */
	@Test
	public void testGetHistogramWhite() {
		assertEquals(1.0f, white.getHistogram()[255], 0.001f);
		for (int i = 254; i >= 0; i--) {
			assertEquals(0.0f, white.getHistogram()[i], 0.001f);
		}
	}

	/**
	 * Simple case: fully black image.
	 */
	@Test
	public void testGetHistogramBlack() {
		assertEquals(1.0f, black.getHistogram()[0], 0.001f);
		for (int i = 255; i > 0; i--) {
			assertEquals(0.0f, black.getHistogram()[i], 0.001f);
		}
	}

	/**
	 * Check the even grey distribution of the gradient.
	 */
	@Test
	public void testGetHistogramGradient() {
		float sum = 0;
		for (int i = 255; i >= 0; i--) {
			sum += gradient.getHistogram()[i];
			// All values should roughly be included.
			assertEquals(0.01f, gradient.getHistogram()[i], 0.01f);
		}
		assertEquals(1.0f, sum, 0.001f);
	}

	/**
	 * Check whether the histogram value sum of the color image equals 1.
	 */
	@Test
	public void testGetHistogramColor() {
		float sum = 0;
		for (int i = 255; i >= 0; i--) {
			sum += color.getHistogram()[i];
		}
		assertEquals(1.0f, sum, 0.001f);
	}

}
