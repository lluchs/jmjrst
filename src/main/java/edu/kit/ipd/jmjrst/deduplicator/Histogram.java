// Quelltext der Schnittstellendatei nicht ändern! (Kommentare hinzufügen erlaubt.)
// Klassenname der implementierenden Klasse: edu.kit.ipd.jmjrst.deduplicator.GreyHistogram
package edu.kit.ipd.jmjrst.deduplicator;

import java.io.File;
import java.io.IOException;

/**
 * Schnittstelle für ein Histogramm.
 */
public interface Histogram {

	/**
	 * Liefert das Histogramm (eines Bildes).
	 * 
	 * @return ein Histogramm mit 256 relativen Häufigkeiten.
	 */
	float[] getHistogram();

	/**
	 * Setze die Datei, für die das Histogramm berechnet werden soll.
	 * 
	 * @param f
	 *            Jene Datei.
	 * @throws IOException
	 *             Wird ausgelöst, falls die Datei nicht als Bilddatei
	 *             eingelesen werden kann.
	 */
	void buildFrom(File f) throws IOException;

}