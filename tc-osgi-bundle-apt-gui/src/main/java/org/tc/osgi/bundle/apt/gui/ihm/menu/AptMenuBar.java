package org.tc.osgi.bundle.apt.gui.ihm.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.tc.osgi.bundle.apt.gui.ihm.frame.AptGuiFrame;
import org.tc.osgi.bundle.apt.gui.module.service.BundleUtilsServiceProxy;
import org.tc.osgi.bundle.apt.gui.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.utils.interf.module.exception.TcOsgiException;

/**
 * AptMenuBar.java.
 * @author collonville thomas
 * @version
 * @track
 */
public class AptMenuBar extends JMenuBar {

	/**
	 * long serialVersionUID.
	 */
	private static final long serialVersionUID = 7097384133983818112L;

	/**
	 * AptGuiFrame aptGuiFrame.
	 */
	private final AptGuiFrame aptGuiFrame;

	/**
	 * JMenu edit.
	 */
	private final JMenu edit = new JMenu();
	/**
	 * JMenu file.
	 */
	private final JMenu file = new JMenu();

	/**
	 * AptMenuBar constructor.
	 * @param aptGuiFrame AptGuiFrame
	 */
	public AptMenuBar(final AptGuiFrame aptGuiFrame) {
		super();
		this.aptGuiFrame = aptGuiFrame;
		initComponent();

	}

	/**
	 * initComponent.
	 */
	private void initComponent() {

		file.setText("File");
		this.add(file);

		edit.setText("Edit");
		this.add(edit);

		initJMenuFile();

	}

	/**
	 * initJMenuFile.
	 */
	private void initJMenuFile() {
		JMenuItem item = new JMenuItem("Import apt project directory");
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				final JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setMultiSelectionEnabled(false);
				final int returnVal = chooser.showOpenDialog(chooser);
				final File file = chooser.getSelectedFile();
				if (returnVal == JFileChooser.APPROVE_OPTION) {

					aptGuiFrame.addTreePane(file);
					LoggerServiceProxy.getInstance().getLogger(AptMenuBar.class).debug("Importation du projet:" + file.getAbsolutePath());
				}
			}
		});
		file.add(item);

		file.add(new JSeparator());
		item = new JMenuItem("Save current apt file");
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				// TODO
			}
		});
		file.add(item);

		item = new JMenuItem("Save all open apt file");
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				// TODO
			}
		});
		file.add(item);

		file.add(new JSeparator());

		item = new JMenuItem("Exit");
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					BundleUtilsServiceProxy.getInstance().getBundleKiller().processOnBundle(aptGuiFrame.getContext(), AptGuiFrame.AUTO_BUNDLE_NAME);
				} catch (final TcOsgiException e1) {
					LoggerServiceProxy.getInstance().getLogger(AptGuiFrame.class).error("Probleme lors de l'arret du bundle " + AptGuiFrame.AUTO_BUNDLE_NAME,
						e1);
				}
			}
		});
		file.add(item);
	}
}
