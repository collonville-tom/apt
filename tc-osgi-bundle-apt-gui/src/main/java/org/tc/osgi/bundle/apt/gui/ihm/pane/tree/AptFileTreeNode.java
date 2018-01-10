package org.tc.osgi.bundle.apt.gui.ihm.pane.tree;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.tc.osgi.bundle.apt.gui.conf.AptGuiPropertyFile;
import org.tc.osgi.bundle.apt.gui.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

/**
 * AptFileTreeNode.java.
 * @author collonville thomas
 * @version
 * @track
 */
public class AptFileTreeNode extends DefaultMutableTreeNode implements TreeNode {

	/**
	 * List<String> excludeDir.
	 */
	private static List<String> excludeDir = new ArrayList<String>();
	/**
	 * long serialVersionUID.
	 */
	private static final long serialVersionUID = 7074575471165182047L;

	static {
		try {
			for (final String s : AptGuiPropertyFile.getInstance().getExcludeDir().split(",")) {
				AptFileTreeNode.excludeDir.add(s);
			}
		} catch (final FieldTrackingAssignementException e) {
			LoggerServiceProxy.getInstance().getLogger(AptFileTreeNode.class).error("La liste d'exclusion ne peut etre charger", e);
		}
	}

	/**
	 * getExcludeDir.
	 * @return List<String>
	 */
	public static List<String> getExcludeDir() {
		return AptFileTreeNode.excludeDir;
	}

	/**
	 * File file.
	 */
	private final File file;

	/**
	 * AptFileTreeNode constructor.
	 * @param root File
	 */
	public AptFileTreeNode(final File root) {
		super();
		file = root;
	}

	/**
	 * buildSubDirectory.
	 */
	public void buildSubDirectory() {
		if (super.getChildCount() == 0) {
			rebuildSubDirectory();
		}
	}

	/**
	 * getFile.
	 * @return File
	 */
	public File getFile() {
		return file;
	}

	/**
	 * isAptFile.
	 * @return boolean
	 */
	public boolean isAptFile() {
		if (file.getName().endsWith(".apt")) {
			return true;
		}
		return false;
	}

	/**
	 * @return boolean
	 * @see javax.swing.tree.DefaultMutableTreeNode#isLeaf()
	 */
	@Override
	public boolean isLeaf() {
		if (file.isDirectory()) {
			return false;
		}
		return true;
	}

	/**
	 * rebuildSubDirectory.
	 */
	public void rebuildSubDirectory() {
		if (file.isDirectory()) {
			removeAllChildren();
			for (final File f : file.listFiles()) {
				if (f.isDirectory()) {
					if (!AptFileTreeNode.excludeDir.contains(f.getName())) {
						add(new AptFileTreeNode(f));
					}
				}

				else {
					if (f.getName().endsWith(".apt")) {
						add(new AptFileTreeNode(f));
					}
				}

			}

		}
	}

	/**
	 * @return String
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	@Override
	public String toString() {
		return file.getName();
	}

}
