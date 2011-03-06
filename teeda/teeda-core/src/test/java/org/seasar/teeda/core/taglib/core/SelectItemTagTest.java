/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.taglib.core;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;

import junit.framework.TestCase;

/**
 * @author yone
 */
public class SelectItemTagTest extends TestCase {

    public void testGetComponentType() throws Exception {
        // # Arrange #
        SelectItemTag tag = new SelectItemTag();

        // # Act & Assert #
        assertEquals("javax.faces.SelectItem", tag.getComponentType());
    }

    public void testGetRenderType() throws Exception {
        // # Arrange #
        SelectItemTag tag = new SelectItemTag();

        // # Act & Assert #
        assertEquals(null, tag.getRendererType());
    }

    public void testSetProperties() throws Exception {
        // # Arrange #
        UISelectItem item = createUISelectItem();

        SelectItemTag tag = new SelectItemTag();
        tag.setItemDescription("item description");
        tag.setItemDisabled("true");
        tag.setItemLabel("item label");
        tag.setItemValue("item value");
        // # Act #
        tag.setProperties(item);

        // # Assert #
        assertEquals("item description", item.getItemDescription());
        assertTrue(item.isItemDisabled());
        assertEquals("item label", item.getItemLabel());
        assertEquals("item value", item.getItemValue());
    }

    public void testRelease() throws Exception {
        // # Arrange #
        SelectItemTag tag = new SelectItemTag();
        tag.setItemDescription("item description");
        tag.setItemDisabled("true");
        tag.setItemLabel("item label");
        tag.setItemValue("item value");

        // # Act #
        tag.release();

        // # Assert #
        assertEquals(null, tag.getItemDescription());
        assertEquals(null, tag.getItemDisabled());
        assertEquals(null, tag.getItemLabel());
        assertEquals(null, tag.getItemValue());
    }

    private UISelectItem createUISelectItem() {
        return (UISelectItem) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UISelectItem();
    }
}
