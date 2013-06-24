package edu.kit.ipd.jmjrst.view;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.jis.Main;

import edu.kit.ipd.jmjrst.deduplicator.Comparator;
import edu.kit.ipd.jmjrst.deduplicator.GreyHistogramComparator;
import edu.kit.ipd.jmjrst.deduplicator.cluster.Cluster;
import edu.kit.ipd.jmjrst.deduplicator.cluster.CompleteLinkage;
import edu.kit.ipd.jmjrst.deduplicator.cluster.Dendrogram;
import edu.kit.ipd.jmjrst.deduplicator.cluster.DendrogramImpl;

/**
 * Das Bildgruppen-Dialogfenster erlaubt es, einen Überblick über möglicherweise
 * ähnliche Bilder zu verschaffen.
 *
 */
public class ClusterDialog extends JDialog {
	private static final long serialVersionUID = 5875401850092115129L;
	private Main m;
	
	private JSlider similarityThresholdSlider;
	private JSpinner edgeQualityWeight;
	private JSpinner megapixelsQualityWeight;
	
	private Box clusterBox;
	
	private Comparator imageComparator;
	private Dendrogram dendrogram;
	private Cluster cluster;

	/**
	 * @param m
	 *          a reference to the Main Class.
	 * @throws HeadlessException ex
	 */
	public ClusterDialog(Main m) throws HeadlessException {
		super(m, m.mes.getString("Menu.17"), true);
		this.m = m;
		
		// Create the upper panel for controls.
		JPanel controls = new JPanel();
		
		controls.add(new JLabel("Ähnlichkeitsgrenzwert"));
		similarityThresholdSlider = new JSlider(0, 100, 50);
		controls.add(similarityThresholdSlider);
		
		controls.add(new JLabel("Gewicht der Auflösung"));
		megapixelsQualityWeight = new JSpinner(new SpinnerNumberModel(50, 0, 100, 1));
		controls.add(megapixelsQualityWeight);

		controls.add(new JLabel("Gewicht der Kantensuche"));
		edgeQualityWeight = new JSpinner(new SpinnerNumberModel(50, 0, 100, 1));
		controls.add(edgeQualityWeight);
		
		this.add(controls, BorderLayout.NORTH);
		
		// Create the main box holding the image groups.
		clusterBox = Box.createVerticalBox();
		// The box should scroll on overflow.
		this.add(new JScrollPane(clusterBox), BorderLayout.CENTER);
		
		this.initializeDeduplicator();
		
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Erstellt die nötigen Objekte für die eigentliche Funktionalität.
	 */
	private void initializeDeduplicator() {
		imageComparator = new GreyHistogramComparator();
		try {
			imageComparator.setFiles(Arrays.asList(m.list.getPictures()));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(m,
				    "Could not load images.",
				    "Load Error",
				    JOptionPane.ERROR_MESSAGE);
			this.setVisible(false);
			return;
		}
		
		dendrogram = new DendrogramImpl(new CompleteLinkage());
		cluster = dendrogram.buildFrom(imageComparator.getSimilarities());
		this.renderClusters();
	}

	/**
	 * Füllt die clusterBox mit Bildern.
	 */
	private void renderClusters() {
		// Remove previously rendered clusters.
		clusterBox.removeAll();
		
		// Iterate over clusters.
		float sim = (float) similarityThresholdSlider.getValue() / 100;
		int i = 1;
		Iterator<Cluster> it = cluster.subClusterIterator(sim);
		while (it.hasNext()) {
			Cluster c = it.next();
			// Create a new horizontal box holding images.
			Box b = Box.createHorizontalBox();
			System.out.println("Image" + i);
			b.add(new JLabel("G" + i++));
			clusterBox.add(b);
		}
	}
	
	
}
