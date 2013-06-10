package edu.kit.ipd.jmjrst.deduplicator.cluster.imagequality;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.ipd.jmjrst.deduplicator.imagequality.QualityMeasureMegapixels;

/**
 * Testet den Pixelzahl-Qualitätsberechner.
 *
 */
public class QualityMeasureMegapixelsTest {
	private static BufferedImage small;
	private static BufferedImage middle;
	private static BufferedImage large;
	
	private QualityMeasureMegapixels subject;
	
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
	 * Erstellt den Berechner.
	 */
	@Before
	public void setUp() {
		subject = new QualityMeasureMegapixels();
	}

	/**
	 * Testet ein kleines Bild.
	 */
	@Test
	public void testSmall() {
		assertEquals(0.0f, subject.getWeightedQuality(small), 0.0001f);
	}

	/**
	 * Testet ein mittleres Bild.
	 */
	@Test
	public void testMiddle() {
		assertEquals(3.39f, subject.getWeightedQuality(middle), 0.01f);
	}

	/**
	 * Testet ein kleines Bild.
	 */
	@Test
	public void testLarge() {
		assertEquals(50.0f, subject.getWeightedQuality(large), 0.0001f);
	}

}
