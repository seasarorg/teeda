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

/**
 * @author higa
 * @author shot
 */
public class LifecycleImpl extends Lifecycle {

    private static final String EXECUTED_ATTR = LifecycleImpl.class.getName()
            + ".EXECUTED";

    private static final String POSTBACK_ATTR = "postback";

    private PhaseListener[] phaseListeners = new PhaseListener[0];

    private Phase restoreViewPhase;

    private AbstractPhase applyRequestValuesPhase;

    private Phase invokeApplicationPhase;

    private Phase renderResponsePhase;

    private Phase processValidationPhase;

    private Phase updateModelValuesPhase;

    public LifecycleImpl() {
    }

    public void execute(FacesContext context) throws FacesException {
        ExternalContext extContext = context.getExternalContext();
        Map requestMap = extContext.getRequestMap();
        try {
            restoreViewPhase.execute(context);
            Postback postback = NullPostback.getCurrentInstance();
            if (restoreViewPhase instanceof Postback) {
                postback = (Postback) restoreViewPhase;
                requestMap.put(POSTBACK_ATTR, Boolean.valueOf(postback
                        .isPostBack()));
            }
            if (isFinished(context)) {
                return;
            }
            if (requestMap.containsKey(EXECUTED_ATTR)) {
                context.renderResponse();
                return;
            }
            requestMap.put(EXECUTED_ATTR, EXECUTED_ATTR);
            applyRequestValuesPhase.execute(context);
            if (isFinished(context)) {
                applyRequestValuesPhase.initializeChildren(context, context
                        .getViewRoot());
                return;
            }
            if (postback.isPostBack() || hasEvent(context)) {
                processValidationPhase.execute(context);
                if (isFinished(context)) {
                    return;
                }
            }
            updateModelValuesPhase.execute(context);
            if (isFinished(context)) {
                return;
            }
            invokeApplicationPhase.execute(context);
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

    public void render(FacesContext context) throws FacesException {
        if (context.getResponseComplete()) {
            return;
        }
        renderResponsePhase.execute(context);
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

    public void setRestoreViewPhase(Phase restoreViewPhase) {
        this.restoreViewPhase = restoreViewPhase;
    }

    public void setApplyRequestValuesPhase(AbstractPhase applyRequestValuesPhase) {
        this.applyRequestValuesPhase = applyRequestValuesPhase;
    }

    public void setInvokeApplicationPhase(Phase invokeApplicationPhase) {
        this.invokeApplicationPhase = invokeApplicationPhase;
    }

    public void setRenderResponsePhase(Phase renderPhase) {
        renderResponsePhase = renderPhase;
    }

    public void setProcessValidationsPhase(Phase processValidationPhase) {
        this.processValidationPhase = processValidationPhase;
    }

    public void setUpdateModelValuesPhase(Phase updateModelValuesPhase) {
        this.updateModelValuesPhase = updateModelValuesPhase;
    }

    public AbstractPhase getApplyRequestValuesPhase() {
        return applyRequestValuesPhase;
    }

    public Phase getInvokeApplicationPhase() {
        return invokeApplicationPhase;
    }

    public Phase getProcessValidationPhase() {
        return processValidationPhase;
    }

    public Phase getRenderResponsePhase() {
        return renderResponsePhase;
    }

    public Phase getRestoreViewPhase() {
        return restoreViewPhase;
    }

    public Phase getUpdateModelValuesPhase() {
        return updateModelValuesPhase;
    }

    private static final class NullPostback implements Postback {

        private static final NullPostback instance = new NullPostback();

        public static NullPostback getCurrentInstance() {
            return instance;
        }

        public boolean isPostBack() {
            return false;
        }

    }
}