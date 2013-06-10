package edu.kit.ipd.jmjrst.deduplicator.cluster.imagequality;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.ipd.jmjrst.deduplicator.imagequality.ISOSpeedQualityMeasure;

/**
 * Testet den ISO-Geschwindigkeits-Qualitätsberechner.
 *
 */
public class ISOSpeedQualityMeasureTest {
	private static File iso100f;
	private static File iso400f;
	private static File iso1600f;
	private static BufferedImage iso100;
	private static BufferedImage iso400;
	private static BufferedImage iso1600;
	
	private ISOSpeedQualityMeasure subject;
	
	/**
	 * Lädt Testbilder.
	 * @throws Exception Wenn das Laden eines Bildes fehlschlägt.
	 */
	@BeforeClass
	public static void setUpImages() throws Exception {
		iso100f = new File("src/test/resources/iso-100.JPG");
		iso400f = new File("src/test/resources/iso-400.JPG");
		iso1600f = new File("src/test/resources/iso-1600.JPG");
		iso100 = ImageIO.read(iso100f);
		iso400 = ImageIO.read(iso400f);
		iso1600 = ImageIO.read(iso1600f);
	}

	/**
	 * Erstellt den Berechner.
	 */
	@Before
	public void setUp() {
		subject = new ISOSpeedQualityMeasure();
		subject.addFile(iso100, iso100f);
		subject.addFile(iso400, iso400f);
		subject.addFile(iso1600, iso1600f);
	}

	/**
	 * Testet ein Bild mit ISO 100.
	 */
	@Test
	public void testISO100() {
		assertEquals(50.0f, subject.getWeightedQuality(iso100), 0.0001f);
	}

	/**
	 * Testet ein Bild mit ISO 400.
	 */
	@Test
	public void testISO400() {
		assertEquals(40.0f, subject.getWeightedQuality(iso400), 0.0001f);
	}

	/**
	 * Testet ein Bild mit ISO 1600.
	 */
	@Test
	public void testISO1600() {
		assertEquals(0.0f, subject.getWeightedQuality(iso1600), 0.0001f);
	}

}
