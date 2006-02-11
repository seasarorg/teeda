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
package org.seasar.teeda.core.config.impl;

import java.util.Locale;

import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.render.RenderKitFactory;
import javax.faces.webapp.FacesServlet;

import junitx.framework.ObjectAssert;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.teeda.core.config.assembler.AssemblerAssembler;
import org.seasar.teeda.core.config.assembler.impl.DefaultAssembleProvider;
import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.mock.MockActionListener;
import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockApplicationFactory;
import org.seasar.teeda.core.mock.MockExternalContextImpl;
import org.seasar.teeda.core.mock.MockFacesContextFactory;
import org.seasar.teeda.core.mock.MockLifecycleFactory;
import org.seasar.teeda.core.mock.MockNavigationHandler;
import org.seasar.teeda.core.mock.MockPropertyResolver;
import org.seasar.teeda.core.mock.MockRenderKitFactory;
import org.seasar.teeda.core.mock.MockStateManager;
import org.seasar.teeda.core.mock.MockVariableResolver;
import org.seasar.teeda.core.mock.MockViewHandlerImpl;
import org.seasar.teeda.core.util.FactoryFinderUtil;

/**
 * @author shot
 */
public class ConfigFilesFacesConfiguratorTest extends S2TestCase {

    /**
     * Constructor for ConfigFilesFacesConfigurator.
     * 
     * @param name
     */
    public ConfigFilesFacesConfiguratorTest(String name) {
        super(name);
    }

    public void testConfigure1() throws Exception {
        // ## Arrange ##
        String path1 = getClass().getPackage().getName().replace('.', '/')
                + "/ConfigFilesFacesConfiguratorTest-testConfigure1_1.xml";

        String path2 = getClass().getPackage().getName().replace('.', '/')
                + "/ConfigFilesFacesConfiguratorTest-testConfigure1_2.xml";

        getServletContext().setInitParameter(FacesServlet.CONFIG_FILES_ATTR,
                path1 + ", " + path2);
        ExternalContext externalContext = new MockExternalContextImpl(
                getServletContext(), getRequest(), getResponse());

        ConfigFilesFacesConfigurator configurator = new ConfigFilesFacesConfigurator(
                externalContext);

        // ## Act ##
        FacesConfig facesConfig = configurator.configure();
        
        // ## Assert ##
        assertNotNull(facesConfig);
        
        // do actually initialize.
        AssemblerAssembler assembler = new AssemblerAssembler();
        DefaultAssembleProvider provider = new DefaultAssembleProvider();
        provider.setExternalContext(externalContext);
        assembler.setAssembleProvider(provider);

        // ## Act ##
        assembler.assembleFactories(facesConfig);

        // ## Assert ##
        ApplicationFactory appFactory = FactoryFinderUtil
                .getApplicationFactory();
        ObjectAssert.assertInstanceOf(MockApplicationFactory.class, appFactory);

        LifecycleFactory lifecycleFactory = FactoryFinderUtil
                .getLifecycleFactory();
        ObjectAssert.assertInstanceOf(MockLifecycleFactory.class,
                lifecycleFactory);

        FacesContextFactory contextFactory = FactoryFinderUtil
                .getFacesContextFactory();
        ObjectAssert.assertInstanceOf(MockFacesContextFactory.class,
                contextFactory);

        RenderKitFactory renderKitFactory = FactoryFinderUtil
                .getRenderKitFactory();
        ObjectAssert.assertInstanceOf(MockRenderKitFactory.class,
                renderKitFactory);

        // ## Act ##
        assembler.assembleApplication(facesConfig);

        // ## Assert ##
        Application app = appFactory.getApplication();
        assertNotNull(app);
        ObjectAssert.assertInstanceOf(MockApplication.class, app);

        ObjectAssert.assertInstanceOf(MockActionListener.class, app
                .getActionListener());
        ObjectAssert.assertInstanceOf(MockStateManager.class, app
                .getStateManager());
        ObjectAssert.assertInstanceOf(MockNavigationHandler.class, app
                .getNavigationHandler());
        ObjectAssert.assertInstanceOf(MockPropertyResolver.class, app
                .getPropertyResolver());
        ObjectAssert.assertInstanceOf(MockVariableResolver.class, app
                .getVariableResolver());
        ObjectAssert.assertInstanceOf(MockViewHandlerImpl.class, app
                .getViewHandler());

        assertEquals(Locale.JAPANESE, app.getDefaultLocale());
        assertEquals(Locale.ENGLISH, app.getSupportedLocales().next());
        assertEquals("hoge", app.getDefaultRenderKitId());
        assertEquals("message", app.getMessageBundle());

    }
}
