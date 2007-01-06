/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

import java.io.IOException;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;

import org.seasar.framework.util.ArrayUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.ErrorPageManager;
import org.seasar.teeda.core.util.PostbackUtil;

/**
 * @author higa
 * @author shot
 */
public class LifecycleImpl extends Lifecycle {

    private static final String EXECUTED_ATTR = LifecycleImpl.class.getName()
            + ".EXECUTED";

    private PhaseListener[] phaseListeners = new PhaseListener[0];

    private Phase restoreViewPhase;

    private AbstractPhase applyRequestValuesPhase;

    private Phase invokeApplicationPhase;

    private Phase renderResponsePhase;

    private Phase processValidationPhase;

    private Phase updateModelValuesPhase;

    private ErrorPageManager errorPageManager;

    public LifecycleImpl() {
    }

    public void execute(final FacesContext context) throws FacesException {
        final ExternalContext extContext = context.getExternalContext();
        final Map requestMap = extContext.getRequestMap();
        requestMap.put(JsfConstants.FACES_CONTEXT, context);
        try {
            restoreViewPhase.execute(context);
            final boolean postback = PostbackUtil.isPostback(requestMap);
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
            if (postback || hasEvent(context)) {
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
        } catch (final EvaluationException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else if (cause instanceof Error) {
                throw (Error) cause;
            } else {
                throw e;
            }
        } catch (final Exception e) {
            handleException(context, e);
        }
    }

    protected void handleException(final FacesContext context, final Exception e) {
        final ErrorPageManager manager = getErrorPageManager();
        final ExternalContext externalContext = context.getExternalContext();
        try {
            if (manager.handleException(e, externalContext)) {
                context.responseComplete();
            } else {
                throw (RuntimeException) e;
            }
        } catch (IOException ioe) {
            throw new FacesException(ioe.getMessage(), ioe);
        }
    }

    public void render(final FacesContext context) throws FacesException {
        if (context.getResponseComplete()) {
            return;
        }
        try {
            renderResponsePhase.execute(context);
        } catch(FacesException e) {
            throw e;
        } catch(Exception e) {
            handleException(context, e);
        }
    }

    protected boolean isFinished(final FacesContext context)
            throws FacesException {
        return context.getResponseComplete() || context.getRenderResponse();
    }

    public void addPhaseListener(final PhaseListener listener) {
        phaseListeners = (PhaseListener[]) ArrayUtil.add(phaseListeners,
                listener);
    }

    public void removePhaseListener(final PhaseListener listener) {
        phaseListeners = (PhaseListener[]) ArrayUtil.remove(phaseListeners,
                listener);
    }

    public PhaseListener[] getPhaseListeners() {
        return phaseListeners;
    }

    protected boolean hasEvent(final FacesContext context) {
        final UIViewRoot viewRoot = context.getViewRoot();
        return viewRoot.getEventSize() > 0;
    }

    public void setRestoreViewPhase(final Phase restoreViewPhase) {
        this.restoreViewPhase = restoreViewPhase;
    }

    public void setApplyRequestValuesPhase(
            final AbstractPhase applyRequestValuesPhase) {
        this.applyRequestValuesPhase = applyRequestValuesPhase;
    }

    public void setInvokeApplicationPhase(final Phase invokeApplicationPhase) {
        this.invokeApplicationPhase = invokeApplicationPhase;
    }

    public void setRenderResponsePhase(final Phase renderPhase) {
        renderResponsePhase = renderPhase;
    }

    public void setProcessValidationsPhase(final Phase processValidationPhase) {
        this.processValidationPhase = processValidationPhase;
    }

    public void setUpdateModelValuesPhase(final Phase updateModelValuesPhase) {
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

    public ErrorPageManager getErrorPageManager() {
        return errorPageManager;
    }

    public void setErrorPageManager(ErrorPageManager errorPageManager) {
        this.errorPageManager = errorPageManager;
    }
}