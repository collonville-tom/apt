package org.tc.osgi.bundle.apt.io;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.tc.osgi.bundle.apt.io.exception.AptConnectorException;
import org.tc.osgi.bundle.apt.io.interf.model.IAptObject;
import org.tc.osgi.bundle.apt.io.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.apt.io.module.service.PropertyServiceProxy;
import org.tc.osgi.bundle.apt.io.utils.AptCleaner;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.module.service.impl.LoggerUtilsServiceImpl;
import org.tc.osgi.bundle.utils.module.service.impl.PropertyUtilsServiceImpl;

import junit.framework.Assert;

/**
 * AptConnectorTest.java.
 * 
 * @author Collonville Thomas
 * @version 0.0.1
 * @req STD_BUNDLE_APT_CONNECTOR_040
 * @track SRS_BUNDLE_APT_CONNECTOR_010, SRS_BUNDLE_APT_CONNECTOR_030
 */
public class AptConnectorTest {

	/**
	 * test.
	 */
	@Test
	public void test() {
		LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
		PropertyServiceProxy.getInstance().setService(new PropertyUtilsServiceImpl());
		AptConnector connector;
		try {
			connector = new AptConnector("src/test/resources/srs.apt");
			AptConnector connector2 = new AptConnector("target/srs2.apt");
			final List<IAptObject> lo = new AptCleaner().clean(connector.processAptFile());

			final StringBuffer buff = new StringBuffer();
			for (final IAptObject o : lo) {
				buff.append(o.getAptType()).append(":");
				buff.append(o.getContent()).append(System.lineSeparator());
				connector2.saveAptFile(o);
			}

			LoggerServiceProxy.getInstance().getLogger(AptConnector.class).debug(buff.toString());
			System.out.println(buff.toString());
		} catch (FieldTrackingAssignementException | AptConnectorException | IOException e) {
			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void test2() {
		LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
		PropertyServiceProxy.getInstance().setService(new PropertyUtilsServiceImpl());
		AptConnector connector;
		try {
			connector = new AptConnector("src/test/resources/srs.apt");

			List<IAptObject> lo = new AptCleaner().clean(connector.processAptFile());
			connector.setDocument(lo);
			lo = connector.getDocument();

			// List<AptObject> lo = connector.processAptFile();
			final StringBuffer buff = new StringBuffer();
			for (final IAptObject o : lo) {
				buff.append(o.getAptType()).append(":");
				buff.append(o.getContent()).append(System.lineSeparator());

			}
		} catch (FieldTrackingAssignementException | AptConnectorException e) {
			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void test3() {
		LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
		PropertyServiceProxy.getInstance().setService(new PropertyUtilsServiceImpl());
		AptConnector connector;
		try {
			connector = new AptConnector("src/test/resources/srs2.apt");

			List<IAptObject> lo = new AptCleaner().clean(connector.processAptFile());

		} catch (FieldTrackingAssignementException | AptConnectorException e) {
			Assert.assertNotNull(e);
		}

	}

	@Test
	public void test4() {
		LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
		PropertyServiceProxy.getInstance().setService(new PropertyUtilsServiceImpl());
		AptConnector connector;
		try {
			connector = new AptConnector("src/test/resources");

			List<IAptObject> lo = new AptCleaner().clean(connector.processAptFile());

		} catch (FieldTrackingAssignementException | AptConnectorException e) {
			Assert.assertNotNull(e);
		}

	}
}
