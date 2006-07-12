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
package javax.faces.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.FactoryFinder;
import javax.faces.application.ApplicationFactory;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockApplicationFactory;
import org.seasar.teeda.core.mock.MockApplicationFactory2;

/**
 * @author shot
 */
public class FactoryFinderUtilTest extends TestCase {

    public void testCheckValidFactoryNames() {
        String name = FactoryFinder.APPLICATION_FACTORY;
        try {
            FactoryFinderUtil.checkValidFactoryNames(name);
        } catch (Exception e) {
            fail();
        }
        try {
            FactoryFinderUtil.checkValidFactoryNames("notFactoryname");
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    public void testGetClassLoader() {
        assertNotNull(FactoryFinderUtil.getClassLoader());
    }

    public void testGetAbstractFactoryClass() {
        Class clazz = FactoryFinderUtil
                .getAbstractFactoryClass(FactoryFinder.FACES_CONTEXT_FACTORY);
        assertEquals(FactoryFinder.FACES_CONTEXT_FACTORY, clazz.getName());
        clazz = FactoryFinderUtil.getAbstractFactoryClass("aaa");
        assertNull(clazz);
    }

    public void testCreateFactoryInstance() {
        List list = new ArrayList();
        list.add("org.seasar.teeda.core.mock.MockApplicationFactory");
        Object o = FactoryFinderUtil.createFactoryInstance(
                FactoryFinder.APPLICATION_FACTORY, list);
        assertNotNull(o);
        assertEquals("org.seasar.teeda.core.mock.MockApplicationFactory", o
                .getClass().getName());
        list = new ArrayList();
        list.add("javax.faces.internal.FactoryFinderUtilTest");
        try {
            o = FactoryFinderUtil.createFactoryInstance(
                    FactoryFinder.APPLICATION_FACTORY, list);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    public void testGetCurrentInstanceByConstructor() {
        MockApplicationFactory f = new MockApplicationFactory();
        Class implClass = MockApplicationFactory2.class;
        Class abstractClass = ApplicationFactory.class;
        Object result = null;
        try {
            result = FactoryFinderUtil.getCurrentFactoryInstance(implClass,
                    abstractClass, f);
        } catch (Exception e) {
            fail();
        }
        assertNotNull(result);
        assertTrue(result instanceof ApplicationFactory);
    }

    public void testIsAlreadySetFactory() {
        Map map = new HashMap();
        assertFalse(FactoryFinderUtil.isAlreadySetFactory(map, "aaa"));

        map.put("aaa", "aaa");
        assertTrue(FactoryFinderUtil.isAlreadySetFactory(map, "aaa"));
    }

}
