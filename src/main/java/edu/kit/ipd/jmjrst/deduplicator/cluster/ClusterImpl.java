package edu.kit.ipd.jmjrst.deduplicator.cluster;

/**
 * Ein Cluster ist ein Binärbaum mit Verbindungsgraden.
 *
 */
public class ClusterImpl implements Cluster {
	
	private int id;
	private float linkage;
	private Cluster left;
	private Cluster right;
	
	/**
	 * Standard-Konstruktor.
	 */
	public ClusterImpl() {
		this.id = -1;
	}
	
	/**
	 * Erstellt ein inneres Cluster mit Ausgangswerten.
	 * 
	 * @param left linkes Element
	 * @param right rechtes Element
	 * @param linkage Verbindungsgrad
	 */
	public ClusterImpl(Cluster left, Cluster right, float linkage) {
		this();
		this.left = left;
		this.right = right;
		this.linkage = linkage;
	}
	
	/**
	 * Erstellt ein Blattcluster.
	 * 
	 * @param id Die ID des Blattclusters.
	 */
	public ClusterImpl(int id) {
		this.id = id;
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
	public int getFileIndex() {
		return id;
	}

	@Override
	public void setFileIndex(int index) {
		this.id = index;
	}
}
