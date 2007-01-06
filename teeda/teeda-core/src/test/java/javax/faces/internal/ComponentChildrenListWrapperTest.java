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

import javax.faces.component.UIComponent;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockUIComponent;

/**
 * @author shot
 */
public class ComponentChildrenListWrapperTest extends TestCase {

    public void testAddComponent() {
        MockUIComponent component = new MockUIComponent();
        component.setId("a");
        ComponentChildrenListWrapper wrapper = new ComponentChildrenListWrapper(
                component);
        MockUIComponent child = new MockUIComponent();
        wrapper.add(child);
        assertEquals("a", child.getParent().getId());
    }

    public void testRemoveComponent() {
        MockUIComponent component = new MockUIComponent();
        component.setId("a");
        ComponentChildrenListWrapper wrapper = new ComponentChildrenListWrapper(
                component);
        assertEquals(0, wrapper.size());
        MockUIComponent child = new MockUIComponent();
        child.setId("b");
        wrapper.add(child);
        assertEquals(1, wrapper.size());
        UIComponent c = (UIComponent) wrapper.remove(wrapper.size() - 1);
        assertEquals("b", c.getId());
        assertEquals(0, wrapper.size());
    }

}
