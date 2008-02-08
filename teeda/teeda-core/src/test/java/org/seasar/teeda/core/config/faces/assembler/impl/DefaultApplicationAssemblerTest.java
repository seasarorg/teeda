/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.config.faces.assembler.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.seasar.teeda.core.config.faces.element.ApplicationElement;
import org.seasar.teeda.core.config.faces.element.LocaleConfigElement;
import org.seasar.teeda.core.config.faces.element.impl.ApplicationElementImpl;
import org.seasar.teeda.core.config.faces.element.impl.LocaleConfigElementImpl;
import org.seasar.teeda.core.mock.MockActionListener;
import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockNavigationHandler;
import org.seasar.teeda.core.mock.MockPropertyResolver;
import org.seasar.teeda.core.mock.MockStateManager;
import org.seasar.teeda.core.mock.MockVariableResolver;
import org.seasar.teeda.core.mock.MockViewHandlerImpl;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class DefaultApplicationAssemblerTest extends TeedaTestCase {

    /**
     * Constructor for SimpleApplicationAssemblerTest.
     * @param name
     */
    public DefaultApplicationAssemblerTest(String name) {
        super(name);
    }

    public void testAssembleApplication() throws Exception {
        // ## Arrange ##
        ApplicationElement element = new ApplicationElementImpl();
        element
                .addActionListener("org.seasar.teeda.core.mock.MockActionListener");
        element.addDefaultRenderKitId("hoge");
        LocaleConfigElement locale = new LocaleConfigElementImpl();
        locale.setDefaultLocale("en");
        locale.addSupportedLocale("ja");
        locale.addSupportedLocale("fr");
        element.addLocaleConfig(locale);
        element.addMessageBundle("foo");
        element
                .addNavigationHandler("org.seasar.teeda.core.mock.MockNavigationHandler");
        element
                .addPropertyResolver("org.seasar.teeda.core.mock.MockPropertyResolver");
        element.addStateManager("org.seasar.teeda.core.mock.MockStateManager");
        element
                .addVariableResolver("org.seasar.teeda.core.mock.MockVariableResolver");
        element
                .addViewHandler("org.seasar.teeda.core.mock.MockViewHandlerImpl");
        List list = new ArrayList();
        list.add(element);
        DefaultApplicationAssembler assembler = new DefaultApplicationAssembler(
                list);

        // ## Act ##
        assembler.assemble();

        // ## Assert ##
        MockApplication app = getApplication();
        assertTrue(app.getActionListener() instanceof MockActionListener);
        assertEquals("hoge", app.getDefaultRenderKitId());
        assertEquals(Locale.ENGLISH, app.getDefaultLocale());
        assertEquals("foo", app.getMessageBundle());
        assertTrue(app.getNavigationHandler() instanceof MockNavigationHandler);
        assertTrue(app.getPropertyResolver() instanceof MockPropertyResolver);
        assertTrue(app.getStateManager() instanceof MockStateManager);
        assertTrue(app.getVariableResolver() instanceof MockVariableResolver);
        assertTrue(app.getViewHandler() instanceof MockViewHandlerImpl);

    }
}
