package edu.kit.ipd.jmjrst.deduplicator;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the histogram comparator with a couple of sample images.
 */
public class GreyHistogramComparatorTest {
	
	/**
	 * Test comparisons.
	 */
	GreyHistogramComparator all;
	GreyHistogramComparator bw;
	GreyHistogramComparator grey;

	/**
	 * Loads the sample images with some grouping.
	 * @throws Exception it might not work
	 */
	@Before
	public void setUp() throws Exception {
		File white = new File("src/test/resources/white.png");
		File black = new File("src/test/resources/black.png");
		File gradient = new File("src/test/resources/gradient.png");
		File color = new File("src/test/resources/colors.png");
		
		List<File> allFiles = Arrays.asList(new File[] { white, black, gradient, color });
		List<File> bwFiles = Arrays.asList(new File[] { white, black });
		List<File> greyFiles = Arrays.asList(new File[] { white, black, gradient });
		
		all = new GreyHistogramComparator();
		all.setFiles(allFiles);
		bw = new GreyHistogramComparator();
		bw.setFiles(bwFiles);
		grey = new GreyHistogramComparator();
		grey.setFiles(greyFiles);
	}

	/**
	 * Tests the dimension of the result matrix.
	 */
	@Test
	public void testGetSimilaritiesBWDimension() {
		float[][] sim = bw.getSimilarities();
		assertEquals(2, sim.length);
		assertEquals(2, sim[0].length);
	}

	/**
	 * Tests the dimension of the result matrix.
	 */
	@Test
	public void testGetSimilaritiesGreyDimension() {
		float[][] sim = grey.getSimilarities();
		assertEquals(3, sim.length);
		assertEquals(3, sim[0].length);
	}

	/**
	 * Tests the dimension of the result matrix.
	 */
	@Test
	public void testGetSimilaritiesAllDimension() {
		float[][] sim = all.getSimilarities();
		assertEquals(4, sim.length);
		assertEquals(4, sim[0].length);
	}

	/**
	 * Tests the contents of the result matrix.
	 */
	@Test
	public void testGetSimilaritiesBW() {
		float[][] sim = bw.getSimilarities();
		assertEquals(1.0, sim[0][0], 0.001f);
		assertEquals(1.0, sim[1][1], 0.001f);
		assertEquals(0.0, sim[1][0], 0.001f);
		assertEquals(0.0, sim[0][1], 0.001f);
	}

	/**
	 * Tests the contents of the result matrix.
	 */
	@Test
	public void testGetSimilaritiesAllIdentity() {
		float[][] sim = all.getSimilarities();
		assertEquals(1.0, sim[0][0], 0.001f);
		assertEquals(1.0, sim[1][1], 0.001f);
		assertEquals(1.0, sim[2][2], 0.001f);
		assertEquals(1.0, sim[3][3], 0.001f);
	}

	/**
	 * Tests the contents of the result matrix.
	 * 
	 * The grey gradient should roughly have a similarity of 0.5 to the pure images.
	 */
	@Test
	public void testGetSimilaritiesGrey() {
		float[][] sim = grey.getSimilarities();
		assertEquals(0.5, sim[0][2], 0.1f);
		assertEquals(0.5, sim[2][0], 0.1f);
		assertEquals(0.5, sim[1][2], 0.1f);
		assertEquals(0.5, sim[2][1], 0.1f);
	}

}
