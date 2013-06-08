package edu.kit.ipd.jmjrst.deduplicator.cluster;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

/**
 * Testklasse für ClusterImpl (unvollständig);
 * 
 * @author tk
 * 
 */
public class ClusterImplTest {
	/**
	 * Testcluster für rein lesenden Zugriff.
	 */
	Cluster testCluster;

	/**
	 * Initialisiert den Testcluster.
	 */
	@Before
	public void setUp() {
		// Wurzel
		testCluster = new ClusterImpl();
		testCluster.setLinkage(0.0001f);

		// erste Verzweigung
		testCluster.setLeft(new ClusterImpl());
		testCluster.getLeft().setLinkage(0.001f);
		testCluster.setRight(new ClusterImpl());
		testCluster.getRight().setLinkage(0.005f);

		// zweite Verzweigung, linke Seite
		testCluster.getLeft().setLeft(new ClusterImpl());
		testCluster.getLeft().getLeft().setLinkage(0.1f);
		testCluster.getLeft().setRight(new ClusterImpl());
		// Blatt: testCluster.getLeft().getRight() -> keine Linkage

		testCluster.getLeft().getLeft().setLeft(new ClusterImpl());
		testCluster.getLeft().getLeft().setRight(new ClusterImpl());

		// zweite Verzweigung, rechte Seite
		testCluster.getRight().setLeft(new ClusterImpl());
		testCluster.getRight().getLeft().setLinkage(0.01f);
		testCluster.getRight().setRight(new ClusterImpl());
		testCluster.getRight().getRight().setLinkage(0.05f);

		// verbleibende Blätter
		testCluster.getRight().getLeft().setLeft(new ClusterImpl());
		testCluster.getRight().getLeft().setRight(new ClusterImpl());
		testCluster.getRight().getRight().setLeft(new ClusterImpl());
		testCluster.getRight().getRight().setRight(new ClusterImpl());
	}

	/**
	 * Basis-Testfall für den Iterator in ClusterImpl.
	 */
	@Test
	public void testImageIterator() {
		float thres = 0.005f;

		Iterator<Cluster> subClusters = testCluster.subClusterIterator(thres);
		int i = 0;
		while (subClusters.hasNext()) {
			i++;
			subClusters.next();
			// System.out.println("Cluster Nr. " + i);
		}

		// wir erwarten bei obigem Aufbau und Schwellwert 3 Subcluster
		assertEquals(i, 3);
	}
}
