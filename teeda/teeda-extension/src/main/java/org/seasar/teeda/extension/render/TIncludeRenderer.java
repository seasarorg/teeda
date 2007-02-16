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
package org.seasar.teeda.extension.render;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.internal.UIComponentUtil;

import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.component.TInclude;
import org.seasar.teeda.extension.component.UIBody;
import org.seasar.teeda.extension.html.HtmlComponentInvoker;

/**
 * @author higa
 */
public class TIncludeRenderer extends AbstractRenderer {

    public static final String COMPONENT_FAMILY = TInclude.COMPONENT_FAMILY;

    public static final String RENDERER_TYPE = TInclude.DEFAULT_RENDERER_TYPE;

    private static final Logger logger = Logger
            .getLogger(TIncludeRenderer.class);

    private ViewHandler viewHandler;

    private NamingConvention namingConvention;

    private HtmlComponentInvoker htmlComponentInvoker;

    /**
     * @return Returns the viewHandler.
     */
    public ViewHandler getViewHandler() {
        return viewHandler;
    }

    /**
     * @param viewHandler The viewHandler to set.
     */
    public void setViewHandler(ViewHandler viewHandler) {
        this.viewHandler = viewHandler;
    }

    /**
     * @param namingConvention The namingConvention to set.
     */
    public void setNamingConvention(NamingConvention namingConvention) {
        this.namingConvention = namingConvention;
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

    public void decode(FacesContext context, UIComponent component) {
        super.decode(context, component);
        include(context, (TInclude) component);
        for (Iterator i = component.getChildren().iterator(); i.hasNext();) {
            UIComponent child = (UIComponent) i.next();
            child.processDecodes(context);
        }
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeEnd(context, component);
        TInclude inc = (TInclude) component;
        if (!inc.isIncluded()) {
            include(context, inc);
        }
        if (!inc.isIncluded()) {
            return;
        }
        String srcViewId = calcViewId(context, inc.getSrc());
        if (htmlComponentInvoker.isInitialized(context)) {
            String componentName = htmlComponentInvoker.getComponentName(
                    srcViewId, HtmlComponentInvoker.INITIALIZE);
            if (componentName != null) {
                htmlComponentInvoker.invokeInitialize(context, componentName);
            }
        }
        if (context.getResponseComplete()) {
            return;
        }
        String componentName = htmlComponentInvoker.getComponentName(srcViewId,
                HtmlComponentInvoker.PRERENDER);
        if (componentName != null) {
            htmlComponentInvoker.invokePrerender(context, componentName);
        }
        if (context.getResponseComplete()) {
            return;
        }
        RendererUtil.renderChildren(context, component);
        component.getChildren().clear();
    }

    protected void include(FacesContext context, TInclude component) {
        String src = component.getSrc();
        if (StringUtil.isEmpty(src)) {
            return;
        }
        String viewId = calcViewId(context, src);
        UIViewRoot viewRoot = viewHandler.restoreView(context, viewId);
        if (viewRoot == null) {
            viewRoot = viewHandler.createView(context, viewId);
        }
        if (viewRoot == null) {
            return;
        }
        UIComponent body = UIComponentUtil.findDescendant(viewRoot,
                UIBody.class);
        if (body == null) {
            logger.log("WTDA0202", new Object[] { viewId });
        } else {
            component.getChildren().addAll(body.getChildren());
            component.setIncluded(true);
        }
    }

    protected String calcViewId(FacesContext context, String src) {
        return TInclude.calcViewId(context, src, namingConvention
                .getViewRootPath());
    }
}