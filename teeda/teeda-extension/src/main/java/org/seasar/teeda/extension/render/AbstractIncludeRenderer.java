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
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.extension.component.AbstractInclude;
import org.seasar.teeda.extension.helper.PathHelper;
import org.seasar.teeda.extension.html.HtmlComponentInvoker;

/**
 * @author higa
 */
public abstract class AbstractIncludeRenderer extends AbstractRenderer {

    private ViewHandler viewHandler;

    private PathHelper pathHelper;

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
     * @return Returns the pathHelper.
     */
    public PathHelper getPathHelper() {
        return pathHelper;
    }

    /**
     * @param pathHelper The pathHelper to set.
     */
    public void setPathHelper(PathHelper pathHelper) {
        this.pathHelper = pathHelper;
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
        include(context, (AbstractInclude) component);
        for (Iterator i = component.getChildren().iterator(); i.hasNext();) {
            UIComponent child = (UIComponent) i.next();
            child.processDecodes(context);
        }
    }

    public boolean getRendersChildren() {
        return true;
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeEnd(context, component);
        component.getChildren().clear();
    }

    protected void include(FacesContext context, AbstractInclude component) {
        IncludedBody includedBody = getIncludedBody(context, component);
        if (includedBody == null) {
            return;
        }
        component.getChildren().addAll(includedBody.getComponentList());
        component.setIncludedViewId(includedBody.getViewId());
    }

    protected abstract IncludedBody getIncludedBody(FacesContext context,
            AbstractInclude component);

    protected void invoke(FacesContext context, String includedViewId) {
        if (htmlComponentInvoker.isInitialized(context)) {
            String componentName = htmlComponentInvoker.getComponentName(
                    includedViewId, HtmlComponentInvoker.INITIALIZE);
            if (componentName != null) {
                htmlComponentInvoker.invokeInitialize(context, componentName);
            }
        }
        if (context.getResponseComplete()) {
            return;
        }
        String componentName = htmlComponentInvoker.getComponentName(
                includedViewId, HtmlComponentInvoker.PRERENDER);
        if (componentName != null) {
            htmlComponentInvoker.invokePrerender(context, componentName);
        }
        if (context.getResponseComplete()) {
            return;
        }
    }
}