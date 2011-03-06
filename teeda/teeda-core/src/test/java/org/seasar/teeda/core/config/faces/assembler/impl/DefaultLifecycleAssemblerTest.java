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
package org.seasar.teeda.core.config.faces.assembler.impl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.PhaseListener;

import org.seasar.teeda.core.config.faces.element.LifecycleElement;
import org.seasar.teeda.core.config.faces.element.impl.LifecycleElementImpl;
import org.seasar.teeda.core.mock.MockPhaseListener;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class DefaultLifecycleAssemblerTest extends TeedaTestCase {

    public void testAssemble1() throws Exception {
        PhaseListener[] orgListeners = getLifecycle().clearAllPhaseListener();
        try {
            // # Arrange #
            LifecycleElement lifecycleElement = new LifecycleElementImpl();
            lifecycleElement
                    .addPhaseListener("org.seasar.teeda.core.mock.MockPhaseListener");
            List list = new ArrayList();
            list.add(lifecycleElement);
            DefaultLifecycleAssembler assembler = new DefaultLifecycleAssembler(
                    list);

            // # Act #
            assembler.assemble();

            // # Assert #
            PhaseListener[] listeners = getLifecycle().getPhaseListeners();
            assertNotNull(listeners);
            assertEquals(1, listeners.length);
            assertTrue(listeners[0] instanceof MockPhaseListener);
        } finally {
            getLifecycle().setupDefaultPhaseListener(orgListeners);
        }
    }
}
