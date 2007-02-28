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
package org.seasar.teeda.extension.html.impl;

import java.io.IOException;
import java.util.Map;

import javax.faces.FactoryFinder;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.internal.PageContextUtil;
import javax.faces.render.RenderKitFactory;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.servlet.S2ContainerServlet;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.teeda.core.application.TeedaStateManager;
import org.seasar.teeda.core.application.ViewHandlerImpl;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.core.util.ExternalContextUtil;
import org.seasar.teeda.core.util.PortletExternalContextUtil;
import org.seasar.teeda.core.util.PortletUtil;
import org.seasar.teeda.core.util.ServletExternalContextUtil;
import org.seasar.teeda.extension.component.RenderPreparableUtil;
import org.seasar.teeda.extension.exception.JspRuntimeException;
import org.seasar.teeda.extension.html.HtmlSuffix;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.PageDescCache;
import org.seasar.teeda.extension.html.PagePersistence;
import org.seasar.teeda.extension.html.TagProcessor;
import org.seasar.teeda.extension.html.TagProcessorCache;
import org.seasar.teeda.extension.jsp.PageContextImpl;

/**
 * @author higa
 * @author shot
 */
public class HtmlViewHandler extends ViewHandlerImpl {

    private TagProcessorCache tagProcessorCache;

    private PagePersistence pagePersistence;

    private HtmlSuffix htmlSuffix;

    private TeedaStateManager stateManager;

    private PageDescCache pageDescCache;

    public void setTagProcessorCache(TagProcessorCache tagProcessorCache) {
        this.tagProcessorCache = tagProcessorCache;
    }

    public void setPagePersistence(PagePersistence pagePersistence) {
        this.pagePersistence = pagePersistence;
    }

    /**
     * @param htmlSuffix The htmlSuffix to set.
     */
    public void setHtmlSuffix(HtmlSuffix htmlSuffix) {
        this.htmlSuffix = htmlSuffix;
    }

    /**
     * @param stateManager The stateManager to set.
     */
    public void setStateManager(TeedaStateManager stateManager) {
        this.stateManager = stateManager;
    }

    public UIViewRoot restoreView(FacesContext context, String viewId) {
        htmlSuffix.setupSuffix(context, viewId);
        setUpRequestForExternalBinding(context, viewId);
        tagProcessorCache.updateTagProcessor(viewId);
        return super.restoreView(context, viewId);
    }

    protected void setUpRequestForExternalBinding(FacesContext context,
            String viewId) {
        pagePersistence.restore(context, viewId);
    }

    public UIViewRoot createView(FacesContext context, String viewId) {
        UIViewRoot viewRoot = super.createView(context, viewId);
        TagProcessor processor = tagProcessorCache.getTagProcessor(viewId);
        if (processor == null) {
            return viewRoot;
        }
        final HttpServletRequest request = prepareRequest(context);
        final HttpServletResponse response = prepareResponse(context);
        try {
            PageContext pageContext = createPageContext(request, response);
            PageContextUtil.setCurrentFacesContextAttribute(pageContext,
                    context);
            PageContextUtil.setCurrentViewRootAttribute(pageContext, viewRoot);
            processor.composeComponentTree(context, pageContext, null);
            stateManager.saveViewToServer(context, viewRoot);
        } catch (JspException e) {
            throw new JspRuntimeException(e);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        return viewRoot;
    }

    public void renderView(FacesContext context, UIViewRoot viewRoot)
            throws IOException {
        RenderPreparableUtil.encodePrepareForComponent(context, viewRoot);
        ExternalContext externalContext = context.getExternalContext();
        String path = ExternalContextUtil.getViewId(externalContext);
        renderView(context, path);
    }

    protected void renderView(FacesContext context, String path)
            throws IOException {
        final HttpServletRequest request = prepareRequest(context);
        final HttpServletResponse response = prepareResponse(context);
        setNoCacheToResponse(response);
        PageContext pageContext = createPageContext(request, response);
        TagProcessor tagProcessor = tagProcessorCache.getTagProcessor(path);
        try {
            tagProcessor.process(pageContext, null);
        } catch (JspException ex) {
            throw new JspRuntimeException(ex);
        }
        PageDesc pageDesc = pageDescCache.getPageDesc(path);
        if (hasPageOrSubapplicationScope(pageDesc)) {
            final String pageName = pageDesc.getPageName();
            final Object component = DIContainerUtil.getComponent(pageName);
            final Map subApplicationScopeValues = ScopeValueHelper
                    .getOrCreateSubApplicationScopeValues(context);
            saveValueToScope(component, subApplicationScopeValues, pageDesc
                    .getSubapplicationScopePropertyNames());
            final Map pageScopeValues = ScopeValueHelper
                    .getOrCreatePageScopeValues(context);
            saveValueToScope(component, pageScopeValues, pageDesc
                    .getPageScopePropertyNames());
        }
        pageContext.getOut().flush();
    }

    private static boolean hasPageOrSubapplicationScope(PageDesc pageDesc) {
        if (pageDesc == null) {
            return false;
        }
        return (pageDesc.hasPageScopeProperty() || pageDesc
                .hasSubapplicationScopeProperty());
    }

    protected void saveValueToScope(Object component, Map scopeContext,
            String[] scopePropertyNames) {
        if (component == null) {
            return;
        }
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(component.getClass());
        for (int i = 0; i < scopePropertyNames.length; i++) {
            String propertyName = scopePropertyNames[i];
            if (beanDesc.hasPropertyDesc(propertyName)) {
                PropertyDesc propertyDesc = beanDesc
                        .getPropertyDesc(propertyName);
                scopeContext
                        .put(propertyName, propertyDesc.getValue(component));
            }
        }
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

    protected RenderKitFactory getRenderKitFactory() {
        return (RenderKitFactory) FactoryFinder
                .getFactory(FactoryFinder.RENDER_KIT_FACTORY);
    }

    protected HttpServletRequest prepareRequest(FacesContext context) {
        final ExternalContext externalContext = context.getExternalContext();
        // PortletSupport
        if (!PortletUtil.isPortlet(context)) {
            return ServletExternalContextUtil.getRequest(externalContext);
        } else {
            return PortletExternalContextUtil
                    .wrapByHttpServletRequestWrapper(externalContext);
        }
    }

    protected HttpServletResponse prepareResponse(FacesContext context) {
        final ExternalContext externalContext = context.getExternalContext();
        // PortletSupport
        if (!PortletUtil.isPortlet(context)) {
            return ServletExternalContextUtil.getResponse(externalContext);
        } else {
            return PortletExternalContextUtil
                    .wrapByHttpServletResponseWrapper(externalContext);
        }
    }

    protected void setNoCacheToResponse(HttpServletResponse res) {
        res.setHeader("Expires", "-1");
        res.setHeader("Pragma", "No-cache");
        res.setHeader("Cache-Control", "no-cache");
    }

    public PageDescCache getPageDescCache() {
        return pageDescCache;
    }

    public void setPageDescCache(PageDescCache pageDescCache) {
        this.pageDescCache = pageDescCache;
    }

}
