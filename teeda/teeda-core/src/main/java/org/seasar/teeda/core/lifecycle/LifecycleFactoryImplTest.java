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
package org.seasar.teeda.core.lifecycle;

import java.util.Iterator;

import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;

import org.seasar.teeda.core.exception.LifecycleIdAlreadyExistRuntimeException;
import org.seasar.teeda.core.exception.LifecycleIdNotFoundRuntimeException;
import org.seasar.teeda.core.mock.MockLifecycle;
import org.seasar.teeda.core.mock.MockLifecycleImpl;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class LifecycleFactoryImplTest extends TeedaTestCase {

    public void testGetLifecycle_lifecycleNotFound() throws Exception {
        // #Arrange
        LifecycleFactoryImpl factory = new LifecycleFactoryImpl();
        try {
            // #Act & Assert
            factory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
            fail();
        } catch (LifecycleIdNotFoundRuntimeException expected) {
            success();
        }
    }

    public void testGetLifecycle() throws Exception {
        // #Arrange
        LifecycleFactoryImpl factory = new LifecycleFactoryImpl();
        MockLifecycle lifecycle = new MockLifecycleImplForLifecycleFactoryImplTest();
        factory.addLifecycle("aaa", lifecycle);

        // #Act
        Lifecycle l = factory.getLifecycle("aaa");

        // #Assert
        assertNotNull(l);
        assertTrue(l instanceof MockLifecycleImplForLifecycleFactoryImplTest);
    }

    public void testAddLifecycle_alreadyRegistered() throws Exception {
        // #Arrange
        LifecycleFactoryImpl factory = new LifecycleFactoryImpl();
        MockLifecycle lifecycle = new MockLifecycleImplForLifecycleFactoryImplTest();
        factory.addLifecycle("aaa", lifecycle);

        try {
            // #Act
            factory.addLifecycle("aaa", lifecycle);
            fail();
        } catch (LifecycleIdAlreadyExistRuntimeException expected) {
            success();
        }
    }

    public void testGetLifecycleIds() throws Exception {
        // #Arrange
        LifecycleFactoryImpl factory = new LifecycleFactoryImpl();
        MockLifecycle lifecycle = new MockLifecycleImplForLifecycleFactoryImplTest();
        factory.addLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE, lifecycle);

        // #Act
        Iterator itr = factory.getLifecycleIds();

        // #Assert
        assertNotNull(itr);
        assertEquals(LifecycleFactory.DEFAULT_LIFECYCLE, itr.next().toString());
    }

    private static class MockLifecycleImplForLifecycleFactoryImplTest extends
            MockLifecycleImpl {
    }
}
