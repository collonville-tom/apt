package org.tc.osgi.bundle.apt.gui.ihm.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import org.tc.osgi.bundle.apt.gui.ihm.pane.tree.AptTreePane;
import org.tc.osgi.bundle.apt.gui.module.service.GuiUtilsServiceProxy;
import org.tc.osgi.bundle.apt.gui.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.utils.interf.module.exception.ServiceNotLoadException;

/**
 * NewAptFileDialog.java.
 * @author collonville thomas
 * @version
 * @track
 */
public class NewAptFileDialog extends JDialog {

	/**
	 * long serialVersionUID.
	 */
	private static final long serialVersionUID = -8465117927699838413L;
	/**
	 * JLabel createLabel.
	 */
	protected JLabel createLabel;
	/**
	 * JButton jButton1.
	 */
	private JButton jButton1;
	/**
	 * JButton jButton2.
	 */
	private JButton jButton2;
	/**
	 * JLabel jLabel2.
	 */
	private JLabel jLabel2;
	/**
	 * JSeparator jSeparator1.
	 */
	private JSeparator jSeparator1;
	/**
	 * JTextField jTextField2.
	 */
	private JTextField jTextField2;
	/**
	 * JTextField nameField.
	 */
	protected JTextField nameField;

	/**
	 * AptTreePane tree.
	 */
	private final AptTreePane tree;

	/**
	 * NewAptFileDialog constructor.
	 * @param tree AptTreePane
	 * @param file File
	 * @throws ServiceNotLoadException
	 */
	public NewAptFileDialog(final AptTreePane tree, final File file) throws ServiceNotLoadException {
		super(tree.getFrame());
		this.tree = tree;

		setTitle("New apt file");
		setName("New apt file");
		setModal(true);
		setResizable(false);
		initComponents(file);
		this.setLocation(GuiUtilsServiceProxy.getInstance().getLocationControl(this).getPointInMiddleFrame());
		LoggerServiceProxy.getInstance().getLogger(NewAptFileDialog.class).debug("Ouverture du dialogue de creation de fichier");
	}

	/**
	 * buildFileInstance.
	 * @param file String
	 * @return File
	 */
	protected File buildFileInstance(final String file) {
		return new File(file + ".apt");
	}

	/**
	 * createFile.
	 * @param newfile File
	 * @return boolean
	 * @throws IOException
	 */
	protected boolean createFile(final File newfile) throws IOException {
		return newfile.createNewFile();
	}

	/**
	 * initComponents.
	 * @param file File
	 */
	private void initComponents(final File file) {
		jButton1 = new JButton();
		jButton2 = new JButton();
		jSeparator1 = new JSeparator();
		createLabel = new JLabel();
		nameField = new JTextField();
		jLabel2 = new JLabel();
		jTextField2 = new JTextField();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		jButton1.setText("ok");
		jButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				NewAptFileDialog.this.setVisible(false);
				final File newfile = NewAptFileDialog.this.buildFileInstance(file.getAbsolutePath() + File.separator + nameField.getText());
				final JDialog pane = new JDialog(tree.getFrame());
				pane.setModal(true);
				pane.setResizable(false);
				JLabel label;
				try {
					pane.setLocation(GuiUtilsServiceProxy.getInstance().getLocationControl(pane).getPointInMiddleFrame());

					if (NewAptFileDialog.this.createFile(newfile)) {
						label = new JLabel("Fichier ou repertoire créé");
						LoggerServiceProxy.getInstance().getLogger(NewAptFileDialog.class).debug("Creation du fichier" + file.getAbsolutePath());
						// ouvrir le fichier construit
					} else {
						label = new JLabel("Erreur le fichier ou le repertoire existe deja");
						LoggerServiceProxy.getInstance().getLogger(NewAptFileDialog.class).debug("Erreur de creation du fichier" + file.getAbsolutePath());
					}
					tree.reloadTree();
				} catch (final IOException e1) {
					label = new JLabel("Erreur lors de la creation du fichier ou du repertoire:" + e1.getLocalizedMessage());
					LoggerServiceProxy.getInstance().getLogger(NewAptFileDialog.class).error("Erreur de creation du fichier" + file.getAbsolutePath(), e1);

				}

				pane.add(label);
				pane.setSize(label.size());

				pane.pack();
				pane.setVisible(true);

			}
		});

		jButton2.setText("cancel");
		jButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				NewAptFileDialog.this.setVisible(false);
			}
		});

		createLabel.setText("Apt file name :");

		nameField.setText("newAtpFile");

		jLabel2.setText("in directory :");

		jTextField2.setText(file.getAbsolutePath());
		jTextField2.setEditable(false);

		final GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jSeparator1).addGroup(
			layout.createSequentialGroup().addContainerGap().addGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
					layout.createSequentialGroup().addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE).addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED, 218, Short.MAX_VALUE).addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 88,
						GroupLayout.PREFERRED_SIZE)).addComponent(nameField).addGroup(
					layout.createSequentialGroup().addGroup(
						layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(createLabel).addComponent(jLabel2))
						.addGap(0, 0, Short.MAX_VALUE)).addComponent(jTextField2)).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
			GroupLayout.Alignment.TRAILING,
			layout.createSequentialGroup().addContainerGap().addComponent(createLabel).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(
				nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(
				LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jTextField2,
				GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
				GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(
					layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jButton1).addComponent(jButton2)).addContainerGap()));

		pack();

	}
}
