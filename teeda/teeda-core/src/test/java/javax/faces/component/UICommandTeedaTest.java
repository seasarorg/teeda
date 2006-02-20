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

import javax.faces.context.FacesContext;

import org.seasar.teeda.core.mock.MockSerializableActionListener;
import org.seasar.teeda.core.mock.MockSerializableMethodBinding;

/**
 * @author manhole
 */
public class UICommandTeedaTest extends UIComponentBaseTeedaTest {

    public void testSaveAndRestoreState() throws Exception {
        super.testSaveAndRestoreState();

        // ## Arrange ##
        UICommand command1 = createUICommand();
        command1.addActionListener(new MockSerializableActionListener());
        command1.setAction(new MockSerializableMethodBinding());
        command1.setActionListener(new MockSerializableMethodBinding());
        command1.setImmediate(true);
        command1.setValue("bb");

        FacesContext context = getFacesContext();

        // ## Act ##
        Object state = command1.saveState(context);
        assertTrue(state instanceof Serializable);

        UICommand command2 = createUICommand();
        command2.restoreState(context, state);

        // ## Assert ##
        assertEquals(command1.getActionListeners().length, command2
                .getActionListeners().length);
        assertEquals(command1.getActionListeners().getClass(), command2
                .getActionListeners().getClass());
        assertEquals(command1.getAction().getClass(), command2.getAction()
                .getClass());
        assertEquals(command1.getActionListener().getClass(), command2
                .getActionListener().getClass());
        assertEquals(command1.isImmediate(), command2.isImmediate());

        assertEquals("bb", command2.getValue());
    }

    private UICommand createUICommand() {
        return (UICommand) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UICommand();
    }

}
