package org.tc.osgi.bundle.apt.gui.ihm.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
 * DeleteConfirmDialog.java.
 * @author collonville thomas
 * @version
 * @track
 */
public class DeleteConfirmDialog extends JDialog {

	/**
	 * long serialVersionUID.
	 */
	private static final long serialVersionUID = 696322959216490844L;
	/**
	 * JButton jButton1.
	 */
	private JButton jButton1;
	/**
	 * JButton jButton2.
	 */
	private JButton jButton2;
	/**
	 * JLabel jLabel1.
	 */
	private JLabel jLabel1;
	/**
	 * JSeparator jSeparator1.
	 */
	private JSeparator jSeparator1;
	/**
	 * JTextField jTextField1.
	 */
	private JTextField jTextField1;
	/**
	 * AptTreePane tree.
	 */
	private final AptTreePane tree;

	/**
	 * DeleteConfirmDialog constructor.
	 * @param tree AptTreePane
	 * @param file File
	 * @throws ServiceNotLoadException
	 */
	public DeleteConfirmDialog(final AptTreePane tree, final File file) throws ServiceNotLoadException {
		super(tree.getFrame());
		this.tree = tree;

		setTitle("Delete file");
		setName("Delete file");
		setModal(true);
		setResizable(false);
		initComponents(file);
		this.setLocation(GuiUtilsServiceProxy.getInstance().getLocationControl(this).getPointInMiddleFrame());
		LoggerServiceProxy.getInstance().getLogger(DeleteConfirmDialog.class).debug("Ouverture du dialogue de suppression de fichier ou de repertoire");
	}

	/**
	 * initComponents.
	 * @param file File
	 */
	private void initComponents(final File file) {

		jButton1 = new JButton();
		jButton2 = new JButton();
		jSeparator1 = new JSeparator();
		jLabel1 = new JLabel();
		jTextField1 = new JTextField();
		jTextField1.setText(file.getAbsolutePath());
		jTextField1.setEditable(false);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		jButton1.setText("ok");
		jButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				DeleteConfirmDialog.this.setVisible(false);

				final JDialog pane = new JDialog(tree.getFrame());
				pane.setModal(true);
				pane.setResizable(false);
				JLabel label;
				pane.setLocation(GuiUtilsServiceProxy.getInstance().getLocationControl(pane).getPointInMiddleFrame());

				if (file.delete()) {
					label = new JLabel("Fichier ou repertoire supprimé");
					LoggerServiceProxy.getInstance().getLogger(DeleteConfirmDialog.class).debug("Suppression du fichier" + file.getAbsolutePath());
					// fermer le fichier si ouver dans le tableau
				} else {
					label = new JLabel("Erreur le fichier ou le repertoire n'existe pas");
					LoggerServiceProxy.getInstance().getLogger(DeleteConfirmDialog.class).debug("Erreur de suppression du fichier" + file.getAbsolutePath());
				}
				tree.reloadTree();

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
				DeleteConfirmDialog.this.setVisible(false);
			}
		});

		jLabel1.setText("Suprimerle fichier suivant :");

		final GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jSeparator1).addGroup(
			layout.createSequentialGroup().addContainerGap().addGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
					layout.createSequentialGroup().addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE).addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jButton2, GroupLayout.PREFERRED_SIZE,
						88, GroupLayout.PREFERRED_SIZE)).addComponent(jTextField1).addGroup(
					layout.createSequentialGroup().addComponent(jLabel1).addGap(0, 197, Short.MAX_VALUE))).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
			GroupLayout.Alignment.TRAILING,
			layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(
				jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(
				LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10,
				GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(
				layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jButton1).addComponent(jButton2)).addContainerGap()));

		pack();
	}
}
