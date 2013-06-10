/**
 *  Vorhandenen Quelltext der Schnittstellendatei nicht ändern!
 *  Sie dürfen weitere Methoden und Kommentare hinzufügen.
 *  
 *  Klassenname der implementierenden Klasse:
 *  edu.kit.ipd.jmjrst.deduplicator.imagequality.SortedImagesImpl
 */
package edu.kit.ipd.jmjrst.deduplicator.imagequality;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Schnittstelle für nach Qualitätsmaß sortierte Bilder.
 * 
 */
public interface SortedImages {
	/**
	 * Setzt die zu sortierende Menge der Bilder.
	 * 
	 * @param imageList
	 *            Die Bilderliste
	 */
	void setImageList(List<BufferedImage> imageList);

	/**
	 * Setzt die Qualitätsmerkmale (mit Gewichten), die zur Sortierung verwendet
	 * werden sollen.
	 * 
	 * @param featureList
	 *            Die Liste der Qualitätsmerkmale
	 */
	void setQualityFeatures(List<QualityMeasure> featureList);

	/**
	 * Liefert die Liste der nach den Qualitätsmerkmalen sortierten Bilder
	 * zurück.
	 * 
	 * @return Die sortierte Bilderliste
	 */
	List<BufferedImage> getSortedImages();
}