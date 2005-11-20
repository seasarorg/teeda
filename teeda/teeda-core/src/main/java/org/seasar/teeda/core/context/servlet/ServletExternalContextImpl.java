/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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

import org.seasar.framework.log.Logger;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public class ServletExternalContextImpl extends ExternalContext {

    private static Logger logger_ = Logger.getLogger(ServletExternalContextImpl.class);
    
    private final ServletContext context_;
    private final ServletRequest request_;
    private final ServletResponse response_;
    private String servletPath_ = null;
    private String pathInfo_ = null;
    private boolean isHttpServletRequest_ = false;
    private boolean isHttpServletResponse_ = false;
    private Map applicationMap_ = null;
    private Map initParameterMap_ = null;
    private Map sessionMap_ = null;
    private Map requestCookieMap_ = null;
    private Map requestParameterMap_ = null;
    private Map requestParameterValuesMap_ = null;
    private Map requestMap_ = null;
    private Map requestHeaderMap_ = null;
    private Map requestHeaderValuesMap_ = null;
    
    public ServletExternalContextImpl(final ServletContext context,
            final ServletRequest request, final ServletResponse response){
        context_ = context;
        request_ = request;
        response_ = response;
        isHttpServletRequest_ = ServletExternalContextUtil.isHttpServletRequest(request_);
        isHttpServletResponse_ = ServletExternalContextUtil.isHttpServletResponse(response_);
        ServletExternalContextUtil.setCharacterEncoding(request_);
        if(isHttpServletRequest_){
            HttpServletRequest httpRequest = (HttpServletRequest)request_;
            servletPath_ = httpRequest.getServletPath();
            pathInfo_ = httpRequest.getPathInfo();
        }
    }
    
    public void dispatch(String path) throws IOException {
        ServletExternalContextUtil.dispatch(path, request_, response_);
    }

    public String encodeActionURL(String url) {
        if(url == null){
            throw new NullPointerException();
        }
        if(!isHttpServletResponse_){
            throw new IllegalStateException();
        }
        HttpServletResponse httpResponse = (HttpServletResponse)response_;
        return httpResponse.encodeURL(url);
    }

    public String encodeNamespace(final String name) {
        if(name == null){
            throw new NullPointerException();
        }
        return name;
    }

    public String encodeResourceURL(String url) {
        if(url == null){
            throw new NullPointerException();
        }
        if(!isHttpServletResponse_){
            throw new IllegalStateException();
        }
        HttpServletResponse httpResponse = (HttpServletResponse)response_;
        return httpResponse.encodeURL(url);
    }

    public Map getApplicationMap() {
        if(applicationMap_ == null){
            applicationMap_ = new ServletApplicationMap(context_); 
        }
        return applicationMap_;
    }

    public String getAuthType() {
        if(isHttpServletRequest_){
            throw new IllegalStateException();
        }
        HttpServletRequest request = (HttpServletRequest)request_;
        return request.getAuthType();
    }

    public Object getContext() {
        return context_;
    }

    public String getInitParameter(String name) {
        return context_.getInitParameter(name);
    }

    public Map getInitParameterMap() {
        if(initParameterMap_ == null){
            initParameterMap_ = new ServletInitParameterMap(context_);
        }
        return initParameterMap_;
    }

    public String getRemoteUser() {
        if(!isHttpServletRequest_){
            throw new IllegalStateException();
        }
        HttpServletRequest httpRequest = (HttpServletRequest)request_;
        return httpRequest.getRemoteUser();
    }

    public Object getRequest() {
        return request_;
    }

    public String getRequestContextPath() {
        if(!isHttpServletRequest_){
            throw new IllegalStateException();
        }
        HttpServletRequest httpRequest = (HttpServletRequest)request_;
        return httpRequest.getContextPath();
    }

    public Map getRequestCookieMap() {
        if(!isHttpServletRequest_){
            throw new IllegalStateException();
        }
        if(requestCookieMap_ == null){
            requestCookieMap_ = new CookieMap((HttpServletRequest)request_);
        }
        return requestCookieMap_;
    }

    public Map getRequestHeaderMap() {
        if(!isHttpServletRequest_){
            throw new IllegalStateException();
        }
        if(requestHeaderMap_ == null){
            requestHeaderMap_ = 
                new ServletRequestHeaderMap((HttpServletRequest)request_);
        }
        return requestHeaderMap_;
    }

    public Map getRequestHeaderValuesMap() {
        if(requestHeaderValuesMap_ == null){
            requestHeaderValuesMap_ = 
                new ServletRequestHeaderValuesMap((HttpServletRequest)request_);
        }
        return requestHeaderValuesMap_;
    }

    public Locale getRequestLocale() {
        return request_.getLocale();
    }

    public Iterator getRequestLocales() {
        final Enumeration locales = request_.getLocales();
        return ServletExternalContextUtil.getLocales(locales);
    }

    public Map getRequestMap() {
        if(requestMap_ == null){
            requestMap_ = new ServletRequestMap(request_);
        }
        return requestMap_;
    }

    public Map getRequestParameterMap() {
        if(requestParameterMap_ == null){
            requestParameterMap_ = new ServletRequestParameterMap(request_);
        }
        return requestParameterMap_;
    }

    public Iterator getRequestParameterNames() {
        final Enumeration paramNames = request_.getParameterNames();
        return ServletExternalContextUtil.getRequestParameterNames(paramNames);
    }

    public Map getRequestParameterValuesMap() {
        if(requestParameterValuesMap_ == null){
            requestParameterValuesMap_ = new ServletRequestParameterValuesMap(request_); 
        }
        return requestParameterValuesMap_;
    }

    public String getRequestPathInfo() {
        return pathInfo_;
    }

    public String getRequestServletPath() {
        return servletPath_;
    }

    public URL getResource(String path) throws MalformedURLException {
        return context_.getResource(path);
    }

    public InputStream getResourceAsStream(String path) {
        return context_.getResourceAsStream(path);
    }

    public Set getResourcePaths(String path) {
        return context_.getResourcePaths(path);
    }

    public Object getResponse() {
        return response_;
    }

    public Object getSession(boolean create) {
        if(!isHttpServletRequest_){
            throw new IllegalStateException("Request should be supported session.");
        }
        HttpServletRequest httpRequest = (HttpServletRequest)request_;
        return httpRequest.getSession(create);
    }

    public Map getSessionMap() {
        if(sessionMap_ == null){
            HttpServletRequest httpRequest = (HttpServletRequest)request_;
            sessionMap_ = new HttpSessionMap(httpRequest);
        }
        return sessionMap_;
    }

    public Principal getUserPrincipal() {
        if(!isHttpServletRequest_){
            throw new IllegalStateException();
        }
        HttpServletRequest httpRequest = (HttpServletRequest)request_;
        return httpRequest.getUserPrincipal();
    }

    public boolean isUserInRole(String role) {
        if(!isHttpServletRequest_){
            throw new IllegalStateException();
        }
        HttpServletRequest httpRequest = (HttpServletRequest)request_;
        return httpRequest.isUserInRole(role);
    }

    public void log(String message) {
        if(message == null){
            throw new NullPointerException();
        }
        context_.log(message);
        logger_.debug(message);
    }

    public void log(String message, Throwable exception) {
        if(message == null || exception == null){
            throw new NullPointerException();
        }
        context_.log(message, exception);
        logger_.log(exception);
    }

    public void redirect(String url) throws IOException {
        if(!isHttpServletResponse_){
            throw new IllegalStateException("Redirect is only for HttpServletResponse");
        }
        ServletExternalContextUtil.redirect(url, response_);
    }

}
