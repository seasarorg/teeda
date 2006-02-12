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
package javax.faces.event;

import java.util.EventObject;

import javax.faces.context.FacesContext;
import javax.faces.internal.AssertionUtil;
import javax.faces.lifecycle.Lifecycle;

/**
 * @author shot
 */
public class PhaseEvent extends EventObject {

    private static final long serialVersionUID = 1L;

    private FacesContext facesContext_;

    private PhaseId phaseId_;

    public PhaseEvent(FacesContext context, PhaseId phaseId, Lifecycle lifecycle) {
        super(lifecycle);
        AssertionUtil.assertNotNull("FacesContext", context);
        AssertionUtil.assertNotNull("PhaseId", phaseId);
        AssertionUtil.assertNotNull("Lifecycle", lifecycle);
        facesContext_ = context;
        phaseId_ = phaseId;
    }

    public FacesContext getFacesContext() {
        return facesContext_;
    }

    public PhaseId getPhaseId() {
        return phaseId_;
    }
}
