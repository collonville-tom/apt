package org.tc.osgi.bundle.apt.gui.ihm.dialog;

import java.io.File;
import java.io.IOException;

import org.tc.osgi.bundle.apt.gui.ihm.pane.tree.AptTreePane;
import org.tc.osgi.bundle.apt.gui.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.utils.interf.module.exception.ServiceNotLoadException;

/**
 * NewDirDialog.java.
 * @author collonville thomas
 * @version
 * @track
 */
public class NewDirDialog extends NewAptFileDialog {

	/**
	 * long serialVersionUID.
	 */
	private static final long serialVersionUID = 2819852291167474124L;

	/**
	 * NewDirDialog constructor.
	 * @param tree AptTreePane
	 * @param file File
	 * @throws ServiceNotLoadException
	 */
	public NewDirDialog(final AptTreePane tree, final File file) throws ServiceNotLoadException {
		super(tree, file);
		setTitle("New directory");
		setName("New directory");
		createLabel.setText("Directory name :");
		nameField.setText("newDirectory");
		LoggerServiceProxy.getInstance().getLogger(NewDirDialog.class).debug("Ouverture du dialogue de creation de repertoire");
	}

	/**
	 * @param file String
	 * @return File
	 * @see org.tc.osgi.bundle.apt.gui.ihm.dialog.NewAptFileDialog#buildFileInstance(java.lang.String)
	 */
	@Override
	protected File buildFileInstance(final String file) {
		return new File(file);
	}

	/**
	 * @param newfile File
	 * @return boolean
	 * @throws IOException
	 * @see org.tc.osgi.bundle.apt.gui.ihm.dialog.NewAptFileDialog#createFile(java.io.File)
	 */
	@Override
	protected boolean createFile(final File newfile) throws IOException {
		return newfile.mkdir();
	}
}
