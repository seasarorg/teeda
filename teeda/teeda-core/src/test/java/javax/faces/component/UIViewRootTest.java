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

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import junitx.framework.StringAssert;

import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.mock.NullFacesEvent;

/**
 * @author manhole
 */
public class UIViewRootTest extends UIComponentBaseTest {

    public void testSetGetRenderKitId() {
        UIViewRoot viewRoot = createUIViewRoot();
        assertEquals(null, viewRoot.getRenderKitId());
        viewRoot.setRenderKitId("RENDER");
        assertEquals("RENDER", viewRoot.getRenderKitId());
    }

    public void testSetGetRenderKitId_ValueBinding() {
        UIViewRoot viewRoot = createUIViewRoot();
        assertEquals(null, viewRoot.getValueBinding("renderKitId"));
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "aaa");
        viewRoot.setValueBinding("renderKitId", vb);
        assertEquals("aaa", viewRoot.getRenderKitId());
        assertEquals("aaa", viewRoot.getValueBinding("renderKitId").getValue(
                context));
    }

    public void testGetRenderKitId_withNoValueBinding() throws Exception {
        UIViewRoot viewRoot = createUIViewRoot();
        viewRoot.setValueBinding("renderKitId", null);
        assertEquals(null, viewRoot.getRenderKitId());
    }

    public void testSetGetViewId() {
        UIViewRoot viewRoot = createUIViewRoot();
        assertEquals(null, viewRoot.getViewId());
        viewRoot.setViewId("bbb");
        assertEquals("bbb", viewRoot.getViewId());
    }

    public void testSetGetViewId_ValueBindingNotWork() {
        UIViewRoot viewRoot = createUIViewRoot();
        assertEquals(null, viewRoot.getValueBinding("viewId"));
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "aaa");
        viewRoot.setValueBinding("viewId", vb);
        assertEquals(null, viewRoot.getRenderKitId());
        assertEquals("aaa", viewRoot.getValueBinding("viewId")
                .getValue(context));
    }

    public void testQueueEvent() throws Exception {
        // TODO
        // ## Arrange ##
        UIViewRoot viewRoot = createUIViewRoot();
        assertEquals(0, viewRoot.getEventSize());

        // ## Act ##
        viewRoot.queueEvent(new NullFacesEvent());

        // ## Assert ##
        assertEquals(1, viewRoot.getEventSize());
    }

    public void testQueueEvent_WithParent() throws Exception {
        // override the default behavior
    }

    // TODO processDecodes
    public void testProcessDecodes() throws Exception {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##
        
    }
    
    // TODO encodeBegin
    // TODO processValidators
    // TODO processUpdates
    // TODO processApplication

    public void testCreateUniqueId() {
        UIViewRoot viewRoot = createUIViewRoot();
        List l = new ArrayList();
        for (int i = 0; i < 10; i++) {
            String id = viewRoot.createUniqueId();
            StringAssert.assertStartsWith(UIViewRoot.UNIQUE_ID_PREFIX, id);
            assertEquals("should be unique:" + id, false, l.contains(id));
            l.add(id);
        }
    }

    // TODO getLocalte
    // TODO setLocale

    private UIViewRoot createUIViewRoot() {
        return (UIViewRoot) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UIViewRoot();
    }

}
