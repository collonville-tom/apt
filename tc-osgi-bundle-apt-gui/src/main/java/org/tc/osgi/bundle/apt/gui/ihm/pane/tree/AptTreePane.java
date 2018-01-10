package org.tc.osgi.bundle.apt.gui.ihm.pane.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.tc.osgi.bundle.apt.gui.ihm.dialog.DeleteConfirmDialog;
import org.tc.osgi.bundle.apt.gui.ihm.dialog.NewAptFileDialog;
import org.tc.osgi.bundle.apt.gui.ihm.dialog.NewDirDialog;
import org.tc.osgi.bundle.apt.gui.ihm.frame.AptGuiFrame;
import org.tc.osgi.bundle.apt.gui.ihm.pane.table.AptTablePane;
import org.tc.osgi.bundle.apt.gui.module.service.AptIoServiceProxy;
import org.tc.osgi.bundle.apt.gui.module.service.GuiUtilsServiceProxy;
import org.tc.osgi.bundle.apt.gui.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.apt.io.interf.exception.AptException;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.module.exception.ServiceNotLoadException;

public class AptTreePane extends JTree {

	/**
	 * long serialVersionUID.
	 */
	private static final long serialVersionUID = -8929975715292928960L;

	private final AptGuiFrame frame;

	private JScrollPane jScrollPane;

	public AptTreePane(final AptGuiFrame aptGuiFrame, final File file) {
		super();
		frame = aptGuiFrame;
		setModel(new DefaultTreeModel(new AptFileTreeNode(file)));
		initComponent(file.getName());

	}

	public AptGuiFrame getFrame() {
		return frame;
	}

	public JScrollPane getjScrollPane() {
		return jScrollPane;
	}

