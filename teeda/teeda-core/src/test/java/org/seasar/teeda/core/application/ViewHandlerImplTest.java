/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.teeda.core.application;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;

import javax.faces.render.RenderKitFactory;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletRequestImpl;
import org.seasar.framework.mock.servlet.MockServletContext;
import org.seasar.teeda.core.config.webapp.element.ServletMappingElement;
import org.seasar.teeda.core.config.webapp.element.WebappConfig;
import org.seasar.teeda.core.config.webapp.element.impl.ServletMappingElementImpl;
import org.seasar.teeda.core.config.webapp.element.impl.WebappConfigImpl;
import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockApplicationImpl;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class ViewHandlerImplTest extends TeedaTestCase {

    // TODO testing
    private MockApplication orgApp_;

    private MockHttpServletRequest orgReq_;

    protected void setUp() throws Exception {
        orgApp_ = getApplication();
        orgReq_ = getExternalContext().getMockHttpServletRequest();
    }

    protected void tearDown() throws Exception {
        setApplication(orgApp_);
        getExternalContext().setMockHttpServletRequest(orgReq_);
    }

    public void testCalculateLocale_facesContextIsNull() throws Exception {
        ViewHandlerImpl handler = new ViewHandlerImpl();
        try {
            handler.calculateLocale(null);
            fail();
        } catch (NullPointerException expected) {
            assertTrue(true);
        }
    }

    public void testGetLocaleFromSupportedLocales_isMatch() throws Exception {
        MockApplication mockApp = new MockApplicationImpl();
        mockApp.addSupportedLocale(Locale.ENGLISH);
        getFacesContext().setApplication(mockApp);
        MockHttpServletRequest req = new MockTeedaHttpServletRequestImpl(
                getServletContext());
        req.setLocale(Locale.ENGLISH);
        getExternalContext().setMockHttpServletRequest(req);
        ViewHandlerImpl handler = new ViewHandlerImpl();

        Locale l = handler.getLocaleFromSupportedLocales(getFacesContext());
        assertEquals(Locale.ENGLISH, l);
    }

    public void testGetLocaleFromSupportedLocales_isNotMatch() throws Exception {
        MockApplication mockApp = new MockApplicationImpl();
        mockApp.addSupportedLocale(Locale.ENGLISH);
        getFacesContext().setApplication(mockApp);
        MockHttpServletRequest req = new MockTeedaHttpServletRequestImpl(
                getServletContext());
        req.setLocale(Locale.FRANCE);
        getExternalContext().setMockHttpServletRequest(req);
        ViewHandlerImpl handler = new ViewHandlerImpl();

        Locale l = handler.getLocaleFromSupportedLocales(getFacesContext());
        assertNull(l);
    }

    public void testGetLocaleFromDefaultLocale_isMatch() throws Exception {
        MockApplication mockApp = new MockApplicationImpl();
        mockApp.setDefaultLocale(Locale.ENGLISH);
        getFacesContext().setApplication(mockApp);
        MockHttpServletRequest req = new MockTeedaHttpServletRequestImpl(
                getServletContext());
        req.setLocale(Locale.ENGLISH);
        getExternalContext().setMockHttpServletRequest(req);
        ViewHandlerImpl handler = new ViewHandlerImpl();

        Locale l = handler.getLocaleFromDefaultLocale(getFacesContext());
        assertEquals(Locale.ENGLISH, l);
    }

    public void testGetLocaleFromDefaultLocale_isNotMatch() throws Exception {
        MockApplication mockApp = new MockApplicationImpl();
        mockApp.setDefaultLocale(Locale.JAPAN);
        getFacesContext().setApplication(mockApp);
        MockHttpServletRequest req = new MockTeedaHttpServletRequestImpl(
                getServletContext());
        req.setLocale(Locale.ENGLISH);
        getExternalContext().setMockHttpServletRequest(req);
        ViewHandlerImpl handler = new ViewHandlerImpl();

        Locale l = handler.getLocaleFromDefaultLocale(getFacesContext());
        assertNull(l);
    }

    public void testIsMatchLocale_localeCompletelyMatch() throws Exception {
        ViewHandlerImpl handler = new ViewHandlerImpl();
        assertTrue(handler.isMatchLocale(Locale.FRANCE, Locale.FRANCE));
    }

    public void testIsMatchLocale_localeLanguageMatch() throws Exception {
        ViewHandlerImpl handler = new ViewHandlerImpl();
        assertTrue(handler.isMatchLocale(Locale.JAPAN, Locale.JAPANESE));
    }

    public void testIsMatchLocale_notMatchAtAll() throws Exception {
        ViewHandlerImpl handler = new ViewHandlerImpl();
        assertFalse(handler.isMatchLocale(Locale.ENGLISH, Locale.JAPAN));
    }

    public void testCalculateRenderKitId_fromApplication() throws Exception {
        MockApplication app = new MockApplicationImpl();
        app.setDefaultRenderKitId("hoge");
        getFacesContext().setApplication(app);
        ViewHandlerImpl handler = new ViewHandlerImpl();

        String renderKitId = handler.calculateRenderKitId(getFacesContext());

        assertEquals("hoge", renderKitId);
    }

    public void testCalculateRenderKitId_fromRenderKitFactory()
            throws Exception {
        MockApplication app = new MockApplicationImpl();
        getFacesContext().setApplication(app);
        ViewHandlerImpl handler = new ViewHandlerImpl();

        String renderKitId = handler.calculateRenderKitId(getFacesContext());

        assertEquals(RenderKitFactory.HTML_BASIC_RENDER_KIT, renderKitId);
    }

    public void testCalculateRenderKitId_facesContextIsNull() throws Exception {
        ViewHandlerImpl handler = new ViewHandlerImpl();
        try {
            handler.calculateRenderKitId(null);
            fail();
        } catch (NullPointerException expected) {
            success();
        }
    }

    public void testGetUrlPattern_notMatchExtenstion() throws Exception {
        MockHttpServletRequest req = new MockTeedaHttpServletRequestImpl(
                getServletContext(), "/teeda");
        req.setPathInfo(null);
        getExternalContext().setMockHttpServletRequest(req);
        WebappConfig webappConfig = new WebappConfigImpl();
        ServletMappingElement servletMapping = new ServletMappingElementImpl();
        servletMapping.setServletName("hoge");
        servletMapping.setUrlPattern("/teeda");
        webappConfig.addServletMappingElement(servletMapping);
        ViewHandlerImpl handler = new ViewHandlerImpl();
        String url = handler.getUrlPattern(webappConfig, getFacesContext());
        assertNull(url);
    }

    public void testGetUrlPattern_pathInfo() throws Exception {
        MockHttpServletRequest req = new MockTeedaHttpServletRequestImpl(
                getServletContext(), "/teeda");
        req.setPathInfo("path");
        getExternalContext().setMockHttpServletRequest(req);
        WebappConfig webappConfig = new WebappConfigImpl();
        ServletMappingElement servletMapping = new ServletMappingElementImpl();
        servletMapping.setServletName("hoge");
        servletMapping.setUrlPattern("/teeda.*");
        webappConfig.addServletMappingElement(servletMapping);
        ViewHandlerImpl handler = new ViewHandlerImpl();
        String url = handler.getUrlPattern(webappConfig, getFacesContext());
        assertEquals("/teeda", url);
    }

    public void testGetViewIdPath_getSimply() throws Exception {
        String servletPath = "/teeda";
        String pathInfo = "/test";
        String viewId = "/hoge.jsp";
        String urlPattern = "/teeda/*";
        String expected = "/teeda/hoge.jsp";

        settingUpExternalContextForViewHandler(servletPath, pathInfo,
                urlPattern);
        ViewHandlerImpl handler = new ViewHandlerImpl();
        String url = handler.getViewIdPath(getFacesContext(), viewId);
        assertEquals(expected, url);
    }

    public void testGetViewIdPath_getWithoutServletPath() throws Exception {
        String servletPath = "/";
        String pathInfo = "/test.jsf";
        String viewId = "/hoge.jsp";
        String urlPattern = "*.jsf";
        String expected = "/hoge.jsp";

        settingUpExternalContextForViewHandler(servletPath, pathInfo,
                urlPattern);
        ViewHandlerImpl handler = new ViewHandlerImpl();
        String url = handler.getViewIdPath(getFacesContext(), viewId);
        assertEquals(expected, url);
    }

    public void testGetViewIdPath_getWithJsfExtensionAndJsp() throws Exception {
        String servletPath = "/ext/a.jsf";
        String pathInfo = null;
        String viewId = "/teeda/hoge.jsp";
        String urlPattern = "*.jsf";
        String expected = "/teeda/hoge.jsf";

        settingUpExternalContextForViewHandler(servletPath, pathInfo,
                urlPattern);
        ViewHandlerImpl handler = new ViewHandlerImpl();
        String url = handler.getViewIdPath(getFacesContext(), viewId);
        assertEquals(expected, url);
    }

    public void testGetViewIdPath_getWithJsfExtension() throws Exception {
        String servletPath = "/ext/a.jsf";
        String pathInfo = null;
        String viewId = "/teeda/bbb";
        String urlPattern = "*.jsf";
        String expected = "/teeda/bbb.jsf";

        settingUpExternalContextForViewHandler(servletPath, pathInfo,
                urlPattern);
        ViewHandlerImpl handler = new ViewHandlerImpl();
        String url = handler.getViewIdPath(getFacesContext(), viewId);
        assertEquals(expected, url);
    }

    public void testGetActionUrl_succeed() throws Exception {
        String servletPath = "/teeda";
        String pathInfo = "/test";
        String viewId = "/hoge.jsp";
        String urlPattern = "/teeda/*";
        String expected = "/teeda/hoge.jsp";

        settingUpExternalContextForViewHandler(servletPath, pathInfo,
                urlPattern);
        MockServletContext orgServletContext = getServletContext();
        MockServletContext mock = new MockTeedaServletContextImpl("");
        MockHttpServletRequest req = new MockTeedaHttpServletRequestImpl(mock,
                servletPath);
        req.setPathInfo(pathInfo);
        getExternalContext().setMockHttpServletRequest(req);

        ViewHandlerImpl handler = new ViewHandlerImpl();
        String url = handler.getActionURL(getFacesContext(), viewId);
        assertEquals(expected, url);

        setServletContext(orgServletContext);
    }

    public void testGetActionUrl_facesContextIsNull() throws Exception {
        try {
            new ViewHandlerImpl().getActionURL(null, "a");
            fail();
        } catch (NullPointerException expected) {
            success();
        }
    }

    public void testGetActionUrl_viewIdIsNull() throws Exception {
        try {
            new ViewHandlerImpl().getActionURL(getFacesContext(), null);
            fail();
        } catch (NullPointerException expected) {
            success();
        }
    }
    
    public void testGetResourceURL_facesContextIsNull() throws Exception {
        try {
            new ViewHandlerImpl().getResourceURL(null, "a");
            fail();
        } catch (NullPointerException expected) {
            success();
        }
    }

    public void testGetResourceURL_pathIsNull() throws Exception {
        try {
            new ViewHandlerImpl().getResourceURL(getFacesContext(), null);
            fail();
        } catch (NullPointerException expected) {
            success();
        }
    }

    public void testGetResourceURL_pathNotStartWithSlash() throws Exception {
        String resourceUrl = new ViewHandlerImpl().getResourceURL(
                getFacesContext(), "hoge");
        assertEquals("hoge", resourceUrl);
    }
    
    public void testGetResourceURL_pathStartWithSlash() throws Exception {
        MockServletContext orgServletContext = getServletContext();
        MockServletContext mock = new MockTeedaServletContextImpl("/aaa");
        MockHttpServletRequest req = new MockTeedaHttpServletRequestImpl(mock,
                "/");
        getExternalContext().setMockHttpServletRequest(req);

        String resourceUrl = new ViewHandlerImpl().getResourceURL(
                getFacesContext(), "/hoge");
        assertEquals("/aaa/hoge", resourceUrl);
        setServletContext(orgServletContext);
    }

    //TODO test createView, renderView, restoreView


    // need getLocales() return Enumeration.
    private static class MockTeedaHttpServletRequestImpl extends
            MockHttpServletRequestImpl {

        private Vector locales = new Vector();

        public MockTeedaHttpServletRequestImpl(ServletContext context,
                String servletPath) {
            super(context, servletPath);
        }

        public MockTeedaHttpServletRequestImpl(ServletContext context) {
            super(context, "/hello.html");
        }

        public void setLocale(Locale locale) {
            super.setLocale(locale);
            locales.add(locale);
        }

        public Enumeration getLocales() {
            return locales.elements();
        }

    }

    private static class MockTeedaServletContextImpl implements
            MockServletContext {

        private String path_;

        public MockTeedaServletContextImpl(String path) {
            path_ = path;
        }

        public void addMimeType(String file, String type) {
        }

        public void setInitParameter(String name, String value) {
        }

        public MockHttpServletRequestImpl createRequest(String path) {
            return null;
        }

        public ServletContext getContext(String arg0) {
            return null;
        }

        public int getMajorVersion() {
            return 0;
        }

        public int getMinorVersion() {
            return 0;
        }

        public String getMimeType(String arg0) {
            return null;
        }

        public Set getResourcePaths(String arg0) {
            return null;
        }

        public URL getResource(String arg0) throws MalformedURLException {
            return null;
        }

        public InputStream getResourceAsStream(String arg0) {
            return null;
        }

        public RequestDispatcher getRequestDispatcher(String arg0) {
            return null;
        }

        public RequestDispatcher getNamedDispatcher(String arg0) {
            return null;
        }

        public Servlet getServlet(String arg0) throws ServletException {
            return null;
        }

        public Enumeration getServlets() {
            return null;
        }

        public Enumeration getServletNames() {
            return null;
        }

        public void log(String arg0) {
        }

        public void log(Exception arg0, String arg1) {
        }

        public void log(String arg0, Throwable arg1) {
        }

        public String getRealPath(String arg0) {
            return null;
        }

        public String getServerInfo() {
            return null;
        }

        public String getInitParameter(String arg0) {
            return null;
        }

        public Enumeration getInitParameterNames() {
            return null;
        }

        public Object getAttribute(String arg0) {
            return null;
        }

        public Enumeration getAttributeNames() {
            return null;
        }

        public void setAttribute(String arg0, Object arg1) {
        }

        public void removeAttribute(String arg0) {
        }

        public String getServletContextName() {
            return path_;
        }

    }

    protected void settingUpExternalContextForViewHandler(String servletPath,
            String pathInfo, String urlPattern) {
        MockHttpServletRequest req = new MockTeedaHttpServletRequestImpl(
                getServletContext(), servletPath);
        req.setPathInfo(pathInfo);
        getExternalContext().setMockHttpServletRequest(req);
        WebappConfig webappConfig = new WebappConfigImpl();
        ServletMappingElement servletMapping = new ServletMappingElementImpl();
        servletMapping.setServletName("hoge");
        servletMapping.setUrlPattern(urlPattern);
        webappConfig.addServletMappingElement(servletMapping);
        getExternalContext().getApplicationMap().put(
                WebappConfig.class.getName(), webappConfig);
    }

}
