/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockActionListener;
import org.seasar.teeda.core.mock.MockUIComponent;

/**
 * @author shot
 */
public class ActionEventTest extends TestCase {

    public void testActionEvent() throws Exception {
        ActionEvent event = new ActionEvent(new MockUIComponent());
        assertTrue(event != null);

        try {
            new ActionEvent(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    public void testIsAppropriateListener() throws Exception {

        ActionEvent event = new ActionEvent(new MockUIComponent());
        assertTrue(event.isAppropriateListener(new MockActionListener()));

        FacesListener notActionListener = new FacesListener() {

            public void processAction(ActionEvent actionEvent)
                    throws AbortProcessingException {
            }

        };
        assertFalse(event.isAppropriateListener(notActionListener));
    }

    public void testProcessListener() throws Exception {

        ActionEvent event = new ActionEvent(new MockUIComponent());

        MockActionListener listener = new MockActionListener();
        event.processListener(listener);

        ActionEvent backEvent = listener.getEvent();
        assertEquals(event, backEvent);

    }

}
