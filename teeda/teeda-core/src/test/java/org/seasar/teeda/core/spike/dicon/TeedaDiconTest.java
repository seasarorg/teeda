package org.seasar.teeda.core.spike.dicon;

import javax.faces.context.ExternalContext;
import javax.servlet.ServletContext;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.container.impl.S2ContainerImpl;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletResponse;
import org.seasar.framework.mock.servlet.MockHttpServletResponseImpl;
import org.seasar.framework.mock.servlet.MockServletContext;
import org.seasar.framework.mock.servlet.MockServletContextImpl;
import org.seasar.framework.unit.S2FrameworkTestCase;
import org.seasar.teeda.core.config.faces.FacesConfigurator;
import org.seasar.teeda.core.context.servlet.ServletExternalContextImpl;

public class TeedaDiconTest extends S2FrameworkTestCase {

    private S2Container orgContainer_;

    public void setUp() throws Exception {
        orgContainer_ = SingletonS2ContainerFactory.getContainer();
        S2Container container = new S2ContainerImpl();
        MockServletContext servletContext = new MockServletContextImpl("teeda");
        MockHttpServletRequest request = servletContext
                .createRequest("/hello.html");
        MockHttpServletResponse response = new MockHttpServletResponseImpl(
                request);
        container.setRequest(request);
        container.setResponse(response);
        container.setServletContext(servletContext);
        SingletonS2ContainerFactory.setContainer(container);
    }

    public void testDicon() throws Exception {
        S2Container container = SingletonS2ContainerFactory.getContainer();
        S2ContainerFactory.include(container, convertPath("teeda.dicon"));

        ServletContext servletContext = (ServletContext) container.getComponent("application");
        assertNotNull(servletContext);
        FacesConfigurator c = (FacesConfigurator) container
                .getComponent("configFilesConfigurator");
        assertNotNull(c);
        
        ExternalContext extContext = (ExternalContext) container.getComponent(ExternalContext.class);
        assertNotNull(extContext);
        assertTrue(extContext instanceof ServletExternalContextImpl);
    }

    public void tearDown() throws Exception {
        SingletonS2ContainerFactory.setContainer(orgContainer_);
    }
}
