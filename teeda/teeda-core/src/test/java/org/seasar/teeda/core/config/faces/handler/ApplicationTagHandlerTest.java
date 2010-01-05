/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.config.faces.handler;

import java.util.List;
import java.util.Locale;

import org.seasar.teeda.core.config.faces.element.ApplicationElement;
import org.seasar.teeda.core.config.faces.element.FacesConfig;
import org.seasar.teeda.core.config.faces.element.LocaleConfigElement;
import org.seasar.teeda.core.config.faces.element.impl.ApplicationElementImpl;
import org.seasar.teeda.core.config.faces.element.impl.FacesConfigImpl;

public class ApplicationTagHandlerTest extends TagHandlerTestCase {

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Constructor for TestApplicationHandler.
     * @param arg0
     */
    public ApplicationTagHandlerTest(String arg0) {
        super(arg0);
    }

    public void testApplicationTagHandler() {
        ApplicationTagHandler handler = new ApplicationTagHandler();
        getContext().push(new FacesConfigImpl());
        ApplicationElement appElement = new ApplicationElementImpl();
        getContext().push(appElement);
        handler.end(getContext(), null);
        FacesConfig facesConfig = (FacesConfig) getContext().pop();
        List appElements = facesConfig.getApplicationElements();
        assertNotNull(appElements);
        ApplicationElement target = (ApplicationElement) appElements.get(0);
        assertTrue(target instanceof ApplicationElement);
        assertEquals(appElement, target);
    }

    public void testApplicationTagHandlerByXMLParse() throws Exception {
        FacesConfig facesConfig = parse("testApplicationTagHandler.xml");
        List list = facesConfig.getApplicationElements();
        assertEquals(1, list.size());

        ApplicationElement application = (ApplicationElement) list.get(0);
        assertEquals(1, application.getActionListeners().size());
        assertEquals("a", application.getActionListeners().get(0));

        assertEquals(1, application.getDefaultRenderKitIds().size());
        assertEquals("b", application.getDefaultRenderKitIds().get(0));

        assertEquals(1, application.getLocaleConfigs().size());
        LocaleConfigElement locale = (LocaleConfigElement) application
                .getLocaleConfigs().get(0);
        assertEquals(Locale.ENGLISH, locale.getDefaultLocale());
        assertEquals(Locale.JAPANESE, locale.getSupportedLocales().get(0));

        assertEquals(1, application.getMessageBundles().size());
        assertEquals("e", application.getMessageBundles().get(0));

        assertEquals(1, application.getNavigationHandlers().size());
        assertEquals("f", application.getNavigationHandlers().get(0));

        assertEquals(1, application.getPropertyResolvers().size());
        assertEquals("g", application.getPropertyResolvers().get(0));

        assertEquals(1, application.getStateManagers().size());
        assertEquals("h", application.getStateManagers().get(0));

        assertEquals(1, application.getVariableResolvers().size());
        assertEquals("i", application.getVariableResolvers().get(0));

        assertEquals(1, application.getViewHandlers().size());
        assertEquals("j", application.getViewHandlers().get(0));

    }

}
