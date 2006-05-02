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
import java.util.Map;

import javax.faces.context.FacesContext;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.mock.NullFacesContext;
import org.seasar.teeda.core.mock.NullFacesEvent;
import org.seasar.teeda.core.mock.NullValueBinding;
import org.seasar.teeda.core.unit.ExceptionAssert;

/**
 * @author manhole
 */
public abstract class AbstractUIComponentTest extends TestCase {

    public final void testGetValueBinding_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.getValueBinding(null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testSetValueBinding_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.setValueBinding(null, new NullValueBinding());
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testSetValueBinding_IllegalArgName1() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.setValueBinding("id", new NullValueBinding());
        } catch (IllegalArgumentException iae) {
            ExceptionAssert.assertMessageExist(iae);
        }
    }

    public final void testSetValueBinding_IllegalArgName2() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.setValueBinding("parent", new NullValueBinding());
        } catch (IllegalArgumentException iae) {
            ExceptionAssert.assertMessageExist(iae);
        }
    }

    public final void testGetClientId_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.getClientId(null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testSetIdNull() throws Exception {
        UIComponent component = createUIComponent();
        component.setId(null);
        assertTrue(true);
    }

    public final void testSetId_IllegalArg1() throws Exception {
        UIComponent component = createUIComponent();
        try {
            // Must not be a zero-length String
            component.setId("");
            fail();
        } catch (IllegalArgumentException iae) {
            ExceptionAssert.assertMessageExist(iae);
        }
    }

    public final void testSetId_IllegalArg2() throws Exception {
        UIComponent component = createUIComponent();
        try {
            // First character must be a letter or an underscore ('_').
            component.setId("1");
            fail();
        } catch (IllegalArgumentException iae) {
            ExceptionAssert.assertMessageExist(iae);
        }
    }

    public final void testSetId_IllegalArg3() throws Exception {
        UIComponent component = createUIComponent();
        try {
            // Subsequent characters must be a letter, a digit,
            // an underscore ('_'), or a dash ('-').
            component.setId("a:");
            fail();
        } catch (IllegalArgumentException iae) {
            ExceptionAssert.assertMessageExist(iae);
        }
    }

    public final void testFindComponent_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.findComponent(null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    // TODO java.lang.IllegalArgumentException - if an intermediate identifier
    // in a search expression identifies a UIComponent that is not a
    // NamingContainer
    public final void todo_testFindComponent_IllegalArgs() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.findComponent("");
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testBroadcast_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.broadcast(null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testDecode_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.decode(null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testEncodeBegin_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.encodeBegin(null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testEncodeChildren_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.encodeChildren(null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testEncodeEnd_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.encodeEnd(null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testAddFacesListener_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.addFacesListener(null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testGetFacesListeners_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.getFacesListeners(null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testGetFacesListeners_IllegalArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            // if class is not, and does not implement, FacesListener
            component.getFacesListeners(String.class);
            fail();
        } catch (IllegalArgumentException iae) {
            ExceptionAssert.assertMessageExist(iae);
        }
    }

    public final void testRemoveFacesListener_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.removeFacesListener(null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testQueueEvent_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.queueEvent(null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testQueueEvent_NoParent() throws Exception {
        UIComponent component = createUIComponent();
        if (component instanceof UIViewRoot) {
            component.queueEvent(new NullFacesEvent());
        } else {
            try {
                component.queueEvent(new NullFacesEvent());
                fail();
            } catch (IllegalStateException ise) {
                ExceptionAssert.assertMessageExist(ise);
            }
        }
    }

    public final void testProcessRestoreState_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.processRestoreState(null, new Object());
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testProcessDecodes_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.processDecodes(null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testProcessValidators_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.processValidators(null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testProcessUpdates_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.processUpdates(null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testProcessSaveState_NullArg() throws Exception {
        UIComponent component = createUIComponent();
        try {
            component.processSaveState(null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testGetAttributes_ShouldBeSerializable() throws Exception {
        UIComponent component = createUIComponent();
        Map attributes = component.getAttributes();
        assertEquals(true, attributes instanceof Serializable);
    }

    public final void testGetAttributes() throws Exception {
        UIComponent component = createUIComponent();
        Map attributes = component.getAttributes();
        attributes.put("id", "hoge");
        assertEquals("hoge", component.getId());

        component.setId("bar");
        assertEquals("bar", attributes.get("id"));
    }

    public final void testSetGetTransient() throws Exception {
        UIComponent component = createUIComponent();
        assertEquals(false, component.isTransient());
        component.setTransient(true);
        assertEquals(true, component.isTransient());
    }

    public final void testSetGetTransient_ValueBindingNotWork()
            throws Exception {
        UIComponent component = createUIComponent();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue((FacesContext) new NullFacesContext(), Boolean.TRUE);
        component.setValueBinding("transient", vb);
        assertEquals("[transient] property don't need ValueBinding", false,
                component.isTransient());
    }

    protected abstract UIComponent createUIComponent();

}
