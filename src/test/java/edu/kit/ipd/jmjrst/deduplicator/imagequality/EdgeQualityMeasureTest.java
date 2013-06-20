package edu.kit.ipd.jmjrst.deduplicator.imagequality;

/**
 * Testklasse für das Kantenverhältnis-Qualitätsmaß (EdgeQualityMeasure)
 */
public class EdgeQualityMeasureTest extends AbstractQualityMeasureTest {

	@Override
	protected QualityMeasure constructTestSubject() {
		return new EdgeQualityMeasure();
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
	protected float expectedMedium() {
		return 0;
	}

	@Override
	protected float expectedMax() {
		return 0;
	}

	@Override
	protected float expectedBwGradient() {
		return 0;
	}

	@Override
	protected float expectedColorGradient() {
		return 10.343595f;
	}

}
