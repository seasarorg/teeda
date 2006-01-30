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
package javax.faces.component;

import java.util.Iterator;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.internal.NamingContainerUtil_;

/**
 * @author shot
 */
public class UIForm extends UIComponentBase implements NamingContainer {

    public static final String COMPONENT_FAMILY = "javax.faces.Form";

    public static final String COMPONENT_TYPE = "javax.faces.Form";

    private static final String DEFAULT_RENDER_TYPE = "javax.faces.Form";

    private transient boolean submitted_ = false;

    public UIForm() {
        setRendererType(DEFAULT_RENDER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public void setId(String id) {
        super.setId(id);
        NamingContainerUtil_.refreshDescendantComponentClientId(this);
    }

    public boolean isSubmitted() {
        return submitted_;
    }

    public void setSubmitted(boolean submitted) {
        submitted_ = submitted;
    }

    public void processDecodes(FacesContext context) {
        ComponentUtils_.assertNotNull("context", context);
        decode(context);
        processAppropriateAction(context, PhaseId.APPLY_REQUEST_VALUES);
    }

    public void processUpdates(FacesContext context) {
        ComponentUtils_.assertNotNull("context", context);
        processAppropriateAction(context, PhaseId.UPDATE_MODEL_VALUES);
    }

    public void processValidators(FacesContext context) {
        ComponentUtils_.assertNotNull("context", context);
        processAppropriateAction(context, PhaseId.PROCESS_VALIDATIONS);
    }

    protected void processAppropriateAction(FacesContext context, PhaseId phase) {
        if (!isRendered()) {
            return;
        }
        if (!isSubmitted()) {
            return;
        }
        for (Iterator children = getFacetsAndChildren(); children.hasNext();) {
            UIComponent component = (UIComponent) children.next();
            ComponentUtils_.processAppropriatePhaseAction(context, component,
                    phase);
        }
    }
}
