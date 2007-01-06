/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.mock.MockLifecycleImpl;

/**
 * @author shot
 */
public class PhaseEventTest extends TestCase {

    public void testPhaseEvent_FacesContextCantBeNull() throws Exception {
        try {
            new PhaseEvent(null, PhaseId.ANY_PHASE, new MockLifecycleImpl());
            fail();
        } catch (NullPointerException expected) {
            assertTrue(true);
        }
    }

    public void testPhaseEvent_PhaseIdCantBeNull() throws Exception {
        try {
            new PhaseEvent(new MockFacesContextImpl(), null,
                    new MockLifecycleImpl());
            fail();
        } catch (NullPointerException expected) {
            assertTrue(true);
        }
    }

    //never reached because IllegalArgumentException occured when path Lifecycle instance to EventObject
    public void fixme_testPhaseEvent_LifecycleCantBeNull() throws Exception {
        try {
            new PhaseEvent(new MockFacesContextImpl(), PhaseId.ANY_PHASE, null);
            fail();
        } catch (NullPointerException expected) {
            assertTrue(true);
        }
    }

}
