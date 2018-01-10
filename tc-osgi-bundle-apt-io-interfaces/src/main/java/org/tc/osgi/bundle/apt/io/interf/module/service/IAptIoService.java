package org.tc.osgi.bundle.apt.io.interf.module.service;

import java.io.IOException;
import java.util.List;

import org.tc.osgi.bundle.apt.io.interf.exception.AptException;
import org.tc.osgi.bundle.apt.io.interf.model.AptType;
import org.tc.osgi.bundle.apt.io.interf.model.IAptObject;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

/**
 * IAptIoService.java.
 * @author collonville thomas
 * @version
 * @track
 */
public interface IAptIoService {

	public IAptObject newInstance(AptType type, String atpValue);

	/**
	 * cleanObject.
	 * @param l List<AptObject>
	 * @return List<AptObject>
	 * @throws FieldTrackingAssignementException
	 */
	public List<IAptObject> cleanObject(List<IAptObject> l) throws FieldTrackingAssignementException;

	/**
	 * getAptObjectList.
	 * @param filePathName String
	 * @return List<AptObject>
	 * @throws IOException
	 * @throws AptConnectorException
	 */
	public List<IAptObject> getAptObjectList(String filePathName) throws IOException, AptException;

	/**
	 * getAptObjectList.
	 * @param filePathName String
	 * @param content List<String>
	 * @return List<AptObject>
	 */
	public List<IAptObject> getAptObjectList(String filePathName, List<String> content);

	/**
	 * getAptType.
	 * @param aptType String
	 * @return AptType
	 * @throws FieldTrackingAssignementException
	 */
	public AptType getAptType(String aptType) throws FieldTrackingAssignementException;

	/**
	 * saveAptFile.
	 * @param filePathName String
	 * @param aptObject AptObject
	 * @throws IOException
	 * @throws AptConnectorException
	 */
	public void saveAptFile(String filePathName, IAptObject aptObject) throws IOException, AptException;
}
