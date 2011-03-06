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
package org.seasar.teeda.extension.event;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.internal.FacesMessageUtil;

import org.seasar.teeda.core.util.PostbackUtil;
import org.seasar.teeda.extension.util.ConditionUtil;

/**
 * @author koichik
 */
public class ResetConditionPhaseListener implements PhaseListener {

    private static final long serialVersionUID = 1L;

    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

    public void beforePhase(final PhaseEvent event) {
        final FacesContext context = FacesContext.getCurrentInstance();
        final boolean postback = PostbackUtil.isPostback(context
                .getExternalContext().getRequestMap());
        final boolean noError = !FacesMessageUtil
                .hasErrorOrFatalMessage(context);
        if (!postback || noError) {
            ConditionUtil.removeConditions(context);
        }
    }

    public void afterPhase(final PhaseEvent event) {
    }

}
