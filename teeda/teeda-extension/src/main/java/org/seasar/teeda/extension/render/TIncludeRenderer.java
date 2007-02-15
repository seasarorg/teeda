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
import java.util.List;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.internal.SubApplicationUtil;
import javax.faces.internal.UIComponentUtil;

import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.component.TInclude;
import org.seasar.teeda.extension.component.UIBody;

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

    public void decode(FacesContext context, UIComponent component) {
        super.decode(context, component);
        includes(context, (TInclude) component);
        for (Iterator i = component.getChildren().iterator(); i.hasNext();) {
            UIComponent child = (UIComponent) i.next();
            child.processDecodes(context);
        }
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeEnd(context, component);
        if (component.getChildCount() == 0) {
            includes(context, (TInclude) component);
        }
        RendererUtil.renderChildren(context, component);
        component.getChildren().clear();
    }

    protected void includes(FacesContext context, TInclude include) {
        String src = include.getSrc();
        String[] array = StringUtil.split(src, ", ");
        for (int i = 0; i < array.length; i++) {
            include(context, include, array[i]);
        }

    }

    protected void include(FacesContext context, TInclude component, String src) {
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
            List children = body.getChildren();
            for (int i = 0; i < children.size(); i++) {
                component.getChildren().add((UIComponent) children.get(i));
            }
        }

    }

    protected String calcViewId(FacesContext context, String src) {
        AssertionUtil.assertNotNull("src", src);
        if (src.startsWith("/")) {
            String viewRootPath = namingConvention.getViewRootPath();
            if ("/".endsWith(viewRootPath)) {
                viewRootPath = "";
            }
            return viewRootPath + src;
        }
        return SubApplicationUtil.getSubApplicationPath(context.getViewRoot()
                .getViewId())
                + "/" + src;
    }
}