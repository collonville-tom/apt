package org.tc.osgi.bundle.apt.gui.module.activator;

import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.apt.gui.conf.AptGuiPropertyFile;
import org.tc.osgi.bundle.apt.gui.ihm.frame.AptGuiFrame;
import org.tc.osgi.bundle.apt.gui.module.service.AptIoServiceProxy;
import org.tc.osgi.bundle.apt.gui.module.service.BundleUtilsServiceProxy;
import org.tc.osgi.bundle.apt.gui.module.service.GuiUtilsServiceProxy;
import org.tc.osgi.bundle.apt.gui.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.apt.gui.module.service.PropertyServiceProxy;
import org.tc.osgi.bundle.apt.io.interf.module.service.IAptIoService;
import org.tc.osgi.bundle.gui.utils.interf.module.service.IGuiUtilsService;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.interf.module.exception.TcOsgiException;
import org.tc.osgi.bundle.utils.interf.module.service.ILoggerUtilsService;
import org.tc.osgi.bundle.utils.interf.module.service.IPropertyUtilsService;
import org.tc.osgi.bundle.utils.interf.module.utils.AbstractTcOsgiActivator;
import org.tc.osgi.bundle.utils.interf.module.utils.TcOsgiProxy;

/**
 * Activator.java.
 * @author Collonville Thomas
 * @version 0.0.1
 */
public class AptGuiActivator extends AbstractTcOsgiActivator {

	/**
	 * AptGuiFrame frame.
	 */
	private AptGuiFrame frame;

	private TcOsgiProxy<ILoggerUtilsService> iLoggerUtilsService;
	private TcOsgiProxy<IPropertyUtilsService> iPropertyUtilsService;
	private TcOsgiProxy<IAptIoService> iAptIoService;
	private TcOsgiProxy<IGuiUtilsService> iGuiUtilsService;

	private String guiUtilsDependencyBundleName;
	private String aptIoDependencyBundleName;

	@Override
	protected void checkInitBundleUtilsService(BundleContext context) throws TcOsgiException {
		throw new TcOsgiException("checkInitBundleUtilsService not implemented");

	}

	@Override
	protected void initProxys(BundleContext context) throws TcOsgiException {
		BundleUtilsServiceProxy.getInstance().setService(this.getIBundleUtilsService().getInstance());

		this.iLoggerUtilsService = new TcOsgiProxy<ILoggerUtilsService>(context, ILoggerUtilsService.class);
		LoggerServiceProxy.getInstance().setService(this.iLoggerUtilsService.getInstance());

		this.iAptIoService = new TcOsgiProxy<IAptIoService>(context, IAptIoService.class);
		AptIoServiceProxy.getInstance().setService(this.iAptIoService.getInstance());

		this.waitSpringBundle(1000);

		this.iGuiUtilsService = new TcOsgiProxy<IGuiUtilsService>(context, IGuiUtilsService.class);
		GuiUtilsServiceProxy.getInstance().setService(this.iGuiUtilsService.getInstance());

	}

	@Override
	protected void initServices(BundleContext context) throws TcOsgiException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void detachProxys(BundleContext context) throws TcOsgiException {
		this.iGuiUtilsService.close();
		this.iAptIoService.close();
		this.iPropertyUtilsService.close();
		this.iLoggerUtilsService.close();

	}

	@Override
	protected void detachServices(BundleContext context) throws TcOsgiException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void beforeStart(BundleContext context) throws TcOsgiException {
		// Cas specifique d'init du proxy des properties avant initproxy dedié
		// pour permettre l'initialisation des valeurs des bundles en
		// dependances.
		this.iPropertyUtilsService = new TcOsgiProxy<IPropertyUtilsService>(context, IPropertyUtilsService.class);
		PropertyServiceProxy.getInstance().setService(this.iPropertyUtilsService.getInstance());

		this.getIBundleUtilsService().getInstance().getBundleStarter().processOnBundle(context, this.getAptIoDependencyBundleName());
		this.getIBundleUtilsService().getInstance().getBundleStarter().processOnBundle(context, this.getGuiUtilsDependencyBundleName());

	}

	@Override
	protected void beforeStop(BundleContext context) throws TcOsgiException {
		frame.setVisible(false);
		frame = null;

	}

	@Override
	protected void afterStart(BundleContext context) throws TcOsgiException {
		frame = new AptGuiFrame(context);
		frame.setVisible(true);

	}

	@Override
	protected void afterStop(BundleContext context) throws TcOsgiException {
		// TODO Auto-generated method stub

	}

	public String getGuiUtilsDependencyBundleName() throws FieldTrackingAssignementException {
		if (guiUtilsDependencyBundleName == null) {
			PropertyServiceProxy.getInstance().getXMLPropertyFile(AptGuiPropertyFile.getInstance().getXMLFile()).fieldTraking(this,
				"guiUtilsDependencyBundleName");
		}
		return guiUtilsDependencyBundleName;
	}

	public void setGuiUtilsDependencyBundleName(String guiUtilsDependencyBundleName) {
		this.guiUtilsDependencyBundleName = guiUtilsDependencyBundleName;
	}

	public String getAptIoDependencyBundleName() throws FieldTrackingAssignementException {
		if (aptIoDependencyBundleName == null) {
			PropertyServiceProxy.getInstance().getXMLPropertyFile(AptGuiPropertyFile.getInstance().getXMLFile())
				.fieldTraking(this, "aptIoDependencyBundleName");
		}
		return aptIoDependencyBundleName;
	}

	public void setAptIoDependencyBundleName(String aptIoDependencyBundleName) {
		this.aptIoDependencyBundleName = aptIoDependencyBundleName;
	}

}
