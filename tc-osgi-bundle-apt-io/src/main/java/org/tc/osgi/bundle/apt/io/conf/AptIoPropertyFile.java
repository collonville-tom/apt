package org.tc.osgi.bundle.apt.io.conf;

import org.tc.osgi.bundle.apt.io.module.service.PropertyServiceProxy;
import org.tc.osgi.bundle.utils.interf.conf.AbstractPropertyFile;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

/**
 * AptConfiguration.java.
 * 
 * @author collonville thomas
 * @version 0.0.1
 */
public final class AptIoPropertyFile extends AbstractPropertyFile {
	/**
	 * String EQUINOXLOADERFILE.
	 */
	public static final String APT_IO_FILE = "apt-io";

	/**
	 * String BUNDLE_RACINE.
	 */
	public final static String BUNDLE_RACINE = "tc.osgi.bundle.apt-io.";

	/**
	 * DefaultConfig conf.
	 */
	private static AptIoPropertyFile instance = null;

	/**
	 * getInstance.
	 * 
	 * @return DefaultConfig
	 * @throws EquinoxConfigException
	 * @throws FieldTrackingAssignementException
	 */
	public static AptIoPropertyFile getInstance() {
		if (AptIoPropertyFile.instance == null) {
			AptIoPropertyFile.instance = new AptIoPropertyFile();
		}
		return AptIoPropertyFile.instance;
	}

	/**
	 * String baseDirectory.
	 */
	private final String aptEOL = null;

	/**
	 * AptConfiguration constructor.
	 */
	private AptIoPropertyFile() {
		super(AptIoPropertyFile.APT_IO_FILE, AptIoPropertyFile.class.getClassLoader());
	}

	/**
	 * getAptEOL.
	 * 
	 * @return String
	 * @throws FieldTrackingAssignementException
	 */
	public String getAptEOL() throws FieldTrackingAssignementException {
		if (aptEOL == null) {
			PropertyServiceProxy.getInstance().getYamlPropertyFile(getYamlFile()).fieldTraking(this, "aptEOL");
		}
		return aptEOL;
	}

	/**
	 * @return String
	 * @see org.tc.osgi.bundle.utils.conf.AbstractPropertyFile#getBundleRacine()
	 */
	@Override
	public String getBundleRacine() {
		return AptIoPropertyFile.BUNDLE_RACINE;
	}

	/**
	 * @return String
	 * @see org.tc.osgi.bundle.utils.conf.AbstractPropertyFile#getConfFile()
	 */
	@Override
	public String getConfFile() {
		return AptIoPropertyFile.APT_IO_FILE;
	}

	/**
	 * @return String
	 * @see org.tc.osgi.bundle.utils.conf.AbstractPropertyFile#getXMLFile()
	 */
	@Override
	public String getXMLFile() {
		return AptIoPropertyFile.getInstance().getConfigDirectory() + getConfFile();
	}

	@Override
	public String getYamlFile() {
		// TODO Auto-generated method stub
		return AptIoPropertyFile.getInstance().getConfigDirectory() + getConfFile();
	}

}
