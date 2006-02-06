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

import java.util.Enumeration;
import java.util.Locale;
import java.util.Vector;

import javax.faces.render.RenderKitFactory;
import javax.servlet.ServletContext;

import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletRequestImpl;
import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockApplicationImpl;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class ViewHandlerImplTest extends TeedaTestCase {

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

    public void testGetLocaleFromSupportedLocales() throws Exception {
        MockApplication mockApp = new MockApplicationImpl();
        mockApp.addSupportedLocale(Locale.ENGLISH);
        getFacesContext().setApplication(mockApp);
        MockHttpServletRequest req = new MockTeedaHttpServletRequestImpl(getServletContext());
        req.setLocale(Locale.ENGLISH);
        getExternalContext().setMockHttpServletRequest(req);
        ViewHandlerImpl handler = new ViewHandlerImpl();

        Locale l = handler.getLocaleFromSupportedLocales(getFacesContext());
        assertEquals(Locale.ENGLISH, l);
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

    private static class MockTeedaHttpServletRequestImpl extends MockHttpServletRequestImpl {

        private Vector locales = new Vector(); 
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
    
}
