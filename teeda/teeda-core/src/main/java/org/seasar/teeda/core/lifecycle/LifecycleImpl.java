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
package org.seasar.teeda.core.lifecycle;

import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;

import org.seasar.framework.util.ArrayUtil;
import org.seasar.teeda.core.util.ExternalContextUtil;

/**
 * @author higa
 * @author shot
 */
public class LifecycleImpl extends Lifecycle {

    private static final String VIEW_ID_ATTR = LifecycleImpl.class.getName()
            + ".VIEW_ID";

    private static final String EXECUTED_ATTR = LifecycleImpl.class.getName()
            + ".EXECUTED";

    private static final String REDIRECTED_TIME_ATTR = LifecycleImpl.class
            .getName()
            + ".REDIRECTED_TIME";

    private static final long REDIRECT_WAIT_TIME = 2000;

    private PhaseListener[] phaseListeners = new PhaseListener[0];

    private AbstractPhase restoreViewPhase_;

    private AbstractPhase applyRequestValuesPhase_;

    private AbstractPhase invokeApplicationPhase_;

    private AbstractPhase renderResponsePhase_;

    private AbstractPhase processValidationPhase_;

    private AbstractPhase updateModelValuesPhase_;

    public LifecycleImpl() {
    }

    public void execute(FacesContext context) throws FacesException {
        try {
            restoreViewPhase_.execute(context);
            Postback postback = null;
            if (restoreViewPhase_ instanceof Postback) {
                postback = (Postback) restoreViewPhase_;
            }
            if (isFinished(context)) {
                return;
            }
            ExternalContext extContext = context.getExternalContext();
            Map sessionMap = extContext.getSessionMap();
            Long redirectedTime = (Long) sessionMap.get(REDIRECTED_TIME_ATTR);
            if (redirectedTime != null) {
                sessionMap.remove(REDIRECTED_TIME_ATTR);
                if (System.currentTimeMillis() - redirectedTime.longValue() < REDIRECT_WAIT_TIME) {
                    context.renderResponse();
                    return;
                }
            }
            Map requestMap = extContext.getRequestMap();
            if (requestMap.containsKey(EXECUTED_ATTR)) {
                context.renderResponse();
                return;
            }
            requestMap.put(EXECUTED_ATTR, EXECUTED_ATTR);
            applyRequestValuesPhase_.execute(context);
            if (isFinished(context)) {
                applyRequestValuesPhase_.initializeChildren(context, context
                        .getViewRoot());
                return;
            }
            if (postback != null && postback.isPostBack() || hasEvent(context)) {
                processValidationPhase_.execute(context);
                if (isFinished(context)) {
                    return;
                }
            }
            updateModelValuesPhase_.execute(context);
            if (isFinished(context)) {
                return;
            }
            invokeApplicationPhase_.execute(context);
            if (isGetRedirect(context)) {
                sessionMap.put(REDIRECTED_TIME_ATTR, new Long(System
                        .currentTimeMillis()));
            }
        } catch (EvaluationException ex) {
            Throwable cause = ex.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else if (cause instanceof Error) {
                throw (Error) cause;
            } else {
                throw ex;
            }
        }
    }

    protected boolean isGetRedirect(FacesContext context) {
        if (!context.getResponseComplete()) {
            return false;
        }
        ExternalContext extContext = context.getExternalContext();
        return ExternalContextUtil.isGetRedirect(extContext);
    }

    public void render(FacesContext context) throws FacesException {
        if (context.getResponseComplete()) {
            return;
        }
        renderResponsePhase_.execute(context);
    }

    protected String getViewIdFromSession(ExternalContext externalContext) {

        return (String) externalContext.getSessionMap().get(VIEW_ID_ATTR);
    }

    protected void saveViewIdToSession(ExternalContext externalContext,
            String viewId) {

        externalContext.getSessionMap().put(VIEW_ID_ATTR, viewId);
    }

    protected boolean isFinished(FacesContext context) throws FacesException {
        return context.getResponseComplete() || context.getRenderResponse();
    }

    public void addPhaseListener(PhaseListener listener) {
        phaseListeners = (PhaseListener[]) ArrayUtil.add(phaseListeners,
                listener);
    }

    public void removePhaseListener(PhaseListener listener) {
        phaseListeners = (PhaseListener[]) ArrayUtil.remove(phaseListeners,
                listener);
    }

    public PhaseListener[] getPhaseListeners() {
        return phaseListeners;
    }

    protected boolean hasEvent(FacesContext context) {
        UIViewRoot viewRoot = context.getViewRoot();
        return viewRoot.getEventSize() > 0;
    }

    public void setRestoreViewPhase(AbstractPhase restoreViewPhase) {
        restoreViewPhase_ = restoreViewPhase;
    }

    public void setApplyRequestValuesPhase(AbstractPhase applyRequestValuesPhase) {
        applyRequestValuesPhase_ = applyRequestValuesPhase;
    }

    public void setInvokeApplicationPhase(AbstractPhase invokeApplicationPhase) {
        invokeApplicationPhase_ = invokeApplicationPhase;
    }

    public void setRenderResponsePhase(AbstractPhase renderPhase) {
        renderResponsePhase_ = renderPhase;
    }

    public void setProcessValidationsPhase(AbstractPhase processValidationPhase) {
        processValidationPhase_ = processValidationPhase;
    }

    public void setUpdateModelValuesPhase(AbstractPhase updateModelValuesPhase) {
        updateModelValuesPhase_ = updateModelValuesPhase;
    }

    public Phase getApplyRequestValuesPhase() {
        return applyRequestValuesPhase_;
    }

    public Phase getInvokeApplicationPhase() {
        return invokeApplicationPhase_;
    }

    public Phase getProcessValidationPhase() {
        return processValidationPhase_;
    }

    public Phase getRenderResponsePhase() {
        return renderResponsePhase_;
    }

    public Phase getRestoreViewPhase() {
        return restoreViewPhase_;
    }

    public Phase getUpdateModelValuesPhase() {
        return updateModelValuesPhase_;
    }

}