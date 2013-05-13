// Quelltext der Schnittstellendatei nicht ändern! (Kommentare hinzufügen erlaubt.)
// Klassenname der implementierenden Klasse: edu.kit.ipd.jmjrst.deduplicator.GreyHistogramComparator
package edu.kit.ipd.jmjrst.deduplicator;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Schnittstelle für Bildvergleicher.
 * 
 */
public interface Comparator {

	/**
	 * Liefert die Ähnlichkeiten zwischen 0 und 1 für N Bilder in einer
	 * N*N-Matrix.
	 * 
	 * @return eine Matrix mit allen Ähnlichkeitswerten.
	 */
	float[][] getSimilarities();

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
	void setFiles(List<File> imageFiles) throws IOException;

}