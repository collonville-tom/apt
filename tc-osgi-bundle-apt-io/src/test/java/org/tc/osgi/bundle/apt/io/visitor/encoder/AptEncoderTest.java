package org.tc.osgi.bundle.apt.io.visitor.encoder;

import org.junit.Test;
import org.tc.osgi.bundle.apt.io.interf.model.AptType;
import org.tc.osgi.bundle.apt.io.model.AptObject;
import org.tc.osgi.bundle.apt.io.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.apt.io.module.service.PropertyServiceProxy;
import org.tc.osgi.bundle.utils.module.service.impl.LoggerUtilsServiceImpl;
import org.tc.osgi.bundle.utils.module.service.impl.PropertyUtilsServiceImpl;

/**
 * AptEncoderTest.java.
 * @author Collonville Thomas
 * @version 0.0.1
 * @req STD_BUNDLE_APT_CONNECTOR_030
 * @track SRS_BUNDLE_APT_CONNECTOR_020, SRS_BUNDLE_APT_CONNECTOR_030
 */
public class AptEncoderTest {

	/**
	 * test.
	 */
	@Test
	public void test() {
		LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
		PropertyServiceProxy.getInstance().setService(new PropertyUtilsServiceImpl());
		final AptObject file = new AptObject(AptType.FILE, "Fichier");
		file.getListOfAptElement().add(new AptObject(AptType.TITLE, "Titre"));
		file.getListOfAptElement().add(new AptObject(AptType.PARAGRAPHE, "Blabla1"));
		file.getListOfAptElement().add(new AptObject(AptType.CHAPTER, "Chapter1"));
		file.getListOfAptElement().add(new AptObject(AptType.PARAGRAPHE, "Blabla2"));
		file.getListOfAptElement().add(new AptObject(AptType.SUBCHAPTER, "SubChapter1"));
		file.getListOfAptElement().add(new AptObject(AptType.PARAGRAPHE, "Blabla3"));
		file.getListOfAptElement().add(new AptObject(AptType.ITEM, "Toto"));
		file.getListOfAptElement().add(new AptObject(AptType.ITEM, "Tata"));
		file.getListOfAptElement().add(new AptObject(AptType.SUBCHAPTER, "SubChapter2"));
		file.getListOfAptElement().add(new AptObject(AptType.PARAGRAPHE, "Blabla4"));
		file.getListOfAptElement().add(new AptObject(AptType.ITEM, "Tito"));
		file.getListOfAptElement().add(new AptObject(AptType.ITEM, "Tutu"));
		file.getListOfAptElement().add(new AptObject(AptType.CHAPTER, "Chapter2"));
		file.getListOfAptElement().add(new AptObject(AptType.PARAGRAPHE, "Blabla4"));

		final AptEncoder encod = new AptEncoder();
		file.accept(encod);
		LoggerServiceProxy.getInstance().getLogger(AptEncoder.class).debug(encod.getBuff().toString());
		System.out.println(encod.getBuff().toString());

	}
}
