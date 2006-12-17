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
package org.seasar.teeda.extension.component;

import java.util.Map;

import javax.faces.component.AbstractUIComponentTest;
import javax.faces.component.UIComponent;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author higa
 */
public class TSessionSaveTest extends AbstractUIComponentTest {

    private MockFacesContext context = new MockFacesContextImpl();

    protected UIComponent createUIComponent() {
        return new TSessionSave();
    }

    protected TSessionSave createTSessionSave() {
        return (TSessionSave) createUIComponent();
    }

    public void testEncodeEnd() throws Exception {
        TSessionSave sessionSave = createTSessionSave();
        sessionSave.setId("aaaSessionSave");
        MockValueBinding vb = new MockValueBinding("#{hogePage.aaa}");
        vb.setValue(context, "111");
        sessionSave.setValueBinding("value", vb);
        sessionSave.encodeEnd(context);
        Map values = sessionSave.getSaveValues(context);
        assertNotNull(values);
        assertEquals("111", values.get("aaa"));
    }

    public void testDecode() throws Exception {
        TSessionSave sessionSave = createTSessionSave();
        sessionSave.setId("aaaSessionSave");
        MockValueBinding vb = new MockValueBinding("#{hogePage.aaa}");
        sessionSave.setValueBinding("value", vb);
        Map values = sessionSave.getOrCreateSaveValues(context);
        values.put("aaa", "111");
        sessionSave.decode(context);
        assertEquals("111", vb.getValue(context));
        assertTrue(sessionSave.isLocalValueSet());
        assertEquals("111", sessionSave.getLocalValue());
    }
}
