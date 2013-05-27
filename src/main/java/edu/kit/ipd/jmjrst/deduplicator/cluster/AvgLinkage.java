package edu.kit.ipd.jmjrst.deduplicator.cluster;

/**
 * Berechnet den Verbindungsgrad aus dem Mittel der vorigen Verbindungsgrade.
 *
 */
public class AvgLinkage extends AbstractLinkage {

	@Override
	public float getLinkage(Cluster newCluster, Cluster existingCluster) {
		float left = getLeafSim(newCluster.getLeft().getFileIndex(), existingCluster.getFileIndex());
		float right = getLeafSim(newCluster.getRight().getFileIndex(), existingCluster.getFileIndex());
		
		return 0.5f * left + 0.5f * right;
	}

}
