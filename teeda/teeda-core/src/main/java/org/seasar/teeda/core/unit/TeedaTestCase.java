/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.unit;

import java.util.Locale;

import javax.faces.FactoryFinder;
import javax.faces.component.UIViewRoot;
import javax.faces.internal.ConverterResource;
import javax.faces.internal.FacesConfigOptions;
import javax.faces.internal.NormalConverterBuilderImpl;
import javax.faces.internal.NormalValidatorBuilderImpl;
import javax.faces.internal.ValidatorResource;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.render.RenderKitFactory;

import org.seasar.framework.unit.S2FrameworkTestCase;
import org.seasar.teeda.core.application.navigation.NavigationResource;
import org.seasar.teeda.core.managedbean.ManagedBeanFactory;
import org.seasar.teeda.core.managedbean.impl.ManagedBeanFactoryImpl;
import org.seasar.teeda.core.managedbean.impl.ManagedBeanScopeSaverImpl;
import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockApplicationFactory;
import org.seasar.teeda.core.mock.MockApplicationImpl;
import org.seasar.teeda.core.mock.MockExternalContext;
import org.seasar.teeda.core.mock.MockExternalContextImpl;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockFacesContextFactory;
import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.mock.MockLifecycle;
import org.seasar.teeda.core.mock.MockLifecycleFactory;
import org.seasar.teeda.core.mock.MockLifecycleImpl;
import org.seasar.teeda.core.mock.MockNavigationHandler;
import org.seasar.teeda.core.mock.MockPhaseListener;
import org.seasar.teeda.core.mock.MockPropertyResolver;
import org.seasar.teeda.core.mock.MockRenderKit;
import org.seasar.teeda.core.mock.MockRenderKitFactory;
import org.seasar.teeda.core.mock.MockRenderKitImpl;
import org.seasar.teeda.core.mock.MockRenderer;
import org.seasar.teeda.core.mock.MockStateManager;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.mock.MockVariableResolver;
import org.seasar.teeda.core.mock.MockViewHandler;
import org.seasar.teeda.core.mock.MockViewHandlerImpl;
import org.seasar.teeda.core.scope.impl.S2ScopeTranslator;
import org.seasar.teeda.core.scope.impl.ScopeManagerImpl;

/**
 * @author shot
 *
 * Set up JSF env for easy-testing JSF Application and view.
 */
public abstract class TeedaTestCase extends S2FrameworkTestCase {

    private MockExternalContext externalContext;

    private MockApplication application;

    private MockFacesContext facesContext;

    private MockLifecycle lifecycle;

    private MockRenderKit renderKit;

    private MockPhaseListener phaseListener;

    private MockNavigationHandler navigationHandler;

    private MockPropertyResolver propertyResolver;

    private MockVariableResolver variableResolver;

    private MockViewHandler viewHandler;

    private MockStateManager stateManager;

    public TeedaTestCase() {
        setWarmDeploy(false);
    }

    public TeedaTestCase(String name) {
        super(name);
        setWarmDeploy(false);
    }

    protected void setUpContainer() throws Throwable {
        super.setUpContainer();
        externalContext = new MockExternalContextImpl(getServletContext(),
                getRequest(), getResponse());
        application = new MockApplicationImpl();
        navigationHandler = new MockNavigationHandler();
        application.setNavigationHandler(navigationHandler);
        propertyResolver = new MockPropertyResolver();
        application.setPropertyResolver(propertyResolver);
        variableResolver = new MockVariableResolver();
        application.setVariableResolver(variableResolver);
        viewHandler = new MockViewHandlerImpl();
        application.setViewHandler(viewHandler);
        stateManager = new MockStateManager();
        application.setStateManager(stateManager);

        facesContext = new MockFacesContextImpl(externalContext, application);
        UIViewRoot viewRoot = new UIViewRoot();
        viewRoot.setRenderKitId(RenderKitFactory.HTML_BASIC_RENDER_KIT);
        viewRoot.setLocale(Locale.getDefault());
        facesContext.setViewRoot(viewRoot);
        renderKit = new MockRenderKitImpl();

        // default setting
        MockRenderer renderer = new MockRenderer();
        renderKit.addRenderer(MockUIComponent.COMPONENT_FAMILY,
                MockUIComponent.COMPONENT_TYPE, renderer);

        lifecycle = new MockLifecycleImpl();
        phaseListener = new MockPhaseListener();
        lifecycle.addPhaseListener(phaseListener);
        initFactories();
        setFactories();
        ValidatorResource.setValidatorBuilder(new NormalValidatorBuilderImpl(
                getContainer()));
        ConverterResource.setConverterBuilder(new NormalConverterBuilderImpl(
                getContainer()));
    }

