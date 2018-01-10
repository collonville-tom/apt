package org.tc.osgi.bundle.apt.io.visitor.decoder;

import org.tc.osgi.bundle.apt.io.exception.AptDecoderException;
import org.tc.osgi.bundle.apt.io.interf.model.AptType;
import org.tc.osgi.bundle.apt.io.interf.model.IAptObject;
import org.tc.osgi.bundle.apt.io.interf.visitor.AbstractAptObjectVisitor;
import org.tc.osgi.bundle.apt.io.model.AptObject;
import org.tc.osgi.bundle.apt.io.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.apt.io.utils.AptSymbols;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

/**
 * AbstractAptDecoder.java.
 * @author Collonville Thomas
 * @version 0.0.1
 */
public class AptDecoder extends AbstractAptObjectVisitor {

	/**
	 * AptDecoder constructor.
	 */
	public AptDecoder() {
	}

	/**
	 * extractSubElement.
	 * @param object AptObject
	 * @throws FieldTrackingAssignementException
	 * @throws AptDecoderException
	 */
	protected void extractSubElement(final IAptObject object) throws FieldTrackingAssignementException, AptDecoderException {
		for (final String ligne : object.getContent()) {
			IAptObject subAptObject = null;
			if (object.getContent().indexOf(ligne) == 0) {
				subAptObject = new AptObject(AptType.TITLE, ligne);
			} else {

				switch (AptSymbols.getInstance().aptType(ligne)) {
					case EMPTYLINE:
						subAptObject = new AptObject(AptType.EMPTYLINE, ligne);
						break;
					case ITEM:
						subAptObject = new AptObject(AptType.ITEM, ligne);
						break;
					case PARAGRAPHE:
						subAptObject = new AptObject(AptType.PARAGRAPHE, ligne);
						break;
					case SUBCHAPTER:
						subAptObject = new AptObject(AptType.SUBCHAPTER, ligne);
						break;
					case DELIMITER:
						subAptObject = new AptObject(AptType.DELIMITER, ligne);
						break;
					case PICTURE:
						subAptObject = new AptObject(AptType.PICTURE, ligne);
						break;
					case CHAPTER:
						subAptObject = new AptObject(AptType.CHAPTER, ligne);
						break;
					case TITLE:
						subAptObject = new AptObject(AptType.TITLE, ligne);
						break;
					case UNKNOW:
						subAptObject = new AptObject(AptType.UNKNOW, ligne);
						break;
					default:
						throw new AptDecoderException("Tag non reconnu(" + ligne + ")");
				}
			}
			if (subAptObject != null) {
				object.getListOfAptElement().add(subAptObject);
				subAptObject.setParent(object);
			}
		}

	}

	/**
	 * @return String
	 * @see org.tc.osgi.bundle.utils.pattern.visitor.AbstractVisitor#toString()
	 */
	@Override
	public String toString() {
		return "AptDecoder Visitor";
	}

	/**
	 * @param arg0 AptObject
	 * @see org.tc.osgi.bundle.utils.pattern.visitor.AbstractVisitor#visit(org.tc.osgi.bundle.utils.pattern.visitor.IVisitable)
	 */
	@Override
	public void visit(final IAptObject arg0) {
		try {
			extractSubElement(arg0);
		} catch (AptDecoderException | FieldTrackingAssignementException e) {
			LoggerServiceProxy.getInstance().getLogger(AptDecoder.class).error(e.getMessage(), e);
		}

	}
}
