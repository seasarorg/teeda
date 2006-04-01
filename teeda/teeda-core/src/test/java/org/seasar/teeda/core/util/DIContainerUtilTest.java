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
package org.seasar.teeda.core.util;

import junitx.framework.ArrayAssert;

import org.seasar.framework.container.ComponentNotFoundRuntimeException;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class DIContainerUtilTest extends TeedaTestCase {

    public void testGetComponent_findOne() throws Exception {
        getContainer().register(Hoge.class);
        Hoge hoge = (Hoge) DIContainerUtil.getComponent(Hoge.class);
        assertNotNull(hoge);
    }

    public void testGetComponent_NotFound() throws Exception {
        try {
            DIContainerUtil.getComponent(Hoge.class);
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

    public void testGetComponentNoException_findOneByClass() throws Exception {
        getContainer().register(Hoge.class, "hoge");
        Hoge hoge = (Hoge) DIContainerUtil.getComponentNoException("hoge");
        assertNotNull(hoge);
    }

    public void testGetComponentKeys_sameClass() throws Exception {
        getContainer().register(Hoge.class, "hoge1");
        getContainer().register(Hoge.class, "hoge2");
        getContainer().register(Hoge.class, "hoge3");
        Object[] keys = DIContainerUtil.getComponentKeys(Hoge.class);
        ArrayAssert.assertEquals(new String[] { "hoge1", "hoge2", "hoge3" },
                keys);
    }

    public void testGetComponentKeys_includeChildClass() throws Exception {
        getContainer().register(Hoge.class, "hoge1");
        getContainer().register(Hoge2.class, "hoge2");
        getContainer().register(Hoge3.class, "hoge3");
        Object[] keys = DIContainerUtil.getComponentKeys(Hoge.class);
        ArrayAssert.assertEquals(new String[] { "hoge1", "hoge2", "hoge3" },
                keys);
    }

    public void testHasComponent() throws Exception {
        getContainer().register(Hoge.class, "hoge1");
        assertTrue(DIContainerUtil.hasComponent(Hoge.class));
        assertTrue(DIContainerUtil.hasComponent("hoge1"));
        assertFalse(DIContainerUtil.hasComponent("hoge2"));
    }

    public void testHasComponent2() throws Exception {
        getContainer().register(Hoge2.class);
        assertTrue(DIContainerUtil.hasComponent(Hoge.class));
        assertTrue(DIContainerUtil.hasComponent(Hoge2.class));
    }

    public static class Hoge {

    }

    public static class Hoge2 extends Hoge {

    }

    public static class Hoge3 extends Hoge2 {

    }

}
