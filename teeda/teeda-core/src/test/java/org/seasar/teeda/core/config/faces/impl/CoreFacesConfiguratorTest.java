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
package org.seasar.teeda.core.config.faces.impl;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;

import junitx.framework.ObjectAssert;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.container.factory.ClassPathResourceResolver;
import org.seasar.teeda.core.application.navigation.NavigationCaseContext;
import org.seasar.teeda.core.application.navigation.NavigationContext;
import org.seasar.teeda.core.application.navigation.NavigationContextFactory;
import org.seasar.teeda.core.config.faces.assembler.AssemblerAssembler;
import org.seasar.teeda.core.config.faces.assembler.impl.DefaultAssembleProvider;
import org.seasar.teeda.core.config.faces.element.FacesConfig;
import org.seasar.teeda.core.config.faces.rule.FacesConfigTagHandlerRule;
import org.seasar.teeda.core.managedbean.ManagedBeanFactory;
import org.seasar.teeda.core.managedbean.impl.ManagedBeanFactoryImpl;
import org.seasar.teeda.core.managedbean.impl.ManagedBeanScopeSaverImpl;
import org.seasar.teeda.core.mock.MockActionListener;
import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockApplicationFactory;
import org.seasar.teeda.core.mock.MockExternalContextImpl;
import org.seasar.teeda.core.mock.MockFacesContextFactory;
import org.seasar.teeda.core.mock.MockLifecycle;
import org.seasar.teeda.core.mock.MockLifecycleFactory;
import org.seasar.teeda.core.mock.MockNavigationHandler;
import org.seasar.teeda.core.mock.MockPhaseListener;
import org.seasar.teeda.core.mock.MockPropertyResolver;
import org.seasar.teeda.core.mock.MockRenderKitFactory;
import org.seasar.teeda.core.mock.MockRenderKitImpl;
import org.seasar.teeda.core.mock.MockRenderer;
import org.seasar.teeda.core.mock.MockStateManager;
import org.seasar.teeda.core.mock.MockVariableResolver;
import org.seasar.teeda.core.mock.MockViewHandlerImpl;
import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.scope.impl.S2ScopeTranslator;
import org.seasar.teeda.core.scope.impl.ScopeManagerImpl;
import org.seasar.teeda.core.util.FactoryFinderUtil;

/**
 * @author shot
 */
public class CoreFacesConfiguratorTest extends S2TestCase {

    private static ClassPathResourceResolver resolver = new ClassPathResourceResolver();

    private static FacesConfigTagHandlerRule rule = new FacesConfigTagHandlerRule();

    /**
     * Constructor for CoreFacesConfiguratorTest.
     * 
     * @param name
     */
    public CoreFacesConfiguratorTest(String name) {
        super(name);
    }

    public void testConfigure1() throws Exception {
        // ## Arrange ##
        CoreFacesConfigurator configurator = new CoreFacesConfigurator();
        configurator.setPath(getClass().getPackage().getName()
                .replace('.', '/')
                + "/CoreFacesConfiguratorTest-testConfigure1.xml");

        // ## Act ##
        FacesConfig facesConfig = configurator.configure();

        // ## Assert ##
        assertNotNull(facesConfig);

        // do actually initialize.
        AssemblerAssembler assembler = new AssemblerAssembler();
        DefaultAssembleProvider provider = new DefaultAssembleProvider();
        ExternalContext externalContext = new MockExternalContextImpl(
                getServletContext(), getRequest(), getResponse());
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

        // ## Act ##
        assembler.assembleLifecycle(facesConfig);

        Lifecycle lifecycle = lifecycleFactory
                .getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
        assertNotNull(lifecycle);
        ObjectAssert.assertInstanceOf(MockLifecycle.class, lifecycle);

        PhaseListener[] listeners = lifecycle.getPhaseListeners();
        assertEquals(1, listeners.length);
        ObjectAssert.assertInstanceOf(MockPhaseListener.class, listeners[0]);

        // ## Arrange ##
        getContainer().register(ManagedBeanFactoryImpl.class);
        getContainer().register(ManagedBeanScopeSaverImpl.class);
        getContainer().register(ScopeManagerImpl.class);
        getContainer().register(S2ScopeTranslator.class);

        // ## Act ##
        assembler.assembleManagedBeans(facesConfig);

        // # Assert #
        ManagedBeanFactory mbFactory = (ManagedBeanFactory) getContainer()
                .getComponent(ManagedBeanFactory.class);
        Object o = mbFactory.getManagedBean("hogeBean");
        assertNotNull(o);
        ObjectAssert.assertInstanceOf(Hoge.class, o);
        Scope scope = mbFactory.getManagedBeanScope("hogeBean");
        assertEquals(Scope.APPLICATION, scope);

        // ## Act ##
        assembler.assembleRenderKits(facesConfig);

        // # Assert #
        FacesContext context = contextFactory.getFacesContext(
                getServletContext(), getRequest(), getResponse(), lifecycle);
        RenderKit renderKit = renderKitFactory.getRenderKit(context,
                "renderkitid");
        assertNotNull(renderKit);
        ObjectAssert.assertInstanceOf(MockRenderKitImpl.class, renderKit);

        Renderer renderer = renderKit.getRenderer("family", "type");
        assertNotNull(renderer);
        ObjectAssert.assertInstanceOf(MockRenderer.class, renderer);

        // ## Act ##
        assembler.assmbleNavigationRules(facesConfig);

        // # Assert #
        Map map = NavigationContextFactory.getNavigationContexts(context);
        NavigationContext navContext = (NavigationContext) map.get("from");
        assertEquals("from", navContext.getFromViewId());
        List cases = navContext.getNavigationCases();
        assertNotNull(cases);
        assertEquals(1, cases.size());
        NavigationCaseContext caseContext = (NavigationCaseContext) cases
                .get(0);
        assertEquals("action", caseContext.getFromAction());
        assertEquals("outcome", caseContext.getFromOutcome());
        assertEquals("to", caseContext.getToViewId());
    }

}
