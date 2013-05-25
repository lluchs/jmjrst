/**
 *  Vorhandenen Quelltext der Schnittstellendatei nicht ändern!
 *  Sie dürfen weitere Methoden und Kommentare hinzufügen.
 *  
 *  Klassenname der implementierenden Klasse:
 *  edu.kit.ipd.jmjrst.deduplicator.cluster.DendrogramImpl
 */
package edu.kit.ipd.jmjrst.deduplicator.cluster;

/**
 * Schnittstelle für ein Dendrogramm mit einer gegebenen Distanzmethode.
 * 
 * @author tk
 * 
 */
public interface Dendrogram {

	/**
	 * Erzeugt aus einer Ähnlichkeitsmatrix einen Binärbaum gemäß der
	 * hierarchischen Clusteranalyse.
	 * 
	 * @param sims
	 *            Die Ähnlichkeitsmatrix
	 * @return Die Wurzel des Baums
	 */
	Cluster buildFrom(float[][] sims);

	/**
	 * Setzt die zu verwendende Distanzmethode.
	 * 
	 * @param dm
	 *            Die Distanzmethode, z. B. complete linkage oder average
	 *            linkage.
	 */
	void setDm(LinkageMethod dm);

}