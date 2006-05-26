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
package org.seasar.teeda.core.lifecycle.impl;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.portlet.PortletRequest;

import org.seasar.teeda.core.lifecycle.AbstractPhase;
import org.seasar.teeda.core.lifecycle.Postback;
import org.seasar.teeda.core.portlet.FacesPortlet;
import org.seasar.teeda.core.util.ExternalContextUtil;
import org.seasar.teeda.core.util.PortletUtil;

/**
 * @author shot
 */
public class RestoreViewPhase extends AbstractPhase implements Postback {

    private static final String VIEW_ID_ATTR = RestoreViewPhase.class.getName()
            + ".VIEW_ID";

    private static final String POSTBACK_ATTR = RestoreViewPhase.class
            .getName()
            + ".POSTBACK";

    public RestoreViewPhase() {
    }

    protected void executePhase(FacesContext context) throws FacesException {
        ExternalContext externalContext = context.getExternalContext();
        String viewId = null;
        // PortletSupport
        if (PortletUtil.isPortlet(context)) {
            PortletRequest request = (PortletRequest) externalContext
                    .getRequest();
            viewId = request.getParameter(FacesPortlet.VIEW_ID);
        } else {
            viewId = ExternalContextUtil.getViewId(externalContext);
        }
        ViewHandler viewHandler = context.getApplication().getViewHandler();
        UIViewRoot viewRoot = viewHandler.restoreView(context, viewId);
        if (viewRoot == null) {
            viewRoot = viewHandler.createView(context, viewId);
        }
        String previousViewId = getViewIdFromSession(externalContext);
        context.setViewRoot(viewRoot);
        saveViewIdToSession(externalContext, viewId);
        initializeChildren(context, viewRoot);
        if (externalContext.getRequestParameterMap().isEmpty()) {
            context.renderResponse();
        }
        setPostback(externalContext, viewId.equals(previousViewId));
    }

    protected String getViewIdFromSession(ExternalContext externalContext) {
        return (String) externalContext.getSessionMap().get(VIEW_ID_ATTR);
    }

    protected void saveViewIdToSession(ExternalContext externalContext,
            String viewId) {
        externalContext.getSessionMap().put(VIEW_ID_ATTR, viewId);
    }

    protected PhaseId getCurrentPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    protected void setPostback(ExternalContext externalContext, boolean postback) {
        externalContext.getRequestMap().put(POSTBACK_ATTR,
                new Boolean(postback));
    }

    public boolean isPostBack() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Boolean postBack = (Boolean) externalContext.getRequestMap().get(
                POSTBACK_ATTR);
        return postBack.booleanValue();
    }

}
