package org.tc.osgi.bundle.apt.io.utils;

import java.util.List;

import org.junit.Test;
import org.tc.osgi.bundle.apt.io.AptConnector;
import org.tc.osgi.bundle.apt.io.exception.AptConnectorException;
import org.tc.osgi.bundle.apt.io.interf.model.IAptObject;
import org.tc.osgi.bundle.apt.io.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.apt.io.module.service.PropertyServiceProxy;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.module.service.impl.LoggerUtilsServiceImpl;
import org.tc.osgi.bundle.utils.module.service.impl.PropertyUtilsServiceImpl;

import junit.framework.Assert;

/**
 * AptCleanerTest.java.
 * 
 * @author Collonville Thomas
 * @version 0.0.1
 * @req STD_BUNDLE_APT_CONNECTOR_020
 * @track SRS_BUNDLE_APT_CONNECTOR_010, SRS_BUNDLE_APT_CONNECTOR_030
 */
public class AptCleanerTest {

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
			final List<IAptObject> lo = new AptCleaner().clean(connector.processAptFile());
			final StringBuffer buff = new StringBuffer();
			for (final IAptObject o : lo) {
				buff.append(o.getContent()).append(System.lineSeparator());
			}

			LoggerServiceProxy.getInstance().getLogger(AptConnector.class).debug(buff.toString());
		} catch (FieldTrackingAssignementException | AptConnectorException e) {
			Assert.fail(e.getMessage());
		}
	}

}
