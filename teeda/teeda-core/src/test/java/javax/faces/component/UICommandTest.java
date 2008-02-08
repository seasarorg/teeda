/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

import junitx.framework.ObjectAssert;

import org.seasar.teeda.core.mock.MockActionListener;
import org.seasar.teeda.core.mock.MockMethodBinding;
import org.seasar.teeda.core.mock.MockUIComponentBase;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.mock.NullFacesEvent;
import org.seasar.teeda.core.mock.NullUIComponent;

/**
 * @author manhole
 */
public class UICommandTest extends UIComponentBaseTest {

    public final void testSetGetAction() {
        UICommand command = createUICommand();
        MockMethodBinding mb = new MockMethodBinding();
        command.setAction(mb);
        assertEquals(mb, command.getAction());
    }

    public final void testSetGetAction_ValueBindingNotWork() {
        UICommand command = createUICommand();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), new MockMethodBinding());
        command.setValueBinding("action", vb);
        assertEquals(null, command.getAction());
    }

    public final void testSetGetActionListener() {
        UICommand command = createUICommand();
        MockMethodBinding mb = new MockMethodBinding();
        command.setActionListener(mb);
        assertEquals(mb, command.getActionListener());
    }

    public final void testSetGetActionListener_ValueBindingNotWork() {
        UICommand command = createUICommand();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), new MockMethodBinding());
        command.setValueBinding("action", vb);
        assertEquals(null, command.getActionListener());
    }

    public final void testSetGetImmediate() {
        UICommand command = createUICommand();
        command.setImmediate(true);
        assertEquals(true, command.isImmediate());
    }

    public final void testSetGetImmediate_ValueBinding() {
        UICommand command = createUICommand();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), new Boolean(true));
        command.setValueBinding("immediate", vb);
        assertEquals(true, command.isImmediate());
    }

    public final void testAddGetRemoveActionListeners() throws Exception {
        UICommand command = createUICommand();
        assertEquals(0, command.getActionListeners().length);
        ActionListener v1 = new MockActionListener();
        ActionListener v2 = new MockActionListener();
        ActionListener v3 = new MockActionListener();
        command.addActionListener(v1);
        assertEquals(1, command.getActionListeners().length);
        command.addActionListener(v2);
        assertEquals(2, command.getActionListeners().length);
        command.addActionListener(v3);
        assertEquals(3, command.getActionListeners().length);

        command.removeActionListener(v2);
        assertEquals(2, command.getActionListeners().length);
        command.removeActionListener(v2);
        assertEquals(2, command.getActionListeners().length);
    }

    public final void testBroadcast_ToDefaultActionListener() throws Exception {
        // ## Arrange ##
        UICommand command = createUICommand();
        ActionEvent actionEvent = new ActionEvent(new NullUIComponent());

        MockActionListener actionListener = new MockActionListener();
        getFacesContext().getApplication().setActionListener(actionListener);

        // ## Act ##
        command.broadcast(actionEvent);

        // ## Assert ##
        assertSame(actionEvent, actionListener.getEvent());
    }

    public final void testBroadcast_ToMethodReferencedActionListener()
            throws Exception {
        // ## Arrange ##
        UICommand command = createUICommand();
        MockMethodBinding mb = new MockMethodBinding();
        command.setActionListener(mb);
        ActionEvent actionEvent = new ActionEvent(new NullUIComponent());

        MockActionListener actionListener = new MockActionListener();
        getFacesContext().getApplication().setActionListener(actionListener);

        // ## Act ##
        command.broadcast(actionEvent);

        // ## Assert ##
        assertEquals(true, mb.isInvokeCalled());
        assertSame(actionEvent, mb.getInvokeParams()[0]);
    }

    public final void testBroadcast_NoActionEvent() throws Exception {
        // ## Arrange ##
        UICommand command = createUICommand();
        MockMethodBinding mb = new MockMethodBinding();
        command.setActionListener(mb);
        NullFacesEvent noActionEvent = new NullFacesEvent();
        ObjectAssert.assertNotInstanceOf(ActionEvent.class, noActionEvent);

        MockActionListener actionListener = new MockActionListener();
        getFacesContext().getApplication().setActionListener(actionListener);

        // ## Act ##
        command.broadcast(noActionEvent);

        // ## Assert ##
        assertEquals(null, actionListener.getEvent());
        assertEquals(false, mb.isInvokeCalled());
    }

    public final void testQueueEvent_ImmediateTrue() throws Exception {
        // ## Arrange ##
        final Object[] args = { null };
        MockUIComponentBase parent = new MockUIComponentBase() {
            public void queueEvent(FacesEvent event) {
                args[0] = event;
            }
        };
        UICommand command = createUICommand();
        command.setImmediate(true);
        parent.getChildren().add(command);

        ActionEvent actionEvent = new ActionEvent(new NullUIComponent());

        // ## Act ##
        command.queueEvent(actionEvent);

        // ## Assert ##
        assertEquals(PhaseId.APPLY_REQUEST_VALUES, actionEvent.getPhaseId());
        assertSame(actionEvent, args[0]);
    }

    public final void testQueueEvent_ImmediateFalse() throws Exception {
        // ## Arrange ##
        final Object[] args = { null };
        MockUIComponentBase parent = new MockUIComponentBase() {
            public void queueEvent(FacesEvent event) {
                args[0] = event;
            }
        };
        UICommand command = createUICommand();
        command.setImmediate(false);
        parent.getChildren().add(command);

        // ## Act ##
        ActionEvent actionEvent = new ActionEvent(new NullUIComponent());
        command.queueEvent(actionEvent);

        assertEquals(PhaseId.INVOKE_APPLICATION, actionEvent.getPhaseId());

        // ## Assert ##
        assertSame(actionEvent, args[0]);
    }

    public final void testQueueEvent_WithNotActionEvent() throws Exception {
        // ## Arrange ##
        final Object[] args = { null };
        MockUIComponentBase parent = new MockUIComponentBase() {
            public void queueEvent(FacesEvent event) {
                args[0] = event;
            }
        };
        UICommand command = createUICommand();
        command.setImmediate(false);
        parent.getChildren().add(command);

        NullFacesEvent noActionEvent = new NullFacesEvent();
        ObjectAssert.assertNotInstanceOf(ActionEvent.class, noActionEvent);

        // ## Act ##
        command.queueEvent(noActionEvent);

        // ## Assert ##
        assertEquals(PhaseId.ANY_PHASE, noActionEvent.getPhaseId());
        assertSame(noActionEvent, args[0]);
    }

    private UICommand createUICommand() {
        return (UICommand) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UICommand();
    }

}
