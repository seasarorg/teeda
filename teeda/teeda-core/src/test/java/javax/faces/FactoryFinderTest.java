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
package javax.faces;

import junit.framework.TestCase;

/**
 * @author Shinpei Ohtani
 */
public class FactoryFinderTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
        FactoryFinder.releaseFactories();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        FactoryFinder.releaseFactories();
    }

    public void testApplicationFactory() throws Exception {
        assertEquals("javax.faces.application.ApplicationFactory",
                FactoryFinder.APPLICATION_FACTORY);
    }

    public void testFacesContextFactory() throws Exception {
        assertEquals("javax.faces.context.FacesContextFactory",
                FactoryFinder.FACES_CONTEXT_FACTORY);
    }

    public void testLifecycleFactory() throws Exception {
        assertEquals("javax.faces.lifecycle.LifecycleFactory",
                FactoryFinder.LIFECYCLE_FACTORY);
    }

    public void testRenderKitFactory() throws Exception {
        assertEquals("javax.faces.render.RenderKitFactory",
                FactoryFinder.RENDER_KIT_FACTORY);
    }

    public void testGetFactory() throws Exception {

        FactoryFinder.releaseFactories();

        FactoryFinder.setFactory(FactoryFinder.APPLICATION_FACTORY,
                "org.seasar.teeda.core.mock.MockApplicationFactory");

        Object target = FactoryFinder
                .getFactory(FactoryFinder.APPLICATION_FACTORY);

        assertEquals("org.seasar.teeda.core.mock.MockApplicationFactory",
                target.getClass().getName());

    }

    public void testDuplicateSetFactory() throws Exception {
        FactoryFinder.releaseFactories();

        FactoryFinder.setFactory(FactoryFinder.APPLICATION_FACTORY,
                "org.seasar.teeda.core.mock.MockApplicationFactory");

        FactoryFinder.setFactory(FactoryFinder.APPLICATION_FACTORY,
                "org.seasar.teeda.core.mock.MockApplicationFactory2");

        Object target = FactoryFinder
                .getFactory(FactoryFinder.APPLICATION_FACTORY);

        assertEquals("org.seasar.teeda.core.mock.MockApplicationFactory2",
                target.getClass().getName());

    }

    public void testNotFactoryNameGiven() {
        try {
            FactoryFinder.getFactory(null);
            fail();
        } catch (NullPointerException e) {
            assertTrue(true);
        }
        try {
            FactoryFinder.setFactory(null,
                    "org.seasar.teeda.core.mock.MockApplicationFactory");
            fail();
        } catch (NullPointerException e) {
            assertTrue(true);
        }
    }

    public void testNotSetFactory() throws Exception {
        try {
            FactoryFinder
                    .getFactory("org.seasar.teeda.core.mock.MockApplicationFactory");
            fail();
        } catch (IllegalStateException e) {
            assertTrue(true);
        }
    }

    public void testSetUnidentifyFatoryName() throws Exception {
        try {
            FactoryFinder.setFactory(this.getClass().getName(),
                    "org.seasar.teeda.core.mock.MockApplicationFactory");
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }
}
