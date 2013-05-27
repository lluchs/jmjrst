package edu.kit.ipd.jmjrst.deduplicator.cluster;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CompleteLinkageTest {
	
	CompleteLinkage subject;
	float[][] sims;
	Cluster[] leaves;

	@Before
	public void setUp() throws Exception {
		subject = new CompleteLinkage();
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

	@Test
	public void testClusterBC() {
		Cluster bc = new ClusterImpl(leaves[1], leaves[2], 0);
		assertEquals(0.2f, subject.getLinkage(bc, leaves[0]), 0.01f);
		assertEquals(0.1f, subject.getLinkage(bc, leaves[3]), 0.01f);
	}

	@Test
	public void testClusterAD() {
		Cluster ad = new ClusterImpl(leaves[0], leaves[3], 0);
		assertEquals(0.1f, subject.getLinkage(ad, leaves[1]), 0.01f);
		assertEquals(0.3f, subject.getLinkage(ad, leaves[2]), 0.01f);
	}

	@Test
	public void testClusterAB() {
		Cluster ab = new ClusterImpl(leaves[0], leaves[1], 0);
		assertEquals(0.3f, subject.getLinkage(ab, leaves[2]), 0.01f);
		assertEquals(0.1f, subject.getLinkage(ab, leaves[3]), 0.01f);
	}

	@Test
	public void testClusterCD() {
		Cluster ab = new ClusterImpl(leaves[2], leaves[3], 0);
		assertEquals(0.3f, subject.getLinkage(ab, leaves[0]), 0.01f);
		assertEquals(0.1f, subject.getLinkage(ab, leaves[1]), 0.01f);
	}

}
