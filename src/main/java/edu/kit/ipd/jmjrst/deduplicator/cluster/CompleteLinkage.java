package edu.kit.ipd.jmjrst.deduplicator.cluster;

/**
 * Berechnet den Verbindungsgrad anhand des Minimums der Ähnlichkeit aller Kindelemente der Cluster.
 *
 */
public class CompleteLinkage implements LinkageMethod {

	@Override
	public float getLinkage(Cluster newCluster, Cluster existingCluster) {
		if (newCluster.getId() == -1) {
				return Math.min(
						getLinkage(newCluster.getLeft(), existingCluster),
						getLinkage(newCluster.getRight(), existingCluster));
		} else {
			if (existingCluster.getId() == -1) {
				return Math.min(
						getLinkage(newCluster, existingCluster.getLeft()),
						getLinkage(newCluster, existingCluster.getRight()));
			} else {
				return newCluster.getDistance(existingCluster.getId());
			}
		}
	}

}
