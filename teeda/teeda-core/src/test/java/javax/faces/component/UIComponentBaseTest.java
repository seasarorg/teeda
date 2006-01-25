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
import java.util.HashMap;
import java.util.List;

import javax.faces.event.FacesListener;

import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockExternalContextImpl;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.mock.MockUIComponentBase;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.mock.NullFacesContext;
import org.seasar.teeda.core.mock.NullFacesEvent;
import org.seasar.teeda.core.mock.NullFacesListener;
import org.seasar.teeda.core.unit.ExceptionAssert;

public class UIComponentBaseTest extends AbstractUIComponentTest {

    public void testSetGetValueBinding() {
        UIComponentBase base = createUIComponentBase();
        MockValueBinding vb = new MockValueBinding();
        base.setValueBinding("hoge", vb);
        assertTrue(vb == base.getValueBinding("hoge"));
    }

    public void testSetGetId() {
        UIComponentBase component = createUIComponentBase();
        assertEquals(null, component.getId());
        component.setId("a12345");
        assertEquals("a12345", component.getId());
    }

    public void testGetParent() {
        UIComponentBase component = createUIComponentBase();
        assertEquals(null, component.getParent());
        UIComponent parent = createUIComponent();
        component.setParent(parent);
        assertSame(parent, component.getParent());
    }

    public void testSetGetRendered() throws Exception {
        UIComponentBase componentBase = createUIComponentBase();
        componentBase.setRendered(false);
        assertEquals(false, componentBase.isRendered());
    }

