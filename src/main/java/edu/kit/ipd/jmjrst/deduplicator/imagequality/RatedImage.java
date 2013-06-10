package edu.kit.ipd.jmjrst.deduplicator.imagequality;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Die Klasse RatedImage stellt  bewertete Bilder dar zu Zwecken der Sortierung.
 * 
 * Achtung: "equals" ist nicht analog zur Sortierung via compareTo()
 * implementiert, da zwei Bilder gleicher Qualität nicht unbedingt gleich sind.
 *
 */
public class RatedImage implements Comparable<RatedImage> {
	private BufferedImage image;
	private float rating;
	
	/**
	 * Konstruiert ein bewertetes Bild.
	 * @param image Das zu bewertende Bild.
	 */
	public RatedImage(BufferedImage image) {
		this.image = image;
	}
	
	/**
	 * Gibt das eigentliche Bild zurück.
	 * @return das Bild
	 */
	public BufferedImage getImage() {
		return image;
	}
	
	/**
	 * Bewertet das Bild mit den gegebenen Qualitätsmerkmalen.
	 * @param qms die Qualitätsberechner
	 */
	public void rate(List<QualityMeasure> qms) {
		rating = 0;
		for (QualityMeasure qm : qms) {
			rating += qm.getWeightedQuality(image);
		}
	}

	@Override
	public int compareTo(RatedImage other) {
		return Float.compare(rating, other.rating);
	}

}
