package org.tc.osgi.bundle.apt.gui.module.service;



import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JMenuItem;


import org.junit.Test;
import org.mockito.Mockito;
import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.gui.utils.module.service.impl.GuiUtilsServiceImpl;
import org.tc.osgi.bundle.utils.interf.module.exception.TcOsgiException;
import org.tc.osgi.bundle.utils.module.service.impl.LoggerUtilsServiceImpl;

import junit.framework.Assert;

public class GuiUtilsServiceProxyTest {

	@Test
	public void test() {
		
		LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
		GuiUtilsServiceImpl guiSe=new GuiUtilsServiceImpl();
		guiSe.setLoggerUtilsService(LoggerServiceProxy.getInstance());
		
		GuiUtilsServiceProxy.getInstance().setService(guiSe);
		Assert.assertNotNull(GuiUtilsServiceProxy.getInstance().getService());
		
		JMenuItem item=Mockito.mock(JMenuItem.class);
		Assert.assertNotNull(GuiUtilsServiceProxy.getInstance().getBasicMouseAdapter(item));
		
		
		BundleContext context=Mockito.mock(BundleContext.class);
		String autoBundleName="autoBundleName";
		try {
			Assert.assertNotNull(GuiUtilsServiceProxy.getInstance().getBundleClosingWindowsAdapter(context, autoBundleName));
		} catch (TcOsgiException e) {
			Assert.fail();
		}
		
		Image img=Mockito.mock(Image.class);
		Assert.assertNotNull(GuiUtilsServiceProxy.getInstance().getImagePane(img));
		
		JDialog diag=Mockito.mock(JDialog.class);
		Window win=Mockito.mock(Window.class);
		Rectangle rect=Mockito.mock(Rectangle.class);
		Point p=Mockito.mock(Point.class);
		
		
		Mockito.when(diag.getOwner()).thenReturn(win);
		Mockito.when(diag.getBounds()).thenReturn(rect);
		
		Mockito.when(win.getLocation()).thenReturn(p);
		Mockito.when(win.getBounds()).thenReturn(rect);
		
		
		
		
		Assert.assertNotNull(GuiUtilsServiceProxy.getInstance().getLocationControl(diag));
		
	}

}
