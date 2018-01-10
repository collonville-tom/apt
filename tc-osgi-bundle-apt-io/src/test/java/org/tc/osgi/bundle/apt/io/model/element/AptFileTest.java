package org.tc.osgi.bundle.apt.io.model.element;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.tc.osgi.bundle.apt.io.interf.model.AptType;
import org.tc.osgi.bundle.apt.io.interf.model.IAptObject;
import org.tc.osgi.bundle.apt.io.model.AptObject;
import org.tc.osgi.bundle.apt.io.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.apt.io.visitor.decoder.AptDecoder;
import org.tc.osgi.bundle.utils.module.service.impl.LoggerUtilsServiceImpl;

/**
 * AptFileTest.java.
 * @author Collonville Thomas
 * @version 0.0.1
 * @req STD_BUNDLE_APT_CONNECTOR_010
 * @track SRS_BUNDLE_APT_CONNECTOR_010, SRS_BUNDLE_APT_CONNECTOR_030
 */
public class AptFileTest {

	/**
	 * test.
	 */
	@Test
	public void test() {
		try {
			LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
			final BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/srs.apt")));
			final List<String> l = new ArrayList<String>();
			while (reader.ready()) {
				l.add(reader.readLine());
			}
			final IAptObject aptFile = new AptObject(AptType.FILE, l);
			aptFile.accept(new AptDecoder());
			System.out.println(aptFile.toString());

		} catch (final FileNotFoundException e) {
			Assert.fail(e.getMessage());
		} catch (final IOException e) {
			Assert.fail(e.getMessage());
		}
	}
}
