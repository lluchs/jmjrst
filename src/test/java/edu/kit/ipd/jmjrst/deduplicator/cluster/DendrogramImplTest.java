package edu.kit.ipd.jmjrst.deduplicator.cluster;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class DendrogramImplTest {
	
	DendrogramImpl subject;
	float[][] sims;

	@Before
	public void setUp() throws Exception {
		subject = new DendrogramImpl(null);
		sims = new float[][] {{   1, 0.2f, 0.3f, 0.5f},
		                      {0.2f,    1, 0.7f, 0.1f},
		                      {0.3f, 0.7f,    1, 0.4f},
		                      {0.5f, 0.1f, 0.4f,    1}};
	}
	
	/**
	 * Checks a cluster using depth-first-search.
	 * @param c the cluster to check
	 * @param ids the expected id sequence
	 */
	private void checkCluster(Cluster c, LinkedList<Integer> ids) {
		if (c != null) {
			assertEquals(ids.poll().intValue(), c.getFileIndex());
			System.out.println(c.getFileIndex());
			System.out.print("L");
			checkCluster(c.getLeft(), ids);
			System.out.print("R");
			checkCluster(c.getRight(), ids);
		}
	}

	@Test
	public void testCompleteLinkage() {
		subject.setDm(new CompleteLinkage());
		Cluster result = subject.buildFrom(sims);
		System.out.println("Complete Linkage");
		for (float[] row : sims) {
			System.out.println(Arrays.toString(row));
		}
		LinkedList<Integer> expected = new LinkedList<Integer>(
				Arrays.asList(new Integer[] {3, 3, 3, 0, 2, 2, 1}));
		checkCluster(result, expected);
	}

	@Test
	public void testAvgLinkage() {
		subject.setDm(new AvgLinkage());
		Cluster result = subject.buildFrom(sims);
		System.out.println("Average Linkage");
		for (float[] row : sims) {
			System.out.println(Arrays.toString(row));
		}
		LinkedList<Integer> expected = new LinkedList<Integer>(
				Arrays.asList(new Integer[] {3, 3, 3, 0, 2, 2, 1}));
		checkCluster(result, expected);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalArgument() {
		subject.buildFrom(null);
	}

}
