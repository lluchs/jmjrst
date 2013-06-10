package edu.kit.ipd.jmjrst.deduplicator.imagequality;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Nach Qualitätsmaß sortierte Bilder.
 *
 */
public class SortedImagesImpl implements SortedImages {
	
	private List<RatedImage> images;
	private List<QualityMeasure> features;
	
	/** Markiert, ob die Bildliste schon sortiert ist. */
	private boolean rated = false;

	@Override
	public void setImageList(List<BufferedImage> imageList) {
		if (imageList == null) {
			throw new IllegalArgumentException("No images given.");
		}
		images = new LinkedList<RatedImage>();
		for (BufferedImage image : imageList) {
			images.add(new RatedImage(image));
		}
		rated = false;
	}

	@Override
	public void setQualityFeatures(List<QualityMeasure> featureList) {
		if (featureList == null || featureList.isEmpty()) {
			throw new IllegalArgumentException("The feature list must not be empty.");
		}
		this.features = featureList;
		rated = false;
	}

	@Override
	public List<BufferedImage> getSortedImages() {
		if (images == null || features == null) {
			return null;
		}
		sortImages();
		// Bilder extrahieren.
		List<BufferedImage> out = new LinkedList<BufferedImage>();
		for (RatedImage image : images) {
			out.add(image.getImage());
		}
		return out;
	}
	
	/**
	 * Sortiert die gespeicherten Bilder.
	 */
	private void sortImages() {
		// Nichts tun falls bereits geschehen oder nicht genügend Information vorhanden.
		if (rated || images == null || features == null) {
			return;
		}
		// Bilder bewerten.
		for (RatedImage image : images) {
			image.rate(features);
		}
		// Bilder sortieren.
		Collections.sort(images);
		// Als bewertet markieren.
		rated = true;
	}

}
