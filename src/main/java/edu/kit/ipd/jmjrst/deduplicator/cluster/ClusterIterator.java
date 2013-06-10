package edu.kit.ipd.jmjrst.deduplicator.cluster;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Iterator zum iterieren über Cluster.
 * 
 * Dabei bestimmt ein Schwellwert, welche Cluster zurückgegeben werden.
 * 
 * Achtung: Der Iterator funktioniert nur dann korrekt, wenn es sich bei dem
 * übergeben Cluster tatsächlich um einen (azyklischen) Binärbaum handelt.
 *
 */
public class ClusterIterator implements Iterator<Cluster> {
	Queue<Cluster> queue;
	float similarity;

	/**
	 * Konstruiert einen neuen Iterator mit einem Schwellwert.
	 * @param cluster Das Cluster, über das iteriert wird.
	 * @param similarity Der Schwellwert.
	 */
	public ClusterIterator(ClusterImpl cluster, float similarity) {
		this.similarity = similarity;
		queue = new LinkedList<Cluster>();
		queue.add(cluster);
	}

	@Override
	public boolean hasNext() {
		return !queue.isEmpty();
	}

	@Override
	public Cluster next() {
		if (!hasNext()) {
		    return null;
		}
		Cluster cluster = queue.poll();
		// Schwellwert für innere Knoten überprüfen.
		if (cluster.getLeft() != null && cluster.getRight() != null && cluster.getLinkage() < similarity) {
			// Der Cluster ist unterhalb des Schwellwertes, Kindknoten betrachten.
			queue.add(cluster.getLeft());
			queue.add(cluster.getRight());
			// Einer der neuen Knoten aus der Queue heraussuchen.
			return next();
		} else {
			// Der Cluster ist ein Blatt oder überhalb des Schwellwertes, zurückgeben.
			return cluster;
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
