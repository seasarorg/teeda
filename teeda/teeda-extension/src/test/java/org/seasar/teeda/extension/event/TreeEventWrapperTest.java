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
package org.seasar.teeda.extension.event;

import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.PhaseId;

import org.seasar.teeda.core.mock.MockFacesEvent;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class TreeEventWrapperTest extends TeedaTestCase {

    public void testGetPhaseId() throws Exception {
        MockUIComponent component = new MockUIComponent();
        component.setId("a");
        FacesEvent original = new MockFacesEvent(component) {

            private static final long serialVersionUID = 1L;

            public PhaseId getPhaseId() {
                return PhaseId.INVOKE_APPLICATION;
            }

        };
        TreeEventWrapper e = new TreeEventWrapper(original, "0", component);
        assertEquals(PhaseId.INVOKE_APPLICATION, e.getPhaseId());
    }

    public void testIsAppropriateListener_allwaysReturnFalse() throws Exception {
        MockUIComponent component = new MockUIComponent();
        component.setId("a");
        FacesEvent original = new MockFacesEvent(component) {

            private static final long serialVersionUID = 1L;

            public boolean isAppropriateListener(FacesListener listener) {
                return true;
            }

        };
        TreeEventWrapper e = new TreeEventWrapper(original, "0", component);
        assertFalse(e.isAppropriateListener(new FacesListener() {
        }));
    }

    public void testProcessListener_throwException() throws Exception {
        MockUIComponent component = new MockUIComponent();
        component.setId("a");
        FacesEvent original = new MockFacesEvent(component) {

            private static final long serialVersionUID = 1L;

        };
        TreeEventWrapper e = new TreeEventWrapper(original, "0", component);
        try {
            e.processListener(new FacesListener() {
            });
            fail();
        } catch (UnsupportedOperationException expected) {
            success();
        }
    }
}
