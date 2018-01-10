package org.tc.osgi.bundle.apt.gui.ihm.pane.table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.tc.osgi.bundle.apt.gui.ihm.frame.AptGuiFrame;
import org.tc.osgi.bundle.apt.gui.module.service.AptIoServiceProxy;
import org.tc.osgi.bundle.apt.gui.module.service.GuiUtilsServiceProxy;
import org.tc.osgi.bundle.apt.gui.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.apt.io.interf.exception.AptException;
import org.tc.osgi.bundle.apt.io.interf.model.AptType;
import org.tc.osgi.bundle.apt.io.interf.model.IAptObject;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

/**
 * AptTablePane.java.
 * @author collonville thomas
 * @version
 * @track
 */
public class AptTablePane extends JTable {

	/**
	 * long serialVersionUID.
	 */
	private static final long serialVersionUID = 3565539276959148312L;
	/**
	 * AptGuiFrame frame.
	 */
	private final AptGuiFrame frame;
	/**
	 * JScrollPane jScrollPane.
	 */
	private JScrollPane jScrollPane;

	/**
	 * AptTablePane constructor.
	 * @param aptGuiFrame AptGuiFrame
	 * @param file File
	 * @throws IOException
	 * @throws FieldTrackingAssignementException
	 * @throws AptException 
	 */
	public AptTablePane(final AptGuiFrame aptGuiFrame, final File file) throws IOException, FieldTrackingAssignementException, AptException {
		super();
		frame = aptGuiFrame;
		LoggerServiceProxy.getInstance().getLogger(AptTablePane.class).debug("Overture du fichier " + file.getAbsolutePath() + " dans le tabbedPane");
		buildModel(file);
		initComponent(file.getAbsolutePath());

	}

	/**
	 * buildModel.
	 * @param file File
	 * @throws IOException
	 * @throws FieldTrackingAssignementException
	 * @throws AptException 
	 */
	private void buildModel(final File file) throws IOException, FieldTrackingAssignementException, AptException {
		LoggerServiceProxy.getInstance().getLogger(AptTablePane.class).debug("Construction du model de donnée issu du fichier apt via le service apt-io");
		final DefaultTableModel model = new DefaultTableModel();
		List<IAptObject> aptObjectList = AptIoServiceProxy.getInstance().getAptObjectList(file.getAbsolutePath());
		aptObjectList = AptIoServiceProxy.getInstance().cleanObject(aptObjectList);
		final AptType[] type = new AptType[aptObjectList.size()];
		final Object[] data = aptObjectList.toArray();
		for (int i = 1; i < aptObjectList.size(); i++) {
			type[i] = aptObjectList.get(i).getAptType();
			LoggerServiceProxy.getInstance().getLogger(AptTablePane.class).debug("Entrée " + i + " avec la donnée " + data[i] + " de type " + type[i]);
		}

		model.addColumn(AptType.class.getSimpleName(), type);
		model.addColumn(IAptObject.class.getSimpleName(), data);
		setModel(model);

	}

	/**
	 * contains.
	 * @param tab int[]
	 * @param value int
	 * @return boolean
	 */
	private boolean contains(final int[] tab, final int value) {
		for (int i = 0; i < tab.length; i++) {
			if (tab[i] == value) {
				return true;
			}
		}
		return false;
	}

	/**
	 * getActualModelContent.
	 * @return AptObject
	 * @throws FieldTrackingAssignementException
	 */
	public IAptObject getActualModelContent() throws FieldTrackingAssignementException {
		final DefaultTableModel model = (DefaultTableModel) getModel();
		final IAptObject value = (IAptObject) model.getValueAt(0, 1);
		for (int i = 1; i < model.getRowCount(); i++) {
			final Object obj = model.getValueAt(i, 1);
			final Object type = model.getValueAt(i, 0);
			final String aptValue = obj.toString();
			final String aptType = type.toString();

			value.getListOfAptElement().add(AptIoServiceProxy.getInstance().newInstance(AptType.valueOf(aptType), aptValue));

		}
		return value;
	}

