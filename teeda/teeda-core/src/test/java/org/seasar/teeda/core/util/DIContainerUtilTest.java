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
package org.seasar.teeda.core.util;

import org.seasar.framework.container.ComponentNotFoundRuntimeException;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class DIContainerUtilTest extends TeedaTestCase {

    /**
     * Constructor for DIContainerUtilTest.
     * 
     * @param name
     */
    public DIContainerUtilTest(String name) {
        super(name);
    }

    public void testGetComponent_findOne() throws Exception {
        getContainer().register(Hoge.class);
        Hoge hoge = (Hoge) DIContainerUtil.getComponent(Hoge.class);
        assertNotNull(hoge);
    }

    public void testGetComponent_NotFound() throws Exception {
        try {
            Hoge hoge = (Hoge) DIContainerUtil.getComponent(Hoge.class);
            fail();
        } catch (ComponentNotFoundRuntimeException expected) {
            success();
        }
    }

    public void testGetComponentNoException_findOne() throws Exception {
        getContainer().register(Hoge.class);
        Hoge hoge = (Hoge) DIContainerUtil.getComponentNoException(Hoge.class);
        assertNotNull(hoge);
    }

    public void testGetComponentNoException_NotFound() throws Exception {
        Hoge hoge = null;
        try {
            hoge = (Hoge) DIContainerUtil.getComponentNoException(Hoge.class);
        } catch (Exception e) {
            fail();
        }
        assertNull(hoge);
    }

    public static class Hoge {

    }
}
