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
package org.seasar.teeda.core.application;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.internal.AssertionUtil;
import javax.faces.render.RenderKitFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.config.webapp.element.ServletMappingElement;
import org.seasar.teeda.core.config.webapp.element.WebappConfig;
import org.seasar.teeda.core.util.ExternalContextUtil;
import org.seasar.teeda.core.util.ServletExternalContextUtil;
import org.seasar.teeda.core.util.StateManagerUtil;
import org.seasar.teeda.core.util.WebappConfigUtil;
import org.seasar.teeda.core.view.ViewRenderer;

/**
 * @author higa
 * @author shot
 */
public class ViewHandlerImpl extends ViewHandler {

    private ViewRenderer viewRenderer_;

    public ViewHandlerImpl() {
    }

    public Locale calculateLocale(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        Locale supportedLocale = getLocaleFromSupportedLocales(context);
        if (supportedLocale != null) {
            return supportedLocale;
        }
        Locale defaultLocale = getLocaleFromDefaultLocale(context);
        if (defaultLocale != null) {
            return defaultLocale;
        }
        return Locale.getDefault();
    }

    protected Locale getLocaleFromSupportedLocales(FacesContext context) {
        Application app = context.getApplication();
        for (Iterator locales = context.getExternalContext()
                .getRequestLocales(); locales.hasNext();) {
            Locale locale = (Locale) locales.next();
            for (Iterator supportedLocales = app.getSupportedLocales(); supportedLocales
                    .hasNext();) {
                Locale supportedLocale = (Locale) supportedLocales.next();
                if (isMatchLocale(locale, supportedLocale)) {
                    return supportedLocale;
                }
            }
        }
        return null;
    }

    protected Locale getLocaleFromDefaultLocale(FacesContext context) {
        Locale defaultLocale = context.getApplication().getDefaultLocale();
        for (Iterator locales = context.getExternalContext()
                .getRequestLocales(); locales.hasNext();) {
            Locale reqLocale = (Locale) locales.next();
            if (isMatchLocale(reqLocale, defaultLocale)) {
                return defaultLocale;
            }
        }
        return null;
    }

    protected boolean isMatchLocale(Locale reqLocale, Locale jsfLocale) {
        if (reqLocale.equals(jsfLocale)) {
            return true;
        }
        return reqLocale.getLanguage().equals(jsfLocale.getLanguage())
                && StringUtil.isEmpty(jsfLocale.getCountry());
    }

    public String calculateRenderKitId(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        String renderKitId = context.getApplication().getDefaultRenderKitId();
        if (renderKitId == null) {
            renderKitId = RenderKitFactory.HTML_BASIC_RENDER_KIT;
        }
        return renderKitId;
    }

    public UIViewRoot createView(FacesContext context, String viewId) {
        AssertionUtil.assertNotNull("context", context);
        Locale locale = null;
        String renderKitId = null;
        UIViewRoot viewRoot = context.getViewRoot();
        if (viewRoot != null) {
            locale = viewRoot.getLocale();
            renderKitId = viewRoot.getRenderKitId();
        } else {
            locale = calculateLocale(context);
            renderKitId = calculateRenderKitId(context);
        }
        viewRoot = (UIViewRoot) context.getApplication().createComponent(
                UIViewRoot.COMPONENT_TYPE);
        viewRoot.setViewId(viewId);
        viewRoot.setLocale(locale);
        viewRoot.setRenderKitId(renderKitId);
        return viewRoot;
    }

    public String getActionURL(FacesContext context, String viewId) {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("viewId", viewId);
        String path = getViewIdPath(context, viewId);
        if (path != null && path.startsWith("/")) {
            return context.getExternalContext().getRequestContextPath() + path;
        } else {
            return path;
        }
    }

    public String getResourceURL(FacesContext context, String path) {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("path", path);
        if (path.startsWith("/")) {
            return context.getExternalContext().getRequestContextPath() + path;
        } else {
            return path;
        }
    }

