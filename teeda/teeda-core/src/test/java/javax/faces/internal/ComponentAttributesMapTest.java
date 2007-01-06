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
package javax.faces.internal;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.ExceptionAssert;

/**
 * @author shot
 */
public class ComponentAttributesMapTest extends TestCase {

    public void testGetComponentProperty() {
        MockUIComponent component = new MockUIComponent();
        component.setFamily("mock");
        ComponentAttributesMap map = new ComponentAttributesMap(component);
        assertNotNull(map.get("family"));
        assertEquals("mock", map.get("family"));
    }

    public void testPutComponentProperty() {
        MockUIComponent component = new MockUIComponent();
        component.setFamily("mock");
        ComponentAttributesMap map = new ComponentAttributesMap(component);
        assertEquals("mock", map.get("family"));
        component.setFamily("mock2");
        assertEquals("mock2", map.get("family"));
    }

    public void testRemoveComponentProperty() throws Exception {
        // ## Arrange ##
        MockUIComponent component = new MockUIComponent();
        component.setFamily("mock");
        ComponentAttributesMap map = new ComponentAttributesMap(component);

        // ## Act & Assert ##
        try {
            map.remove("family");
            fail();
        } catch (IllegalArgumentException iae) {
            ExceptionAssert.assertMessageExist(iae);
        }
    }

    public void testContainsKey_ComponentProperty() throws Exception {
        // ## Arrange ##
        MockUIComponent component = new MockUIComponent();
        component.setFamily("mock");
        ComponentAttributesMap map = new ComponentAttributesMap(component);

        // ## Act & Assert ##
        assertEquals(false, map.containsKey("family"));
    }

    public void testContainsKey_NotComponentProperty() throws Exception {
        // ## Arrange ##
        MockUIComponent component = new MockUIComponent();
        component.setFamily("mock");
        ComponentAttributesMap map = new ComponentAttributesMap(component);

        // ## Act & Assert ##
        assertEquals(false, map.containsKey("aaaa"));
        map.put("aaaa", "some value");
        assertEquals(true, map.containsKey("aaaa"));
    }

    public void testGetComponentWriteOnlyProperty() {
        MyMockUIComponent component = new MyMockUIComponent();
        ComponentAttributesMap map = new ComponentAttributesMap(component);
        try {
            assertNull(map.get("barProperty"));
        } catch (IllegalArgumentException iae) {
            fail();
        }
    }

    public void testPutComponentReadOnlyProperty() {
        MyMockUIComponent component = new MyMockUIComponent();
        ComponentAttributesMap map = new ComponentAttributesMap(component);
        try {
            map.put("fooProperty", "some value");
            assertTrue(map.size() == 1);
        } catch (Exception e) {
            fail();
        }
    }
    
    private static class MyMockUIComponent extends MockUIComponent {
        // read only
        public String getFooProperty() {
            return "fooValue";
        }

        // write only
        public void setBarProperty() {
        }
    }

    public void testPut_NotComponentProperty() throws Exception {
        // ## Arrange ##
        MockUIComponent component = new MockUIComponent();
        ComponentAttributesMap map = new ComponentAttributesMap(component);

        // ## Act ##
        map.put("notUIComponentProperty", "some value");

        // ## Assert ##
        assertEquals("some value", map.get("notUIComponentProperty"));
    }

    public void testPut_KeyIsNull() throws Exception {
        MockUIComponent c = new MockUIComponent();
        ComponentAttributesMap map = new ComponentAttributesMap(c);
        try {
            map.put(null, new Object());
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testPut_ValueIsNull() throws Exception {
        MockUIComponent c = new MockUIComponent();
        ComponentAttributesMap map = new ComponentAttributesMap(c);
        try {
            map.put("hoge", null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testPut_KeyShouldBeString() throws Exception {
        MockUIComponent c = new MockUIComponent();
        ComponentAttributesMap map = new ComponentAttributesMap(c);
        try {
            map.put(new Integer(1), "value");
            fail();
        } catch (ClassCastException cce) {
            ExceptionAssert.assertMessageExist(cce);
        }
    }

    public void testPutAll() throws Exception {
        MockUIComponent c = new MockUIComponent();
        ComponentAttributesMap map = new ComponentAttributesMap(c);
        Map m = new HashMap();
        m.put("a", "A");
        map.putAll(m);
        assertEquals("A", map.get("a"));
    }

    // TODO more tests

}
