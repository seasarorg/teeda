/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.net.URLClassLoader;

import javax.faces.application.ApplicationFactory;
import javax.faces.context.FacesContextFactory;
import javax.faces.internal.FactoryFinderUtil;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.render.RenderKitFactory;

import junitx.framework.ObjectAssert;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.teeda.core.config.faces.assembler.AssemblerAssembler;
import org.seasar.teeda.core.config.faces.assembler.impl.DefaultAssembleProvider;
import org.seasar.teeda.core.config.faces.element.FacesConfig;
import org.seasar.teeda.core.mock.MockApplicationFactory;
import org.seasar.teeda.core.mock.MockFacesContextFactory;
import org.seasar.teeda.core.mock.MockLifecycleFactory;
import org.seasar.teeda.core.mock.MockRenderKitFactory;

/**
 * @author shot
 */
public class MetaInfFacesConfiguratorTest extends S2TestCase {

    public void testConfigure1() throws Exception {
        final File buildDir = ResourceUtil.getBuildDir(getClass());
        File jarDir = new File(buildDir, getClass().getPackage().getName()
                .replace('.', '/'));
        File[] jars = jarDir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if (name.endsWith(".jar")) {
                    return true;
                }
                return false;
            }
        });
        assertEquals(1, jars.length);
        URL[] jarUrls = new URL[jars.length];
        for (int i = 0; i < jars.length; i++) {
            System.out.println(jars[i].toURL());
            jarUrls[i] = jars[i].toURL();
        }
        ClassLoader orgCl = Thread.currentThread().getContextClassLoader();
        URLClassLoader cl = new URLClassLoader(jarUrls, orgCl);
        try {
            Thread.currentThread().setContextClassLoader(cl);

            // ## Arrange ##
            String path = getClass().getPackage().getName().replace('.', '/');

            MetaInfFacesConfigurator configurator = new MetaInfFacesConfigurator();
            configurator.setPath(path);

            // ## Act ##
            FacesConfig facesConfig = configurator.configure();

            // ## Assert ##
            assertNotNull(facesConfig);

            // do actually initialize.
            AssemblerAssembler assembler = new AssemblerAssembler();
            DefaultAssembleProvider provider = new DefaultAssembleProvider();
            assembler.setAssembleProvider(provider);

            // ## Act ##
            assembler.assembleFactories(facesConfig);

            // ## Assert ##
            ApplicationFactory appFactory = FactoryFinderUtil
                    .getApplicationFactory();
            ObjectAssert.assertInstanceOf(MockApplicationFactory.class,
                    appFactory);

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

        } finally {
            Thread.currentThread().setContextClassLoader(orgCl);
        }
    }

}
