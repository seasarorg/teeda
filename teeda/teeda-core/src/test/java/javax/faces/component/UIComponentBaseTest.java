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

import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.mock.MockUIComponentBase;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.mock.NullFacesContext;
import org.seasar.teeda.core.unit.AssertUtil;

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
        UIComponent component = createUIComponent();
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
        UIComponent component = createUIComponent();
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
        UIComponent component = createUIComponent();
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

        UIComponent target = new MockUIComponent();
        target.setId("bb");

        UIComponent notNamingContainer = new MockUIComponentBase();
        notNamingContainer.setId("aa");
        notNamingContainer.getChildren().add(target);
        notNamingContainer.getChildren().add(component);

        // ## Act ##
        UIComponent found = component.findComponent("bb");

        // ## Assert ##
        assertEquals(target, found);
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

        UIForm namingContainer = new UIForm();
        namingContainer.setId("aa");
        namingContainer.getChildren().add(target);

        namingContainer.getChildren().add(component);

        // ## Act ##
        UIComponent found = component.findComponent("aa:bb");

        // ## Assert ##
        assertSame(target, found);
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
        assertSame(target, found);
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

        UIComponent notNamingContainer = new MockUIComponentBase();
        notNamingContainer.setId("b");
        namingContainer.getChildren().add(notNamingContainer);
        notNamingContainer.getChildren().add(target);

        // ## Act & Assert ##
        try {
            component.findComponent("a:b:z");
            fail();
        } catch (IllegalArgumentException iae) {
            AssertUtil.assertExceptionMessageExist(iae);
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

    public void testBroadcast() {
        // TODO testing.
    }

    public void testDecode() {
        // TODO testing.
    }

    public void testEncodeBegin() {
        // TODO testing.
    }

    public void testEncodeChildren() {
        // TODO testing.
    }

    public void testEncodeEnd() {
        // TODO testing.
    }

    public void testHandleFacesListeners() {
        UIComponentBase base = createUIComponentBase();
        try {
            base.addFacesListener(null);
            fail();
        } catch (NullPointerException e) {
            success();
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

    private void success() {
    }

    public void testHandleFacesListeners2() {
        MockUIComponentBase base = new MockUIComponentBase();
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

    public void testQueueEvent() {
        MockUIComponent parent = new MockUIComponent();
        MockUIComponentBase target = new MockUIComponentBase();
        target.setParent(parent);

        try {
            target.queueEvent(null);
            fail();
        } catch (NullPointerException e) {
            success();
        }

        target.setParent(null);
        try {
            target.queueEvent(new FacesEvent(target) {

                public boolean isAppropriateListener(FacesListener listener) {
                    return false;
                }

                public void processListener(FacesListener listener) {
                }

            });
            fail();
        } catch (IllegalStateException e) {
            success();
        }

        target.setParent(parent);
        target.queueEvent(new FacesEvent(target) {

            public boolean isAppropriateListener(FacesListener listener) {
                return false;
            }

            public void processListener(FacesListener listener) {
            }

        });

        assertNotNull(parent.getQueueEvent());
    }

    public void testProcessRestoreState() {
        // TODO testing.
    }

    public void testProcessDecodes() throws Exception {
        // TODO testing.
    }

    public void testProcessValidators() {
        // TODO testing.
    }

    public void testProcessUpdates() {
        // TODO testing.
    }

    public void testProcessSaveState() {
        // TODO testing.
    }

    public void testGetFacesContext() {
        // TODO testing.
    }

    public void testGetRenderer() {
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

    protected FacesContext getFacesContext() {
        MockFacesContextImpl context = new MockFacesContextImpl();
        return context;
    }

}
