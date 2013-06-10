package edu.kit.ipd.jmjrst.deduplicator.cluster.imagequality;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.ipd.jmjrst.deduplicator.imagequality.DimensionQualityMeasure;
import edu.kit.ipd.jmjrst.deduplicator.imagequality.ISOSpeedQualityMeasure;
import edu.kit.ipd.jmjrst.deduplicator.imagequality.QualityMeasure;
import edu.kit.ipd.jmjrst.deduplicator.imagequality.SortedImagesImpl;

/**
 * Testet den Bildsortierer.
 *
 */
public class SortedImagesImplTest {
	private static File iso100f;
	private static File iso400f;
	private static File iso1600f;
	private static BufferedImage iso100;
	private static BufferedImage iso400;
	private static BufferedImage iso1600;
	private static BufferedImage small;
	private static BufferedImage middle;
	private static BufferedImage large;
	
	private ISOSpeedQualityMeasure isoSpeedQM;
	private DimensionQualityMeasure dimensionQM;
	
	private SortedImagesImpl subject;

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
		small = ImageIO.read(new File("src/test/resources/black.png"));
		middle = ImageIO.read(new File("src/test/resources/middle.png"));
		large = ImageIO.read(new File("src/test/resources/large.png"));
	}

	/**
	 * Erstellt die zu testenden Klassen.
	 */
	@Before
	public void setUp() {
		dimensionQM = new DimensionQualityMeasure();
		isoSpeedQM = new ISOSpeedQualityMeasure();
		isoSpeedQM.addFile(iso100, iso100f);
		isoSpeedQM.addFile(iso400, iso400f);
		isoSpeedQM.addFile(iso1600, iso1600f);
		
		subject = new SortedImagesImpl();
	}

	/**
	 * Testet Sortieren nach ISO-Geschwindigkeit.
	 */
	@Test
	public void testISOQuality() {
		subject.setQualityFeatures(Arrays.asList(new QualityMeasure[] {isoSpeedQM}));
		subject.setImageList(Arrays.asList(new BufferedImage[] {iso400, iso1600, iso100}));
		List<BufferedImage> result = subject.getSortedImages();
		assertEquals(iso1600, result.get(0));
		assertEquals(iso400, result.get(1));
		assertEquals(iso100, result.get(2));
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

	/**
	 * Testet Sortieren nach Bilddimension und ISO-Geschwindigkeit.
	 */
	@Test
	public void testCompositeQuality() {
		dimensionQM.setWeight(70);
		isoSpeedQM.setWeight(30);
		subject.setQualityFeatures(Arrays.asList(new QualityMeasure[] {dimensionQM, isoSpeedQM}));
		subject.setImageList(Arrays.asList(new BufferedImage[] {iso100, middle, small, iso400, large, iso1600}));
		List<BufferedImage> result = subject.getSortedImages();
		assertEquals(iso1600, result.get(0));
		assertEquals(small, result.get(1));
		assertEquals(middle, result.get(2));
		assertEquals(iso400, result.get(3));
		assertEquals(iso100, result.get(4));
		assertEquals(large, result.get(5));
	}

}
