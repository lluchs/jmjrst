package edu.kit.ipd.jmjrst.deduplicator.cluster;

import java.util.ArrayList;

/**
 * Implementiert ein Dendrogramm mit gegebener
 * Verbindungsgradberechnungsstrategie.
 * @author Lukas
 *
 */
public class DendrogramImpl implements Dendrogram {
	
	private LinkageMethod dm;
	
	/**
	 * Erstellt ein Dendrogramm.
	 * @param dm Verbindungsgradberechnungsstrategie
	 */
	public DendrogramImpl(LinkageMethod dm) {
		this.dm = dm;
	}

	@Override
	public Cluster buildFrom(float[][] sims) {
		dm.setLeafSims(sims);
		// Build a set of leaves.
		ArrayList<Cluster> clusters = new ArrayList<Cluster>(sims.length);
		for (int i = 0; i < sims.length; i++) {
			clusters.add(new ClusterImpl(i));
		}
		// Merge clusters until there's only one left.
		while (clusters.size() > 1) {
			// Find the smallest distance in our cluster.
			float biggest = Float.MIN_VALUE;
			Cluster sc1 = null;
			Cluster sc2 = null;
			for (Cluster c1 : clusters) {
				for (Cluster c2 : clusters) {
					// Don't go further if the two clusters are the same, as our similarity matrix is symmetric.
					if (c1 == c2) {
						break;
					}
					float sim = sims[c1.getFileIndex()][c2.getFileIndex()];
					if (sim > biggest) {
						biggest = sim;
						sc1 = c1;
						sc2 = c2;
					}
				}
			}
			// Merge the found clusters.
			clusters.remove(sc1);
			clusters.remove(sc2);
			Cluster newCluster = new ClusterImpl(sc1, sc2, biggest);
			int newIndex = sc1.getFileIndex();
			newCluster.setFileIndex(newIndex);
			// Adjust the similarity matrix.
			for (Cluster c : clusters) {
				float newLinkage = dm.getLinkage(newCluster, c);
				sims[newIndex][c.getFileIndex()] = newLinkage;
				sims[c.getFileIndex()][newIndex] = newLinkage;
			}
			clusters.add(newCluster);
		}
		// Return the single remaining root cluster.
		return clusters.get(0);
	}

	@Override
	public void setDm(LinkageMethod dm) {
		this.dm = dm;
	}

}
