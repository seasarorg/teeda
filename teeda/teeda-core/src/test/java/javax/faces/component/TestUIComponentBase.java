package javax.faces.component;

import java.io.Serializable;

import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.render.RenderKitFactory;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.mock.NullFacesContext;

public class TestUIComponentBase extends AbstractUIComponentTest {

    public void testHandleValueBinding() {
        MockUIComponentBase base = new MockUIComponentBase();
        MockValueBinding vb = new MockValueBinding();
        try {
            base.setValueBinding(null, vb);
            fail();
        } catch (NullPointerException e) {
            success();
        }
        try {
            base.setValueBinding("id", vb);
            fail();
        } catch (IllegalArgumentException e) {
            success();
        }
        try {
            base.setValueBinding("parent", vb);
            fail();
        } catch (IllegalArgumentException e) {
            success();
        }

        base.setValueBinding("hoge", vb);
        assertTrue(vb == base.getValueBinding("hoge"));
    }

    public void testGetClientId() {
        MockUIComponentBase component = new MockUIComponentBase();
        component.setId("a");

        MockUIComponent parent = new MockUIComponentWithNamingContainer();
        parent.setClientId("b");
        component.setParent(parent);

        String clientId = component.getClientId(getFacesContext());
        assertNotNull(clientId);
        assertEquals("b:a", clientId);
    }

    public void testGetId() {
        // TODO getId() を実装します。
    }

    public void testSetId() {
        // TODO setId() を実装します。
    }

    public void testGetParent() {
        // TODO getParent() を実装します。
    }

    public void testSetParent() {
        // TODO setParent() を実装します。
    }

    public void testIsRendered() {
        // TODO isRendered() を実装します。
    }

    public void testSetRendered() {
        // TODO setRendered() を実装します。
    }

    public void testGetRendererType() {
        // TODO getRendererType() を実装します。
    }

    public void testSetRendererType() {
        // TODO setRendererType() を実装します。
    }

    public void testGetRendersChildren() {
        // TODO getRendersChildren() を実装します。
    }

    public void testGetChildren() {
        // TODO getChildren() を実装します。
    }

    public void testGetChildCount() {
        // TODO getChildCount() を実装します。
    }

    public void testFindComponent() {
        // TODO findComponent() を実装します。
    }

    public void testGetFacets() {
        // TODO getFacets() を実装します。
    }

    public void testGetFacet() {
        // TODO getFacet() を実装します。
    }

    public void testGetFacetsAndChildren() {
        // TODO getFacetsAndChildren() を実装します。
    }

    public void testBroadcast() {
        // TODO broadcast() を実装します。
    }

    public void testDecode() {
        // TODO decode() を実装します。
    }

    public void testEncodeBegin() {
        // TODO encodeBegin() を実装します。
    }

    public void testEncodeChildren() {
        // TODO encodeChildren() を実装します。
    }

    public void testEncodeEnd() {
        // TODO encodeEnd() を実装します。
    }

    public void testHandleFacesListeners() {

        MockUIComponentBase base = new MockUIComponentBase();
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
        // TODO processRestoreState() を実装します。
    }

    public void testProcessDecodes() {
        // TODO processDecodes() を実装します。
    }

    public void testProcessValidators() {
        // TODO processValidators() を実装します。
    }

    public void testProcessUpdates() {
        // TODO processUpdates() を実装します。
    }

    public void testProcessSaveState() {
        // TODO processSaveState() を実装します。
    }

    public void testGetFacesContext() {
        // TODO getFacesContext() を実装します。
    }

    public void testGetRenderer() {
        // TODO getRenderer() を実装します。
    }

    public void testSaveAndRestoreState() throws Exception {
        UIComponentBase component1 = (UIComponentBase) createUIComponent();
        component1.setId("abc");
        component1.setRendered(true);
        component1.setRendererType("some renderer");

        MockFacesContext context = getFacesContext();
        UIViewRoot viewRoot = new UIViewRoot();
        viewRoot.setRenderKitId(RenderKitFactory.HTML_BASIC_RENDER_KIT);
        context.setViewRoot(viewRoot);
        Object state = component1.saveState(context);
        assertTrue(state instanceof Serializable);

        UIComponentBase component2 = (UIComponentBase) createUIComponent();
        component2.restoreState(context, state);

        assertEquals(component1.getAttributes().size(), component2
            .getAttributes().size());
        assertEquals(component1.getChildCount(), component2.getChildCount());
        assertEquals(component1.getClientId(context), component2
            .getClientId(context));
        assertEquals(component1.getFacets().size(), component2.getFacets()
            .size());
        //assertEquals(component1.getFacetsAndChildren(), component2
        //    .getFacetsAndChildren());
        assertEquals(component1.getId(), component2.getId());
        assertEquals(component1.getParent(), component2.getParent());
        assertEquals(component1.getRendererType(), component2.getRendererType());
        assertEquals(component1.getRendersChildren(), component2
            .getRendersChildren());
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
        // TODO restoreAttachedState() を実装します。
    }

    public void testUIComponent() {
        // TODO UIComponent() を実装します。
    }

    public void testGetFamily() {
        // TODO getFamily() を実装します。
    }

    private static class MockUIComponentBase extends UIComponentBase {

        public String getFamily() {
            return "";
        }

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

    private static class MockUIComponentWithNamingContainer extends
        MockUIComponent implements NamingContainer {

    }

    protected UIComponent createUIComponent() {
        return new MockUIComponentBase();
    }

}
