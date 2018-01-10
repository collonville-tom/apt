package org.tc.osgi.bundle.apt.io.interf.model;

import java.util.List;

import org.tc.osgi.bundle.apt.io.interf.visitor.AbstractAptObjectVisitor;
import org.tc.osgi.bundle.utils.interf.pattern.visitor.IVisitable;

public interface IAptObject extends IVisitable<AbstractAptObjectVisitor> {

	public List<IAptObject> getListOfAptElement();

	public List<String> getContent();

	public AptType getAptType();

	public void setContent(final List<String> newCleanList);

	public void setParent(final IAptObject object);
}