    public void renderView(FacesContext context, UIViewRoot viewRoot)
            throws IOException, FacesException {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("viewRoot", viewRoot);
        ExternalContext externalContext = context.getExternalContext();
//        String path = ExternalContextUtil.getViewId(externalContext);
//        String viewId = viewRoot.getViewId();
//        if (path.equals(viewId)) {
//            HttpServletRequest request = ServletExternalContextUtil
//                    .getRequest(externalContext);
//            HttpServletResponse response = ServletExternalContextUtil
//                    .getResponse(externalContext);
//            getViewRenderer().renderView(path, request, response);
//        } else {
            externalContext.dispatch(viewRoot.getViewId());
//        }
    }

    public UIViewRoot restoreView(FacesContext context, String viewId) {
        AssertionUtil.assertNotNull("context", context);
        Application app = context.getApplication();
        String renderKitId = calculateRenderKitId(context);
        StateManager stateManager = app.getStateManager();
        return stateManager.restoreView(context, viewId, renderKitId);
    }

    public void writeState(FacesContext context) throws IOException {
        AssertionUtil.assertNotNull("context", context);
        if (StateManagerUtil.isSavingStateClient(context)) {
            context.getResponseWriter().writeText(JsfConstants.STATE_MARKER,
                    null);
        }
    }

    public ViewRenderer getViewRenderer() {
        return viewRenderer_;
    }

    public void setViewRenderer(ViewRenderer viewRenderer) {
        viewRenderer_ = viewRenderer;
    }

    protected String getViewIdPath(FacesContext context, String viewId) {
        if (!viewId.startsWith("/")) {
            throw new IllegalArgumentException();
        }
        // PortletSupport

        WebappConfig webappConfig = getWebappConfig(context);
        String urlPattern = getUrlPattern(webappConfig, context);
        if (urlPattern != null) {
            if (urlPattern.startsWith("*.")) {
                urlPattern = urlPattern.substring(1, urlPattern.length());
                if (viewId.endsWith(urlPattern)) {
                    return viewId;
                } else {
                    int index = viewId.lastIndexOf(".");
                    if (index >= 0) {
                        return viewId.substring(0, index) + urlPattern;
                    } else {
                        return viewId + urlPattern;
                    }
                }
            } else {
                if (urlPattern.endsWith("/*")) {
                    urlPattern = urlPattern.substring(0,
                            urlPattern.length() - 2);
                }
                return urlPattern + viewId;
            }
        } else {
            return viewId;
        }
    }

    protected String getUrlPattern(WebappConfig webappConfig,
            FacesContext context) {
        String servletPath = context.getExternalContext()
                .getRequestServletPath();
        String pathInfo = context.getExternalContext().getRequestPathInfo();
        List servletMappings = webappConfig.getServletMappingElement();
        for (Iterator itr = servletMappings.iterator(); itr.hasNext();) {
            ServletMappingElement servletMapping = (ServletMappingElement) itr
                    .next();
            String urlPattern = servletMapping.getUrlPattern();
            if ((isExtensionMapping(urlPattern) && pathInfo == null)
                    || (!isExtensionMapping(urlPattern) && pathInfo != null)) {
                if(isExtensionMapping(urlPattern)) {
                    String extension = urlPattern.substring(1, urlPattern.length());
                    if (servletPath.endsWith(extension)
                            || servletPath.equals(urlPattern)) {
                        return urlPattern;
                    }
                } else {
                    urlPattern = urlPattern.substring(0, urlPattern.length() - 2);
                    if (servletPath.equals(urlPattern)) {
                        return urlPattern;
                    }
                }
            }
        }
        return null;
    }

    protected WebappConfig getWebappConfig(FacesContext context) {
        return WebappConfigUtil.getWebappConfig(context);
    }

    private static boolean isExtensionMapping(String urlPattern) {
        return (urlPattern != null) && (urlPattern.startsWith("*."));
    }
}
