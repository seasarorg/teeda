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
package javax.faces.webapp;

import java.io.IOException;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.event.PhaseId;
import javax.faces.internal.PhaseUtil;
import javax.faces.internal.WebAppUtil;
import javax.faces.internal.WindowIdUtil;
import javax.faces.internal.scope.PageScope;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shot
 */
public final class FacesServlet implements Servlet {

    public static final String CONFIG_FILES_ATTR = "javax.faces.CONFIG_FILES";

    public static final String LIFECYCLE_ID_ATTR = "javax.faces.LIFECYCLE_ID";

    private ServletConfig config = null;

    private FacesContextFactory facesContextFactory = null;

    private Lifecycle lifecycle = null;

    public void destroy() {
        config = null;
        facesContextFactory = null;
        lifecycle = null;
    }

    public ServletConfig getServletConfig() {
        return config;
    }

    public String getServletInfo() {
        return "Teeda - A JSF implementation with DI x AOP by Seasar Foundation -";
    }

    public void init(ServletConfig config) throws ServletException {
        this.config = config;
        facesContextFactory = (FacesContextFactory) WebAppUtil
                .getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
        LifecycleFactory lifecycleFactory = (LifecycleFactory) WebAppUtil
                .getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        String lifecycleId = WebAppUtil.getLifecycleId(config);
        lifecycle = lifecycleFactory.getLifecycle(lifecycleId);
    }

    public void service(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {
        PhaseUtil.setCurrentPhase(PhaseId.ANY_PHASE);
        final ServletContext servletContext = config.getServletContext();
        final FacesContext context = facesContextFactory.getFacesContext(
                servletContext, request, response, lifecycle);

        final ExternalContext externalContext = context.getExternalContext();
        final String pathInfo = externalContext.getRequestPathInfo();
        if (pathInfo != null &&
                (pathInfo.startsWith("/WEB-INF") || pathInfo
                        .startsWith("/META-INF"))) {
            final HttpServletResponse hres = (HttpServletResponse) response;
            hres.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try {
            WindowIdUtil.setupWindowId(externalContext);
            final Map pageScope = PageScope.getOrCreateContext(context);
            synchronized (pageScope) {
                lifecycle.execute(context);
                lifecycle.render(context);
            }
        } catch (FacesException e) {
            Throwable t = e.getCause();
            if (t == null) {
                throw new ServletException(e.getMessage(), e);
            } else {
                if (t instanceof ServletException) {
                    throw ((ServletException) t);
                } else if (t instanceof IOException) {
                    throw ((IOException) t);
                } else {
                    throw new ServletException(t.getMessage(), t);
                }
            }
        } finally {
            context.release();
            PhaseUtil.clearPhase();
        }
    }

}
