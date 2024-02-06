package org.tc.osgi.bundle.apt.gui.ihm.frame;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.apt.gui.conf.AptGuiPropertyFile;
import org.tc.osgi.bundle.apt.gui.ihm.menu.AptMenuBar;
import org.tc.osgi.bundle.apt.gui.ihm.pane.table.AptTablePane;
import org.tc.osgi.bundle.apt.gui.ihm.pane.tree.AptTreePane;
import org.tc.osgi.bundle.apt.gui.module.service.GuiUtilsServiceProxy;
import org.tc.osgi.bundle.apt.gui.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.apt.gui.module.service.PropertyServiceProxy;
import org.tc.osgi.bundle.apt.io.interf.exception.AptException;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.exception.TcOsgiException;

/**
 * AptGuiFrame.java.
 * 
 * @author collonville thomas
 * @version
 * @track
 */
public class AptGuiFrame extends JFrame {

	// TODO mettre dans le yaml
	public static final String AUTO_BUNDLE_NAME = "tc-osgi-bundle-apt-gui";
	public static final String AUTO_BUNDLE_VERSION = "0.11.0.SNAPSHOT";

	/**
	 * long serialVersionUID.
	 */
	private static final long serialVersionUID = 5139544187821154830L;

	/**
	 * String appTitle.
	 */
	private String appTitle;

	/**
	 * BundleContext context.
	 */

	private BundleContext context;
	/**
	 * JTabbedPane left.
	 */
	private JTabbedPane left;

	/**
	 * JTabbedPane right.
	 */
	private JTabbedPane right;

	/**
	 * JSplitPane splitPane.
	 */
	private JSplitPane splitPane;

	/**
	 * AptGuiFrame constructor.
	 * 
	 * @param context BundleContext
	 * @throws TcOsgiException
	 */
	public AptGuiFrame(final BundleContext context) throws TcOsgiException {
		setTitle(getAppTitle());
		LoggerServiceProxy.getInstance().getLogger(AptGuiFrame.class).debug("Lancement de l'application:" + getTitle());
		initComponents();
		addWindowListener(GuiUtilsServiceProxy.getInstance().getBundleClosingWindowsAdapter(context, AptGuiFrame.AUTO_BUNDLE_NAME));

	}

	/**
	 * addTablePane.
	 * 
	 * @param file File
	 * @throws IOException
	 * @throws FieldTrackingAssignementException
	 * @throws AptException
	 */
	public void addTablePane(final File file) throws IOException, FieldTrackingAssignementException, AptException {
		new AptTablePane(this, file);
	}

	/**
	 * addTreePane.
	 * 
	 * @param file File
	 */
	public void addTreePane(final File file) {
		new AptTreePane(this, file);

	}

	/**
	 * getAppTitle.
	 * 
	 * @return String
	 * @throws FieldTrackingAssignementException
	 */
	private String getAppTitle() throws FieldTrackingAssignementException {
		if (appTitle == null) {
			PropertyServiceProxy.getInstance().getYamlPropertyFile(AptGuiPropertyFile.getInstance().getYamlFile()).fieldTraking(this, "appTitle");
		}
		return appTitle;
	}

	/**
	 * Getter context.
	 * 
	 * @return BundleContext
	 */
	public BundleContext getContext() {
		return context;
	}

	/**
	 * getLeft.
	 * 
	 * @return JTabbedPane
	 */
	public JTabbedPane getLeft() {
		return left;
	}

	/**
	 * getRight.
	 * 
	 * @return JTabbedPane
	 */
	public JTabbedPane getRight() {
		return right;
	}

	/**
	 * initCloseAction.
	 * 
	 * @param menu      JPopupMenu
	 * @param component Component
	 * @return JMenuItem
	 */
	private JMenuItem initCloseAction(final JPopupMenu menu, final Component component) {
		final JMenuItem action = new JMenuItem("Close file");
		action.setEnabled(true);
		action.addActionListener(new ActionListener() {

			/**
			 * @param e ActionEvent
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(final ActionEvent e) {
				LoggerServiceProxy.getInstance().getLogger(AptGuiFrame.class).debug("Fermeture de la tabbedPane:" + ((JTabbedPane) component).getSelectedComponent().getName());
				((JTabbedPane) component).remove(((JTabbedPane) component).getSelectedComponent());
				menu.setVisible(false);

			}
		});
		action.addMouseListener(GuiUtilsServiceProxy.getInstance().getBasicMouseAdapter(action));
		menu.add(action);
		return action;
	}

	/**
	 * initComponents.
	 */
	private void initComponents() {

		setJMenuBar(new AptMenuBar(this));
		initSplitPane();

		initTabbedPane();

		pack();

	}

	/**
	 * initSplitPane.
	 */
	private void initSplitPane() {
		splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setResizeWeight(0.25);
		splitPane.setAutoscrolls(true);
		splitPane.setOneTouchExpandable(true);

		final GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);

		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(splitPane, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE));
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(splitPane, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE));
	}

	/**
	 * initTabbedPane.
	 */
	private void initTabbedPane() {
		left = new JTabbedPane();
		final JPopupMenu popup = new JPopupMenu();
		left.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(final MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {

					final JMenuItem closeAction = AptGuiFrame.this.initCloseAction(popup, e.getComponent());
					closeAction.setEnabled(true);
					popup.setVisible(true);
					popup.setLocation((e.getX() + left.getLocationOnScreen().x) - 10, (e.getY() + left.getLocationOnScreen().y) - 10);
				}
			}

			@Override
			public void mouseEntered(final MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(final MouseEvent e) {
				popup.setVisible(false);

			}

			@Override
			public void mousePressed(final MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(final MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		right = new JTabbedPane();
		right.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(final MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					final JPopupMenu popup = new JPopupMenu();
					final JMenuItem closeAction = AptGuiFrame.this.initCloseAction(popup, e.getComponent());
					closeAction.setEnabled(true);
					popup.setVisible(true);
					// TODO utiliser les service gui-utils
					popup.setLocation((e.getX() + right.getLocationOnScreen().x) - 10, (e.getY() + right.getLocationOnScreen().y) - 10);

				}
			}

			@Override
			public void mouseEntered(final MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(final MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(final MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(final MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		splitPane.setRightComponent(right);
		splitPane.setLeftComponent(left);

	}

}
