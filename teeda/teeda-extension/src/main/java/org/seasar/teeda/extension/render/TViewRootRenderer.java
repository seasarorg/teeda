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
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.internal.FacesConfigOptions;
import javax.faces.internal.UIComponentUtil;
import javax.servlet.ServletContext;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.hotdeploy.HotdeployUtil;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.core.util.PostbackUtil;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.ServletContextUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.TViewRoot;
import org.seasar.teeda.extension.component.UIBody;
import org.seasar.teeda.extension.helper.PathHelper;
import org.seasar.teeda.extension.html.HtmlComponentInvoker;
import org.seasar.teeda.extension.html.HtmlDescCache;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.PageDescCache;

/**
 * @author higa
 */
public class TViewRootRenderer extends AbstractRenderer {

    public static final String COMPONENT_FAMILY = TViewRoot.COMPONENT_FAMILY;

    public static final String RENDERER_TYPE = TViewRoot.DEFAULT_RENDERER_TYPE;

    private static final String STACK_KEY = TViewRootRenderer.class.getName();

    private static final Logger logger = Logger
            .getLogger(TViewRootRenderer.class);

    private ViewHandler viewHandler;

    private HtmlComponentInvoker htmlComponentInvoker;

    private ServletContext servletContext;

    private HtmlDescCache htmlDescCache;

    private PageDescCache pageDescCache;

    private PathHelper pathHelper;

    public static IncludedBody popIncludedBody(FacesContext context) {
        Map requestMap = context.getExternalContext().getRequestMap();
        Stack stack = (Stack) requestMap.get(STACK_KEY);
        if (stack == null) {
            return null;
        }
        return (IncludedBody) stack.pop();
    }

    protected static void pushIncludedBody(FacesContext context,
            IncludedBody includedBody) {
        Map requestMap = context.getExternalContext().getRequestMap();
        Stack stack = (Stack) requestMap.get(STACK_KEY);
        if (stack == null) {
            stack = new Stack();
            requestMap.put(STACK_KEY, stack);
        }
        stack.push(includedBody);
    }

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

    /**
     * @return Returns the servletContext.
     */
    public ServletContext getServletContext() {
        return servletContext;
    }

    /**
     * @param servletContext The servletContext to set.
     */
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * @return Returns the htmlDescCache.
     */
    public HtmlDescCache getHtmlDescCache() {
        return htmlDescCache;
    }

    /**
     * @param htmlDescCache The htmlDescCache to set.
     */
    public void setHtmlDescCache(HtmlDescCache htmlDescCache) {
        this.htmlDescCache = htmlDescCache;
    }

    /**
     * @return Returns the pageDescCache.
     */
    public PageDescCache getPageDescCache() {
        return pageDescCache;
    }

    /**
     * @param pageDescCache The pageDescCache to set.
     */
    public void setPageDescCache(PageDescCache pageDescCache) {
        this.pageDescCache = pageDescCache;
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

    public void decode(FacesContext context, UIComponent component) {
        super.decode(context, component);
        layout(context, (TViewRoot) component);
        for (Iterator i = component.getChildren().iterator(); i.hasNext();) {
            UIComponent child = (UIComponent) i.next();
            child.processDecodes(context);
        }
    }

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeBegin(context, component);
        TViewRoot viewRoot = (TViewRoot) component;
        if (viewRoot.getRootViewId() == null) {
            layout(context, viewRoot);
        }
        invoke(context, viewRoot.getRootViewId());
        RendererUtil.renderChildren(context, component);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeEnd(context, component);
        component.getChildren().clear();
    }

    protected void layout(FacesContext context, TViewRoot component) {
        TViewRoot child = component;
        TViewRoot parent = getParentViewRoot(context, child);
        while (parent != null) {
            UIComponent body = UIComponentUtil.findDescendant(child,
                    UIBody.class);
            if (body == null) {
                logger.log("WTDA0202", new Object[] { child.getViewId() });
            }
            pushIncludedBody(context, new IncludedBody(child.getViewId(), body
                    .getChildren()));
            child = parent;
            parent = getParentViewRoot(context, child);
        }
        if (child != component) {
            component.setRootViewId(child.getViewId());
            component.getChildren().clear();
            component.getChildren().addAll(child.getChildren());
        } else {
            component.setRootViewId(component.getViewId());
        }
    }

    protected TViewRoot getParentViewRoot(FacesContext context,
            TViewRoot component) {

        String parentViewId = getParentViewId(context, component);
        if (parentViewId == null) {
            return null;
        }
        if (HotdeployUtil.isHotdeploy()
                || htmlDescCache.getHtmlDesc(parentViewId) == null) {
            InputStream is = ServletContextUtil.getResourceAsStream(
                    servletContext, parentViewId);
            if (is != null) {
                InputStreamUtil.close(is);
            } else {
                return null;
            }
        }
        UIViewRoot viewRoot = viewHandler.restoreView(context, parentViewId);
        if (viewRoot == null) {
            viewRoot = viewHandler.createView(context, parentViewId);
        }
        return (TViewRoot) viewRoot;
    }

    protected String getParentViewId(FacesContext context, TViewRoot component) {

        String parentPath = FacesConfigOptions.getDefaultLayoutPath();
        if (component != context.getViewRoot()
                || context.getViewRoot().getViewId().indexOf("/layout/") >= 0) {
            parentPath = null;
        }
        PageDesc pageDesc = pageDescCache.getPageDesc(component.getViewId());
        if (pageDesc != null
                && pageDesc.hasProperty(ExtensionConstants.LAYOUT_ATTR)) {
            Object page = DIContainerUtil.getComponent(pageDesc.getPageName());
            BeanDesc beanDesc = BeanDescFactory.getBeanDesc(page.getClass());
            PropertyDesc propDesc = beanDesc
                    .getPropertyDesc(ExtensionConstants.LAYOUT_ATTR);
            if (propDesc.hasReadMethod()) {
                parentPath = (String) propDesc.getValue(page);
            }
        }
        if (parentPath == null) {
            return null;
        }
        return pathHelper.fromViewRootRelativePathToViewId(parentPath);
    }

    protected void invoke(FacesContext context, String viewId) {
        ExternalContext externalContext = context.getExternalContext();
        Map requestMap = externalContext.getRequestMap();
        if (!PostbackUtil.isPostback(requestMap)) {
            invoke(context, viewId, HtmlComponentInvoker.INITIALIZE);
        }
        if (context.getResponseComplete()) {
            return;
        }
        invoke(context, viewId, HtmlComponentInvoker.PRERENDER);
    }

    protected String invoke(FacesContext context, String path, String methodName) {
        String componentName = htmlComponentInvoker.getComponentName(path,
                methodName);
        return htmlComponentInvoker.invoke(context, componentName, methodName);
    }
}