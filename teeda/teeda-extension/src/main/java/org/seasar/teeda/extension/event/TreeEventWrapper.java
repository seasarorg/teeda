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
package org.seasar.teeda.extension.event;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.PhaseId;

public class TreeEventWrapper extends FacesEvent {

    private static final long serialVersionUID = 1L;

    private FacesEvent originalEvent;

    private String nodeId;

    public TreeEventWrapper(FacesEvent facesEvent, String nodeId,
            UIComponent component) {
        super(component);
        originalEvent = facesEvent;
        this.nodeId = nodeId;
    }

    public PhaseId getPhaseId() {
        return originalEvent.getPhaseId();
    }

    public void setPhaseId(PhaseId phaseId) {
        originalEvent.setPhaseId(phaseId);
    }

    public void queue() {
        originalEvent.queue();
    }

    public String toString() {
        return originalEvent.toString();
    }

    public boolean isAppropriateListener(FacesListener faceslistener) {
        return false;
    }

    public void processListener(FacesListener faceslistener) {
        throw new UnsupportedOperationException();
    }

    public FacesEvent getOriginalEvent() {
        return originalEvent;
    }

    public String getOriginalNodeId() {
        return nodeId;
    }
}
