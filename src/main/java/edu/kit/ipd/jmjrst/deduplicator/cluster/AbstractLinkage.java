package edu.kit.ipd.jmjrst.deduplicator.cluster;

/**
 * Basisklasse für Verbindungsgradalgorithmen.
 *
 */
abstract class AbstractLinkage implements LinkageMethod {
	
	private float[][] sims;

	@Override
	public void setLeafSims(float[][] sims) {
		this.sims = sims;
	}
	
	/**
	 * Gibt die gespeicherte Ähnlichkeit zweiter Blätter zurück.
	 * @param id1 Erste Blatt-ID.
	 * @param id2 Zweite Blatt-ID.
	 * @return Die Ähnlichkeit.
	 */
	protected float getLeafSim(int id1, int id2) {
		return sims[id1][id2];
	}

}