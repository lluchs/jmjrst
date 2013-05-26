package edu.kit.ipd.jmjrst.deduplicator.cluster;

/**
 * Ein Cluster ist ein Binärbaum mit Verbindungsgraden.
 *
 */
public class ClusterImpl implements Cluster {
	
	private int id;
	private float[] distances;
	private float linkage;
	private Cluster left;
	private Cluster right;
	
	/**
	 * Erstellt ein inneres Cluster mit Ausgangswerten.
	 * 
	 * @param left linkes Element
	 * @param right rechtes Element
	 * @param linkage Verbindungsgrad
	 */
	public ClusterImpl(Cluster left, Cluster right, float linkage) {
		this.left = left;
		this.right = right;
		this.linkage = linkage;
		this.id = -1;
	}
	
	/**
	 * Erstellt ein Blattcluster.
	 * 
	 * @param id Die ID des Blattclusters.
	 * @param distances Array mit Distanzen zu den anderen Blattclustern.
	 */
	public ClusterImpl(int id, float[] distances) {
		this.id = id;
		this.distances = distances;
	}

	@Override
	public Cluster getLeft() {
		return left;
	}

	@Override
	public void setLeft(Cluster left) {
		this.left = left;
	}

	@Override
	public Cluster getRight() {
		return right;
	}

	@Override
	public void setRight(Cluster right) {
		this.right = right;
	}

	@Override
	public float getLinkage() {
		return linkage;
	}

	@Override
	public void setLinkage(float dist) {
		this.linkage = dist;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public float getDistance(int id) {
		return distances[id];
	}

}
