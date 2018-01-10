package org.tc.osgi.bundle.apt.io.visitor.encoder;

import org.tc.osgi.bundle.apt.io.interf.model.AptType;
import org.tc.osgi.bundle.apt.io.interf.model.IAptObject;
import org.tc.osgi.bundle.apt.io.interf.visitor.AbstractAptObjectVisitor;
import org.tc.osgi.bundle.apt.io.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.apt.io.utils.AptSymbols;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;

/**
 * AbstractAptEncoder.java.
 * @author Collonville Thomas
 * @version 0.0.1
 */
public class AptEncoder extends AbstractAptObjectVisitor {

	/**
	 * StringBuffer buff.
	 */
	private final StringBuffer buff = new StringBuffer();

	/**
	 * getBuff.
	 * @return StringBuffer
	 */
	public StringBuffer getBuff() {
		return buff;
	}

	/**
	 * push.
	 * @param pattern String
	 * @param o AptObject
	 */
	private void push(final String pattern, final IAptObject o) {

		buff.append(pattern);
		for (final String tmp : o.getContent()) {
			buff.append(tmp);
		}
		for (final IAptObject obj : o.getListOfAptElement()) {
			obj.accept(this);
		}
	}

	/**
	 * @return String
	 * @see org.tc.osgi.bundle.utils.pattern.visitor.AbstractVisitor#toString()
	 */
	@Override
	public String toString() {
		return "AptEncoder visitor";
	}

	/**
	 * @param o AptObject
	 * @see org.tc.osgi.bundle.utils.pattern.visitor.AbstractVisitor#visit(org.tc.osgi.bundle.utils.pattern.visitor.IVisitable)
	 */
	@Override
	public void visit(final IAptObject o) {
		if (o.getAptType().equals(AptType.FILE)) {
			for (final IAptObject obj : o.getListOfAptElement()) {
				obj.accept(this);
			}
		}
		if (o.getAptType().equals(AptType.TITLE)) {
			push("", o);
		}
		if (o.getAptType().equals(AptType.DELIMITER)) {
			push("", o);
		}
		if (o.getAptType().equals(AptType.PICTURE)) {
			push("", o);
		}
		try {
			if (o.getAptType().equals(AptType.ITEM)) {
				push(AptSymbols.getInstance().getITEM(), o);
			}
			if (o.getAptType().equals(AptType.PARAGRAPHE)) {
				push(AptSymbols.getInstance().getPARAGRAPHE(), o);
			}
			if (o.getAptType().equals(AptType.CHAPTER)) {
				push(AptSymbols.getInstance().getCHAPTER(), o);
			}
			if (o.getAptType().equals(AptType.SUBCHAPTER)) {
				push(AptSymbols.getInstance().getSUBCHAPTER(), o);
			}
			if (o.getAptType().equals(AptType.UNKNOW)) {
				push("", o);
			}
			buff.append(System.lineSeparator());
		} catch (final FieldTrackingAssignementException e) {
			LoggerServiceProxy.getInstance().getLogger(this.getClass()).error(e);
		}
	}
}
