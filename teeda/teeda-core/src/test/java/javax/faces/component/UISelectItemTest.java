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
package javax.faces.component;

import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author shot
 * @author manhole
 */
public class UISelectItemTest extends UIComponentBaseTest {

    public void testGetFamily() {
        UISelectItem item = createUISelectItem();
        assertEquals(item.getFamily(), UISelectItem.COMPORNENT_FAMILY);
    }

    public void testSetItemDescription() {
        UISelectItem item = createUISelectItem();
        item.setItemDescription("aaa");
        assertEquals(item.getItemDescription(), "aaa");
    }

    public void testGetItemDescription() {
        UISelectItem item = createUISelectItem();
        MockValueBinding vb = new MockValueBinding();
        String value = "aaa";
        vb.setValue(getFacesContext(), value);
        item.setValueBinding("itemDescription", vb);
        assertEquals(value, item.getItemDescription());
    }

    public void testSetItemDisabled() {
        UISelectItem item = createUISelectItem();
        boolean value = true;
        item.setItemDisabled(value);
        assertEquals(item.isItemDisabled(), value);
    }

    public void testGetItemDisabled() {
        UISelectItem item = createUISelectItem();
        MockValueBinding vb = new MockValueBinding();
        boolean value = true;
        vb.setValue(getFacesContext(), new Boolean(value));
        item.setValueBinding("itemDisabled", vb);
        assertEquals(value, item.isItemDisabled());
    }

    public void testSetItemLabel() {
        UISelectItem item = createUISelectItem();
        String str = "bbb";
        item.setItemLabel(str);
        assertEquals(item.getItemLabel(), str);
    }

    public void testGetItemLabel() {
        UISelectItem item = createUISelectItem();
        MockValueBinding vb = new MockValueBinding();
        String value = "bbb";
        vb.setValue(getFacesContext(), value);
        item.setValueBinding("itemLabel", vb);
        assertEquals(value, item.getItemLabel());
    }

    public void testSetItemValue() {
        UISelectItem item = createUISelectItem();
        Integer num = new Integer(3);
        item.setItemValue(num);
        assertEquals(item.getItemValue(), num);
    }

    public void testGetItemValue() {
        UISelectItem item = createUISelectItem();
        MockValueBinding vb = new MockValueBinding();
        Integer value = new Integer(3);
        vb.setValue(getFacesContext(), value);
        item.setValueBinding("itemValue", vb);
        assertEquals(value, item.getItemValue());
    }

    public void testSetValue() {
        UISelectItem item = createUISelectItem();
        String str = "a";
        item.setValue(str);
        assertEquals(item.getValue(), str);
    }

    public void testGetValue() {
        UISelectItem item = createUISelectItem();
        MockValueBinding vb = new MockValueBinding();
        String value = "a";
        vb.setValue(getFacesContext(), value);
        item.setValueBinding("value", vb);
        assertEquals(value, item.getValue());
    }

    private UISelectItem createUISelectItem() {
        return (UISelectItem) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UISelectItem();
    }

}
