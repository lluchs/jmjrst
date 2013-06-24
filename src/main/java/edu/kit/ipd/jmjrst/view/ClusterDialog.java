package edu.kit.ipd.jmjrst.view;

import java.awt.HeadlessException;

import javax.swing.JDialog;

import org.jis.Main;

/**
 * Das Bildgruppen-Dialogfenster erlaubt es, einen Überblick über möglicherweise
 * ähnliche Bilder zu verschaffen.
 *
 */
public class ClusterDialog extends JDialog {
	private static final long serialVersionUID = 5875401850092115129L;
	private Main m;

	/**
	 * @param m
	 *          a reference to the Main Class.
	 * @throws HeadlessException ex
	 */
	public ClusterDialog(Main m) throws HeadlessException {
		super(m, m.mes.getString("Menu.17"), true);
		this.m = m;

		setVisible(true);
	}
	
	
}
