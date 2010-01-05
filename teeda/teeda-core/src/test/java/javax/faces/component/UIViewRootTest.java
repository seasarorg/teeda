/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

import junit.framework.AssertionFailedError;
import junitx.framework.StringAssert;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockFacesEvent;
import org.seasar.teeda.core.mock.MockUIComponentBase;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.mock.MockViewHandler;
import org.seasar.teeda.core.mock.MockViewHandlerImpl;
import org.seasar.teeda.core.mock.NullFacesEvent;
import org.seasar.teeda.core.mock.NullUIComponent;
import org.seasar.teeda.core.unit.ExceptionAssert;

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

    public void testProcessDecodes_Broadcast() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        UIViewRoot viewRoot = createUIViewRoot();
        {
            NullUIComponent eventSource = new NullUIComponent() {
                public void broadcast(FacesEvent event)
                        throws AbortProcessingException {
                    calls[0] = true;
                }
            };
            FacesEvent event = new MockFacesEvent(eventSource);
            event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
            viewRoot.queueEvent(event);
        }
        {
            NullUIComponent eventSource = new NullUIComponent() {
                public void broadcast(FacesEvent event)
                        throws AbortProcessingException {
                    throw new AssertionFailedError("should not be called");
                }
            };
            FacesEvent event = new MockFacesEvent(eventSource);
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            viewRoot.queueEvent(event);
        }
        FacesContext context = getFacesContext();
        assertEquals(2, viewRoot.getEventSize());

        // ## Act ##
        viewRoot.processDecodes(context);

        // ## Assert ##
        assertEquals(true, calls[0]);
        assertEquals(1, viewRoot.getEventSize());
    }

    public void testProcessDecodes_NotBroadcast() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        UIViewRoot viewRoot = createUIViewRoot();
        {
            NullUIComponent eventSource = new NullUIComponent() {
                public void broadcast(FacesEvent event)
                        throws AbortProcessingException {
                    calls[0] = true;
                }
            };
            FacesEvent event = new MockFacesEvent(eventSource);
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            viewRoot.queueEvent(event);
        }
        FacesContext context = getFacesContext();
        assertEquals(1, viewRoot.getEventSize());

        // ## Act ##
        viewRoot.processDecodes(context);

        // ## Assert ##
        assertEquals(false, calls[0]);
        assertEquals(1, viewRoot.getEventSize());
    }

    public void testProcessDecodes_BroadcastRenderResponseCalled()
            throws Exception {
        // ## Arrange ##
        UIViewRoot viewRoot = createUIViewRoot();
        {
            UIComponent eventSource = new MockUIComponentBase() {
                public void broadcast(FacesEvent event)
                        throws AbortProcessingException {
                    event.getComponent().getFacesContext().renderResponse();
                }
            };
            FacesEvent event = new MockFacesEvent(eventSource);
            event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
            viewRoot.queueEvent(event);
        }
        {
            NullUIComponent eventSource = new NullUIComponent() {
                public void broadcast(FacesEvent event)
                        throws AbortProcessingException {
                    throw new AssertionFailedError("should not be called");
                }
            };
            FacesEvent event = new MockFacesEvent(eventSource);
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            viewRoot.queueEvent(event);
        }
        FacesContext context = getFacesContext();
        assertEquals(2, viewRoot.getEventSize());

        // ## Act ##
        viewRoot.processDecodes(context);

        // ## Assert ##
        assertEquals(0, viewRoot.getEventSize());
    }

    public void testEncodeBegin() throws Exception {
        // ## Arrange ##
        UIViewRoot viewRoot = createUIViewRoot();

        // ## Act ##
        String id = viewRoot.createUniqueId();
        viewRoot.encodeBegin(getFacesContext());

        // ## Assert ##
        assertEquals("unique counter should be reset by encodeBegin", id,
                viewRoot.createUniqueId());
    }

    public void testProcessValidators_Broadcast() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        UIViewRoot viewRoot = createUIViewRoot();
        {
            NullUIComponent eventSource = new NullUIComponent() {
                public void broadcast(FacesEvent event)
                        throws AbortProcessingException {
                    calls[0] = true;
                }
            };
            FacesEvent event = new MockFacesEvent(eventSource);
            event.setPhaseId(PhaseId.PROCESS_VALIDATIONS);
            viewRoot.queueEvent(event);
        }
        {
            NullUIComponent eventSource = new NullUIComponent() {
                public void broadcast(FacesEvent event)
                        throws AbortProcessingException {
                    throw new AssertionFailedError("should not be called");
                }
            };
            FacesEvent event = new MockFacesEvent(eventSource);
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            viewRoot.queueEvent(event);
        }
        FacesContext context = getFacesContext();
        assertEquals(2, viewRoot.getEventSize());

        // ## Act ##
        viewRoot.processValidators(context);

        // ## Assert ##
        assertEquals(true, calls[0]);
        assertEquals(1, viewRoot.getEventSize());
    }

    public void testProcessValidators_NotBroadcast() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        UIViewRoot viewRoot = createUIViewRoot();
        {
            NullUIComponent eventSource = new NullUIComponent() {
                public void broadcast(FacesEvent event)
                        throws AbortProcessingException {
                    calls[0] = true;
                }
            };
            FacesEvent event = new MockFacesEvent(eventSource);
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            viewRoot.queueEvent(event);
        }
        FacesContext context = getFacesContext();
        assertEquals(1, viewRoot.getEventSize());

        // ## Act ##
        viewRoot.processValidators(context);

        // ## Assert ##
        assertEquals(false, calls[0]);
        assertEquals(1, viewRoot.getEventSize());
    }

    public void testProcessValidators_BroadcastRenderResponseCalled()
            throws Exception {
        // ## Arrange ##
        UIViewRoot viewRoot = createUIViewRoot();
        {
            UIComponent eventSource = new MockUIComponentBase() {
                public void broadcast(FacesEvent event)
                        throws AbortProcessingException {
                    event.getComponent().getFacesContext().renderResponse();
                }
            };
            FacesEvent event = new MockFacesEvent(eventSource);
            event.setPhaseId(PhaseId.PROCESS_VALIDATIONS);
            viewRoot.queueEvent(event);
        }
        {
            NullUIComponent eventSource = new NullUIComponent() {
                public void broadcast(FacesEvent event)
                        throws AbortProcessingException {
                    throw new AssertionFailedError("should not be called");
                }
            };
            FacesEvent event = new MockFacesEvent(eventSource);
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            viewRoot.queueEvent(event);
        }
        FacesContext context = getFacesContext();
        assertEquals(2, viewRoot.getEventSize());

        // ## Act ##
        viewRoot.processValidators(context);

        // ## Assert ##
        assertEquals(0, viewRoot.getEventSize());
    }

    public void testProcessUpdates_Broadcast() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        UIViewRoot viewRoot = createUIViewRoot();
        {
            NullUIComponent eventSource = new NullUIComponent() {
                public void broadcast(FacesEvent event)
                        throws AbortProcessingException {
                    calls[0] = true;
                }
            };
            FacesEvent event = new MockFacesEvent(eventSource);
            event.setPhaseId(PhaseId.UPDATE_MODEL_VALUES);
            viewRoot.queueEvent(event);
        }
        {
            NullUIComponent eventSource = new NullUIComponent() {
                public void broadcast(FacesEvent event)
                        throws AbortProcessingException {
                    throw new AssertionFailedError("should not be called");
                }
            };
            FacesEvent event = new MockFacesEvent(eventSource);
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            viewRoot.queueEvent(event);
        }
        FacesContext context = getFacesContext();
        assertEquals(2, viewRoot.getEventSize());

        // ## Act ##
        viewRoot.processUpdates(context);

        // ## Assert ##
        assertEquals(true, calls[0]);
        assertEquals(1, viewRoot.getEventSize());
    }

    public void testProcessUpdates_NotBroadcast() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        UIViewRoot viewRoot = createUIViewRoot();
        {
            NullUIComponent eventSource = new NullUIComponent() {
                public void broadcast(FacesEvent event)
                        throws AbortProcessingException {
                    calls[0] = true;
                }
            };
            FacesEvent event = new MockFacesEvent(eventSource);
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            viewRoot.queueEvent(event);
        }
        FacesContext context = getFacesContext();
        assertEquals(1, viewRoot.getEventSize());

        // ## Act ##
        viewRoot.processUpdates(context);

        // ## Assert ##
        assertEquals(false, calls[0]);
        assertEquals(1, viewRoot.getEventSize());
    }

    public void testProcessUpdates_BroadcastRenderResponseCalled()
            throws Exception {
        // ## Arrange ##
        UIViewRoot viewRoot = createUIViewRoot();
        {
            UIComponent eventSource = new MockUIComponentBase() {
                public void broadcast(FacesEvent event)
                        throws AbortProcessingException {
                    event.getComponent().getFacesContext().renderResponse();
                }
            };
            FacesEvent event = new MockFacesEvent(eventSource);
            event.setPhaseId(PhaseId.UPDATE_MODEL_VALUES);
            viewRoot.queueEvent(event);
        }
        {
            NullUIComponent eventSource = new NullUIComponent() {
                public void broadcast(FacesEvent event)
                        throws AbortProcessingException {
                    throw new AssertionFailedError("should not be called");
                }
            };
            FacesEvent event = new MockFacesEvent(eventSource);
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            viewRoot.queueEvent(event);
        }
        FacesContext context = getFacesContext();
        assertEquals(2, viewRoot.getEventSize());

        // ## Act ##
        viewRoot.processUpdates(context);

        // ## Assert ##
        assertEquals(0, viewRoot.getEventSize());
    }

    public void testProcessApplication_NullArg() throws Exception {
        // ## Arrange ##
        UIViewRoot viewRoot = createUIViewRoot();

        // ## Act ##
        // ## Assert ##
        try {
            viewRoot.processApplication(null);
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testProcessApplication_Broadcast() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        UIViewRoot viewRoot = createUIViewRoot();
        {
            NullUIComponent eventSource = new NullUIComponent() {
                public void broadcast(FacesEvent event)
                        throws AbortProcessingException {
                    calls[0] = true;
                }
            };
            FacesEvent event = new MockFacesEvent(eventSource);
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            viewRoot.queueEvent(event);
        }
        {
            NullUIComponent eventSource = new NullUIComponent() {
                public void broadcast(FacesEvent event)
                        throws AbortProcessingException {
                    throw new AssertionFailedError("should not be called");
                }
            };
            FacesEvent event = new MockFacesEvent(eventSource);
            event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
            viewRoot.queueEvent(event);
        }
        FacesContext context = getFacesContext();
        assertEquals(2, viewRoot.getEventSize());

        // ## Act ##
        viewRoot.processApplication(context);

        // ## Assert ##
        assertEquals(true, calls[0]);
        assertEquals(1, viewRoot.getEventSize());
    }

    public void testProcessApplication_NotBroadcast() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        UIViewRoot viewRoot = createUIViewRoot();
        {
            NullUIComponent eventSource = new NullUIComponent() {
                public void broadcast(FacesEvent event)
                        throws AbortProcessingException {
                    calls[0] = true;
                }
            };
            FacesEvent event = new MockFacesEvent(eventSource);
            event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
            viewRoot.queueEvent(event);
        }
        FacesContext context = getFacesContext();
        assertEquals(1, viewRoot.getEventSize());

        // ## Act ##
        viewRoot.processApplication(context);

        // ## Assert ##
        assertEquals(false, calls[0]);
        assertEquals(1, viewRoot.getEventSize());
    }

    public void testProcessApplication_BroadcastRenderResponseCalled()
            throws Exception {
        // ## Arrange ##
        UIViewRoot viewRoot = createUIViewRoot();
        {
            UIComponent eventSource = new MockUIComponentBase() {
                public void broadcast(FacesEvent event)
                        throws AbortProcessingException {
                    event.getComponent().getFacesContext().renderResponse();
                }
            };
            FacesEvent event = new MockFacesEvent(eventSource);
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            viewRoot.queueEvent(event);
        }
        {
            NullUIComponent eventSource = new NullUIComponent() {
                public void broadcast(FacesEvent event)
                        throws AbortProcessingException {
                    throw new AssertionFailedError("should not be called");
                }
            };
            FacesEvent event = new MockFacesEvent(eventSource);
            event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
            viewRoot.queueEvent(event);
        }
        FacesContext context = getFacesContext();
        assertEquals(2, viewRoot.getEventSize());

        // ## Act ##
        viewRoot.processApplication(context);

        // ## Assert ##
        assertEquals(0, viewRoot.getEventSize());
    }

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

    public void testSetGetLocale() throws Exception {
        // ## Arrange ##
        UIViewRoot viewRoot = createUIViewRoot();

        // ## Act ##
        viewRoot.setLocale(Locale.GERMAN);

        // ## Assert ##
        assertEquals(Locale.GERMAN, viewRoot.getLocale());
    }

    public void testGetLocale_ValueBinding() throws Exception {
        UIViewRoot viewRoot = createUIViewRoot();
        MockFacesContext context = getFacesContext();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(context, Locale.ITALIAN);
        viewRoot.setValueBinding("locale", vb);
        assertEquals(Locale.ITALIAN, viewRoot.getLocale());
        assertEquals(Locale.ITALIAN, viewRoot.getValueBinding("locale")
                .getValue(context));
    }

    public void testGetLocale_ValueBindingString() throws Exception {
        UIViewRoot viewRoot = createUIViewRoot();
        MockFacesContext context = getFacesContext();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(context, "ja");
        viewRoot.setValueBinding("locale", vb);
        assertEquals(Locale.JAPANESE, viewRoot.getLocale());
        assertEquals("ja", viewRoot.getValueBinding("locale").getValue(context));
    }

    public void testGetLocale_ViewHandler() throws Exception {
        // ## Arrange ##
        UIViewRoot viewRoot = createUIViewRoot();
        MockFacesContext context = getFacesContext();
        MockViewHandler handler = new MockViewHandlerImpl();
        handler.setLocale(Locale.FRENCH);
        context.getApplication().setViewHandler(handler);
        // ## Act & Assert ##
        assertEquals(Locale.FRENCH, viewRoot.getLocale());
    }

    private UIViewRoot createUIViewRoot() {
        return (UIViewRoot) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UIViewRoot();
    }

}
