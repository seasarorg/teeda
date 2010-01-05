/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.el;

import java.util.HashMap;
import java.util.Map;

import javax.faces.el.PropertyResolver;

import junit.framework.TestCase;

import org.seasar.teeda.core.exception.SetterNotFoundRuntimeException;
import org.seasar.teeda.core.mock.MockUIComponent;

/**
 * @author shot
 */
public class TeedaPropertyResolverTest extends TestCase {

    public void testGetValue_baseIsNull() throws Exception {
        PropertyResolver resolver = createPropertyResolver();
        assertNull(resolver.getValue(null, "a"));
    }

    public void testGetValue_propertyIsNull() throws Exception {
        PropertyResolver resolver = createPropertyResolver();
        assertNull(resolver.getValue("a", null));
    }

    public void testGetValue_baseIsMapAndValueFound() throws Exception {
        PropertyResolver resolver = createPropertyResolver();
        Map map = new HashMap();
        map.put("a", "A");
        map.put("b", "B");
        assertEquals("A", resolver.getValue(map, "a"));
    }

    public void testGetValue_baseIsMapAndValueNotFound() throws Exception {
        PropertyResolver resolver = createPropertyResolver();
        Map map = new HashMap();
        map.put("a", "A");
        map.put("b", "B");
        assertNull(resolver.getValue(map, "c"));
    }

    public void testGetValue_UIComponentButNoChildren() throws Exception {
        PropertyResolver resolver = createPropertyResolver();
        MockUIComponent component = new MockUIComponent();
        assertNull(resolver.getValue(component, "id"));
    }

    public void testSetValue() throws Exception {
        PropertyResolver resolver = createPropertyResolver();
        try {
            resolver.setValue(new Hoge(), "name", "hoge");
            fail();
        } catch (SetterNotFoundRuntimeException expected) {
            assertTrue(true);
        }
    }

    private PropertyResolver createPropertyResolver() {
        return new TeedaPropertyResolver();
    }

    public static class Hoge {

        private String name;

        public String getName() {
            return name;
        }

    }
}
