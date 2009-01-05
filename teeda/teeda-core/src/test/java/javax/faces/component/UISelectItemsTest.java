/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
 * @author manhole
 */
public class UISelectItemsTest extends UIComponentBaseTest {

    public void testSetGetValue() {
        UISelectItems items = createUISelectItems();
        items.setValue("a");
        assertEquals("a", items.getValue());
    }

    public void testSetGetValue_ValueBinding() {
        UISelectItems items = createUISelectItems();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "a");
        items.setValueBinding("value", vb);
        assertEquals("a", items.getValue());
        assertEquals("a", items.getValueBinding("value").getValue(context));
    }

    private UISelectItems createUISelectItems() {
        return (UISelectItems) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UISelectItems();
    }

}
