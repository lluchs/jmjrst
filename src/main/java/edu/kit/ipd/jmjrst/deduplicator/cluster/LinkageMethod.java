/**
 *  Vorhandenen Quelltext der Schnittstellendatei nicht ändern!
 *  Sie dürfen weitere Methoden und Kommentare hinzufügen.
 *  
 *  Implementieren Sie die Distanzmethoden "Complete Linkage" und "Average Linkage" 
 *  gemäß http://de.wikipedia.org/wiki/Hierarchische_Clusteranalyse.
 *  
 *  Klassenname der implementierenden Klasse für "Complete Linkage":
 *  edu.kit.ipd.jmjrst.deduplicator.cluster.CompleteLinkage
 *  
 *  Klassenname der implementierenden Klasse für "Average Linkage":
 *  edu.kit.ipd.jmjrst.deduplicator.cluster.AvgLinkage
 */
package edu.kit.ipd.jmjrst.deduplicator.cluster;

/**
 * Schnittstelle für Linkage-Verfahren.
 * 
 * @author tk
 * 
 */
public interface LinkageMethod {

	/**
	 * Liefert den neuen Verbindungsgrad zwischen einem bleibenden Element und
	 * dem neu gebildeten Cluster.
	 * 
	 * Erklärung zur Verwendung: Beim Bau eines Dendrogramms werden schrittweise
	 * die am nächsten beieinander liegen Cluster zu einem neuen
	 * zusammengefasst, d. h. aus zwei Elementen wird eines. Nun muss für alle
	 * bislang unbeteiligten Elemente der Verbindungsgrad zum neuen berechnet
	 * werden, wobei die Verbindungsgrade zu den ursprünglichen Elementen als
	 * Grundlage dienen. (In unserem Beispiel entspricht der Verbindungsgrad der
	 * berechneten Ähnlichkeit.)
	 * 
	 * Beispiel: Drei Cluster A, B, C. Verbindungsgrad A-B: 0.8, Verbindungsgrad
	 * A-C 0.4, Verbindungsgrad B-C 0.5. Nun würden beim Dendrogrammbau die
	 * Elemente A und B zu einem neuen Element AB zusammengefasst. Das
	 * verbleibende Element C hat nun zwei "Altverbindungsgrade" (nämlich 0.4
	 * und 0.5), aus denen der neue Verbindungsgrad berechnet wird.
	 * 
	 * Im Allgemeinen genügt es nicht, hier einfach "alte" Verbindungsgrade zu
	 * übergeben (floats), da das konkrete Verfahren zur Berechnung des neuen
	 * Verbindungsgrads ggf. auf alle bereits zusammengefassten Elemente
	 * zugreift. Daher braucht es hier "Cluster".
	 * 
	 * (Noch unschön: Die konkrete LinkageMethod muss für den Verbindungsgrad
	 * zwischen Blättern in der Ähnlichkeitsmatrix nachschauen; diese wird via
	 * setLeafSims() gesetzt.)
	 * 
	 * @param newCluster
	 *            Der neue Cluster.
	 * @param existingCluster
	 *            Ein bleibendes Element, dessen neuer Verbindungsgrad zum neuen
	 *            Cluster berechnet werden soll.
	 * @return Der resultierende neue Verbindungsgrad, je nach implementierendem
	 *         Verfahren.
	 */
	float getLinkage(Cluster newCluster, Cluster existingCluster);

	/**
	 * Setzt die Basisähnlichkeiten für die Blätter der Clusterhierarchie.
	 * 
	 * (Die Ähnlichkeit zweier Blätter der Hierarchie soll aus dieser Matrix
	 * abgelesen werden. Zwei Elemente "mitten im Baum" erhalten ihren
	 * Verbindungsgrad je nach konkreter Methode.)
	 * 
	 * @param sims
	 *            Die Ähnlichkeitsmatrix.
	 */
	void setLeafSims(float[][] sims);
}
