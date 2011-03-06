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
package org.seasar.teeda.extension.render;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.seasar.framework.log.Logger;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.util.PostbackUtil;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.component.TViewRoot;
import org.seasar.teeda.extension.html.HtmlComponentInvoker;
import org.seasar.teeda.extension.util.LayoutBuilder;

/**
 * @author higa
 * @authos shot
 */
public class TViewRootRenderer extends AbstractRenderer implements Invokable {

    public static final String COMPONENT_FAMILY = TViewRoot.COMPONENT_FAMILY;

    public static final String RENDERER_TYPE = TViewRoot.DEFAULT_RENDERER_TYPE;

    private static final Logger logger = Logger
            .getLogger(TViewRootRenderer.class);

    private HtmlComponentInvoker htmlComponentInvoker;

    public void decode(FacesContext context, UIComponent component) {
        super.decode(context, component);
        for (Iterator i = component.getChildren().iterator(); i.hasNext();) {
            UIComponent child = (UIComponent) i.next();
            child.processDecodes(context);
        }
    }

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeBegin(context, component);
        invokeAll(context);
        TViewRoot viewRoot = (TViewRoot) component;
        invoke(context, viewRoot.getRootViewId());
        if (!context.getResponseComplete()) {
            RendererUtil.renderChildren(context, component);
        }
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeEnd(context, component);
    }

    public void invokeAll(FacesContext context) {
        List bodies = LayoutBuilder.getIncludedBodies(context);
        if (bodies == null) {
            return;
        }
        for (int i = 0; i < bodies.size(); i++) {
            IncludedBody body = (IncludedBody) bodies.get(i);
            invoke(context, body.getViewId());
        }
    }

    public void invoke(final FacesContext context, final String viewId) {
        ExternalContext externalContext = context.getExternalContext();
        Map requestMap = externalContext.getRequestMap();
        if (!PostbackUtil.isPostback(requestMap)) {
            String componentName = htmlComponentInvoker.getComponentName(
                    viewId, HtmlComponentInvoker.INITIALIZE);
            if (componentName != null) {
                htmlComponentInvoker.invokeInitialize(context, componentName);
            }
        }
        if (context.getResponseComplete()) {
            return;
        }
        String componentName = htmlComponentInvoker.getComponentName(viewId,
                HtmlComponentInvoker.PRERENDER);
        if (componentName != null) {
            htmlComponentInvoker.invokePrerender(context, componentName);
        }
    }

    /**
     * @return Returns the htmlComponentInvoker.
     */
    public HtmlComponentInvoker getHtmlComponentInvoker() {
        return htmlComponentInvoker;
    }

    /**
     * @param htmlComponentInvoker The htmlComponentInvoker to set.
     */
    public void setHtmlComponentInvoker(
            HtmlComponentInvoker htmlComponentInvoker) {
        this.htmlComponentInvoker = htmlComponentInvoker;
    }

}
