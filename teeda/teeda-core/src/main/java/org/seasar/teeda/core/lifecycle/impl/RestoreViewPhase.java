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

import java.util.Locale;

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
import org.seasar.teeda.core.util.FacesContextUtil;
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

    protected void executePhase(final FacesContext context)
            throws FacesException {
        final ExternalContext externalContext = context.getExternalContext();
        final String viewId = getViewId(context, externalContext);
        final UIViewRoot viewRoot = getViewRoot(context, viewId);
        final String previousViewId = getViewIdFromSession(externalContext);
        context.setViewRoot(viewRoot);
        saveViewIdToSession(externalContext, viewId);
        initializeChildren(context, viewRoot);
        if (externalContext.getRequestParameterMap().isEmpty()) {
            context.renderResponse();
        }
        setPostback(externalContext, viewId.equals(previousViewId));
    }

    private UIViewRoot getViewRoot(final FacesContext context,
            final String viewId) {
        final ViewHandler viewHandler = FacesContextUtil
                .getViewHandler(context);
        UIViewRoot viewRoot = viewHandler.restoreView(context, viewId);
        if (viewRoot != null) {
            final Locale locale = viewHandler.calculateLocale(context);
            if (locale != null) {
                viewRoot.setLocale(locale);
            }
        } else {
            viewRoot = viewHandler.createView(context, viewId);
        }
        return viewRoot;
    }

    private String getViewId(final FacesContext context,
            final ExternalContext externalContext) {
        // PortletSupport
        if (PortletUtil.isPortlet(context)) {
            final PortletRequest request = (PortletRequest) externalContext
                    .getRequest();
            return request.getParameter(FacesPortlet.VIEW_ID);
        } else {
            return ExternalContextUtil.getViewId(externalContext);
        }
    }

    protected String getViewIdFromSession(final ExternalContext externalContext) {
        return (String) externalContext.getSessionMap().get(VIEW_ID_ATTR);
    }

    protected void saveViewIdToSession(final ExternalContext externalContext,
            final String viewId) {
        externalContext.getSessionMap().put(VIEW_ID_ATTR, viewId);
    }

    protected PhaseId getCurrentPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    protected void setPostback(final ExternalContext externalContext,
            final boolean postback) {
        externalContext.getRequestMap().put(POSTBACK_ATTR,
                new Boolean(postback));
    }

    public boolean isPostBack() {
        final FacesContext context = FacesContext.getCurrentInstance();
        final ExternalContext externalContext = context.getExternalContext();
        final Boolean postBack = (Boolean) externalContext.getRequestMap().get(
                POSTBACK_ATTR);
        return postBack.booleanValue();
    }

}
