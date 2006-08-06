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
package org.seasar.teeda.core.context.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.framework.container.external.servlet.CookieMap;
import org.seasar.framework.container.external.servlet.HttpSessionMap;
import org.seasar.framework.container.external.servlet.ServletApplicationMap;
import org.seasar.framework.container.external.servlet.ServletInitParameterMap;
import org.seasar.framework.container.external.servlet.ServletRequestHeaderMap;
import org.seasar.framework.container.external.servlet.ServletRequestHeaderValuesMap;
import org.seasar.framework.container.external.servlet.ServletRequestMap;
import org.seasar.framework.container.external.servlet.ServletRequestParameterMap;
import org.seasar.framework.container.external.servlet.ServletRequestParameterValuesMap;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.core.context.Releaseable;
import org.seasar.teeda.core.scope.impl.DispatchScopeFactory;
import org.seasar.teeda.core.util.ServletExternalContextUtil;

/**
 * @author shot
 */
public class ServletExternalContextImpl extends ExternalContext implements
        Releaseable {

    private static Logger logger = Logger
            .getLogger(ServletExternalContextImpl.class);

    private ServletContext context;

    private ServletRequest request;

    private ServletResponse response;

    private String servletPath = null;

    private String pathInfo = null;

    private boolean isHttpServletRequest = false;

    private boolean isHttpServletResponse = false;

    private Map applicationMap = null;

    private Map initParameterMap = null;

    private Map sessionMap = null;

    private Map requestCookieMap = null;

    private Map requestParameterMap = null;

    private Map requestParameterValuesMap = null;

    private Map requestMap = null;

    private Map requestHeaderMap = null;

    private Map requestHeaderValuesMap = null;

    public ServletExternalContextImpl(final ServletContext context,
            final ServletRequest request, final ServletResponse response) {
        this.context = context;
        this.request = request;
        this.response = response;
        this.isHttpServletRequest = ServletExternalContextUtil
                .isHttpServletRequest(this.request);
        this.isHttpServletResponse = ServletExternalContextUtil
                .isHttpServletResponse(this.response);
        ServletExternalContextUtil.setCharacterEncoding(this.request);
        if (isHttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            this.servletPath = httpServletRequest.getServletPath();
            this.pathInfo = httpServletRequest.getPathInfo();
        }
    }

    public void dispatch(String path) throws IOException {
        try {
            ServletExternalContextUtil.dispatch(path, request, response);
        } finally {
            DispatchScopeFactory.destroy();
        }
    }

    public String encodeActionURL(String url) {
        AssertionUtil.assertNotNull("url is null.", url);
        assertHttpServletResponse();
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        return httpResponse.encodeURL(url);
    }

    public String encodeNamespace(final String name) {
        AssertionUtil.assertNotNull("name is null.", name);
        return name;
    }

    public String encodeResourceURL(String url) {
        AssertionUtil.assertNotNull("url is null.", url);
        assertHttpServletResponse();
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        return httpResponse.encodeURL(url);
    }

    public Map getApplicationMap() {
        if (applicationMap == null) {
            applicationMap = new ServletApplicationMap(context);
        }
        return applicationMap;
    }

    public String getAuthType() {
        assertHttpServletRequest();
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        return httpServletRequest.getAuthType();
    }

    public Object getContext() {
        return context;
    }

    public String getInitParameter(String name) {
        return context.getInitParameter(name);
    }

    public Map getInitParameterMap() {
        if (initParameterMap == null) {
            initParameterMap = new ServletInitParameterMap(context);
        }
        return initParameterMap;
    }

    public String getRemoteUser() {
        assertHttpServletRequest();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        return httpRequest.getRemoteUser();
    }

    public Object getRequest() {
        return request;
    }

    public String getRequestContextPath() {
        assertHttpServletRequest();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        return httpRequest.getContextPath();
    }

    public Map getRequestCookieMap() {
        assertHttpServletRequest();
        if (requestCookieMap == null) {
            requestCookieMap = new CookieMap((HttpServletRequest) request);
        }
        return requestCookieMap;
    }

    public Map getRequestHeaderMap() {
        assertHttpServletRequest();
        if (requestHeaderMap == null) {
            requestHeaderMap = new ServletRequestHeaderMap(
                    (HttpServletRequest) request);
        }
        return requestHeaderMap;
    }

    public Map getRequestHeaderValuesMap() {
        if (requestHeaderValuesMap == null) {
            requestHeaderValuesMap = new ServletRequestHeaderValuesMap(
                    (HttpServletRequest) request);
        }
        return requestHeaderValuesMap;
    }

    public Locale getRequestLocale() {
        return request.getLocale();
    }

    public Iterator getRequestLocales() {
        final Enumeration locales = request.getLocales();
        return ServletExternalContextUtil.getLocales(locales);
    }

    public Map getRequestMap() {
        if (requestMap == null) {
            requestMap = new ServletRequestMap(request);
        }
        return requestMap;
    }

    public Map getRequestParameterMap() {
        if (requestParameterMap == null) {
            requestParameterMap = new ServletRequestParameterMap(request);
        }
        return requestParameterMap;
    }

    public Iterator getRequestParameterNames() {
        final Enumeration paramNames = request.getParameterNames();
        return ServletExternalContextUtil.getRequestParameterNames(paramNames);
    }

    public Map getRequestParameterValuesMap() {
        if (requestParameterValuesMap == null) {
            requestParameterValuesMap = new ServletRequestParameterValuesMap(
                    request);
        }
        return requestParameterValuesMap;
    }

    public String getRequestPathInfo() {
        return pathInfo;
    }

    public String getRequestServletPath() {
        return servletPath;
    }

    public URL getResource(String path) throws MalformedURLException {
        return context.getResource(path);
    }

    public InputStream getResourceAsStream(String path) {
        return context.getResourceAsStream(path);
    }

    public Set getResourcePaths(String path) {
        return context.getResourcePaths(path);
    }

    public Object getResponse() {
        return response;
    }

    public Object getSession(boolean create) {
        assertHttpServletRequest();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        return httpRequest.getSession(create);
    }

    public Map getSessionMap() {
        if (sessionMap == null) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            sessionMap = new HttpSessionMap(httpRequest);
        }
        return sessionMap;
    }

    public Principal getUserPrincipal() {
        assertHttpServletRequest();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        return httpRequest.getUserPrincipal();
    }

    public boolean isUserInRole(String role) {
        assertHttpServletRequest();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        return httpRequest.isUserInRole(role);
    }

    public void log(String message) {
        AssertionUtil.assertNotNull("message is null.", message);
        context.log(message);
        logger.debug(message);
    }

    public void log(String message, Throwable exception) {
        AssertionUtil.assertNotNull("message", message);
        AssertionUtil.assertNotNull("exception", exception);
        context.log(message, exception);
        logger.log(exception);
    }

    public void redirect(String url) throws IOException {
        assertHttpServletResponse();
        ServletExternalContextUtil.redirect(url, response);
    }

    private void assertHttpServletRequest() {
        if (!isHttpServletRequest) {
            throw new IllegalStateException();
        }
    }

    private void assertHttpServletResponse() {
        if (!isHttpServletResponse) {
            throw new IllegalStateException();
        }
    }

    public void release() {
        this.context = null;
        this.request = null;
        this.response = null;
        this.servletPath = null;
        this.pathInfo = null;
        this.applicationMap = null;
        this.initParameterMap = null;
        this.sessionMap = null;
        this.requestCookieMap = null;
        this.requestParameterMap = null;
        this.requestParameterValuesMap = null;
        this.requestMap = null;
        this.requestHeaderMap = null;
        this.requestHeaderValuesMap = null;
    }

}
