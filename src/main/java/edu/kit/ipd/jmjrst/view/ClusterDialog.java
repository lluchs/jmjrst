package edu.kit.ipd.jmjrst.view;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import edu.kit.ipd.jmjrst.deduplicator.imagequality.EdgeQualityMeasure;
import edu.kit.ipd.jmjrst.deduplicator.imagequality.QualityMeasure;
import edu.kit.ipd.jmjrst.deduplicator.imagequality.QualityMeasureMegapixels;
import edu.kit.ipd.jmjrst.deduplicator.imagequality.SortedImages;
import edu.kit.ipd.jmjrst.deduplicator.imagequality.SortedImagesImpl;

/**
 * Das Bildgruppen-Dialogfenster erlaubt es, einen Überblick über möglicherweise
 * ähnliche Bilder zu verschaffen.
 *
 */
public class ClusterDialog extends JDialog implements ChangeListener {
	private static final int IMAGE_HEIGHT = 96;
	private static final int IMAGE_WIDTH = 128;
	private static final int IMAGE_MARGIN = 3;
	private static final int CLUSTER_NAME_PADDING = 20;
	private static final long serialVersionUID = 5875401850092115129L;
	private Main m;
	
	private JSlider similarityThresholdSlider;
	private JSpinner edgeQualityWeight;
	private JSpinner megapixelsQualityWeight;
	
	private Box clusterBox;
	
	private Comparator imageComparator;
	private Dendrogram dendrogram;
	private Cluster cluster;
	private BufferedImage[] images;
	private Image[] thumbnails;
	private Map<BufferedImage, Integer> sortingCache;
	private QualityMeasureMegapixels megapixelsQM;
	private EdgeQualityMeasure edgeQM;
	private SortedImages sorter;

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
		this.buildSortingCache();
		this.renderClusters();
	}

	/**
	 * Füllt den Thumbnail-Cache mit Bildern.
	 * @param pictures Die anzupassenden Bildern.
	 * @throws IOException Falls ein Bild nicht gelesen werden kann.
	 */
	private void buildThumbnails(File[] pictures) throws IOException {
		this.thumbnails = new Image[pictures.length];
		this.images = new BufferedImage[pictures.length];
		for (int i = 0; i < pictures.length; i++) {
			BufferedImage img = ImageIO.read(pictures[i]);
			// Save the image in the cache.
			images[i] = img;
			// Scale the image proportionally to maximum 128x96.
			if (img.getWidth() > img.getHeight()) {
				thumbnails[i] = img.getScaledInstance(IMAGE_WIDTH, -1, Image.SCALE_SMOOTH);
			} else {
				thumbnails[i] = img.getScaledInstance(-1, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
			}
		}
	}
	
	/**
	 * Baut einen Cache auf, sodass die Bilder nur einmal sortiert werden müssen.
	 */
	private void buildSortingCache() {
		SortedImages sorter = getSorter();
		sorter.setImageList(Arrays.asList(images));
		List<BufferedImage> sorted = sorter.getSortedImages();
		sortingCache = new HashMap<BufferedImage, Integer>();
		int i = 0;
		for (BufferedImage img : sorted) {
			sortingCache.put(img, i++);
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
			b.add(Box.createHorizontalStrut(CLUSTER_NAME_PADDING));
			b.add(new JLabel("G" + i++));
			b.add(Box.createHorizontalStrut(CLUSTER_NAME_PADDING));
			this.renderCluster(c, b);
			b.add(Box.createGlue());
			clusterBox.add(b);
			if (it.hasNext()) {
				clusterBox.add(Box.createVerticalStrut(IMAGE_MARGIN));
			}
		}
		clusterBox.revalidate();
		clusterBox.repaint();
	}

	/**
	 * Malt das gegebene Cluster in eine Box.
	 * @param cluster Das Cluster.
	 * @param box Die Box.
	 */
	private void renderCluster(Cluster cluster, Box box) {
		// Iterate over the leaves, building a list.
		List<BufferedImage> leaves = new LinkedList<BufferedImage>();
		Iterator<Cluster> it = cluster.subClusterIterator(1.0f);
		while (it.hasNext()) {
			Cluster leaf = it.next();
			int index = leaf.getFileIndex();
			assert index >= 0 && index < thumbnails.length;
			// Find the image.
			leaves.add(images[index]);
		}
		Collections.sort(leaves, new java.util.Comparator<BufferedImage>() {
			@Override
			public int compare(BufferedImage o1, BufferedImage o2) {
				return sortingCache.get(o2) - sortingCache.get(o1);
			}
		});
		for (Image img : leaves) {
			Image thumb = getThumbnailFor(img);
			box.add(new JLabel(new ImageIcon(thumb)));
			box.add(Box.createHorizontalStrut(IMAGE_MARGIN));
		}
	}
	
	/**
	 * Findet das Vorschaubildchen für ein bestimmtes Bild.
	 * @param img Das Bild.
	 * @return Das zugehörige Vorschaubild.
	 */
	private Image getThumbnailFor(Image img) {
		for (int i = 0; i < images.length; i++) {
			if (img == images[i]) {
				return thumbnails[i];
			}
		}
		return null;
	}

	/**
	 * Erstellt einen Bildsortierer mit eingestellten Qualitätskriterien.
	 * @return Einen fertigen Bildsortierer.
	 */
	private SortedImages getSorter() {
		if (sorter == null) {
			megapixelsQM = new QualityMeasureMegapixels();
			edgeQM = new EdgeQualityMeasure();
			sorter = new SortedImagesImpl();
			sorter.setQualityFeatures(Arrays.asList(
					new QualityMeasure[] {megapixelsQM, edgeQM}));
		}
		// Update the weight every time.
		megapixelsQM.setWeight((Integer) megapixelsQualityWeight.getValue());
		edgeQM.setWeight((Integer) edgeQualityWeight.getValue());
		return sorter;
	}

	@Override
	public void stateChanged(ChangeEvent event) {
		if (event.getSource() instanceof JSpinner) {
			// Spinner set quality measures, so resort!
			buildSortingCache();
		}
		// Re-render all clusters when something changes.
		this.renderClusters();
	}
	
	
}
