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
public class UIMessagesTest extends UIComponentBaseTest {

    public void testSetGetGlobalOnly() {
        UIMessages messages = new UIMessages();
        assertEquals(false, messages.isGlobalOnly());
        messages.setGlobalOnly(true);
        assertEquals(true, messages.isGlobalOnly());
    }

    public void testSetGetGlobalOnly_ValueBinding() {
        UIMessages messages = new UIMessages();
        assertEquals(null, messages.getValueBinding("globalOnly"));
        FacesContext context = getFacesContext();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(context, Boolean.TRUE);
        messages.setValueBinding("globalOnly", vb);
        assertEquals(true, messages.isGlobalOnly());
        assertEquals(Boolean.TRUE, messages.getValueBinding("globalOnly")
                .getValue(context));
    }

    public void testSetGetShowDetail() {
        UIMessages messages = new UIMessages();
        assertEquals(false, messages.isShowDetail());
        messages.setShowDetail(true);
        assertEquals(true, messages.isShowDetail());
    }

    public void testSetGetShowDetail_ValueBinding() {
        UIMessages messages = new UIMessages();
        assertEquals(null, messages.getValueBinding("showDetail"));
        FacesContext context = getFacesContext();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(context, Boolean.TRUE);
        messages.setValueBinding("showDetail", vb);
        assertEquals(true, messages.isShowDetail());
        assertEquals(Boolean.TRUE, messages.getValueBinding("showDetail")
                .getValue(context));
    }

    public void testSetGetShowSummary() {
        UIMessages messages = new UIMessages();
        assertEquals(true, messages.isShowSummary());
        messages.setShowSummary(false);
        assertEquals(false, messages.isShowSummary());
    }

    public void testSetGetShowSummary_ValueBinding() {
        UIMessages messages = new UIMessages();
        assertEquals(null, messages.getValueBinding("showSummary"));
        FacesContext context = getFacesContext();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(context, Boolean.TRUE);
        messages.setValueBinding("showSummary", vb);
        assertEquals(true, messages.isShowSummary());
        assertEquals(Boolean.TRUE, messages.getValueBinding("showSummary")
                .getValue(context));
    }

}
