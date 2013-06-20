package edu.kit.ipd.jmjrst.deduplicator.imagequality;


/**
 * Testet den Pixelzahl-Qualitätsberechner.
 *
 */
public class QualityMeasureMegapixelsTest extends AbstractQualityMeasureTest {

	@Override
	protected QualityMeasure constructTestSubject() {
		return new QualityMeasureMegapixels();
	}

	@Override
	protected float expectedSmall() {
		return 0;
	}

	@Override
	protected float expectedMin() {
		return 0;
	}

	@Override
	protected float expectedMax() {
		return 50;
	}

	@Override
	protected float expectedBwGradient() {
		return 0;
	}

	@Override
	protected float expectedColorGradient() {
		return 0;
	}

}
