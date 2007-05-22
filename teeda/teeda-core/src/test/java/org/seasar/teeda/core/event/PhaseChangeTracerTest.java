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
package org.seasar.teeda.core.event;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;

import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class PhaseChangeTracerTest extends TeedaTestCase {

    public void test() throws Exception {
        PhaseChangeTracer t = new PhaseChangeTracer();
        t.beforePhase(new PhaseEvent(getFacesContext(),
                PhaseId.APPLY_REQUEST_VALUES, getLifecycle()));
        t.afterPhase(new PhaseEvent(getFacesContext(),
                PhaseId.APPLY_REQUEST_VALUES, getLifecycle()));
        assertEquals(PhaseId.ANY_PHASE, t.getPhaseId());
    }
}
