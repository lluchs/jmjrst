package edu.kit.ipd.jmjrst.view;

import java.awt.BorderLayout;
import java.awt.HeadlessException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.jis.Main;

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

		this.pack();
		this.setVisible(true);
	}
	
	
}
