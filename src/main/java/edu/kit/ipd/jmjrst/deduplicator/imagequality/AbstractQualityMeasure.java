package edu.kit.ipd.jmjrst.deduplicator.imagequality;

import java.awt.image.BufferedImage;

/**
 * Abstrakte Basisklasse für Qualitätsmerkmale.
 *
 */
public abstract class AbstractQualityMeasure implements QualityMeasure {
	
	private int weight = 50;

	@Override
	public void setWeight(int weight) {
		if (weight < 0 || weight > 100) {
			throw new IllegalArgumentException("weight must be between 0 and 100.");
		}
		this.weight = weight;
	}

	@Override
	public float getWeightedQuality(BufferedImage bi) {
		return (float) weight * getQuality(bi);
	}
	
	/**
	 * Liefert für das übergebene Bild das Qualitätsmaß als Fließkommazahl
	 * zurück.
	 * 
	 * @param bi
	 *            Das Bild
	 * @return Das ungewichtete Qualitätsmaß
	 */
	public abstract float getQuality(BufferedImage bi);

}
