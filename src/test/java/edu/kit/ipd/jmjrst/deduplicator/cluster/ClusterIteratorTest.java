package edu.kit.ipd.jmjrst.deduplicator.cluster;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Testet den Cluster-Iterator.
 *
 */
public class ClusterIteratorTest {

	private ClusterIterator subject;
	
	/**
	 * Erstellt die zu testende Klasse.
	 */
	@Before
	public void setUp() {
		subject = new ClusterIterator(new ClusterImpl(), 0.0f);
	}

	/**
	 * Testet die nicht implementierte remove()-Methode.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testRemove() {
		subject.remove();
	}
	
	/**
	 * Testet die hasNext-Methode.
	 */
	@Test
	public void testHasNext() {
		assertTrue(subject.hasNext());
		subject.next();
		assertFalse(subject.hasNext());
	}
	
	/**
	 * Testet die next-Methode.
	 */
	@Test
	public void testNext() {
		assertTrue(subject.next() instanceof ClusterImpl);
		assertEquals(null, subject.next());
		assertEquals(null, subject.next());
	}

}
