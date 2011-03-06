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
package org.seasar.teeda.core.mock;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;

import org.seasar.framework.util.ArrayUtil;

public class MockLifecycleImpl extends MockLifecycle {

    private PhaseListener[] phaseListeners_ = new PhaseListener[0];

    public MockLifecycleImpl() {
    }

    public void addPhaseListener(PhaseListener listener) {
        phaseListeners_ = (PhaseListener[]) ArrayUtil.add(phaseListeners_,
                listener);
    }

    public void execute(FacesContext context) throws FacesException {
    }

    public PhaseListener[] getPhaseListeners() {
        return phaseListeners_;
    }

    public void removePhaseListener(PhaseListener listener) {
        phaseListeners_ = (PhaseListener[]) ArrayUtil.remove(phaseListeners_,
                listener);
    }

    public void render(FacesContext context) throws FacesException {
    }

    public PhaseListener[] clearAllPhaseListener() {
        try {
            return phaseListeners_;
        } finally {
            setupDefaultPhaseListener();
        }
    }

    public void setupDefaultPhaseListener() {
        phaseListeners_ = new PhaseListener[0];
    }

    public void setupDefaultPhaseListener(PhaseListener[] listeners) {
        phaseListeners_ = listeners;
    }

}
