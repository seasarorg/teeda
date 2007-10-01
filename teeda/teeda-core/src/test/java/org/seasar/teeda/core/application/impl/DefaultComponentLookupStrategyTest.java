/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.application.impl;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.impl.S2ContainerImpl;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class DefaultComponentLookupStrategyTest extends TeedaTestCase {

    public void testGetComponentByName_normal() throws Exception {
        getContainer().register(Hoge.class, "hoge1");
        DefaultComponentLookupStrategy st = new DefaultComponentLookupStrategy();
        Object o = st.getComponentByName("hoge1");
        assertTrue(o instanceof Hoge);
    }

    public void testGetComponentByName_getFromDefaultNamespace()
            throws Exception {
        S2Container container = new S2ContainerImpl();
        container.register(Hoge.class, "hoge1");
        container.setNamespace("teeda");
        getContainer().include(container);
        DefaultComponentLookupStrategy st = new DefaultComponentLookupStrategy();
        Object o = st.getComponentByName("hoge1");
        assertTrue(o instanceof Hoge);
    }

    public void testGetComponentByClass() throws Exception {
        getContainer().register(Hoge.class);
        DefaultComponentLookupStrategy st = new DefaultComponentLookupStrategy();
        Object o = st.getComponentByClass(Hoge.class);
        assertTrue(o instanceof Hoge);
    }

    public void testNamespace() throws Exception {
        S2Container container = new S2ContainerImpl();
        container.setNamespace("aaa");
        container.register(Hoge.class);
        getContainer().include(container);
        DefaultComponentLookupStrategy st = new DefaultComponentLookupStrategy();
        st.addNamespace("aaa");
        Object o = st.getComponentByClass(Hoge.class);
        assertTrue(o instanceof Hoge);
    }

    public static class Hoge {

    }

    public static class Hoge2 extends Hoge {

    }
}
