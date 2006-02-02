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

/**
 * @author manhole
 */
public class UIViewRootTest extends UIComponentBaseTest {

    public void testSetGetRenderKitId() {
        UIViewRoot root = new UIViewRoot();
        assertEquals(null, root.getRenderKitId());
        root.setRenderKitId("RENDER");
        assertEquals("RENDER", root.getRenderKitId());
    }

    public void testSetGetRenderKitId_ValueBinding() {
        UIViewRoot root = new UIViewRoot();
        assertEquals(null, root.getValueBinding("renderKitId"));
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "aaa");
        root.setValueBinding("renderKitId", vb);
        assertEquals("aaa", root.getRenderKitId());
        assertEquals("aaa", root.getValueBinding("renderKitId").getValue(
                context));
    }

    public void testGetRenderKitId_withNoValueBinding() throws Exception {
        UIViewRoot root = createUIViewRoot();
        root.setValueBinding("renderKitId", null);
        assertEquals(null, root.getRenderKitId());
    }

    public void testSetGetViewId() {
        UIViewRoot root = new UIViewRoot();
        assertEquals(null, root.getViewId());
        root.setViewId("bbb");
        assertEquals("bbb", root.getViewId());
    }

    public void testSetGetViewId_ValueBindingNotWork() {
        UIViewRoot root = new UIViewRoot();
        assertEquals(null, root.getValueBinding("viewId"));
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "aaa");
        root.setValueBinding("viewId", vb);
        assertEquals(null, root.getRenderKitId());
        assertEquals("aaa", root.getValueBinding("viewId").getValue(context));
    }

    public void testQueueEvent() throws Exception {
        // TODO
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##

    }

    public void testQueueEvent_WithParent() throws Exception {
        // override the default behavior
    }

    // TODO processDecodes
    // TODO encodeBegin
    // TODO processValidators
    // TODO processUpdates
    // TODO processApplication

    public void testCreateUniqueId() {
        UIViewRoot root = createUIViewRoot();
        List l = new ArrayList();
        for (int i = 0; i < 10; i++) {
            String id = root.createUniqueId();
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
