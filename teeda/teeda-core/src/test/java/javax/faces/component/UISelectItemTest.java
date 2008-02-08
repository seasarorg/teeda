/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import javax.faces.context.FacesContext;

import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author shot
 * @author manhole
 */
public class UISelectItemTest extends UIComponentBaseTest {

    public void testSetGetItemDescription() {
        UISelectItem item = createUISelectItem();
        item.setItemDescription("aaa");
        assertEquals(item.getItemDescription(), "aaa");
    }

    public void testSetGetItemDescription_ValueBinding() {
        UISelectItem item = createUISelectItem();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "aaa");
        item.setValueBinding("itemDescription", vb);
        assertEquals("aaa", item.getItemDescription());
        assertEquals("aaa", item.getValueBinding("itemDescription").getValue(
                context));
    }

    public void testSetGetItemDisabled() {
        UISelectItem item = createUISelectItem();
        item.setItemDisabled(true);
        assertEquals(true, item.isItemDisabled());
    }

    public void testSetGetItemDisabled_ValueBinding() {
        UISelectItem item = createUISelectItem();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, new Boolean(true));
        item.setValueBinding("itemDisabled", vb);
        assertEquals(true, item.isItemDisabled());
        assertEquals(Boolean.TRUE, item.getValueBinding("itemDisabled")
                .getValue(context));
    }

    public void testSetGetItemLabel() {
        UISelectItem item = createUISelectItem();
        item.setItemLabel("bbb");
        assertEquals("bbb", item.getItemLabel());
    }

    public void testSetGetItemLabel_ValueBinding() {
        UISelectItem item = createUISelectItem();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bbb");
        item.setValueBinding("itemLabel", vb);
        assertEquals("bbb", item.getItemLabel());
        assertEquals("bbb", item.getValueBinding("itemLabel").getValue(context));
    }

    public void testSetGetItemValue() {
        UISelectItem item = createUISelectItem();
        item.setItemValue(new Integer(3));
        assertEquals(new Integer(3), item.getItemValue());
    }

    public void testSetGetItemValue_ValueBinding() {
        UISelectItem item = createUISelectItem();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, new Integer(3));
        item.setValueBinding("itemValue", vb);
        assertEquals(new Integer(3), item.getItemValue());
        assertEquals(new Integer(3), item.getValueBinding("itemValue")
                .getValue(context));
    }

    public void testSetGetValue() {
        UISelectItem item = createUISelectItem();
        item.setValue("a");
        assertEquals("a", item.getValue());
    }

    public void testSetGetValue_ValueBinding() {
        UISelectItem item = createUISelectItem();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "a");
        item.setValueBinding("value", vb);
        assertEquals("a", item.getValue());
        assertEquals("a", item.getValueBinding("value").getValue(context));
    }

    private UISelectItem createUISelectItem() {
        return (UISelectItem) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UISelectItem();
    }

}
