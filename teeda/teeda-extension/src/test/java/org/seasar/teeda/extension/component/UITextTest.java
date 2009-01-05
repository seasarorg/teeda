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
package org.seasar.teeda.extension.component;

import javax.faces.component.AbstractUIComponentTest;
import javax.faces.component.UIComponent;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockFacesContextImpl;

/**
 * @author higa
 *
 */
public class UITextTest extends AbstractUIComponentTest {

    private MockFacesContext facesContext = new MockFacesContextImpl();

    protected UIComponent createUIComponent() {
        return new UIText();
    }

    protected UIText createUIText() {
        return (UIText) createUIComponent();
    }

    public void testSaveAndRestoreState() throws Exception {
        UIText component = createUIText();
        String value = "hoge";
        component.setValue(value);
        Object state = component.saveState(facesContext);
        UIText component2 = createUIText();
        component2.restoreState(facesContext, state);
        assertEquals("1", value, component2.getValue());
    }
}