    protected void initFactories() {
        FactoryFinder.setFactory(FactoryFinder.APPLICATION_FACTORY,
                "org.seasar.teeda.core.mock.MockApplicationFactory");

        FactoryFinder.setFactory(FactoryFinder.FACES_CONTEXT_FACTORY,
                "org.seasar.teeda.core.mock.MockFacesContextFactory");

        FactoryFinder.setFactory(FactoryFinder.LIFECYCLE_FACTORY,
                "org.seasar.teeda.core.mock.MockLifecycleFactory");

        FactoryFinder.setFactory(FactoryFinder.RENDER_KIT_FACTORY,
                "org.seasar.teeda.core.mock.MockRenderKitFactory");
    }

    protected void setFactories() {
        setApplicationFactory();
        setFacesContextFactory();
        setLifecycleFactory();
        setRenderKitFactory();
        setManagedBeanFactory();
    }

    protected void setApplicationFactory() {
        MockApplicationFactory appFactory = (MockApplicationFactory) FactoryFinder
                .getFactory(FactoryFinder.APPLICATION_FACTORY);
        appFactory.setApplication(application);
    }

    protected void setFacesContextFactory() {
        MockFacesContextFactory facesContextFactory = (MockFacesContextFactory) FactoryFinder
                .getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
        facesContextFactory.setFacesContext(facesContext);
    }

    protected void setLifecycleFactory() {
        MockLifecycleFactory lifecycleFactory = (MockLifecycleFactory) FactoryFinder
                .getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        lifecycleFactory.addLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE,
                lifecycle);
    }

    protected void setRenderKitFactory() {
        MockRenderKitFactory renderKitFactory = (MockRenderKitFactory) FactoryFinder
                .getFactory(FactoryFinder.RENDER_KIT_FACTORY);
        renderKitFactory.addRenderKit(RenderKitFactory.HTML_BASIC_RENDER_KIT,
                renderKit);
    }

    protected void setManagedBeanFactory() {
        getContainer().register(ManagedBeanFactoryImpl.class);
        getContainer().register(ManagedBeanScopeSaverImpl.class);
        getContainer().register(ScopeManagerImpl.class);
        getContainer().register(S2ScopeTranslator.class);
    }

    protected ManagedBeanFactory getManagedBeanFactory() {
        return (ManagedBeanFactory) getContainer().getComponent(
                ManagedBeanFactory.class);
    }

    protected String getResponseText() {
        return getResponse().getResponseString();
    }

    protected void tearDownContainer() throws Throwable {
        externalContext = null;
        application = null;
        if (facesContext != null) {
            facesContext.release();
        }
        facesContext = null;
        renderKit = null;
        phaseListener = null;
        navigationHandler = null;
        FactoryFinder.releaseFactories();
        NavigationResource.removeAll();
        ValidatorResource.removeAll();
        ConverterResource.removeAll();
        FacesConfigOptions.clear();
        super.tearDownContainer();
    }

    /**
     * all i want is this(nobody wants to write assertTrue(true), right?)
     */
    protected static void success() {
        assertTrue(true);
    }

    protected static void notDoneYet() {
        fail("This test is not done yet.");
    }

    public MockApplication getApplication() {
        return application;
    }

    public void setApplication(MockApplication application) {
        this.application = application;
        setApplicationFactory();
        facesContext.setApplication(this.application);
    }

    public MockExternalContext getExternalContext() {
        return externalContext;
    }

    public void setExternalContext(MockExternalContext externalContext) {
        this.externalContext = externalContext;
    }

    public MockFacesContext getFacesContext() {
        return facesContext;
    }

    public void setFacesContext(MockFacesContext facesContext) {
        this.facesContext = facesContext;
        setFacesContextFactory();
    }

    public MockLifecycle getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(MockLifecycle lifecycle) {
        this.lifecycle = lifecycle;
        setLifecycleFactory();
    }

    public MockRenderKit getRenderKit() {
        return renderKit;
    }

    public void setRenderKit(MockRenderKit renderKit) {
        this.renderKit = renderKit;
        setRenderKitFactory();
    }

    public MockPropertyResolver getPropertyResolver() {
        return propertyResolver;
    }

    public void setPropertyResolver(MockPropertyResolver propertyResolver) {
        this.propertyResolver = propertyResolver;
        application.setPropertyResolver(propertyResolver);
    }

    public MockVariableResolver getVariableResolver() {
        return variableResolver;
    }

    public void setVariableResolver(MockVariableResolver variableResolver) {
        this.variableResolver = variableResolver;
        application.setVariableResolver(variableResolver);
    }

    public MockViewHandler getViewHandler() {
        return viewHandler;
    }

    public void setViewHandler(MockViewHandler viewHandler) {
        this.viewHandler = viewHandler;
        application.setViewHandler(viewHandler);
    }

    /**
     * @return Returns the stateManager.
     */
    public MockStateManager getStateManager() {
        return stateManager;
    }

    /**
     * @param stateManager The stateManager to set.
     */
    public void setStateManager(MockStateManager stateManager) {
        this.stateManager = stateManager;
    }

}
