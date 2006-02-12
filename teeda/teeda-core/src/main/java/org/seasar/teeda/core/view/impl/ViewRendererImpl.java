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
package org.seasar.teeda.core.view.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.container.servlet.S2ContainerServlet;
import org.seasar.teeda.core.config.taglib.TagPool;
import org.seasar.teeda.core.util.ExternalContextUtil;
import org.seasar.teeda.core.view.ErrorPageManager;
import org.seasar.teeda.core.view.JsfConfig;
import org.seasar.teeda.core.view.JsfContext;
import org.seasar.teeda.core.view.ViewRenderer;
import org.seasar.teeda.core.view.ViewTemplate;
import org.seasar.teeda.core.view.ViewTemplateFactory;

/**
 * @author higa
 * 
 */
public class ViewRendererImpl implements ViewRenderer {

    private JsfConfig jsfConfig;

    private ViewTemplateFactory viewTemplateFactory;

    private TagPool tagPool;

    private ErrorPageManager errorPageManager;

    public ViewRendererImpl(JsfConfig jsfConfig,
            ViewTemplateFactory viewTemplateFactory, TagPool tagPool) {

        this.jsfConfig = jsfConfig;
        this.viewTemplateFactory = viewTemplateFactory;
        this.tagPool = tagPool;
    }

    public void renderView(String path, HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        ViewTemplate template = viewTemplateFactory.getViewTemplate(path);
        ViewProcessor viewProcessor = (ViewProcessor) template
                .getRootTagProcessor();
        String initAction = viewProcessor.getInitAction();
        FacesContext context = FacesContext.getCurrentInstance();
        boolean processed = false;
        if (initAction != null) {
            processed = executeInitAction(context, initAction);
        }
        if (!processed) {
            setupParams(context);
            // response.setCharacterEncoding(viewProcessor.getEncoding());
            response.setContentType(viewProcessor.getContentType());
            JsfContext jsfContext = createJsfContext(request, response);
            setupResponseWriter(jsfContext.getPageContext(), viewProcessor
                    .getContentType(), viewProcessor.getEncoding());
            try {
                viewProcessor.process(jsfContext, null);
            } catch (JspException ex) {
                throw new JspRuntimeException(ex);
            }
            jsfContext.getPageContext().getOut().flush();
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

    protected JsfContext createJsfContext(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        PageContextImpl pageContext = new PageContextImpl();
        pageContext.initialize(getServlet(), request, response, null);
        return new JsfContextImpl(pageContext, jsfConfig, tagPool);
    }

    protected void setupResponseWriter(PageContext pageContext,
            String contentType, String encoding) {
        FacesContext context = FacesContext.getCurrentInstance();
        RenderKitFactory renderFactory = getRenderKitFactory();
        RenderKit renderKit = renderFactory.getRenderKit(context, context
                .getViewRoot().getRenderKitId());
        ResponseWriter writer = renderKit.createResponseWriter(
                new PageContextWriter(pageContext), contentType, encoding);
        context.setResponseWriter(writer);
    }

    protected RenderKitFactory getRenderKitFactory() {
        return (RenderKitFactory) FactoryFinder
                .getFactory(FactoryFinder.RENDER_KIT_FACTORY);
    }

    protected boolean executeInitAction(FacesContext context, String initAction)
            throws IOException {
        Application app = context.getApplication();
        MethodBinding mb = app.createMethodBinding(initAction, null);
        try {
            String outcome = InvokeUtil.invoke(mb, context);
            if (outcome == null || context.getResponseComplete()) {
                return false;
            }
            NavigationHandler nh = app.getNavigationHandler();
            nh.handleNavigation(context, initAction, outcome);
            if (context.getResponseComplete()) {
                return true;
            }
            ViewHandler vh = app.getViewHandler();
            vh.renderView(context, context.getViewRoot());
            return true;
        } catch (EvaluationException ex) {
            Throwable cause = ex.getCause();
            ExternalContext extContext = context.getExternalContext();
            ErrorPageManager manager = getErrorPageManager();
            if (manager.handleException(cause, extContext)) {
                return true;
            }
            throw ex;
        }
    }

    protected ErrorPageManager getErrorPageManager() {
        if (errorPageManager != null) {
            return errorPageManager;
        }
        S2Container container = SingletonS2ContainerFactory.getContainer();
        errorPageManager = (ErrorPageManager) container
                .getComponent(ErrorPageManager.class);
        return errorPageManager;
    }

    protected void setupParams(FacesContext context) {
        ExternalContext externalContext = context.getExternalContext();
        String viewId = ExternalContextUtil.getViewId(externalContext);
        S2Container container = SingletonS2ContainerFactory.getContainer();
        ServletRequest request = container.getRequest();
        JsfConfig jsfConfig = (JsfConfig) container
                .getComponent(JsfConfig.class);
        ViewTemplateFactory viewTemplateFactory = (ViewTemplateFactory) container
                .getComponent(ViewTemplateFactory.class);
        ViewTemplate viewTemplate = viewTemplateFactory.getViewTemplate(viewId);
        ViewProcessor viewProcessor = (ViewProcessor) viewTemplate
                .getRootTagProcessor();
        Map params = new HashMap();
        viewProcessor.setupParams(jsfConfig, params);
        for (Iterator i = params.keySet().iterator(); i.hasNext();) {
            String key = (String) i.next();
            Object value = params.get(key);
            request.setAttribute(key, value);
        }
    }
}