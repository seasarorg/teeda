/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import org.seasar.teeda.core.mock.NullFacesListener;
import org.seasar.teeda.core.mock.NullUIComponent;
import org.seasar.teeda.core.mock.NullValueChangeListener;
import org.seasar.teeda.core.unit.ExceptionAssert;

/**
 * @author manhole
 */
public class ValueChangeEventTest extends TestCase {

    public void testConstructor_ComponentIsNull() throws Exception {
        try {
            new ValueChangeEvent(null, "0", "1");
            fail();
        } catch (IllegalArgumentException iae) {
            ExceptionAssert.assertMessageExist(iae);
        }
    }

    public void testGetOldValue() throws Exception {
        // ## Arrange ##
        ValueChangeEvent event = new ValueChangeEvent(new NullUIComponent(),
                "0", "1");

        // ## Act & Assert ##
        assertEquals("0", event.getOldValue());
    }

    public void testGetNewValue() throws Exception {
        // ## Arrange ##
        ValueChangeEvent event = new ValueChangeEvent(new NullUIComponent(),
                "0", "1");

        // ## Act & Assert ##
        assertEquals("1", event.getNewValue());
    }

    public void testIsAppropriateListener_True() throws Exception {
        // ## Arrange ##
        ValueChangeEvent event = new ValueChangeEvent(new NullUIComponent(),
                "0", "1");

        // ## Act & Assert ##
        assertEquals(true, event
                .isAppropriateListener(new NullValueChangeListener()));
    }

    public void testIsAppropriateListener_False() throws Exception {
        // ## Arrange ##
        ValueChangeEvent event = new ValueChangeEvent(new NullUIComponent(),
                "0", "1");

        // ## Act & Assert ##
        assertEquals(false, event
                .isAppropriateListener(new NullFacesListener()));
    }

    public void testProcessListener() throws Exception {
        // ## Arrange ##
        final boolean[] calls = { false };
        final Object[] params = { null };
        ValueChangeEvent event = new ValueChangeEvent(new NullUIComponent(),
                "0", "1");

        // ## Act ##
        event.processListener(new NullValueChangeListener() {
            public void processValueChange(ValueChangeEvent event)
                    throws AbortProcessingException {
                calls[0] = true;
                params[0] = event;
            }
        });

        // ## Assert ##
        assertEquals(true, calls[0]);
        assertEquals(event, params[0]);
    }

}
