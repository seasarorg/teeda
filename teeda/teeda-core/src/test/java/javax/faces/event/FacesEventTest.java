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
package javax.faces.event;

import javax.faces.component.UIComponent;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockUIComponent;

/**
 * @author shot
 */
public class FacesEventTest extends TestCase {

    public void testFacesEvent() {
        MockUIComponent component = new MockUIComponent();
        FacesEvent event = new TargetFacesEvent(component);
        assertNotNull(event);
        try {
            FacesEvent event2 = new TargetFacesEvent(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    public void testGetComponent() {
        MockUIComponent component = new MockUIComponent();
        FacesEvent event = new TargetFacesEvent(component);
        assertEquals(component, event.getComponent());
    }

    public void testGetPhaseId() {
        MockUIComponent component = new MockUIComponent();
        FacesEvent event = new TargetFacesEvent(component);
        assertEquals(PhaseId.ANY_PHASE, event.getPhaseId());
    }

    public void testSetPhaseId() {
        MockUIComponent component = new MockUIComponent();
        FacesEvent event = new TargetFacesEvent(component);
        event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);

        assertEquals(PhaseId.APPLY_REQUEST_VALUES, event.getPhaseId());
    }

    // TODO : need to test correctly. In now, no idea for this.
    public void testQueue() {
        MockUIComponent component = new MockUIComponent();
        FacesEvent event = new TargetFacesEvent(component);

        event.queue();
    }

    private class TargetFacesEvent extends FacesEvent {

        public TargetFacesEvent(UIComponent component) {
            super(component);
        }

        public boolean isAppropriateListener(FacesListener listener) {
            throw new UnsupportedOperationException();
        }

        public void processListener(FacesListener listener) {
            throw new UnsupportedOperationException();
        }

    }

}
