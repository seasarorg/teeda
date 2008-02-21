/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.internal.UIComponentUtil;
import javax.faces.render.Renderer;

import org.seasar.teeda.extension.component.TViewRoot;
import org.seasar.teeda.extension.render.TViewRootRenderer;

/**
 * @author koichik
 */
public class ComposeLayoutPhaseListener implements PhaseListener {

    private static final long serialVersionUID = 1L;

    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    public void beforePhase(final PhaseEvent event) {
    }

    public void afterPhase(final PhaseEvent event) {
        final FacesContext context = event.getFacesContext();
        final UIViewRoot viewRoot = context.getViewRoot();
        if (viewRoot instanceof TViewRoot) {
            final Renderer renderer = UIComponentUtil.getRenderer(context,
                    viewRoot);
            if (renderer instanceof TViewRootRenderer) {
                final TViewRootRenderer viewRootRenderer = (TViewRootRenderer) renderer;
                viewRootRenderer.layout(context, (TViewRoot) viewRoot);
            }
        }
    }

}
