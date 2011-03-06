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
package org.seasar.teeda.core.render.html;

import java.io.IOException;
import java.util.Map;

import javax.faces.application.StateManager.SerializedView;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractResponseStateManager;
import org.seasar.teeda.core.util.StateManagerUtil;

/**
 * @author shot
 * @author manhole
 */
public class HtmlResponseStateManager extends AbstractResponseStateManager {

    public HtmlResponseStateManager() {
    }

    public void writeState(final FacesContext context,
            final SerializedView serializedView) throws IOException {
        final ResponseWriter writer = context.getResponseWriter();
        writeSerializedView(context, serializedView, writer);
        writeViewId(writer, context.getViewRoot().getViewId());
    }

    private void writeSerializedView(final FacesContext context,
            final SerializedView serializedView, final ResponseWriter writer)
            throws IOException {
        writer.startElement(JsfConstants.INPUT_ELEM, null);
        writer.writeAttribute(JsfConstants.TYPE_ATTR,
                JsfConstants.HIDDEN_VALUE, null);
        writer.writeAttribute(JsfConstants.NAME_ATTR, VIEW_STATE_PARAM, null);
        writer.writeAttribute(JsfConstants.ID_ATTR, VIEW_STATE_PARAM, null);

        final Object value;
        if (isSavingStateInClient(context)) {
            value = getEncodeConverter().getAsEncodeString(
                    new StructureAndState(serializedView));
        } else {
            value = serializedView.getStructure();
        }
        writer.writeAttribute(JsfConstants.VALUE_ATTR, value, null);
        writer.endElement(JsfConstants.INPUT_ELEM);
    }

    boolean isSavingStateInClient(final FacesContext context) {
        return StateManagerUtil.isSavingStateInClient(context);
    }

    public Object getComponentStateToRestore(final FacesContext context) {
        final Map requestMap = context.getExternalContext().getRequestMap();
        final Object state = requestMap.get(FACES_VIEW_STATE);
        requestMap.remove(FACES_VIEW_STATE);
        return state;
    }

    public Object getTreeStructureToRestore(final FacesContext context,
            final String viewId) {
        final Map paramMap = context.getExternalContext()
                .getRequestParameterMap();
        final String viewIdParam = (String) paramMap.get(VIEW_ID);
        final String viewState = (String) paramMap.get(VIEW_STATE_PARAM);
        if (viewState == null || viewIdParam == null) {
            return null;
        }
        if (isSavingStateInClient(context)) {
            if (!viewIdParam.equals(viewId)) {
                return null;
            }
            StructureAndState structureAndState = (StructureAndState) getEncodeConverter()
                    .getAsDecodeObject(viewState);
            context.getExternalContext().getRequestMap().put(
                    AbstractResponseStateManager.FACES_VIEW_STATE,
                    structureAndState.getState());
            return structureAndState.getStructure();
        } else {
            return viewState;
        }
    }

    protected void writeViewId(final ResponseWriter writer, final String viewId)
            throws IOException {
        writer.startElement(JsfConstants.INPUT_ELEM, null);
        writer.writeAttribute(JsfConstants.TYPE_ATTR,
                JsfConstants.HIDDEN_VALUE, null);
        writer.writeAttribute(JsfConstants.NAME_ATTR, VIEW_ID, null);
        writer.writeAttribute(JsfConstants.ID_ATTR, VIEW_ID, null);
        writer.writeAttribute(JsfConstants.VALUE_ATTR, viewId, null);
        writer.endElement(JsfConstants.INPUT_ELEM);
    }

}
