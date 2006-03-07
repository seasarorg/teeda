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

import junit.framework.TestCase;

import org.seasar.teeda.core.config.faces.element.FacesConfig;
import org.seasar.teeda.core.config.faces.element.FactoryElement;
import org.seasar.teeda.core.mock.MockApplicationFactory;
import org.seasar.teeda.core.mock.MockFacesContextFactory;
import org.seasar.teeda.core.mock.MockLifecycleFactory;
import org.seasar.teeda.core.mock.MockRenderKitFactory;

/**
 * @author shot
 */
public class ApplicationFacesConfiguratorTest extends TestCase {

    public void testConfigure() throws Exception {

        String path = this.getClass().getPackage().getName().replaceAll("\\.",
                "/")
                + "/" + "faces-config.xml";
        TargetApplicationFacesConfigurator configurator = new TargetApplicationFacesConfigurator(
                path);
        FacesConfig facesConfig = configurator.configure();

        assertNotNull(facesConfig);

        List factories = facesConfig.getFactoryElements();
        FactoryElement factory = (FactoryElement) factories.get(0);
        assertNotNull(factory);

        assertEquals(MockApplicationFactory.class.getName(), factory
                .getApplicationFactories().get(0));
        assertEquals(MockFacesContextFactory.class.getName(), factory
                .getFacesContextFactories().get(0));
        assertEquals(MockLifecycleFactory.class.getName(), factory
                .getLifecycleFactories().get(0));
        assertEquals(MockRenderKitFactory.class.getName(), factory
                .getRenderKitFactories().get(0));
    }

    private static class TargetApplicationFacesConfigurator extends
            ApplicationFacesConfigurator {

        private String path_;

        public TargetApplicationFacesConfigurator(String path) {
            super();
            path_ = path;
        }

        public String getPath() {
            return path_;
        }

    }
}
