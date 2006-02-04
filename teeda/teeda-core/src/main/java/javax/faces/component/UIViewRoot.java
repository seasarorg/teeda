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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

/**
 * @author shot
 */
public class UIViewRoot extends UIComponentBase {

    public static final String COMPONENT_FAMILY = "javax.faces.ViewRoot";

    public static final String COMPONENT_TYPE = "javax.faces.ViewRoot";

    public static final String UNIQUE_ID_PREFIX = "_id";

    private String renderKitId_ = null;

    private String viewId_ = null;

    private List events_ = null;

    private int lastId_ = 0;

    private Locale locale_ = null;

    public UIViewRoot() {
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public String getRenderKitId() {
        if (renderKitId_ != null) {
            return renderKitId_;
        }
        return (String) ComponentUtils_.getValueBindingValue(this,
                "renderKitId");
    }

    public void setRenderKitId(String renderKitId) {
        renderKitId_ = renderKitId;
    }

    public String getViewId() {
        return viewId_;
    }

    public void setViewId(String viewId) {
        viewId_ = viewId;
    }

    public void queueEvent(FacesEvent event) {
        ComponentUtils_.assertNotNull("event", event);
        getEvents().add(event);
    }

    private List getEvents() {
        if (events_ == null) {
            events_ = new ArrayList();
        }
        return events_;
    }

    public void processDecodes(FacesContext context) {
        super.processDecodes(context);
        broadcastEvents(context, PhaseId.APPLY_REQUEST_VALUES);
        clearEventsIfResponseRendered(context);
    }

    public void encodeBegin(FacesContext context) throws IOException {
        lastId_ = 0;
        clearEvents();
        super.encodeBegin(context);
    }

    public void processValidators(FacesContext context) {
        super.processValidators(context);
        broadcastEvents(context, PhaseId.PROCESS_VALIDATIONS);
        clearEventsIfResponseRendered(context);
    }

    public void processUpdates(FacesContext context) {
        super.processUpdates(context);
        broadcastEvents(context, PhaseId.UPDATE_MODEL_VALUES);
        clearEventsIfResponseRendered(context);
    }

    public void processApplication(FacesContext context) {
        ComponentUtils_.assertNotNull("context", context);
        broadcastEvents(context, PhaseId.INVOKE_APPLICATION);
        clearEventsIfResponseRendered(context);
    }

    public String createUniqueId() {
        return UNIQUE_ID_PREFIX + (lastId_++);
    }

    public Locale getLocale() {
        if (locale_ != null) {
            return locale_;
        }
        Locale locale = null;
        FacesContext context = getFacesContext();
        if (getValueBinding("locale") != null) {
            Object obj = ComponentUtils_.getValueBindingValue(this, "locale");
            if (obj == null) {
                locale = ComponentUtils_.calculateLocale(context);
            } else if (obj instanceof Locale) {
                locale = (Locale) obj;
            } else if (obj instanceof String) {
                locale = getLocaleFromString((String) obj);
            }
        } else {
            locale = ComponentUtils_.calculateLocale(context);
        }
        return locale;
    }

    public void setLocale(Locale locale) {
        locale_ = locale;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        renderKitId_ = (String) values[1];
        viewId_ = (String) values[2];
        locale_ = (Locale) values[3];
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[4];
        values[0] = super.saveState(context);
        values[1] = renderKitId_;
        values[2] = viewId_;
        values[3] = locale_;
        return values;
    }

    private Locale getLocaleFromString(String localeStr) {
        Locale locale = Locale.getDefault();
        if (ComponentUtils_.isLocaleShort(localeStr)) {
            locale = new Locale(localeStr);
        } else if (ComponentUtils_.isLocaleLong(localeStr)) {
            String language = localeStr.substring(0, 2);
            String country = localeStr.substring(3, 5);
            locale = new Locale(language, country);
        }
        return locale;
    }

    protected void broadcastEvents(FacesContext context, PhaseId phaseId) {
        if (getEvents().isEmpty()) {
            return;
        }
        int phaseIdOrdinal = phaseId.getOrdinal();
        for (Iterator itr = events_.iterator(); itr.hasNext();) {
            FacesEvent event = (FacesEvent) itr.next();
            int ordinal = event.getPhaseId().getOrdinal();
            if (ordinal == PhaseId.ANY_PHASE.getOrdinal()
                    || ordinal == phaseIdOrdinal) {
                UIComponent source = event.getComponent();
                try {
                    try {
                        source.broadcast(event);
                    } finally {
                        itr.remove();
                    }
                } catch (AbortProcessingException e) {
                    clearEvents();
                    break;
                }
            }
        }
    }

    private void clearEventsIfResponseRendered(FacesContext context) {
        if (context.getRenderResponse() || context.getResponseComplete()) {
            clearEvents();
        }
    }

    private void clearEvents() {
        events_ = null;
    }

    public int getEventSize() {
        return getEvents().size();
    }

}
