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
package org.seasar.teeda.core.render.html;

import java.io.IOException;
import java.util.Map;

import javax.faces.application.StateManager;
import javax.faces.application.StateManager.SerializedView;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractResponseStateManager;
import org.seasar.teeda.core.util.StateManagerUtil;

/**
 * @author shot
 */
public class HtmlResponseStateManager extends AbstractResponseStateManager {

    public HtmlResponseStateManager() {
    }

    public void writeState(FacesContext context, SerializedView serializedView)
            throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.INPUT_ELEM, null);
        writer.writeAttribute(JsfConstants.TYPE_ATTR,
                JsfConstants.HIDDEN_VALUE, null);
        writer.writeAttribute(JsfConstants.NAME_ATTR, VIEW_STATE_PARAM, null);
        writer.writeAttribute(JsfConstants.ID_ATTR, VIEW_STATE_PARAM, null);

        Object value = null;
        if (StateManagerUtil.isSavingStateInClient(context)) {
            value = getEncodeConverter().getAsEncodeString(serializedView);
        } else {
            value = serializedView.getStructure();
        }
        writer.writeAttribute(JsfConstants.VALUE_ATTR, value, null);
        writer.endElement(JsfConstants.INPUT_ELEM);

        writeViewId(writer, context);
    }

    public Object getComponentStateToRestore(FacesContext context) {
        Map requestMap = context.getExternalContext().getRequestMap();
        Object state = requestMap.get(FACES_VIEW_STATE);
        requestMap.remove(FACES_VIEW_STATE);
        return state;
    }

    public Object getTreeStructureToRestore(FacesContext context, String viewId) {
        Map paramMap = context.getExternalContext().getRequestParameterMap();
        String viewState = (String) paramMap.get(VIEW_STATE_PARAM);
        if (viewState == null) {
            return null;
        }
        Object structure = null;
        if (StateManagerUtil.isSavingStateInClient(context)) {
            structure = getEncodeConverter().getAsDecodeObject(viewState);
        } else {
            structure = viewState;
        }
        return structure;
    }

    protected void writeViewId(ResponseWriter writer, FacesContext context)
            throws IOException {
        writer.startElement(JsfConstants.INPUT_ELEM, null);
        writer.writeAttribute(JsfConstants.TYPE_ATTR,
                JsfConstants.HIDDEN_VALUE, null);
        writer.writeAttribute(JsfConstants.NAME_ATTR, VIEW_STATE_PARAM, null);
        writer.writeAttribute(JsfConstants.ID_ATTR, VIEW_STATE_PARAM, null);
        writer.writeAttribute(JsfConstants.VALUE_ATTR, context.getViewRoot()
                .getViewId(), null);
        writer.endElement(JsfConstants.INPUT_ELEM);
    }

}
