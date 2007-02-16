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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.FactoryFinder;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.PageContextOutWriter;
import javax.faces.internal.PageContextUtil;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.seasar.framework.container.servlet.S2ContainerServlet;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.teeda.core.application.TeedaStateManager;
import org.seasar.teeda.core.application.ViewHandlerImpl;
import org.seasar.teeda.core.util.ExternalContextUtil;
import org.seasar.teeda.core.util.PortletExternalContextUtil;
import org.seasar.teeda.core.util.PortletUtil;
import org.seasar.teeda.core.util.PostbackUtil;
import org.seasar.teeda.core.util.ServletExternalContextUtil;
import org.seasar.teeda.extension.component.RenderPreparableUtil;
import org.seasar.teeda.extension.exception.JspRuntimeException;
import org.seasar.teeda.extension.html.HtmlComponentInvoker;
import org.seasar.teeda.extension.html.HtmlSuffix;
import org.seasar.teeda.extension.html.PagePersistence;
import org.seasar.teeda.extension.html.TagProcessor;
import org.seasar.teeda.extension.html.TagProcessorCache;
import org.seasar.teeda.extension.jsp.PageContextImpl;

/**
 * @author higa
 * @author shot
 */
public class HtmlViewHandler extends ViewHandlerImpl {

    private static final String INITIALIZE = "initialize";

    private static final String PRERENDER = "prerender";

    private TagProcessorCache tagProcessorCache;

    private PagePersistence pagePersistence;

    private HtmlSuffix htmlSuffix;

    private TeedaStateManager stateManager;

    private HtmlComponentInvoker htmlComponentInvoker;

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

    /**
     * @param htmlComponentInvoker The htmlComponentInvoker to set.
     */
    public void setHtmlComponentInvoker(
            HtmlComponentInvoker htmlComponentInvoker) {
        this.htmlComponentInvoker = htmlComponentInvoker;
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
            context.setViewRoot(viewRoot);
            stateManager.saveViewToServer(context);
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
        setupResponseWriter(pageContext, null, request.getCharacterEncoding());
        ExternalContext externalContext = context.getExternalContext();
        Map requestMap = externalContext.getRequestMap();
        if (!PostbackUtil.isPostback(requestMap)) {
            invoke(context, path, INITIALIZE);
        }
        if (context.getResponseComplete()) {
            return;
        }
        invoke(context, path, PRERENDER);
        if (context.getResponseComplete()) {
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

    protected String invoke(FacesContext context, String path, String methodName) {
        String componentName = htmlComponentInvoker.getComponentName(path,
                methodName);
        return htmlComponentInvoker.invoke(context, componentName, methodName);
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

    protected HttpServletRequest prepareRequest(FacesContext context) {
        final ExternalContext externalContext = context.getExternalContext();
        if (!PortletUtil.isPortlet(context)) {
            return ServletExternalContextUtil.getRequest(externalContext);
        } else {
            return PortletExternalContextUtil
                    .wrapByHttpServletRequestWrapper(externalContext);
        }
    }

    protected HttpServletResponse prepareResponse(FacesContext context) {
        final ExternalContext externalContext = context.getExternalContext();
        if (!PortletUtil.isPortlet(context)) {
            return ServletExternalContextUtil.getResponse(externalContext);
        } else {
            return PortletExternalContextUtil
                    .wrapByHttpServletResponseWrapper(externalContext);
        }
    }

    protected void setNoCacheToResponse(HttpServletResponse res) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "E, dd MMM yyyy hh:mm:ss zzz", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        String httpDate = formatter.format(new Date());
        res.setHeader("Expires", httpDate);
        res.setHeader("Pragma", "no-cache");
        res.setHeader("Cache-Control", "no-cache");
    }

}
