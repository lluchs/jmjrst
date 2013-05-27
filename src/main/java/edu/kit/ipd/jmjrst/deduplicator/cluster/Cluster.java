/**
 *  Vorhandenen Quelltext der Schnittstellendatei nicht ändern!
 *  Sie dürfen weitere Methoden und Kommentare hinzufügen.
 *  
 *  Implementieren Sie die Distanzmethoden "Complete Linkage" und "Average Linkage" 
 *  gemäß http://de.wikipedia.org/wiki/Hierarchische_Clusteranalyse.
 *  
 *  Klassenname der implementierenden Klasse:
 *  edu.kit.ipd.jmjrst.deduplicator.cluster.ClusterImpl
 */
package edu.kit.ipd.jmjrst.deduplicator.cluster;

/**
 * Schnittstelle für Cluster (aus zwei Elementen)
 * 
 * Ein Cluster ist ein Binärbaum mit Verbindungsgraden.
 * 
 * @author tk
 * 
 */
public interface Cluster {
	/**
	 * Liefert das linke Element zurück.
	 * 
	 * @return Das linke Element.
	 */
	Cluster getLeft();

	/**
	 * Setzt das linke Element.
	 * 
	 * @param left
	 *            Das linke Element.
	 */
	void setLeft(Cluster left);

	/**
	 * Liefert das rechte Element zurück.
	 * 
	 * @return Das rechte Element.
	 */
	Cluster getRight();

	/**
	 * Setzt das rechte Element.
	 * 
	 * @param right
	 *            Das rechte Element.
	 */
	void setRight(Cluster right);

	/**
	 * Liefert den Verbindungsgrad zwischen dem rechten und dem linken Element
	 * zurück.
	 * 
	 * @return Der Verbindungsgrad.
	 */
	float getLinkage();

	/**
	 * Setzt den (an anderer Stelle berechneten) Verbindungsgrad zwischen dem
	 * rechten und dem linken Element.
	 * 
	 * @param dist
	 *            Der Verbindungsgrad.
	 */
	void setLinkage(float dist);

	/**
	 * Setzt den Index (aus der Ähnlichkeitsmatrix). (Sinnvoll und gedacht für
	 * Blätter.)
	 * 
	 * (Ist recht unschöne Verbindung zwischen Bildern, ihren Histogrammen und
	 * ihrer Position im Cluster.)
	 * 
	 * @param index
	 *            Der Index.
	 */
	void setFileIndex(int index);

	/**
	 * Liefert den Index (aus der Ähnlichkeitsmatrix). (Sinnvoll und gedacht für
	 * Blätter.)
	 * 
	 * @return Der Index.
	 */
	int getFileIndex();
}