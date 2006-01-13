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
package javax.faces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.ApplicationFactory;

import org.seasar.teeda.core.mock.MockApplicationFactory;
import org.seasar.teeda.core.mock.MockApplicationFactory2;

import junit.framework.TestCase;

/**
 * @author Shinpei Ohtani
 */
public class FacesUtil_Test extends TestCase {

    public void testAssertNotNullObject() {
        try {
            FactoryFinderUtil_.assertNotNull("a");
        } catch (Exception e) {
            fail();
        }
        try {
            FactoryFinderUtil_.assertNotNull(null);
            fail();
        } catch (NullPointerException e) {
            assertTrue(true);
        }
    }

    public void testCheckValidFactoryNames() {
        String name = FactoryFinder.APPLICATION_FACTORY;
        try {
            FactoryFinderUtil_.checkValidFactoryNames(name);
        } catch (Exception e) {
            fail();
        }
        try {
            FactoryFinderUtil_.checkValidFactoryNames("notFactoryname");
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    public void testGetClassLoader() {
        assertNotNull(FactoryFinderUtil_.getClassLoader());
    }

    public void testGetAbstractFactoryClass() {
        Class clazz = FactoryFinderUtil_
                .getAbstractFactoryClass(FactoryFinder.FACES_CONTEXT_FACTORY);
        assertEquals(FactoryFinder.FACES_CONTEXT_FACTORY, clazz.getName());
        clazz = FactoryFinderUtil_.getAbstractFactoryClass("aaa");
        assertNull(clazz);
    }

    public void testCreateFactoryInstance() {
        List list = new ArrayList();
        list.add("org.seasar.teeda.core.mock.MockApplicationFactory");
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Object o = FactoryFinderUtil_.createFactoryInstance(
                FactoryFinder.APPLICATION_FACTORY, list, loader);
        assertNotNull(o);
        assertEquals("org.seasar.teeda.core.mock.MockApplicationFactory", o
                .getClass().getName());
        list = new ArrayList();
        list.add("javax.faces.TestFacesUtil_");
        try {
            o = FactoryFinderUtil_.createFactoryInstance(
                    FactoryFinder.APPLICATION_FACTORY, list, loader);
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
            result = FactoryFinderUtil_.getCurrentFactoryInstance(implClass,
                    abstractClass, f);
        } catch (Exception e) {
            fail();
        }
        assertNotNull(result);
        assertTrue(result instanceof ApplicationFactory);
    }

    public void testIsAlreadySetFactory() {
        Map map = new HashMap();
        assertFalse(FactoryFinderUtil_.isAlreadySetFactory(map, "aaa"));

        map.put("aaa", "aaa");
        assertTrue(FactoryFinderUtil_.isAlreadySetFactory(map, "aaa"));
    }

}
