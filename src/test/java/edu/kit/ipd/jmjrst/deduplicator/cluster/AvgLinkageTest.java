package edu.kit.ipd.jmjrst.deduplicator.cluster;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests für die gemittelte Verbindungsberechnung.
 *
 */
public class AvgLinkageTest {
	
	AvgLinkage subject;
	float[][] sims;
	Cluster[] leaves;

	/**
	 * Erstellt die nötigen Testdaten.
	 */
	@Before
	public void setUp() {
		subject = new AvgLinkage();
		sims = new float[][] {{   1, 0.2f, 0.3f, 0.5f},
		                      {0.2f,    1, 0.7f, 0.1f},
		                      {0.3f, 0.7f,    1, 0.4f},
		                      {0.5f, 0.1f, 0.4f,    1}};
		subject.setLeafSims(sims);
		leaves = new Cluster[] {
			new ClusterImpl(0),	
			new ClusterImpl(1),	
			new ClusterImpl(2),	
			new ClusterImpl(3),	
			new ClusterImpl(4)	
		};
	}

	/**
	 * Testet das Cluster BC.
	 */
	@Test
	public void testClusterBC() {
		Cluster bc = new ClusterImpl(leaves[1], leaves[2], 0);
		assertEquals(0.25f, subject.getLinkage(bc, leaves[0]), 0.01f);
		assertEquals(0.25f, subject.getLinkage(bc, leaves[3]), 0.01f);
	}

	/**
	 * Testet das Cluster AD.
	 */
	@Test
	public void testClusterAD() {
		Cluster ad = new ClusterImpl(leaves[0], leaves[3], 0);
		assertEquals(0.15f, subject.getLinkage(ad, leaves[1]), 0.01f);
		assertEquals(0.35f, subject.getLinkage(ad, leaves[2]), 0.01f);
	}

	/**
	 * Testet das Cluster AB.
	 */
	@Test
	public void testClusterAB() {
		Cluster ab = new ClusterImpl(leaves[0], leaves[1], 0);
		assertEquals(0.5f, subject.getLinkage(ab, leaves[2]), 0.01f);
		assertEquals(0.3f, subject.getLinkage(ab, leaves[3]), 0.01f);
	}

	/**
	 * Testet das Cluster CD.
	 */
	@Test
	public void testClusterCD() {
		Cluster ab = new ClusterImpl(leaves[2], leaves[3], 0);
		assertEquals(0.4f, subject.getLinkage(ab, leaves[0]), 0.01f);
		assertEquals(0.4f, subject.getLinkage(ab, leaves[1]), 0.01f);
	}

}