    public void testSetGetRendered_ValueBinding() throws Exception {
        UIComponentBase componentBase = createUIComponentBase();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), Boolean.FALSE);
        componentBase.setValueBinding("rendered", vb);
        assertEquals(false, componentBase.isRendered());
    }

    public void testSetGetRendererType() throws Exception {
        UIComponentBase componentBase = createUIComponentBase();
        componentBase.setRendererType("foo rendererType");
        assertEquals("foo rendererType", componentBase.getRendererType());
    }

    public void testSetGetRendererType_ValueBinding() throws Exception {
        UIComponentBase componentBase = createUIComponentBase();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar rendererType");
        componentBase.setRendererType(null); // XXX
        componentBase.setValueBinding("rendererType", vb);
        assertEquals("bar rendererType", componentBase.getRendererType());
    }

    public void testGetRendersChildren() {
        // TODO testing.
    }

    public void testGetChildCount() {
        UIComponent component = createUIComponentBase();
        assertEquals(0, component.getChildCount());
        component.getChildren().add(new MockUIComponent());
        assertEquals(1, component.getChildCount());
        component.getChildren().add(new MockUIComponent());
        assertEquals(2, component.getChildCount());
        component.getChildren().add(new MockUIComponent());
        assertEquals(3, component.getChildCount());
    }

    public void testGetChildren() {
        // ## Arrange ##
        UIComponent component = createUIComponentBase();
        assertEquals(0, component.getChildCount());
        UIComponent child1 = new MockUIComponent();
        UIComponent child2 = new MockUIComponent();
        UIComponent child3 = new MockUIComponent();
        component.getChildren().add(child1);
        component.getChildren().add(child2);
        component.getChildren().add(child3);

        // ## Act & Assert
        assertSame(child1, component.getChildren().get(0));
        assertSame(child2, component.getChildren().get(1));
        assertSame(child3, component.getChildren().get(2));
    }

    public void testFindComponent() {
        UIComponent component = createUIComponentBase();
        component.setId("aa");
        // base will be the root UIComponent
        UIComponent found = component.findComponent("aa");
        assertSame(component, found);
    }

    public void testFindComponent_Absolute() {
        UIComponentBase component = createUIComponentBase();
        component.setId("aa");
        // base will be the root UIComponent
        UIComponent found = component.findComponent(":aa");
        assertSame(component, found);
    }

    public void testFindComponent_Ance() {
        UIComponentBase component = createUIComponentBase();
        component.setId("aa");
        // base will be the root UIComponent
        UIComponent found = component.findComponent(":aa");
        assertSame(component, found);
    }

    public void testFindComponent_NotSearchFromDescendantNamingContainer() {
        // ## Arrange ##
        UIComponentBase component = createUIComponentBase();
        component.setId("foo");

        UIComponent target = new MockUIComponent();
        target.setId("bb");

        UIForm namingContainer = new UIForm();
        namingContainer.setId("aa");
        namingContainer.getChildren().add(target);

        component.getChildren().add(namingContainer);

        // ## Act ##
        UIComponent found = component.findComponent("bb");

        // ## Assert ##
        assertEquals(null, found);
    }

    public void testFindComponent_SearchDescendant() {
        // ## Arrange ##
        UIComponentBase component = createUIComponentBase();
        component.setId("foo");

        UIComponent target = new MockUIComponentBase();
        target.setId("bb");

        UIComponent noNamingContainerParent = new MockUIComponentBase();
        noNamingContainerParent.setId("aa");
        noNamingContainerParent.getChildren().add(target);
        noNamingContainerParent.getChildren().add(component);

        // ## Act ##
        UIComponent found = component.findComponent("bb");

        // ## Assert ##
        if (component instanceof NamingContainer) {
            assertEquals(null, found);
        } else {
            assertEquals(target, found);
        }
    }

    public void testFindComponent_SearchFromDescendantNamingContainer() {
        // ## Arrange ##
        UIComponentBase component = createUIComponentBase();
        component.setId("foo");

        UIComponent target = new MockUIComponent();
        target.setId("bb");

        UIForm namingContainer = new UIForm();
        namingContainer.setId("aa");
        namingContainer.getChildren().add(target);

        component.getChildren().add(namingContainer);

        // ## Act ##
        UIComponent found = component.findComponent("aa:bb");

        // ## Assert ##
        assertSame(target, found);
    }

    public void testFindComponent_SearchFromAncestorNamingContainer() {
        // ## Arrange ##
        UIComponentBase component = createUIComponentBase();
        component.setId("foo");

        UIComponent target = new MockUIComponent();
        target.setId("bb");

        UIForm namingContainerParent = new UIForm();
        namingContainerParent.setId("aa");
        namingContainerParent.getChildren().add(target);
        namingContainerParent.getChildren().add(component);

        // ## Act ##
        UIComponent found = component.findComponent("aa:bb");

        // ## Assert ##
        if (component instanceof NamingContainer) {
            assertEquals(null, found);
        } else {
            assertSame(target, found);
        }
    }

    public void testFindComponent_NestedNamingContainer() {
        // ## Arrange ##
        UIComponentBase component = createUIComponentBase();
        component.setId("foo");

        UIComponent target = new MockUIComponent();
        target.setId("z");

        UIForm namingContainer1 = new UIForm();
        namingContainer1.setId("a");

        namingContainer1.getChildren().add(component);

        UIComponent namingContainer2 = new UIForm();
        namingContainer2.setId("b");
        namingContainer1.getChildren().add(namingContainer2);
        namingContainer2.getChildren().add(target);

        // ## Act ##
        UIComponent found = component.findComponent("a:b:z");

        // ## Assert ##
        if (component instanceof NamingContainer) {
            assertEquals(null, found);
        } else {
            assertSame(target, found);
        }
    }

    public void testFindComponent_IntermediateUIComponentIsNotNamingContainer() {
        // ## Arrange ##
        UIComponentBase component = createUIComponentBase();
        component.setId("foo");

        UIComponent target = new MockUIComponent();
        target.setId("z");

        UIForm namingContainer = new UIForm();
        namingContainer.setId("a");

        namingContainer.getChildren().add(component);

        UIComponent noNamingContainer = new MockUIComponentBase();
        noNamingContainer.setId("b");
        namingContainer.getChildren().add(noNamingContainer);
        noNamingContainer.getChildren().add(target);

        // ## Act & Assert ##
        try {
            component.findComponent("a:b:z");
            if (component instanceof NamingContainer) {
                // OK
            } else {
                fail();
            }
        } catch (IllegalArgumentException iae) {
            ExceptionAssert.assertMessageExist(iae);
        }
    }

    public void testGetFacets() {
        // TODO testing.
    }

    public void testGetFacet() {
        // TODO testing.
    }

    public void testGetFacetsAndChildren() {
        // TODO testing.
    }

    public final void testBroadcast() throws Exception {
        // ## Arrange ##
        UIComponentBase component = createUIComponentBase();
        FacesListener facesListener1 = new NullFacesListener();
        component.addFacesListener(facesListener1);
        FacesListener facesListener2 = new NullFacesListener();
        component.addFacesListener(facesListener2);

        final List args = new ArrayList();
        NullFacesEvent facesEvent = new NullFacesEvent() {
            private static final long serialVersionUID = 1L;

            public boolean isAppropriateListener(FacesListener listener) {
                args.add(listener);
                return true;
            }

            public void processListener(FacesListener listener) {
                args.add(listener);
            }
        };

        // ## Act ##
        component.broadcast(facesEvent);

        // ## Assert ##
        assertEquals(4, args.size());
        assertSame(facesListener1, args.get(0));
        assertSame(facesListener1, args.get(1));
        assertSame(facesListener2, args.get(2));
        assertSame(facesListener2, args.get(3));
    }

    public void testDecode() throws Exception {
        // TODO testing.
    }

    public void testEncodeBegin() throws Exception {
        // TODO testing.
    }

    public void testEncodeChildren() throws Exception {
        // TODO testing.
    }

    public void testEncodeEnd() throws Exception {
        // TODO testing.
    }

    public void testHandleFacesListeners() throws Exception {
        UIComponentBase base = createUIComponentBase();
        try {
            base.addFacesListener(null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }

        FacesListener listener1 = new FacesListener() {
            public String toString() {
                return "1";
            }
        };

        FacesListener listener2 = new FacesListener() {
            public String toString() {
                return "2";
            }
        };

        base.addFacesListener(listener1);
        base.addFacesListener(listener2);

        FacesListener[] listeners = base.getFacesListeners(FacesListener.class);
        assertEquals("Assert all FacesListner", 2, listeners.length);

        base.removeFacesListener(listener2);

        listeners = base.getFacesListeners(FacesListener.class);
        assertEquals("Should be just one listener", 1, listeners.length);
        assertEquals("1", listeners[0].toString());
    }

    public void testHandleFacesListeners2() {
        UIComponentBase base = createUIComponentBase();
        MockFacesListener1 listener1 = new MockFacesListener1();
        MockFacesListener2 listener2 = new MockFacesListener2();

        base.addFacesListener(listener1);
        base.addFacesListener(listener2);

        FacesListener[] listeners1 = base
                .getFacesListeners(MockFacesListener1.class);
        assertEquals(1, listeners1.length);

        FacesListener[] listeners2 = base
                .getFacesListeners(MockFacesListener2.class);
        assertEquals(1, listeners2.length);

    }

    public void testQueueEvent_WithParent() throws Exception {
        // ## Arrange ##
        UIComponentBase component = createUIComponentBase();
        MockUIComponent parent = new MockUIComponent();

        component.setParent(parent);
        NullFacesEvent event = new NullFacesEvent();

        // ## Act ##
        component.queueEvent(event);

        // ## Assert ##
        assertNotNull(parent.getQueueEvent());
        assertSame(event, parent.getQueueEvent());
    }

    public void testProcessRestoreState() throws Exception {
        // TODO testing.
    }

    public void testProcessDecodes() throws Exception {
        // TODO testing.
    }

    public void testProcessValidators() throws Exception {
        // TODO testing.
    }

    public void testProcessUpdates() throws Exception {
        // TODO testing.
    }

    public void testProcessSaveState() throws Exception {
        // TODO testing.
    }

    public void testGetFacesContext() throws Exception {
        // TODO testing.
    }

    public void testGetRenderer() throws Exception {
        // TODO testing.
    }

    public final void testSaveAndRestoreAttachedState_AttacedObjectIsNull() {
        NullFacesContext context = new NullFacesContext();
        Object stateObj = UIComponentBase.saveAttachedState(context, null);
        UIComponentBase.restoreAttachedState(context, stateObj);
    }

    public final void testSaveAttachedState_ContextIsNull() throws Exception {
        try {
            UIComponentBase.saveAttachedState(null, new Object());
            fail();
        } catch (NullPointerException npe) {
            String message = npe.getMessage();
            assertNotNull(message);
            assertTrue(message.trim().length() > 0);
        }
    }

    public void testRestoreAttachedState() {
        // TODO testing.
    }

    private static class MockFacesListener1 implements FacesListener {
        public String toString() {
            return "mock1";
        }
    }

    private static class MockFacesListener2 implements FacesListener {
        public String toString() {
            return "mock2";
        }
    }

    private UIComponentBase createUIComponentBase() {
        return (UIComponentBase) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new MockUIComponentBase();
    }

    private MockFacesContext facesContext_;

    private MockApplication application_;

    private MockExternalContextImpl externalContext_;

    protected void setUp() throws Exception {
        super.setUp();
        facesContext_ = new MockFacesContextImpl();

        externalContext_ = new MockExternalContextImpl();
        externalContext_.setRequestParameterMap(new HashMap());
        facesContext_.setExternalContext(externalContext_);

        application_ = new MockApplication();
        facesContext_.setApplication(application_);
    }

    protected void tearDown() throws Exception {
        facesContext_.release();
        super.tearDown();
    }

    protected MockFacesContext getFacesContext() {
        return facesContext_;
    }

}
