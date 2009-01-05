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

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author manhole
 */
public class UISelectBooleanTest extends UIInputTest {

    public final void testSetGetSelected() {
        UISelectBoolean selectBoolean = createUISelectBoolean();
        assertEquals(false, selectBoolean.isSelected());
        selectBoolean.setSelected(true);
        assertEquals(true, selectBoolean.isSelected());
    }

    public final void testSetGetSelected_ValueBinding() {
        UISelectBoolean selectBoolean = createUISelectBoolean();
        MockValueBinding vb = new MockValueBinding();
        MockFacesContext context = getFacesContext();
        vb.setValue(context, Boolean.TRUE);
        selectBoolean.setValueBinding("selected", vb);
        assertEquals(true, selectBoolean.isSelected());
        assertEquals(Boolean.TRUE, selectBoolean.getValueBinding("selected")
                .getValue(context));
    }

    public void testSelectedIsAliasForValue() throws Exception {
        UISelectBoolean selectBoolean = createUISelectBoolean();
        assertEquals(null, selectBoolean.getValue());

        selectBoolean.setSelected(true);
        assertEquals(Boolean.TRUE, selectBoolean.getValue());

        selectBoolean.setSelected(false);
        assertEquals(Boolean.FALSE, selectBoolean.getValue());

        selectBoolean.setValue(Boolean.TRUE);
        assertEquals(true, selectBoolean.isSelected());
    }

    public void testSelectedIsAliasForValue_BalueBinding() throws Exception {
        UISelectBoolean selectBoolean = createUISelectBoolean();
        assertEquals(null, selectBoolean.getValueBinding("selected"));
        {
            MockValueBinding vb = new MockValueBinding();
            MockFacesContext context = getFacesContext();
            vb.setValue(context, Boolean.TRUE);
            selectBoolean.setValueBinding("value", vb);
            assertEquals(Boolean.TRUE, selectBoolean
                    .getValueBinding("selected").getValue(context));
        }
        {
            MockValueBinding vb = new MockValueBinding();
            MockFacesContext context = getFacesContext();
            vb.setValue(context, Boolean.FALSE);
            selectBoolean.setValueBinding("selected", vb);
            assertEquals(Boolean.FALSE, selectBoolean.getValueBinding("value")
                    .getValue(context));
        }
    }

    private UISelectBoolean createUISelectBoolean() {
        return (UISelectBoolean) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UISelectBoolean();
    }

}
