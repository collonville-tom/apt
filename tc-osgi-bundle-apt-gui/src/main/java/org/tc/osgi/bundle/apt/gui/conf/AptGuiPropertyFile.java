package org.tc.osgi.bundle.apt.gui.conf;

import org.tc.osgi.bundle.apt.gui.module.service.PropertyServiceProxy;
import org.tc.osgi.bundle.utils.interf.conf.AbstractPropertyFile;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

/**
 * AptGuiPropertyFile.java.
 * @author collonville thomas
 * @version 0.0.1
 */
public final class AptGuiPropertyFile extends AbstractPropertyFile {
	/**
	 * String APT_GUI_FILE.
	 */
	public static final String APT_GUI_FILE = "apt-gui";

	/**
	 * String BUNDLE_RACINE.
	 */
	public final static String BUNDLE_RACINE = "tc.osgi.bundle.apt-gui.";

	/**
	 * DefaultConfig conf.
	 */
	private static AptGuiPropertyFile instance = null;

	/**
	 * getInstance.
	 * @return DefaultConfig
	 * @throws EquinoxConfigException
	 * @throws FieldTrackingAssignementException
	 */
	public static AptGuiPropertyFile getInstance() {
		if (AptGuiPropertyFile.instance == null) {
			AptGuiPropertyFile.instance = new AptGuiPropertyFile();
		}
		return AptGuiPropertyFile.instance;
	}

	/**
	 * String listOfExcludeDir.
	 */
	private String listOfExcludeDir;

	/**
	 * AptConfiguration constructor.
	 */
	private AptGuiPropertyFile() {
		super(AptGuiPropertyFile.APT_GUI_FILE, AptGuiPropertyFile.class.getClassLoader());
	}

	/**
	 * @return String
	 * @see org.tc.osgi.bundle.utils.conf.AbstractPropertyFile#getBundleRacine()
	 */
	@Override
	public String getBundleRacine() {
		return AptGuiPropertyFile.BUNDLE_RACINE;
	}

	/**
	 * @return String
	 * @see org.tc.osgi.bundle.utils.conf.AbstractPropertyFile#getConfFile()
	 */
	@Override
	public String getConfFile() {
		return AptGuiPropertyFile.APT_GUI_FILE;
	}

	/**
	 * getExcludeDir.
	 * @return String
	 * @throws FieldTrackingAssignementException
	 */
	public String getExcludeDir() throws FieldTrackingAssignementException {
		if (listOfExcludeDir == null) {
			PropertyServiceProxy.getInstance().getXMLPropertyFile(getXMLFile()).fieldTraking(this, "listOfExcludeDir");
		}
		return listOfExcludeDir;
	}

	/**
	 * @return String
	 * @see org.tc.osgi.bundle.utils.conf.AbstractPropertyFile#getXMLFile()
	 */
	@Override
	public String getXMLFile() {
		return AptGuiPropertyFile.getInstance().getConfigDirectory() + getConfFile();
	}

}
