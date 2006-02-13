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
package org.seasar.teeda.core.config.faces.handler;

import java.util.List;

import org.seasar.teeda.core.config.faces.element.FacesConfig;
import org.seasar.teeda.core.config.faces.element.LifecycleElement;
import org.seasar.teeda.core.config.faces.element.impl.FacesConfigImpl;
import org.seasar.teeda.core.config.faces.handler.LifecycleTagHandler;
import org.seasar.teeda.core.mock.MockPhaseListener;

/**
 * @author shot
 */
public class LifecycleTagHandlerTest extends TagHandlerTestCase {

    /**
     * Constructor for LifecycleTagHandlerTest.
     * 
     * @param name
     */
    public LifecycleTagHandlerTest(String name) {
        super(name);
    }

    public void testLifecycleTagHandler() throws Exception {
        // # Arrange #
        FacesConfig config = new FacesConfigImpl();
        getContext().push(config);
        LifecycleTagHandler handler = new LifecycleTagHandler();

        // # Act #
        handler.start(getContext(), new NullAttributes());
        handler.end(getContext(), "");

        // # Assert #
        List lifes = config.getLifecycleElements();
        assertNotNull(lifes);
        assertTrue(lifes.size() == 1);
    }

    public void testLifecycleTagHandlerByXMLParse() throws Exception {
        // # Arrange & Act #
        FacesConfig config = parse("testLifecycleTagHandler.xml");

        // # Assert #
        List lifes = config.getLifecycleElements();
        assertNotNull(lifes);
        assertTrue(lifes.size() == 1);
        LifecycleElement lifecycle = (LifecycleElement) lifes.get(0);
        List phaseListeners = lifecycle.getPhaseListeners();
        assertNotNull(phaseListeners);
        assertEquals(MockPhaseListener.class.getName(), phaseListeners.get(0));
    }

}
