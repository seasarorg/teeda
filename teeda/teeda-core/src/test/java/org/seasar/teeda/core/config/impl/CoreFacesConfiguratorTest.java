/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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

import javax.faces.FactoryFinder;
import javax.faces.application.ApplicationFactory;
import javax.faces.context.ExternalContext;

import junitx.framework.ObjectAssert;

import org.seasar.framework.container.factory.ClassPathResourceResolver;
import org.seasar.teeda.core.config.assembler.AssemblerAssembler;
import org.seasar.teeda.core.config.assembler.impl.DefaultAssembleProvider;
import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.config.rule.FacesConfigTagHandlerRule;
import org.seasar.teeda.core.mock.MockActionListener;
import org.seasar.teeda.core.mock.MockApplicationFactory;
import org.seasar.teeda.core.mock.MockExternalContextImpl;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class CoreFacesConfiguratorTest extends TeedaTestCase {

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
        configurator.setResourceResolver(resolver);
        configurator.setTagHandlerRule(rule);
        configurator.setPath(getClass().getPackage().getName()
                .replace('.', '/')
                + "/testConfigure1.xml");

        // ## Act ##
        FacesConfig facesConfig = configurator.configure();

        // ## Assert ##
        // do actually initialize.
        assertNotNull(facesConfig);
        AssemblerAssembler assembler = new AssemblerAssembler();
        DefaultAssembleProvider provider = new DefaultAssembleProvider();
        ExternalContext externalContext = new MockExternalContextImpl(
                getServletContext(), getRequest(), getResponse());
        provider.setExternalContext(externalContext);
        assembler.setAssembleProvider(provider);

        assembler.assembleFactories(facesConfig);
        ApplicationFactory appFactory = (ApplicationFactory) FactoryFinder
                .getFactory(FactoryFinder.APPLICATION_FACTORY);
        ObjectAssert.assertInstanceOf(MockApplicationFactory.class, appFactory);

        assembler.assembleApplication(facesConfig);
        ObjectAssert.assertInstanceOf(MockActionListener.class,
                getApplication().getActionListener());

        assembler.assembleLifecycle(facesConfig);

        assembler.assembleManagedBeans(facesConfig);

        assembler.assembleRenderKits(facesConfig);

        assembler.assmbleNavigationRules(facesConfig);
    }

}
