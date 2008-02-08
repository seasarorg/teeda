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
package org.seasar.teeda.core.util;

import javax.faces.internal.FacesConfigOptions;
import javax.faces.internal.FactoryFinderUtil;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;

import org.seasar.teeda.core.mock.MockLifecycleImpl;
import org.seasar.teeda.core.mock.MockPhaseListener;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class LifecycleUtilTest extends TeedaTestCase {

    public void testGetLifecycle() {
        Lifecycle lifecycle = LifecycleUtil.getLifecycle();
        assertNotNull(lifecycle);
        assertTrue(lifecycle instanceof MockLifecycleImpl);

        LifecycleFactory factory = FactoryFinderUtil.getLifecycleFactory();
        MockLifecycleImpl mock = new MockLifecycleImpl();
        MockPhaseListener listener = new MockPhaseListener("mock");
        mock.addPhaseListener(listener);
        factory.addLifecycle("hoge", mock);

        FacesConfigOptions.setLifecycleId("hoge");
        lifecycle = LifecycleUtil.getLifecycle();
        assertNotNull(lifecycle);
        assertTrue(lifecycle instanceof MockLifecycleImpl);
        assertEquals("mock", (lifecycle.getPhaseListeners()[0].toString()));
    }

    public void testGetLifecycleId() {
        assertEquals(LifecycleFactory.DEFAULT_LIFECYCLE, LifecycleUtil
                .getLifecycleId());

        FacesConfigOptions.setLifecycleId("hoge");
        assertEquals("hoge", LifecycleUtil.getLifecycleId());
    }

}
