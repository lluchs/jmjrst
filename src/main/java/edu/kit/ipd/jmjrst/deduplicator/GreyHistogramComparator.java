package edu.kit.ipd.jmjrst.deduplicator;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Histogramm-Vergleicher für Graustufenbilder.
 * 
 * @see edu.kit.ipd.jmjrst.deduplicator.GreyHistogram
 *
 */
public class GreyHistogramComparator implements Comparator {
	
	private GreyHistogram[] histograms;

	/**
	 * Liefert die Ähnlichkeiten zwischen 0 und 1 für N Bilder in einer
	 * N*N-Matrix.
	 * 
	 * @return eine Matrix mit allen Ähnlichkeitswerten.
	 */
	@Override
	public float[][] getSimilarities() {
		float[][] result = new float[histograms.length][histograms.length];
		for (int i = 0; i < histograms.length; i++) {
			for (int j = 0; j < histograms.length; j++) {
				float similarity = calculateSimilarity(histograms[i], histograms[j]);
				result[i][j] = similarity;
				result[j][i] = similarity;
			}
		}
		return result;
	}
	
	/**
	 * Berechnet den Ähnlichkeitswert zweier Histogramme.
	 * 
	 * @param img1 Das erste Histogramm.
	 * @param img2 Das zweite Histogramm.
	 * @return Den Ähnlichkeitswert.
	 */
	private float calculateSimilarity(GreyHistogram img1, GreyHistogram img2) {
		float[] hist1 = img1.getHistogram();
		float[] hist2 = img2.getHistogram();
		float sum = 0;
		float d = 0;
		for (int i = 0; i < hist1.length; i++) {
			d += hist1[i] - hist2[i];
			sum += Math.abs(d);
		}
		// Normalize value.
		return 1.0f - sum / 255.0f;
	}

	/**
	 * Setzt die Menge der zu vergleichenden Dateien.
	 * 
	 * @param imageFiles
	 *            Liste der zu vergleichenden Bilddateien.
	 * @throws IOException
	 *             Wird ausgelöst, falls eine der Dateien nicht als Bilddatei
	 *             interpretiert werden kann.
	 * @throws IllegalArgumentException
	 *             Wid ausgelöst, falls imageFiles null ist.
	 */
	@Override
	public void setFiles(List<File> imageFiles) throws IOException {
		if (imageFiles == null) {
			throw new IllegalArgumentException("imageFiles must not be null.");
		}
		histograms = new GreyHistogram[imageFiles.size()];
		int i = 0;
		for (File f : imageFiles) {
			GreyHistogram histogram = new GreyHistogram();
			histogram.buildFrom(f);
			histograms[i++] = histogram;
		}
	}

}
