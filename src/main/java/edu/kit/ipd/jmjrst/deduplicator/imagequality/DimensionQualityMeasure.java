package edu.kit.ipd.jmjrst.deduplicator.imagequality;

import java.awt.image.BufferedImage;

/**
 * Bestimmt die Qualität eines Bildes anhand der Pixelzahl.
 *
 */
public class DimensionQualityMeasure extends AbstractQualityMeasure {
	private static final int MIN = 640 * 480;
	private static final int MAX = 4992 * 3328;

	@Override
	public float getQuality(BufferedImage bi) {
		if (bi == null) {
			throw new IllegalArgumentException();
		}
		int size = bi.getWidth() * bi.getHeight();
		float result = (float) (size - MIN) / (MAX - MIN);
		return Math.min(1.0f, Math.max(0.0f, result));
	}

}
