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
package javax.faces.component;

import javax.faces.context.FacesContext;

import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author manhole
 */
public class UIParameterTest extends UIComponentBaseTest {

    public final void testSetGetName() {
        UIParameter parameter = createUIParameter();
        assertEquals(null, parameter.getName());
        parameter.setName("foo name");
        assertEquals("foo name", parameter.getName());
    }

    public final void testSetGetName_ValueBinding() {
        UIParameter parameter = createUIParameter();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar name");
        parameter.setValueBinding("name", vb);
        assertEquals("bar name", parameter.getName());
        assertEquals("bar name", parameter.getValueBinding("name").getValue(
                context));
    }

    public final void testSetGetValue() {
        UIParameter parameter = createUIParameter();
        assertEquals(null, parameter.getValue());
        parameter.setValue("foo value");
        assertEquals("foo value", parameter.getValue());
    }

    public final void testSetGetValue_ValueBinding() {
        UIParameter parameter = createUIParameter();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar value");
        parameter.setValueBinding("value", vb);
        assertEquals("bar value", parameter.getValue());
        assertEquals("bar value", parameter.getValueBinding("value").getValue(
                context));
    }

    private UIParameter createUIParameter() {
        return (UIParameter) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UIParameter();
    }

}
