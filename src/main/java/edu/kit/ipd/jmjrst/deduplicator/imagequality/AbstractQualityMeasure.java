package edu.kit.ipd.jmjrst.deduplicator.imagequality;

import java.awt.image.BufferedImage;

/**
 * Abstrakte Basisklasse für Qualitätsmerkmale.
 *
 */
public abstract class AbstractQualityMeasure implements QualityMeasure {
	
	private int weight;

	@Override
	public void setWeight(int weight) {
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
