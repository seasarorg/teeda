package org.seasar.teeda.core.webapp;

import javax.faces.FactoryFinder;

import org.seasar.framework.container.servlet.PortletExtendedS2ContainerServlet;

public class TeedaPortletExtendedServlet extends
        PortletExtendedS2ContainerServlet {

    private static final long serialVersionUID = 2290614456118342149L;

    public TeedaPortletExtendedServlet() {
        super();
    }

    public void init() {
        super.init();
        TeedaInitializer initializer = new TeedaInitializer();
        initializer.setServletContext(getServletContext());
        initializer.initializeFaces();
    }

    public void destroy() {
        super.destroy();
        FactoryFinder.releaseFactories();
    }
}
