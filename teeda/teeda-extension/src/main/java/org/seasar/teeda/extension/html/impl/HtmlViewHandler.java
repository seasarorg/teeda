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
package org.seasar.teeda.extension.html.impl;

import java.io.IOException;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.PageContextOutWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.servlet.S2ContainerServlet;
import org.seasar.teeda.core.application.ViewHandlerImpl;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.core.util.ExternalContextUtil;
import org.seasar.teeda.core.util.ServletExternalContextUtil;
import org.seasar.teeda.extension.exception.JspRuntimeException;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ActionDescCache;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.PageDescCache;
import org.seasar.teeda.extension.html.PagePersistence;
import org.seasar.teeda.extension.html.TagProcessor;
import org.seasar.teeda.extension.html.TagProcessorCache;
import org.seasar.teeda.extension.jsp.PageContextImpl;

/**
 * @author higa
 *
 */
public class HtmlViewHandler extends ViewHandlerImpl {

    private static final String INITIALIZE = "initialize";

    private TagProcessorCache tagProcessorCache;

    private PageDescCache pageDescCache;

    private ActionDescCache actionDescCache;

    private PagePersistence pagePersistence;

    public void setTagProcessorCache(TagProcessorCache tagProcessorCache) {
        this.tagProcessorCache = tagProcessorCache;
    }

    public void setPageDescCache(PageDescCache pageDescCache) {
        this.pageDescCache = pageDescCache;
    }

    public void setActionDescCache(ActionDescCache actionDescCache) {
        this.actionDescCache = actionDescCache;
    }

    public void setPagePersistence(PagePersistence pagePersistence) {
        this.pagePersistence = pagePersistence;
    }

    public UIViewRoot restoreView(FacesContext context, String viewId) {
        tagProcessorCache.updateTagProcessor(viewId);
        pagePersistence.restore(context, viewId);
        return super.restoreView(context, viewId);
    }

    public void renderView(FacesContext context, UIViewRoot viewRoot)
            throws IOException {
        ExternalContext externalContext = context.getExternalContext();
        String path = ExternalContextUtil.getViewId(externalContext);
        if (path.equals(viewRoot.getViewId())) {
            renderView(context, path);
        } else {
            externalContext.dispatch(viewRoot.getViewId());
        }
    }

    protected void renderView(FacesContext context, String path)
            throws IOException {
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = ServletExternalContextUtil
                .getRequest(externalContext);
        HttpServletResponse response = ServletExternalContextUtil
                .getResponse(externalContext);
        PageContext pageContext = createPageContext(request, response);
        setupResponseWriter(pageContext, null, request.getCharacterEncoding());
        if (invokeInitialize(context, path) != null) {
            return;
        }
        TagProcessor tagProcessor = tagProcessorCache.getTagProcessor(path);
        try {
            tagProcessor.process(pageContext, null);
        } catch (JspException ex) {
            throw new JspRuntimeException(ex);
        }
        pageContext.getOut().flush();
    }

    protected String invokeInitialize(FacesContext context, String path)
            throws IOException {
        PageDesc pageDesc = pageDescCache.getPageDesc(path);
        Object target = null;
        String next = null;
        if (pageDesc != null && pageDesc.hasMethod(INITIALIZE)) {
            target = DIContainerUtil.getComponent(pageDesc.getPageName());
        }
        if (target == null) {
            ActionDesc actionDesc = actionDescCache.getActionDesc(path);
            if (actionDesc != null && actionDesc.hasMethod(INITIALIZE)) {
                target = DIContainerUtil.getComponent(actionDesc
                        .getActionName());
            }
        }
        if (target != null) {
            BeanDesc beanDesc = BeanDescFactory.getBeanDesc(target.getClass());
            next = (String) beanDesc.invoke(target, INITIALIZE, null);
            if (next != null) {
                navigate(context, next);
            }
        }
        return next;
    }

    protected void navigate(FacesContext context, String path)
            throws IOException {
        Application app = context.getApplication();
        NavigationHandler nh = app.getNavigationHandler();
        nh.handleNavigation(context, null, path);
        if (context.getResponseComplete()) {
            return;
        }
        ViewHandler vh = app.getViewHandler();
        vh.renderView(context, context.getViewRoot());
    }

    protected Servlet getServlet() {
        return S2ContainerServlet.getInstance();
    }

    protected ServletConfig getServletConfig() {
        return getServlet().getServletConfig();
    }

    protected ServletContext getServletContext() {
        return getServletConfig().getServletContext();
    }

    protected PageContext createPageContext(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        PageContextImpl pageContext = new PageContextImpl();
        pageContext.initialize(getServlet(), request, response, null);
        return pageContext;
    }

    protected void setupResponseWriter(PageContext pageContext,
            String contentType, String encoding) {
        FacesContext context = FacesContext.getCurrentInstance();
        RenderKitFactory renderFactory = getRenderKitFactory();
        RenderKit renderKit = renderFactory.getRenderKit(context, context
                .getViewRoot().getRenderKitId());
        ResponseWriter writer = renderKit.createResponseWriter(
                new PageContextOutWriter(pageContext), contentType, encoding);
        context.setResponseWriter(writer);
    }

    protected RenderKitFactory getRenderKitFactory() {
        return (RenderKitFactory) FactoryFinder
                .getFactory(FactoryFinder.RENDER_KIT_FACTORY);
    }

}