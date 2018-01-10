package org.tc.osgi.bundle.apt.io.model;

import java.util.ArrayList;
import java.util.List;

import org.tc.osgi.bundle.apt.io.interf.model.AptType;
import org.tc.osgi.bundle.apt.io.interf.model.IAptObject;
import org.tc.osgi.bundle.apt.io.interf.visitor.AbstractAptObjectVisitor;

/**
 * AptObject.java.
 *
 * @author Collonville Thomas
 * @version 0.0.1
 */
public class AptObject implements IAptObject {

	/**
	 * AptType aptType.
	 */
	private final AptType aptType;

	/**
	 * List<String> content.
	 */
	private List<String> content = new ArrayList<String>();

	/**
	 * List<AbstractAptObject> listOfAptObject.
	 */
	private final List<IAptObject> listOfAptObject = new ArrayList<IAptObject>();

	/**
	 * AptObject parentAptObject.
	 */
	private IAptObject parentAptObject = null;

	/**
	 * AptObject constructor.
	 * @param type AptType
	 */
	public AptObject(final AptType type) {
		aptType = type;
	}

	/**
	 * AptObject constructor.
	 * @param aptType AptType
	 * @param content List<String>
	 */
	public AptObject(final AptType aptType, final List<String> content) {
		this(aptType);
		this.content = content;
	}

	/**
	 * AbstractAptObject constructor.
	 *
	 * @param content
	 *            String
	 */
	public AptObject(final AptType aptType, final String content) {
		this(aptType);
		this.content.add(content);
	}

	/**
	 * @param arg0
	 *            AbstractVisitor
	 * @see org.tc.osgi.bundle.utils.pattern.visitor.IVisitable#accept(org.tc.osgi.bundle.utils.pattern.visitor.AbstractVisitor)
	 */
	@Override
	public void accept(final AbstractAptObjectVisitor arg0) {
		arg0.visit(this);
	}

	/**
	 * getAptType.
	 * @return AptType
	 */
	public AptType getAptType() {
		return aptType;
	}

	/**
	 * getContent.
	 * @return List<String>
	 */
	public List<String> getContent() {
		return content;
	}

	/**
	 * getListOfAptElement.
	 *
	 * @return List<AbstractAptObject>
	 */
	public List<IAptObject> getListOfAptElement() {
		return listOfAptObject;
	}

	/**
	 * getParent.
	 * @return AptObject
	 */
	public IAptObject getParent() {
		return parentAptObject;
	}

	/**
	 * setContent.
	 * @param newCleanList List<String>
	 */
	public void setContent(final List<String> newCleanList) {
		content = newCleanList;

	}

	/**
	 * setParent.
	 * @param object AptObject
	 */
	public void setParent(final IAptObject object) {
		parentAptObject = object;
	}

	/**
	 * @return String
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuffer buff = new StringBuffer();
		for (final String s : content) {
			buff.append(s);
		}
		return buff.toString();
	}

}
