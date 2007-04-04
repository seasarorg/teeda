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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import javax.faces.application.ViewHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.internal.scope.RedirectScope;
import javax.servlet.ServletContext;

import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.application.NavigationHandlerImpl;
import org.seasar.teeda.core.util.NavigationHandlerUtil;
import org.seasar.teeda.core.util.PortletUtil;
import org.seasar.teeda.core.util.ServletContextUtil;
import org.seasar.teeda.extension.html.HtmlSuffix;
import org.seasar.teeda.extension.html.PagePersistence;

/**
 * @author higa
 *
 */
public class HtmlNavigationHandler extends NavigationHandlerImpl {

    private PagePersistence pagePersistence;

    private NamingConvention namingConvention;

    private ServletContext servletContext;

    private HtmlSuffix htmlSuffix;

    public void setNamingConvention(NamingConvention namingConvention) {
        this.namingConvention = namingConvention;
    }

    public void setPagePersistence(PagePersistence pagePersistence) {
        this.pagePersistence = pagePersistence;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * @param htmlSuffix The htmlSuffix to set.
     */
    public void setHtmlSuffix(HtmlSuffix htmlSuffix) {
        this.htmlSuffix = htmlSuffix;
    }

    protected void redirect(FacesContext context,
            ExternalContext externalContext, String redirectPath,
            String newViewId) {
        pagePersistence.save(context, newViewId);
        NavigationHandlerUtil.assertNotAlreadyRedirect(context);
        super.redirect(context, externalContext, redirectPath, newViewId);
    }

    public void handleNavigation(FacesContext context, String fromAction,
            String outcome) {
        super.handleNavigation(context, fromAction, outcome);
        if (context.getResponseComplete()) {
            return;
        }
        final ExternalContext externalContext = context.getExternalContext();
        String viewId = context.getViewRoot().getViewId();
        String path = calcPathFromOutcome(context, viewId, outcome);
        if (path == null) {
            return;
        }
        ViewHandler viewHandler = context.getApplication().getViewHandler();
        // PortletSupport
        if (!PortletUtil.isPortlet(context)) {
            String redirectPath = getRedirectActionPath(context, viewHandler,
                    path);
            redirect(context, externalContext, redirectPath, path);
        } else {
            RedirectScope.setRedirectingPath(context, path);
            pagePersistence.save(context, path);
            if (PortletUtil.isRender(context)) {
                //set dummy output stream
                context.setResponseWriter(context.getResponseWriter()
                        .cloneWithWriter(
                                new OutputStreamWriter(
                                        new ByteArrayOutputStream())));
            }
            context.responseComplete();
            context.renderResponse();
        }
    }

    protected String calcPathFromOutcome(FacesContext context, String viewId,
            String outcome) {
        if (outcome == null) {
            return null;
        }
        if (!namingConvention.isValidViewRootPath(viewId)) {
            return viewId;
        }
        int pos = viewId.lastIndexOf('/');
        int pos2 = viewId.lastIndexOf('.');
        String pathFirst = viewId.substring(0, pos + 1);
        String pathLast = viewId.substring(pos2);
        String[] names = StringUtil.split(outcome, "_");
        String suffix = htmlSuffix.getSuffix(context);
        if (names.length == 1) {
            String path = pathFirst + outcome + suffix + pathLast;
            InputStream is = ServletContextUtil.getResourceAsStream(
                    servletContext, path);
            if (is != null) {
                InputStreamUtil.close(is);
                return path;
            }
            return pathFirst + outcome + pathLast;
        }
        StringBuffer buf = new StringBuffer(100);
        for (int i = 0; i < names.length; i++) {
            buf.append(StringUtil.decapitalize(names[i]));
            if (i != names.length - 1) {
                buf.append("/");
            }
        }
        pos = viewId.indexOf('/', 1);
        pathFirst = viewId.substring(0, pos + 1);
        String path = pathFirst + buf + suffix + pathLast;
        if (servletContext.getResourceAsStream(path) != null) {
            return path;
        }
        return pathFirst + buf + pathLast;
    }
}
