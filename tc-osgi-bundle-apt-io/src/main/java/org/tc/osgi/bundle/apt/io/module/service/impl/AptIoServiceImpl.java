package org.tc.osgi.bundle.apt.io.module.service.impl;

import java.io.IOException;
import java.util.List;

import org.tc.osgi.bundle.apt.io.AptConnector;
import org.tc.osgi.bundle.apt.io.exception.AptConnectorException;
import org.tc.osgi.bundle.apt.io.interf.exception.AptException;
import org.tc.osgi.bundle.apt.io.interf.model.AptType;
import org.tc.osgi.bundle.apt.io.interf.model.IAptObject;
import org.tc.osgi.bundle.apt.io.interf.module.service.IAptIoService;
import org.tc.osgi.bundle.apt.io.model.AptObject;
import org.tc.osgi.bundle.apt.io.utils.AptCleaner;
import org.tc.osgi.bundle.apt.io.utils.AptSymbols;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

/**
 * AptIoServiceImpl.java.
 * @author collonville thomas
 * @version
 * @track
 */
public class AptIoServiceImpl implements IAptIoService {

	/**
	 * @param l
	 * @return
	 * @throws FieldTrackingAssignementException
	 * @see org.tc.osgi.bundle.apt.io.module.service.IAptIoService#cleanObject(java.util.List)
	 */
	@Override
	public List<IAptObject> cleanObject(final List<IAptObject> l) throws FieldTrackingAssignementException {
		return new AptCleaner().clean(l);
	}

	/**
	 * @param filePathName String
	 * @return List<AptObject>
	 * @throws IOException
	 * @throws AptConnectorException
	 * @see org.tc.osgi.bundle.apt.io.module.service.IAptIoService#getAptObjectList(java.lang.String)
	 */
	@Override
	public List<IAptObject> getAptObjectList(final String filePathName) throws IOException, AptException {
		return new AptConnector(filePathName).processAptFile();
	}

	/**
	 * @param filePathName String
	 * @param content List<String>
	 * @return List<AptObject>
	 * @throws AptConnectorException
	 * @see org.tc.osgi.bundle.apt.io.module.service.IAptIoService#getAptObjectList(java.lang.String, java.util.List)
	 */
	@Override
	public List<IAptObject> getAptObjectList(final String filePathName, final List<String> content) {
		return new AptConnector(filePathName).processAptFile(content);
	}

	/**
	 * @param aptType String
	 * @return AptType
	 * @throws FieldTrackingAssignementException
	 * @see org.tc.osgi.bundle.apt.io.module.service.IAptIoService#getAptType(java.lang.String)
	 */
	@Override
	public AptType getAptType(final String aptType) throws FieldTrackingAssignementException {
		return AptSymbols.getInstance().aptType(aptType);
	}

	/**
	 * @param filePathName String
	 * @param aptObject AptObject
	 * @throws IOException
	 * @throws AptConnectorException
	 * @see org.tc.osgi.bundle.apt.io.module.service.IAptIoService#saveAptFile(java.lang.String, org.tc.osgi.bundle.apt.io.model.AptObject)
	 */
	@Override
	public void saveAptFile(final String filePathName, final IAptObject aptObject) throws IOException, AptException {
		new AptConnector(filePathName).saveAptFile(aptObject);;
	}

	@Override
	public IAptObject newInstance(AptType type, String atpValue) {
		return new AptObject(type, atpValue);
	}
}
