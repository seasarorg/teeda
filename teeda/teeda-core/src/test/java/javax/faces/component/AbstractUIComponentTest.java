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

import org.seasar.teeda.core.mock.NullFacesEvent;
import org.seasar.teeda.core.mock.NullValueBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author manhole
 */
public abstract class AbstractUIComponentTest extends TeedaTestCase {

    public AbstractUIComponentTest() {
    }

    public AbstractUIComponentTest(String name) {
        super(name);
    }

    public final void testGetValueBinding_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.getValueBinding(null);
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public final void testSetValueBinding_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.setValueBinding(null, new NullValueBinding());
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public final void testGetClientId_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.getClientId(null);
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public final void testSetId_IllegalArg1() throws Exception {
        UIComponent component = createUIComponent();
        try {
            // Must not be a zero-length String
            component.setId("");
            fail();
        } catch (IllegalArgumentException iae) {
            assertExceptionMessage(iae);
        }
    }

    public final void testSetId_IllegalArg2() throws Exception {
        UIComponent component = createUIComponent();
        try {
            // First character must be a letter or an underscore ('_').
            component.setId("1");
            fail();
        } catch (IllegalArgumentException iae) {
            assertExceptionMessage(iae);
        }
    }

    public final void testSetId_IllegalArg3() throws Exception {
        UIComponent component = createUIComponent();
        try {
            // Subsequent characters must be a letter, a digit, 
            // an underscore ('_'), or a dash ('-').
            component.setId("_:");
            fail();
        } catch (IllegalArgumentException iae) {
            assertExceptionMessage(iae);
        }
    }

    public final void testFindComponent_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.findComponent(null);
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    // TODO java.lang.IllegalArgumentException - if an intermediate identifier in a search expression identifies a UIComponent that is not a NamingContainer 
    public final void todo_testFindComponent_IllegalArgs() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.findComponent("");
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public void testBroadcast_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.broadcast(null);
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public final void testDecode_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.decode(null);
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public final void testEncodeBegin_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.encodeBegin(null);
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public final void testEncodeChildren_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.encodeChildren(null);
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public final void testEncodeEnd_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.encodeEnd(null);
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public final void testAddFacesListener_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.addFacesListener(null);
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public final void testGetFacesListeners_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.getFacesListeners(null);
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public final void testGetFacesListeners_IllegalArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            // if class is not, and does not implement, FacesListener
            component.getFacesListeners(String.class);
            fail();
        } catch (IllegalArgumentException iae) {
            assertExceptionMessage(iae);
        }
    }

    public final void testRemoveFacesListener_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.removeFacesListener(null);
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public final void testQueueEvent_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.queueEvent(null);
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public final void testQueueEvent_IllegalState() throws Exception {
        UIComponent component = createUIComponent();
        try {
            // this component is not a descendant of a UIViewRoot
            component.queueEvent(new NullFacesEvent());
            fail();
        } catch (IllegalStateException ise) {
            assertExceptionMessage(ise);
        }
    }

    public final void testProcessRestoreState_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.processRestoreState(null, new Object());
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public final void testProcessDecodes_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.processDecodes(null);
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public final void testProcessValidators_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.processValidators(null);
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public final void testProcessUpdates_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.processUpdates(null);
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    public final void testProcessSaveState_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.processSaveState(null);
            fail();
        } catch (NullPointerException npe) {
            assertExceptionMessage(npe);
        }
    }

    private void assertExceptionMessage(Exception exception) {
        String message = exception.getMessage();
        assertNotNull(message);
        assertTrue(message.trim().length() > 0);
    }

    protected abstract UIComponent createUIComponent();

}
