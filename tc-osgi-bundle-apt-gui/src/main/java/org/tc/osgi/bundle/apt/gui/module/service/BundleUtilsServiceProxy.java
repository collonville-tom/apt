package org.tc.osgi.bundle.apt.gui.module.service;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.context.IBundleCommand;
import org.tc.osgi.bundle.utils.interf.module.service.IBundleUtilsService;

/**
 * UtilsServiceImpl.java.
 *
 * @author Collonville Thomas
 * @version 0.0.5
 * @track SDD_BUNDLE_UTILS_100
 */
public class BundleUtilsServiceProxy implements IBundleUtilsService {

	private static BundleUtilsServiceProxy instance = null;

	public static BundleUtilsServiceProxy getInstance() {
		if (BundleUtilsServiceProxy.instance == null) {
			BundleUtilsServiceProxy.instance = new BundleUtilsServiceProxy();
		}
		return BundleUtilsServiceProxy.instance;
	}

	private IBundleUtilsService service = null;

	public IBundleUtilsService getService() {
		return service;
	}

	public void setService(IBundleUtilsService service) {
		this.service = service;
	}

	private BundleUtilsServiceProxy() {

	}

	@Override
	public BundleContext getBundleContext() throws FieldTrackingAssignementException, MalformedURLException, RemoteException, NotBoundException,
		NumberFormatException, UnknownHostException {
		return service.getBundleContext();
	}

	@Override
	public IBundleCommand getBundleKiller() {
		return service.getBundleKiller();
	}

	@Override
	public IBundleCommand getBundleStarter() {
		return service.getBundleStarter();
	}

	@Override
	public void getClassloaderContent(ClassLoader loader) {
		service.getClassloaderContent(loader);

	}

	@Override
	public <T> void registerService(Class<T> _class, T instance, BundleContext context, BundleActivator activator) {
		service.registerService(_class, instance, context, activator);

	}

	@Override
	public void unregister(Class _class, BundleActivator activator) {
		service.unregister(_class, activator);

	}

}