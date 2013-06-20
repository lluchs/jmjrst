package edu.kit.ipd.jmjrst.deduplicator.imagequality;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 * Heuristik: Viele Kanten pro Pixelzahl entspricht einem scharfen Bild.
 */
public class EdgeQualityMeasure extends AbstractQualityMeasure {
	private static final Kernel SOBEL_X_KERNEL = new Kernel(3, 3, new float[] {
			1, 0, -1, 2, 0, -2, 1, 0, -1 });
	private static final Kernel SOBEL_Y_KERNEL = new Kernel(3, 3, new float[] {
			1, 2, 1, 0, 0, 0, -1, -2, -1 });

	@Override
	public float getQuality(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();

		// Bild in Graustufen umwandeln
		ColorConvertOp op = new ColorConvertOp(
				ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
		BufferedImage myImage = op.filter(image, null);

		BufferedImage gX = new ConvolveOp(SOBEL_X_KERNEL).filter(myImage, null);
		BufferedImage gY = new ConvolveOp(SOBEL_Y_KERNEL).filter(myImage, null);

		int[] x = gX.getRGB(0, 0, w, h, null, 0, w);
		int[] y = gY.getRGB(0, 0, w, h, null, 0, w);

		float sum = 0.0f;
		for (int i = 0; i < (w * h); i++) {
			// ausschließlich Blauwerte aggregieren
			sum += max(abs(x[i] & 0xFF), abs(y[i] & 0xFF));
		}
		return max(0.0f, min(1.0f, sum / (w * h * 128f)));
	}
}
