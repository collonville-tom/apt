package org.tc.osgi.bundle.apt.gui.module.service;

import java.io.IOException;
import java.util.List;

import org.tc.osgi.bundle.apt.io.interf.exception.AptException;
import org.tc.osgi.bundle.apt.io.interf.model.AptType;
import org.tc.osgi.bundle.apt.io.interf.model.IAptObject;
import org.tc.osgi.bundle.apt.io.interf.module.service.IAptIoService;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

public class AptIoServiceProxy implements IAptIoService {

	private static AptIoServiceProxy instance = null;

	public static AptIoServiceProxy getInstance() {
		if (AptIoServiceProxy.instance == null) {
			AptIoServiceProxy.instance = new AptIoServiceProxy();
		}
		return AptIoServiceProxy.instance;
	}

	private IAptIoService service = null;

	private AptIoServiceProxy() {

	}

	public IAptObject newInstance(AptType type, String aptValue) {
		return service.newInstance(type, aptValue);
	}

	/**
	 * @param l
	 * @return
	 * @throws FieldTrackingAssignementException
	 * @see org.tc.osgi.bundle.apt.io.module.service.IAptIoService#cleanObject(java.util.List)
	 */
	@Override
	public List<IAptObject> cleanObject(final List<IAptObject> l) throws FieldTrackingAssignementException {
		return service.cleanObject(l);
	}

	@Override
	public List<IAptObject> getAptObjectList(final String filePathName) throws AptException, IOException {
		return service.getAptObjectList(filePathName);
	}

	/**
	 * @param filePathName
	 * @param content
	 * @return
	 * @see org.tc.osgi.bundle.apt.io.module.service.IAptIoService#getAptObjectList(java.lang.String, java.util.List)
	 */
	@Override
	public List<IAptObject> getAptObjectList(final String filePathName, final List<String> content) {
		return this.getAptObjectList(filePathName, content);
	}

	/**
	 * @param aptType
	 * @return
	 * @throws FieldTrackingAssignementException
	 * @see org.tc.osgi.bundle.apt.io.module.service.IAptIoService#getAptType(java.lang.String)
	 */
	@Override
	public AptType getAptType(final String aptType) throws FieldTrackingAssignementException {
		return service.getAptType(aptType);
	}

	public IAptIoService getService() {
		return service;
	}

	/**
	 * @param filePathName
	 * @param aptObject
	 * @throws IOException
	 * @throws AptException 
	 * @see org.tc.osgi.bundle.apt.io.module.service.IAptIoService#saveAptFile(java.lang.String, org.tc.osgi.bundle.apt.io.model.AptObject)
	 */
	@Override
	public void saveAptFile(final String filePathName, final IAptObject aptObject) throws IOException, AptException {
		LoggerServiceProxy.getInstance().getLogger(AptIoServiceProxy.class).debug("saveAptFile.Sauvegarde fichier " + filePathName);
		service.saveAptFile(filePathName, aptObject);
		LoggerServiceProxy.getInstance().getLogger(AptIoServiceProxy.class).debug("Sauvegarde fichier " + filePathName + " fait");

	}

	public void setService(final IAptIoService service) {
		this.service = service;
	}

}
