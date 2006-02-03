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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.FacesListener;
import javax.faces.internal.SerializableStateHolder;

import junitx.framework.ObjectAssert;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.mock.MockUIComponentBase;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.mock.NullFacesContext;
import org.seasar.teeda.core.mock.NullFacesEvent;
import org.seasar.teeda.core.mock.NullFacesListener;
import org.seasar.teeda.core.mock.NullUIComponent;
import org.seasar.teeda.core.unit.ExceptionAssert;

public class UIComponentBaseTest extends AbstractUIComponentTest {

    public void testSetGetValueBinding() {
        UIComponentBase component = createUIComponentBase();
        MockValueBinding vb = new MockValueBinding();
        component.setValueBinding("hoge", vb);
        assertTrue(vb == component.getValueBinding("hoge"));
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
        componentBase.setRendererType(null);
        componentBase.setValueBinding("rendererType", vb);
        assertEquals("bar rendererType", componentBase.getRendererType());
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

    public void testFindComponent_Parent() {
        UIComponentBase component = createUIComponentBase();
        component.setId("aa");
        UIComponent target = new MockUIComponentBase();
        target.setId("bb");
        target.getChildren().add(component);

        UIComponent found = component.findComponent("bb");
        if (component instanceof NamingContainer) {
            assertEquals(null, found);
        } else {
            assertSame(target, found);
        }
    }

    public void testFindComponent_FromParent() {
        UIComponentBase component = createUIComponentBase();
        component.setId("aa");
        UIComponent target = new MockUIComponentBase();
        target.setId("bb");
        target.getChildren().add(component);

        UIComponent found = component.findComponent("aa");
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
        if (component instanceof NamingContainer) {
            component.findComponent("a:b:z");
        } else {
            try {
                component.findComponent("a:b:z");
                fail();
            } catch (IllegalArgumentException iae) {
                ExceptionAssert.assertMessageExist(iae);
            }
        }
    }

    public void testGetFacetsAndFacet() {
        UIComponentBase component = createUIComponentBase();

        NullUIComponent facet1 = new NullUIComponent();
        component.getFacets().put("a", facet1);
        assertSame(facet1, component.getFacets().get("a"));
        assertEquals(null, component.getFacets().get("b"));

        assertSame(facet1, component.getFacet("a"));
        assertEquals(null, component.getFacet("b"));
    }

    public void testGetFacetsAndChildren() {
        // ## Arrange ##
        UIComponent component = createUIComponentBase();
        assertEquals(0, component.getChildCount());
        UIComponent child1 = new MockUIComponent();
        UIComponent child2 = new MockUIComponent();
        UIComponent child3 = new MockUIComponent();
        UIComponent facet1 = new MockUIComponent();
        UIComponent facet2 = new MockUIComponent();
        component.getChildren().add(child1);
        component.getFacets().put("facet1", facet1);
        component.getChildren().add(child2);
        component.getFacets().put("facet2", facet2);
        component.getChildren().add(child3);

        // ## Act & Assert
        Iterator it = component.getFacetsAndChildren();
        // Facets are undefined order
        assertSame(facet1, (UIComponent) it.next());
        assertSame(facet2, (UIComponent) it.next());
        assertSame(child1, (UIComponent) it.next());
        assertSame(child2, (UIComponent) it.next());
        assertSame(child3, (UIComponent) it.next());

    }

    public void testGetFacetsAndChildren_EmptyIterator() throws Exception {
        // ## Arrange ##
        UIComponent component = createUIComponentBase();

        // ## Act ##
        Iterator it = component.getFacetsAndChildren();

        // ## Assert ##
        assertEquals(false, it.hasNext());
    }

    public void testGetFacetsAndChildren_IteratorNotSupportRemove()
            throws Exception {
        // ## Arrange ##
        UIComponent component = createUIComponentBase();

        // ## Act ##
        Iterator it = component.getFacetsAndChildren();

        // ## Assert ##
        try {
            it.remove();
        } catch (UnsupportedOperationException uoe) {
            ExceptionAssert.assertMessageExist(uoe);
        }
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

    public void testHandleFacesListeners() throws Exception {
        UIComponentBase component = createUIComponentBase();
        try {
            component.addFacesListener(null);
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

        component.addFacesListener(listener1);
        component.addFacesListener(listener2);

        FacesListener[] listeners = component
                .getFacesListeners(FacesListener.class);
        assertEquals("Assert all FacesListner", 2, listeners.length);

        component.removeFacesListener(listener2);

        listeners = component.getFacesListeners(FacesListener.class);
        assertEquals("Should be just one listener", 1, listeners.length);
        assertEquals("1", listeners[0].toString());
    }

    public void testHandleFacesListeners2() {
        UIComponentBase component = createUIComponentBase();
        MockFacesListener1 listener1 = new MockFacesListener1();
        MockFacesListener2 listener2 = new MockFacesListener2();

        component.addFacesListener(listener1);
        component.addFacesListener(listener2);

        FacesListener[] listeners1 = component
                .getFacesListeners(MockFacesListener1.class);
        assertEquals(1, listeners1.length);

        FacesListener[] listeners2 = component
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

    public void testProcessSaveState_TransientTrue() throws Exception {
        UIComponentBase component = createUIComponentBase();
        component.setTransient(true);
        Object state = component.processSaveState(getFacesContext());
        assertEquals(null, state);
    }

    public void testProcessSaveState() throws Exception {
        // ## Arrange ##
        final List callSeq = new ArrayList();
        UIComponentBase component = createUIComponentBase();
        {
            component.getChildren().add(new NullUIComponent() {
                public Object processSaveState(FacesContext context) {
                    callSeq.add("1");
                    return "1";
                }
            });
            component.getChildren().add(new NullUIComponent() {
                public Object processSaveState(FacesContext context) {
                    callSeq.add("2");
                    return "1";
                }
            });
            component.getFacets().put("A", new NullUIComponent() {
                public Object processSaveState(FacesContext context) {
                    callSeq.add("3");
                    return "1";
                }
            });
            component.getFacets().put("B", new NullUIComponent() {
                public Object processSaveState(FacesContext context) {
                    callSeq.add("4");
                    return "1";
                }
            });
        }

        // ## Act ##
        Object state = component.processSaveState(getFacesContext());

        // ## Assert ##
        assertEquals(4, callSeq.size());
        ObjectAssert.assertInstanceOf(Serializable.class, state);
    }

    public void testProcessRestoreState() throws Exception {
        // ## Arrange ##
        final List callSeq = new ArrayList();
        UIComponentBase component = new MockUIComponentBase() {
            public void restoreState(FacesContext context, Object state) {
                callSeq.add("5");
            }
        };
        {
            component.getChildren().add(new NullUIComponent() {
                public void processRestoreState(FacesContext context,
                        Object state) {
                    callSeq.add("1");
                }
            });
            component.getChildren().add(new NullUIComponent() {
                public void processRestoreState(FacesContext context,
                        Object state) {
                    callSeq.add("2");
                }
            });
            component.getFacets().put("A", new NullUIComponent() {
                public void processRestoreState(FacesContext context,
                        Object state) {
                    callSeq.add("3");
                }
            });
            component.getFacets().put("B", new NullUIComponent() {
                public void processRestoreState(FacesContext context,
                        Object state) {
                    callSeq.add("4");
                }
            });
        }
        HashMap m = new HashMap();
        m.put("A", "a");
        m.put("B", "b");

        // ## Act ##
        component.processRestoreState(getFacesContext(),
                new SerializableStateHolder(null, m, Arrays
                        .asList(new Object[] { "", "" })));

        // ## Assert ##
        assertEquals(5, callSeq.size());
        assertEquals(callSeq.toString(), "5", callSeq.get(4));
    }

    public final void testSaveAndRestoreAttachedState_AttacedObjectIsNull() {
        NullFacesContext context = new NullFacesContext();
        Object stateObj = UIComponentBase.saveAttachedState(context, null);
        UIComponentBase.restoreAttachedState(context, stateObj);
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

    protected void setUp() throws Exception {
        super.setUp();
        facesContext_ = new MockFacesContextImpl();
    }

    protected void tearDown() throws Exception {
        facesContext_.release();
        super.tearDown();
    }

    public MockFacesContext getFacesContext() {
        return facesContext_;
    }

}