	private void initComponent(final String name) {
		jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(this);
		jScrollPane.setName(name);
		frame.getRight().addTab(name, jScrollPane);

		final JPopupMenu popup = new JPopupMenu();
		final JMenuItem insert = initInsertLine(popup);
		final JMenuItem remove = initRemoveLine(popup);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					insert.setEnabled(true);
					remove.setEnabled(true);
					popup.setVisible(true);
					popup.setLocation(((e.getX() - jScrollPane.getHorizontalScrollBar().getValue()) + jScrollPane.getLocationOnScreen().x) - 10,
						((e.getY() - jScrollPane.getVerticalScrollBar().getValue()) + jScrollPane.getLocationOnScreen().y) - 10);
				}
			}

			@Override
			public void mousePressed(final MouseEvent e) {
				popup.setVisible(false);
			}
		});

	}

	/**
	 * initInsertLine.
	 * @param popup JPopupMenu
	 * @return JMenuItem
	 */
	private JMenuItem initInsertLine(final JPopupMenu popup) {
		final JMenuItem newFileAction = new JMenuItem("Insert line");
		newFileAction.setEnabled(true);
		newFileAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				popup.setVisible(false);
				AptTablePane.this.getSelectedRow();
				final int[] rows = AptTablePane.this.getSelectedRows();
				final DefaultTableModel model = (DefaultTableModel) AptTablePane.this.getModel();
				final Object[] type = new Object[model.getRowCount() + rows.length];
				final Object[] data = new Object[model.getRowCount() + rows.length];
				LoggerServiceProxy.getInstance().getLogger(AptTablePane.class).debug(
					"Preparation des tableaux model de taille" + String.valueOf(model.getRowCount() + 1));

				for (int j = 0, i = 0; i < model.getRowCount(); i++, j++) {

					if (AptTablePane.this.contains(rows, i)) {
						type[j] = "EMPTYLINE";
						data[j] = "";
						j++;
						LoggerServiceProxy.getInstance().getLogger(AptTablePane.class).debug(
							"Insertion d'une donnée a l'index de la donnée " + model.getValueAt(i, 1) + " de type " + model.getValueAt(i, 0));
					}
					type[j] = model.getValueAt(i, 0);
					data[j] = model.getValueAt(i, 1);
					LoggerServiceProxy.getInstance().getLogger(AptTablePane.class).debug(
						"Reconstruction " + i + " avec la donnée " + data[i] + " de type " + type[i]);

				}

				final DefaultTableModel newModel = new DefaultTableModel();
				newModel.addColumn(AptType.class.getSimpleName(), type);
				newModel.addColumn(IAptObject.class.getSimpleName(), data);
				AptTablePane.this.setModel(newModel);

			}
		});
		newFileAction.addMouseListener(GuiUtilsServiceProxy.getInstance().getBasicMouseAdapter(newFileAction));

		popup.add(newFileAction);
		return newFileAction;
	}

	/**
	 * initRemoveLine.
	 * @param popup JPopupMenu
	 * @return JMenuItem
	 */
	private JMenuItem initRemoveLine(final JPopupMenu popup) {
		final JMenuItem newFileAction = new JMenuItem("Remove line");
		newFileAction.setEnabled(true);
		newFileAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				popup.setVisible(false);
				AptTablePane.this.getSelectedRow();
				final int[] rows = AptTablePane.this.getSelectedRows();
				final DefaultTableModel model = (DefaultTableModel) AptTablePane.this.getModel();
				final Object[] type = new Object[model.getRowCount() - rows.length];
				final Object[] data = new Object[model.getRowCount() - rows.length];
				LoggerServiceProxy.getInstance().getLogger(AptTablePane.class).debug(
					"Preparation des tableau model de taille" + String.valueOf(model.getRowCount() - rows.length));

				for (int j = 0, i = 0; i < model.getRowCount(); i++, j++) {

					if (!AptTablePane.this.contains(rows, i)) {
						type[j] = model.getValueAt(i, 0);
						data[j] = model.getValueAt(i, 1);
						LoggerServiceProxy.getInstance().getLogger(AptTablePane.class).debug(
							"Reconstruction " + i + " avec la donnée " + data[j] + " de type " + type[j]);
					} else {
						j--;
						LoggerServiceProxy.getInstance().getLogger(AptTablePane.class).debug(
							"Suppression de la donnée " + model.getValueAt(i, 1) + " de type " + model.getValueAt(i, 0));
					}

				}

				final DefaultTableModel newModel = new DefaultTableModel();
				newModel.addColumn(AptType.class.getSimpleName(), type);
				newModel.addColumn(IAptObject.class.getSimpleName(), data);
				AptTablePane.this.setModel(newModel);

			}
		});
		newFileAction.addMouseListener(GuiUtilsServiceProxy.getInstance().getBasicMouseAdapter(newFileAction));

		popup.add(newFileAction);
		return newFileAction;
	}
}
