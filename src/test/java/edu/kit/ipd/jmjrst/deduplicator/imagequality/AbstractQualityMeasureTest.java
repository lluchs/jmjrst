package edu.kit.ipd.jmjrst.deduplicator.imagequality;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

/**
 * Abstrakte Basisklasse für die Qualitätsbewertungstests.
 *
 * Entspricht dem Entwurfsmuster "Schablonenmethode".
 * 
 */
public abstract class AbstractQualityMeasureTest {
	private static final float MAX_ERROR = 0x1.0p-64f;
	private static final File TEST_DIR = new File("src/test/resources");
	
	private QualityMeasure qm;

	// Die Schablonenmethoden, die jeder Test implementieren muss:
	
	/**
	 * Konstruiert das zu testende Qualitätsmerkmal.
	 * @return Das zu testende Qualitätsmerkmal.
	 */
	protected abstract QualityMeasure constructTestSubject(); 
	
	/**
	 * Schablonenmehtode: Erwarteter Wert für testSmall().
	 * @return Die erwartete gewichtete Qualität.
	 */
	protected abstract float expectedSmall();
	
	/**
	 * Schablonenmehtode: Erwarteter Wert für testMin().
	 * @return Die erwartete gewichtete Qualität.
	 */
	protected abstract float expectedMin();
	
	/**
	 * Schablonenmehtode: Erwarteter Wert für testMax().
	 * @return Die erwartete gewichtete Qualität.
	 */
	protected abstract float expectedMax();
	
	/**
	 * Schablonenmehtode: Erwarteter Wert für testBwGradient().
	 * @return Die erwartete gewichtete Qualität.
	 */
	protected abstract float expectedBwGradient();
	
	/**
	 * Schablonenmehtode: Erwarteter Wert für testColorGradient().
	 * @return Die erwartete gewichtete Qualität.
	 */
	protected abstract float expectedColorGradient();
	
	// Die Tests:
	
	/**
	 * Initialisiert das Testsubjekt.
	 */
	@Before
	public void setUp() {
		qm = constructTestSubject();
	}


	/**
	 * Testet ein sehr kleines, leeres Bild.
	 */
	@Test
	public void testSmall() {
		BufferedImage image = new BufferedImage(320, 240,
				BufferedImage.TYPE_INT_BGR);
		qm.getWeightedQuality(image);
		assertEquals(expectedSmall(), qm.getWeightedQuality(image), MAX_ERROR);
	}

	/**
	 * Testet ein kleines, leeres Bild.
	 */
	@Test
	public void testMin() {
		BufferedImage image = new BufferedImage(640, 480,
				BufferedImage.TYPE_INT_BGR);
		qm.getWeightedQuality(image);
		assertEquals(expectedMin(), qm.getWeightedQuality(image), MAX_ERROR);
	}

	/**
	 * Testet ein großes, leeres Bild.
	 */
	@Test
	public void testMax() {
		BufferedImage image = new BufferedImage(4992, 3328,
				BufferedImage.TYPE_INT_BGR);
		qm.getWeightedQuality(image);
		assertEquals(expectedMax(), qm.getWeightedQuality(image), MAX_ERROR);
	}

	/**
	 * Testet ein Bild mit einem Schwarzweiß-Gradienten.
	 * 
	 * @throws IOException
	 *             Falls die Bilddatei nicht gelesen werden kann.
	 */
	@Test
	public void testBwGradient() throws IOException {
		BufferedImage image = ImageIO.read(new File(TEST_DIR, "gradient.png"));
		assertEquals(expectedBwGradient(), qm.getWeightedQuality(image), MAX_ERROR);
	}

	/**
	 * Testet ein Bild mit einem Farbverlauf.
	 * 
	 * @throws IOException
	 *             Falls die Bilddatei nicht gelesen werden kann.
	 */
	@Test
	public void testColorGradient() throws IOException {
		BufferedImage image = ImageIO.read(new File(TEST_DIR, "colors.png"));
		assertEquals(expectedColorGradient(), qm.getWeightedQuality(image), MAX_ERROR);
	}

}
