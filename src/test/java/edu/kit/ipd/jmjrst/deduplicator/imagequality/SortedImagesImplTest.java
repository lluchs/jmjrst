package edu.kit.ipd.jmjrst.deduplicator.imagequality;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Testet den Bildsortierer.
 *
 */
public class SortedImagesImplTest {
	private static BufferedImage small;
	private static BufferedImage middle;
	private static BufferedImage large;
	
	private QualityMeasureMegapixels dimensionQM;
	
	private SortedImagesImpl subject;

	/**
	 * Lädt Testbilder.
	 * @throws Exception Wenn das Laden eines Bildes fehlschlägt.
	 */
	@BeforeClass
	public static void setUpImages() throws Exception {
		small = ImageIO.read(new File("src/test/resources/black.png"));
		middle = ImageIO.read(new File("src/test/resources/middle.png"));
		large = ImageIO.read(new File("src/test/resources/large.png"));
	}

	/**
	 * Erstellt die zu testenden Klassen.
	 */
	@Before
	public void setUp() {
		dimensionQM = new QualityMeasureMegapixels();
		
		subject = new SortedImagesImpl();
	}

	/**
	 * Testet Sortieren nach Bilddimension.
	 */
	@Test
	public void testDimensionQuality() {
		subject.setQualityFeatures(Arrays.asList(new QualityMeasure[] {dimensionQM}));
		subject.setImageList(Arrays.asList(new BufferedImage[] {middle, small, large}));
		List<BufferedImage> result = subject.getSortedImages();
		assertEquals(small, result.get(0));
		assertEquals(middle, result.get(1));
		assertEquals(large, result.get(2));
	}

}
