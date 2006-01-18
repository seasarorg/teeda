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

import junitx.framework.ObjectAssert;

import org.seasar.teeda.core.mock.MockUIComponentBase;
import org.seasar.teeda.core.mock.MockUIComponentBaseWithNamingContainer;

/**
 * @author manhole
 */
public class UIDataTeedaTest extends UIComponentBaseTeedaTest {

    // TODO test
    public void testGetCliendId() throws Exception {
        // ## Arrange ##
        UIData component = createUIData();
        component.setId("foo");

        // ## Act & Assert ##
        assertEquals("foo:0", component.getClientId(getFacesContext()));
    }

    public void testGetClientId_WithParentNamingContainer() {
        UIData component = createUIData();
        component.setId("a");

        MockUIComponentBase parent = new MockUIComponentBaseWithNamingContainer();
        parent.setClientId("b");
        parent.getChildren().add(component);

        String clientId = component.getClientId(getFacesContext());
        assertNotNull(clientId);
        assertEquals("b:a:0", clientId);
    }

    public void testGetClientId_WithParentNotNamingContainer() {
        UIData component = createUIData();
        component.setId("a");

        MockUIComponentBase parent = new MockUIComponentBase();
        ObjectAssert.assertNotInstanceOf(NamingContainer.class, parent);
        parent.setClientId("b");
        parent.getChildren().add(component);

        String clientId = component.getClientId(getFacesContext());
        assertNotNull(clientId);
        assertEquals("a:0", clientId);
    }

    private UIData createUIData() {
        return (UIData) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UIData();
    }

}
