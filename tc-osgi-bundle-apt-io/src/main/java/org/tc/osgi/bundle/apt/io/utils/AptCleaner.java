package org.tc.osgi.bundle.apt.io.utils;

import java.util.ArrayList;
import java.util.List;

import org.tc.osgi.bundle.apt.io.interf.model.AptType;
import org.tc.osgi.bundle.apt.io.interf.model.IAptObject;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

/**
 * AptCleaner.java.
 * @author Collonville Thomas
 * @version 0.0.1
 */
public class AptCleaner {

	/**
	 * AptCleaner constructor.
	 */
	public AptCleaner() {

	}

	/**
	 * clean.
	 * @param aptObjects List<AbstractAptObject>
	 * @return List<AbstractAptObject>
	 * @throws FieldTrackingAssignementException
	 */
	public List<IAptObject> clean(final List<IAptObject> aptObjects) throws FieldTrackingAssignementException {
		for (final IAptObject obj : aptObjects) {
			final List<String> newCleanList = new ArrayList<String>();
			// UNKNOW, EMPTYLINE, FILE
			final String chaine2clean = (String) obj.getContent().get(0);
			if (obj.getAptType().equals(AptType.CHAPTER)) {
				newCleanList.add(chaine2clean.substring(AptSymbols.getInstance().getCHAPTER().length()));
			}
			if (obj.getAptType().equals(AptType.SUBCHAPTER)) {
				newCleanList.add(chaine2clean.substring(AptSymbols.getInstance().getSUBCHAPTER().length()));
			}
			if (obj.getAptType().equals(AptType.ITEM)) {
				newCleanList.add(chaine2clean.substring(AptSymbols.getInstance().getITEM().length()));
			}
			if (obj.getAptType().equals(AptType.PARAGRAPHE)) {
				newCleanList.add(chaine2clean.substring(AptSymbols.getInstance().getPARAGRAPHE().length()));
			}
			if (obj.getAptType().equals(AptType.PICTURE)) {
				newCleanList.add(chaine2clean);
			}
			if (obj.getAptType().equals(AptType.DELIMITER)) {
				newCleanList.add(chaine2clean);
			}
			if (obj.getAptType().equals(AptType.TITLE)) {
				newCleanList.add(chaine2clean);
			}
			if (obj.getAptType().equals(AptType.UNKNOW)) {
				newCleanList.add(chaine2clean);
			}
			for (int i = 1; i < obj.getContent().size(); i++) {
				newCleanList.add(obj.getContent().get(i));
			}
			obj.setContent(newCleanList);
			obj.getListOfAptElement().clear();

		}

		return aptObjects;
	}
}
