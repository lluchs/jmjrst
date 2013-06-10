package edu.kit.ipd.jmjrst.deduplicator.imagequality;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifSubIFDDirectory;

/**
 * Bestimmt die Qualität eines Bildes anhand der in der EXIF-Information
 * gespeicherten ISO-Geschwindigkeit.
 * 
 * Dazu müssen den geladenen Bildern zunächst Dateien zugeordnet werden.
 *
 */
public class QualityMeasureISOSpeed extends AbstractQualityMeasure {
	private static final int MIN = 100;
	private static final int MAX = 1600;
	
	private Map<BufferedImage, File> files;
	
	/**
	 * Standard-Konstruktor.
	 */
	public QualityMeasureISOSpeed() {
		files = new HashMap<BufferedImage, File>();
	}
	
	/**
	 * Fügt das gegebene Bild zur internen Dateiliste hinzu.
	 * @param img Das Bild.
	 * @param f Die zugehörige Datei.
	 */
	public void addFile(BufferedImage img, File f) {
		files.put(img, f);
	}

	@Override
	public float getQuality(BufferedImage bi) {
		if (bi == null) {
			throw new IllegalArgumentException();
		}
		File f = files.get(bi);
		if (f == null) {
			return DEFAULT;
		}
		int iso;
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(f);
			Directory directory = metadata.getDirectory(ExifSubIFDDirectory.class);
			iso = directory.getInt(0x8827);
		} catch (ImageProcessingException e) {
			return DEFAULT;
		} catch (IOException e) {
			return DEFAULT;
		} catch (MetadataException e) {
			return DEFAULT;
		}
		float result = (float) (iso - MIN) / (MAX - MIN);
		return 1.0f - Math.min(1.0f, Math.max(0.0f, result));
	}

}
