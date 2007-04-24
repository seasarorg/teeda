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
package javax.faces.component;

import org.seasar.teeda.core.mock.MockFacesContext;

/**
 * @author manhole
 */
public class UISelectItemTeedaTest extends UIComponentBaseTeedaTest {

    public void testSaveAndRestoreState() throws Exception {
        super.testSaveAndRestoreState();
        // ## Arrange ##
        final UISelectItem component1 = createUISelectItem();
        component1.setItemDescription("aaaDesc");
        component1.setItemDisabled(true);
        component1.setItemLabel("fooLabel");
        component1.setItemValue("fooItemValue");
        component1.setValue("hogeValue");

        final MockFacesContext context = getFacesContext();

        // ## Act ##
        final Object state = component1.saveState(context);
        final UISelectItem component2 = createUISelectItem();
        component2.restoreState(context, serializeAndDeserialize(state));

        // ## Assert ##
        assertEquals(component1.getItemDescription(), component2
                .getItemDescription());
        assertEquals(component1.isItemDisabled(), component2.isItemDisabled());
        assertEquals(component1.getItemLabel(), component2.getItemLabel());
        assertEquals(component1.getValue(), component2.getValue());
    }

    private UISelectItem createUISelectItem() {
        return (UISelectItem) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UISelectItem();
    }

}
