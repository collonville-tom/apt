package org.tc.osgi.bundle.apt.io.utils;

import org.tc.osgi.bundle.apt.io.conf.AptIoPropertyFile;
import org.tc.osgi.bundle.apt.io.interf.model.AptType;
import org.tc.osgi.bundle.apt.io.module.service.PropertyServiceProxy;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

/**
 * Symbols.java.
 * 
 * @author Collonville Thomas
 * @version 0.0.1
 */
public final class AptSymbols {

	/**
	 * AptSymbols instance.
	 */
	private static AptSymbols instance = null;

	/**
	 * getInstance.
	 * 
	 * @return AptSymbols
	 */
	public static AptSymbols getInstance() {
		if (AptSymbols.instance == null) {
			AptSymbols.instance = new AptSymbols();
		}
		return AptSymbols.instance;
	}

	/**
	 * String CHAPTER.
	 */
	private final String CHAPTER = null;

	/**
	 * String DELIMITER.
	 */
	private final String DELIMITER = null;

	/**
	 * String ITEM.
	 */
	private final String ITEM = null;

	/**
	 * String PARAGRAPHE.
	 */
	private final String PARAGRAPHE = null;

	/**
	 * String PICTURE.
	 */
	private final String PICTURE = null;

	/**
	 * String SUBCHAPTER.
	 */
	private final String SUBCHAPTER = null;

	/**
	 * AptSymbols constructor.
	 */
	private AptSymbols() {

	}

	/**
	 * aptType.
	 * 
	 * @param chaine AptType
	 * @return String
	 * @throws FieldTrackingAssignementException
	 */
	public AptType aptType(final String chaine) throws FieldTrackingAssignementException {
		if (chaine.startsWith(getITEM())) {
			return AptType.ITEM;
		}
		if (chaine.startsWith(getSUBCHAPTER())) {
			return AptType.SUBCHAPTER;
		}
		if (chaine.startsWith(getCHAPTER())) {
			return AptType.CHAPTER;
		}
		if (chaine.startsWith(getPARAGRAPHE())) {
			return AptType.PARAGRAPHE;
		}
		if (chaine.startsWith(getDELIMITER())) {
			return AptType.DELIMITER;
		}
		if (chaine.startsWith(getPICTURE())) {
			return AptType.PICTURE;
		}
		if (chaine.length() == 0) {
			return AptType.EMPTYLINE;
		}
		return AptType.UNKNOW;
	}

	/**
	 * getCHAPTER.
	 * 
	 * @return String
	 * @throws FieldTrackingAssignementException
	 */
	public String getCHAPTER() throws FieldTrackingAssignementException {
		if (CHAPTER == null) {
			PropertyServiceProxy.getInstance().getYamlPropertyFile(AptIoPropertyFile.getInstance().getYamlFile()).fieldTraking(this, "CHAPTER");
		}
		return CHAPTER;
	}

	/**
	 * getDELIMITER.
	 * 
	 * @return String
	 * @throws FieldTrackingAssignementException
	 */
	public String getDELIMITER() throws FieldTrackingAssignementException {
		if (DELIMITER == null) {
			PropertyServiceProxy.getInstance().getYamlPropertyFile(AptIoPropertyFile.getInstance().getYamlFile()).fieldTraking(this, "DELIMITER");
		}
		return DELIMITER;
	}

	/**
	 * getITEM.
	 * 
	 * @return String
	 * @throws FieldTrackingAssignementException
	 */
	public String getITEM() throws FieldTrackingAssignementException {
		if (ITEM == null) {
			PropertyServiceProxy.getInstance().getYamlPropertyFile(AptIoPropertyFile.getInstance().getYamlFile()).fieldTraking(this, "ITEM");
		}
		return ITEM;
	}

	/**
	 * getPARAGRAPHE.
	 * 
	 * @return String
	 * @throws FieldTrackingAssignementException
	 */
	public String getPARAGRAPHE() throws FieldTrackingAssignementException {
		if (PARAGRAPHE == null) {
			PropertyServiceProxy.getInstance().getYamlPropertyFile(AptIoPropertyFile.getInstance().getYamlFile()).fieldTraking(this, "PARAGRAPHE");
		}
		return PARAGRAPHE;
	}

	/**
	 * getPICTURE.
	 * 
	 * @return String
	 * @throws FieldTrackingAssignementException
	 */
	public String getPICTURE() throws FieldTrackingAssignementException {
		if (PICTURE == null) {
			PropertyServiceProxy.getInstance().getYamlPropertyFile(AptIoPropertyFile.getInstance().getYamlFile()).fieldTraking(this, "PICTURE");
		}
		return PICTURE;
	}

	/**
	 * getSUBCHAPTER.
	 * 
	 * @return String
	 * @throws FieldTrackingAssignementException
	 */
	public String getSUBCHAPTER() throws FieldTrackingAssignementException {
		if (SUBCHAPTER == null) {
			PropertyServiceProxy.getInstance().getYamlPropertyFile(AptIoPropertyFile.getInstance().getYamlFile()).fieldTraking(this, "SUBCHAPTER");
		}
		return SUBCHAPTER;
	}
}
