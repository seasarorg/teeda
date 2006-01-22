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
package org.seasar.teeda.core.event;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.seasar.framework.log.Logger;

/**
 * @author manhole
 */
public class PhaseChangeTracer implements PhaseListener {

    private static final long serialVersionUID = 1L;

    private static final Logger _log = Logger
            .getLogger(PhaseChangeTracer.class);

    public void beforePhase(PhaseEvent event) {
        if (_log.isDebugEnabled()) {
            _log.debug("before: " + event.getPhaseId() + ", " + event);
        }
    }

    public void afterPhase(PhaseEvent event) {
        if (_log.isDebugEnabled()) {
            _log.debug("after: " + event.getPhaseId() + ", " + event);
        }
    }

    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

}
