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

import java.util.EventObject;

import javax.faces.component.UIComponent;

/**
 * @author shot
 */
public abstract class FacesEvent extends EventObject {

    protected PhaseId phaseId;

    public FacesEvent(UIComponent component) {
        super(component);
        if (component == null) {
            throw new IllegalArgumentException("FacesEvent");
        }
        phaseId = PhaseId.ANY_PHASE;
    }

    public UIComponent getComponent() {
        return (UIComponent) getSource();
    }

    public PhaseId getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(PhaseId phaseId) {
        this.phaseId = phaseId;
    }

    public abstract boolean isAppropriateListener(FacesListener listener);

    public abstract void processListener(FacesListener listener);

    public void queue() {
        ((UIComponent) getSource()).queueEvent(this);
    }

}
