/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.internal.RenderPreparable;

import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.extension.html.HtmlComponentInvoker;

/**
 * @author higa
 */
public abstract class AbstractIncludeRenderer extends AbstractRenderer
        implements RenderPreparable {

    private HtmlComponentInvoker htmlComponentInvoker;

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
    }

    protected void invoke(FacesContext context, String includedViewId) {
        String componentName = htmlComponentInvoker.getComponentName(
                includedViewId, HtmlComponentInvoker.INITIALIZE);
        if (componentName != null) {
            if (!htmlComponentInvoker.isInitialized(context, componentName)) {
                htmlComponentInvoker.invokeInitialize(context, componentName);
            }
        }
        if (context.getResponseComplete()) {
            return;
        }
        componentName = htmlComponentInvoker.getComponentName(includedViewId,
                HtmlComponentInvoker.PRERENDER);
        if (componentName != null) {
            htmlComponentInvoker.invokePrerender(context, componentName);
        }
        if (context.getResponseComplete()) {
            return;
        }
    }

    public void postEncodeEnd(final FacesContext context) throws IOException {
        final UIViewRoot viewRoot = context.getViewRoot();
        if (viewRoot != null) {
            viewRoot.getChildren().clear();
        }
    }

    public void preEncodeBegin(final FacesContext context) throws IOException {
    }

}