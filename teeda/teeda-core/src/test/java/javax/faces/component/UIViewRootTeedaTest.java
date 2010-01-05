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

import java.util.Stack;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

import org.seasar.teeda.core.mock.MockFacesEvent;
import org.seasar.teeda.core.mock.NullUIComponent;

/**
 * @author shot
 */
public class UIViewRootTeedaTest extends UIComponentBaseTeedaTest {

    public void testQueueEvent_withAnyPhase() throws Exception {
        UIViewRoot root = createUIViewRoot();
        BroadcastStackUIComponent component = new BroadcastStackUIComponent();
        MockFacesEvent event = new MockFacesEvent(component);
        // do work any phase
        event.setPhaseId(PhaseId.ANY_PHASE);
        root.queueEvent(event);

        root.broadcastEvents(getFacesContext(), PhaseId.APPLY_REQUEST_VALUES);

        Object o = component.getBroadcastedFacesEvent().pop();
        assertNotNull(o);
        assertTrue(o instanceof MockFacesEvent);
        assertEquals(event, o);
    }

    public void testQueueEvent_withOneSelectedPhase() throws Exception {
        UIViewRoot root = createUIViewRoot();
        BroadcastStackUIComponent component = new BroadcastStackUIComponent();
        MockFacesEvent event = new MockFacesEvent(component);
        event.setPhaseId(PhaseId.PROCESS_VALIDATIONS);
        root.queueEvent(event);

        root.broadcastEvents(getFacesContext(), PhaseId.PROCESS_VALIDATIONS);

        Object o = component.getBroadcastedFacesEvent().pop();
        assertNotNull(o);
        assertTrue(o instanceof MockFacesEvent);
        assertEquals(event, o);
    }

    public void testQueueEvent_abort() throws Exception {
        UIViewRoot root = createUIViewRoot();
        AbortUIComponent component1 = new AbortUIComponent();
        MockFacesEvent event1 = new MockFacesEvent(component1);

        BroadcastStackUIComponent component2 = new BroadcastStackUIComponent();
        MockFacesEvent event2 = new MockFacesEvent(component2);
        event1.setPhaseId(PhaseId.PROCESS_VALIDATIONS);
        root.queueEvent(event1);
        root.queueEvent(event2);

        root.broadcastEvents(getFacesContext(), PhaseId.PROCESS_VALIDATIONS);

        assertEquals(0, root.getEventSize());
    }

    public void testSaveAndRestoreState() throws Exception {
        super.testSaveAndRestoreState();
        createUIViewRoot();
        // TODO test
    }

    private UIViewRoot createUIViewRoot() {
        return (UIViewRoot) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UIViewRoot();
    }

    private static class BroadcastStackUIComponent extends NullUIComponent {
        Stack stack = new Stack();

        public void broadcast(FacesEvent event) throws AbortProcessingException {
            stack.push(event);
        }

        public Stack getBroadcastedFacesEvent() {
            return stack;
        }
    }

    private static class AbortUIComponent extends NullUIComponent {
        public void broadcast(FacesEvent event) throws AbortProcessingException {
            throw new AbortProcessingException();
        }
    }

}
