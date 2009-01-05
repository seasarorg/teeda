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

public class UIMessageTest extends UIComponentBaseTest {

    public void testSeGettFor() {
        UIMessage message = createUIMessage();
        message.setFor("FOR");
        assertEquals("FOR", message.getFor());
    }

    public void testSetGetFor_ValueBinding() {
        UIMessage message = createUIMessage();
        FacesContext context = getFacesContext();
        assertEquals(null, message.getValueBinding("for"));
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(context, "aaa");
        message.setValueBinding("for", vb);
        assertEquals("aaa", message.getFor());
        assertEquals("aaa", message.getValueBinding("for").getValue(context));
    }

    public void testSetGetShowDetail() {
        UIMessage message = createUIMessage();
        assertEquals(true, message.isShowDetail());
        message.setShowDetail(false);
        assertEquals(false, message.isShowDetail());
    }

    public void testSetGetShowDetail_ValueBinding() {
        UIMessage message = createUIMessage();
        FacesContext context = getFacesContext();
        assertEquals(null, message.getValueBinding("showDetail"));
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(context, Boolean.TRUE);
        message.setValueBinding("showDetail", vb);
        assertEquals(true, message.isShowDetail());
        assertEquals(Boolean.TRUE, message.getValueBinding("showDetail")
                .getValue(context));
    }

    public void testSetGetShowSummary() {
        UIMessage message = createUIMessage();
        assertEquals(false, message.isShowSummary());
        message.setShowSummary(true);
        assertEquals(true, message.isShowSummary());
    }

    public void testSetGetShowSummary_ValueBinding() {
        UIMessage message = createUIMessage();
        FacesContext context = getFacesContext();
        assertEquals(null, message.getValueBinding("showSummary"));
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(context, Boolean.TRUE);
        message.setValueBinding("showSummary", vb);
        assertEquals(true, message.isShowSummary());
        assertEquals(Boolean.TRUE, message.getValueBinding("showSummary")
                .getValue(context));
    }

    private UIMessage createUIMessage() {
        return (UIMessage) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UIMessage();
    }

}
