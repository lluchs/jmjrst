package edu.kit.ipd.jmjrst.deduplicator;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Histogramm-Rechner für Graustufenbilder.
 *
 */
public class GreyHistogram implements Histogram {
	private float[] histogram;

	/**
	 * Liefert das Histogramm (eines Bildes).
	 * 
	 * @return ein Histogramm mit 256 relativen Häufigkeiten.
	 */
	@Override
	public float[] getHistogram() {
		return histogram;
	}

	/**
	 * Setze die Datei, für die das Histogramm berechnet werden soll.
	 * 
	 * @param f
	 *            Jene Datei.
	 * @throws IOException
	 *             Wird ausgelöst, falls die Datei nicht als Bilddatei
	 *             eingelesen werden kann.
	 */
	@Override
	public void buildFrom(File f) throws IOException {
		BufferedImage image = ImageIO.read(f);
		int[] greyValues = new int[256];
		int wdt = image.getWidth();
		int hgt = image.getHeight();
		for (int x = 0; x < wdt; x++) {
			for (int y = 0; y < hgt; y++) {
				int grey = rgbToGrey(image.getRGB(x, y));
				greyValues[grey]++;
			}
		}
		histogram = new float[256];
		int pixels = wdt * hgt;
		for (int i = 0; i < 256; i++) {
			histogram[i] = (float) greyValues[i] / pixels;
		}
	}
	
	/**
	 * Konvertiert ein RGB-Farbwert zu einem Graustufenwert.
	 * 
	 * @param rgb Die Farbe.
	 * @return Der Graustufenwert.
	 */
	 private int rgbToGrey(int rgb) {
		 Color c = new Color(rgb);
		 return (int) (0.299 * c.getRed() + 0.587 * c.getGreen() + 0.114 * c.getBlue());
	 }

}
