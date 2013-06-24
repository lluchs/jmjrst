package edu.kit.ipd.jmjrst.view;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
public class ClusterDialog extends JDialog implements ChangeListener {
	private static final long serialVersionUID = 5875401850092115129L;
	private Main m;
	
	private JSlider similarityThresholdSlider;
	private JSpinner edgeQualityWeight;
	private JSpinner megapixelsQualityWeight;
	
	private Box clusterBox;
	
	private Comparator imageComparator;
	private Dendrogram dendrogram;
	private Cluster cluster;
	private Image[] thumbnails;

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
		similarityThresholdSlider.addChangeListener(this);
		controls.add(similarityThresholdSlider);
		
		controls.add(new JLabel("Gewicht der Auflösung"));
		megapixelsQualityWeight = new JSpinner(new SpinnerNumberModel(50, 0, 100, 1));
		megapixelsQualityWeight.addChangeListener(this);
		controls.add(megapixelsQualityWeight);

		controls.add(new JLabel("Gewicht der Kantensuche"));
		edgeQualityWeight = new JSpinner(new SpinnerNumberModel(50, 0, 100, 1));
		edgeQualityWeight.addChangeListener(this);
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
			File[] pictures = m.list.getPictures();
			imageComparator.setFiles(Arrays.asList(pictures));
			this.buildThumbnails(pictures);
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
	 * Füllt den Thumbnail-Cache mit Bildern.
	 * @param pictures Die anzupassenden Bildern.
	 * @throws IOException Falls ein Bild nicht gelesen werden kann.
	 */
	private void buildThumbnails(File[] pictures) throws IOException {
		this.thumbnails = new Image[pictures.length];
		for (int i = 0; i < pictures.length; i++) {
			BufferedImage img = ImageIO.read(pictures[i]);
			// Scale the image proportionally to maximum 128x96.
			if (img.getWidth() > img.getHeight()) {
				thumbnails[i] = img.getScaledInstance(128, -1, Image.SCALE_SMOOTH);
			} else {
				thumbnails[i] = img.getScaledInstance(-1, 96, Image.SCALE_SMOOTH);
			}
		}
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
			b.add(new JLabel("G" + i++));
			this.renderCluster(c, b);
			clusterBox.add(b);
		}
	}

	/**
	 * Malt das gegebene Cluster in eine Box.
	 * @param cluster Das Cluster.
	 * @param box Die Box.
	 */
	private void renderCluster(Cluster cluster, Box box) {
		// Iterate over the leaves.
		Iterator<Cluster> it = cluster.subClusterIterator(1.0f);
		while (it.hasNext()) {
			Cluster leaf = it.next();
			int index = leaf.getFileIndex();
			assert index >= 0 && index < thumbnails.length;
			// Find the prerendered image.
			Image thumb = thumbnails[index];
			box.add(new JLabel(new ImageIcon(thumb)));
		}
	}

	@Override
	public void stateChanged(ChangeEvent event) {
		// Re-render all clusters when something changes.
		this.renderClusters();
	}
	
	
}
