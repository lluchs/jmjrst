/**
 *  Vorhandenen Quelltext der Schnittstellendatei nicht ändern!
 *  Sie dürfen weitere Methoden und Kommentare hinzufügen.
 *  
 *  Klassenname für die Pixelgröße als Qualitätsmaß:
 *  edu.kit.ipd.jmjrst.deduplicator.imagequality.QualityMeasureMegapixels
 */
package edu.kit.ipd.jmjrst.deduplicator.imagequality;

import java.awt.image.BufferedImage;

/**
 * @author tk
 * 
 */
public interface QualityMeasure {
	/**
	 * Setzt die Gewichtung des Qualitätsmerkmals.
	 * 
	 * @param weight
	 *            Das Gewicht zwischen 0 und 100 (Standardwert 50).
	 */
	void setWeight(int weight);

	/**
	 * Liefert für das übergebene Bild das Qualitätsmaß als Fließkommazahl
	 * zurück, wobei die gesetzte Gewichtung hier mit eingerechnet ist.
	 * 
	 * @param bi
	 *            Das Bild
	 * @return Das gewichtete Qualitätsmaß
	 */
	float getWeightedQuality(BufferedImage bi);
}