	private void initBasicPopup() {
		final JPopupMenu popup = new JPopupMenu();

		final JMenuItem newFileAction = initNewFileAction(popup);
		final JMenuItem newDirAction = initNewDirectoryAction(popup);
		final JMenuItem openFileAction = initOpenFileAction(popup);
		final JMenuItem deleteFileAction = initDeleteFileAction(popup);
		final JMenuItem saveFileAction = initSaveFileAction(popup);
		// final JMenuItem updateAction =
		initUpdateAction(popup);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					saveFileAction.setEnabled(true);
					newFileAction.setEnabled(true);
					deleteFileAction.setEnabled(true);
					openFileAction.setEnabled(true);
					newDirAction.setEnabled(true);
					final TreePath path = AptTreePane.this.getSelectionPath();
					if (path != null) {
						final AptFileTreeNode node = (AptFileTreeNode) path.getLastPathComponent();
						if (node.isLeaf()) {
							if (node.isAptFile()) {
								newFileAction.setEnabled(false);
								newDirAction.setEnabled(false);
							} else {
								newFileAction.setEnabled(false);
								newDirAction.setEnabled(false);
								openFileAction.setEnabled(false);
								deleteFileAction.setEnabled(false);
								saveFileAction.setEnabled(false);
							}
						} else {
							openFileAction.setEnabled(false);
							saveFileAction.setEnabled(false);
						}

						popup.setVisible(true);
						// TODO utiliser les service gui-utils
						popup.setLocation(((e.getX() - jScrollPane.getHorizontalScrollBar().getValue()) + jScrollPane.getLocationOnScreen().x) - 10,
							((e.getY() - jScrollPane.getVerticalScrollBar().getValue()) + jScrollPane.getLocationOnScreen().y) - 10);
					}
				}
			}

			@Override
			public void mousePressed(final MouseEvent e) {
				popup.setVisible(false);
			}
		});
	}

	private void initComponent(final String name) {
		jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(this);
		frame.getLeft().addTab(name, jScrollPane);
		initTree();

		initBasicPopup();

	}

	private JMenuItem initDeleteFileAction(final JPopupMenu popup) {
		final JMenuItem deleteFileAction = new JMenuItem("Delete apt file");
		deleteFileAction.setEnabled(true);
		deleteFileAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				popup.setVisible(false);
				final TreePath path = AptTreePane.this.getSelectionPath();
				if (path != null) {
					final AptFileTreeNode node = (AptFileTreeNode) path.getLastPathComponent();
					DeleteConfirmDialog dialog;
					try {
						dialog = new DeleteConfirmDialog(AptTreePane.this, node.getFile());
						dialog.setVisible(true);
					} catch (final ServiceNotLoadException e1) {
						LoggerServiceProxy.getInstance().getLogger(AptTreePane.class).error("Erreur de creation de 'instance DeleteConfirmDialog", e1);
					}

				}
			}
		});
		deleteFileAction.addMouseListener(GuiUtilsServiceProxy.getInstance().getBasicMouseAdapter(deleteFileAction));
		popup.add(deleteFileAction);
		return deleteFileAction;
	}

	private JMenuItem initNewDirectoryAction(final JPopupMenu popup) {
		final JMenuItem newDirAction = new JMenuItem("New directory");
		newDirAction.setEnabled(true);
		newDirAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				popup.setVisible(false);
				final TreePath path = AptTreePane.this.getSelectionPath();
				if (path != null) {
					final AptFileTreeNode node = (AptFileTreeNode) path.getLastPathComponent();
					NewDirDialog dialog;
					try {
						dialog = new NewDirDialog(AptTreePane.this, node.getFile());
						dialog.setVisible(true);
					} catch (final ServiceNotLoadException e1) {
						LoggerServiceProxy.getInstance().getLogger(AptTreePane.class).error("Erreur de creation de 'instance NewDirDialog", e1);
					}

				}
			}
		});
		newDirAction.addMouseListener(GuiUtilsServiceProxy.getInstance().getBasicMouseAdapter(newDirAction));

		popup.add(newDirAction);
		return newDirAction;
	}

	private JMenuItem initNewFileAction(final JPopupMenu popup) {
		final JMenuItem newFileAction = new JMenuItem("New apt file");
		newFileAction.setEnabled(true);
		newFileAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				popup.setVisible(false);
				final TreePath path = AptTreePane.this.getSelectionPath();
				if (path != null) {
					final AptFileTreeNode node = (AptFileTreeNode) path.getLastPathComponent();
					NewAptFileDialog dialog;
					try {
						dialog = new NewAptFileDialog(AptTreePane.this, node.getFile());
						dialog.setVisible(true);
					} catch (final ServiceNotLoadException e1) {
						LoggerServiceProxy.getInstance().getLogger(AptTreePane.class).error("Erreur de creation de 'instance NewAptFileDialog", e1);
					}

				}

			}
		});
		newFileAction.addMouseListener(GuiUtilsServiceProxy.getInstance().getBasicMouseAdapter(newFileAction));

		popup.add(newFileAction);
		return newFileAction;
	}

	private JMenuItem initOpenFileAction(final JPopupMenu popup) {
		final JMenuItem openFileAction = new JMenuItem("Open apt file");
		openFileAction.setEnabled(true);
		openFileAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				popup.setVisible(false);
				final TreePath path = AptTreePane.this.getSelectionPath();
				if (path != null) {
					final AptFileTreeNode node = (AptFileTreeNode) path.getLastPathComponent();
					final JDialog pane = new JDialog(AptTreePane.this.getFrame());
					JLabel label;
					try {

						pane.setModal(true);
						pane.setResizable(false);
						pane.setLocation(GuiUtilsServiceProxy.getInstance().getLocationControl(pane).getPointInMiddleFrame());

						frame.addTablePane(node.getFile());

					} catch (IOException | AptException | FieldTrackingAssignementException e1) {
						label = new JLabel("Erreur lors de l'ouverture du fichier apt:" + e1.getLocalizedMessage());
						LoggerServiceProxy.getInstance().getLogger(AptTreePane.class).error(
							"Erreur lors de l'ouverture du fichier apt:" + AptTreePane.this.getName(), e1);
						pane.add(label);
						pane.setSize(label.size());

					} finally {
						pane.pack();
						pane.setVisible(false);
					}

				}
			}
		});
		openFileAction.addMouseListener(GuiUtilsServiceProxy.getInstance().getBasicMouseAdapter(openFileAction));
		popup.add(openFileAction);
		return openFileAction;

	}

	private JMenuItem initSaveFileAction(final JPopupMenu popup) {
		final JMenuItem saveFileAction = new JMenuItem("Save apt file");

		saveFileAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				popup.setVisible(false);
				final TreePath path = AptTreePane.this.getSelectionPath();
				if (path != null) {
					final AptFileTreeNode node = (AptFileTreeNode) path.getLastPathComponent();
					final File file = node.getFile();

					final int index = AptTreePane.this.getFrame().getRight().indexOfTab(file.getAbsolutePath());

					final JScrollPane jscrollPane = (JScrollPane) AptTreePane.this.getFrame().getRight().getComponent(index);
					LoggerServiceProxy.getInstance().getLogger(AptTreePane.class).debug(
						"jscrollPane index:" + index + ", name:" + jscrollPane.getName() + ", type:" + jscrollPane.toString());

					if (jscrollPane.getName().equals(file.getAbsolutePath())) {
						final JDialog pane = new JDialog(AptTreePane.this.getFrame());
						JLabel label;
						final AptTablePane table = (AptTablePane) jscrollPane.getViewport().getView();
						try {
							pane.setModal(true);
							pane.setResizable(false);
							pane.setLocation(GuiUtilsServiceProxy.getInstance().getLocationControl(pane).getPointInMiddleFrame());
							AptIoServiceProxy.getInstance().saveAptFile(file.getAbsolutePath(), table.getActualModelContent());
							label = new JLabel("Sauvegarde de " + file.getAbsolutePath() + " réussi");
							pane.add(label);
							pane.setSize(label.size());

						} catch (FieldTrackingAssignementException | IOException | AptException e1) {
							LoggerServiceProxy.getInstance().getLogger(AptTreePane.class).error(
								"Erreur lors de la sauvegarde du fichier apt:" + AptTreePane.this.getName(), e1);
							label = new JLabel("Erreur lors de la sauvegarde du fichier apt:" + e1.getLocalizedMessage());
							pane.add(label);
							pane.setSize(label.size());
						} finally {
							pane.pack();
							pane.setVisible(true);
						}

					}

				}
			}
		});
		saveFileAction.addMouseListener(GuiUtilsServiceProxy.getInstance().getBasicMouseAdapter(saveFileAction));
		popup.add(saveFileAction);
		return saveFileAction;

	}

	private void initTree() {

		addTreeExpansionListener(new TreeExpansionListener() {

			@Override
			public void treeCollapsed(final TreeExpansionEvent event) {
				event.getPath().getLastPathComponent();

			}

			@Override
			public void treeExpanded(final TreeExpansionEvent event) {
				final AptFileTreeNode node = (AptFileTreeNode) event.getPath().getLastPathComponent();
				node.buildSubDirectory();
				((DefaultTreeModel) AptTreePane.this.getModel()).reload(node);

			}
		});
	}

	private JMenuItem initUpdateAction(final JPopupMenu popup) {
		final JMenuItem updateAction = new JMenuItem("Update directory");
		updateAction.setEnabled(true);
		updateAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				popup.setVisible(false);
				AptTreePane.this.reloadTree();
				LoggerServiceProxy.getInstance().getLogger(AptTreePane.class).debug("Mise a jour de l'arbre:" + AptTreePane.this.getName());
			}
		});
		updateAction.addMouseListener(GuiUtilsServiceProxy.getInstance().getBasicMouseAdapter(updateAction));
		popup.add(updateAction);
		return updateAction;
	}

	public void reloadTree() {
		((AptFileTreeNode) ((DefaultTreeModel) getModel()).getRoot()).rebuildSubDirectory();
		((DefaultTreeModel) AptTreePane.this.getModel()).reload((TreeNode) ((DefaultTreeModel) getModel()).getRoot());
	}

}
