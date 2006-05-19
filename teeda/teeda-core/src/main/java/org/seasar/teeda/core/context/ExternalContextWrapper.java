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
package org.seasar.teeda.core.context;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;

/**
 * @author shot
 */
public class ExternalContextWrapper extends ExternalContext implements
        Serializable {

    private static final long serialVersionUID = 1L;

    private ExternalContext externalContext;

    public ExternalContextWrapper() {
    }
    
    public ExternalContextWrapper(ExternalContext externalContext) {
        this.externalContext = externalContext;
    }

    public void dispatch(String path) throws IOException {
        externalContext.dispatch(path);
    }

    public String encodeActionURL(String url) {
        return externalContext.encodeActionURL(url);
    }

    public String encodeNamespace(String name) {
        return externalContext.encodeNamespace(name);
    }

    public String encodeResourceURL(String url) {
        return externalContext.encodeResourceURL(url);
    }

    public Map getApplicationMap() {
        return externalContext.getApplicationMap();
    }

    public String getAuthType() {
        return externalContext.getAuthType();
    }

    public Object getContext() {
        return externalContext.getContext();
    }

    public String getInitParameter(String name) {
        return externalContext.getInitParameter(name);
    }

    public Map getInitParameterMap() {
        return externalContext.getInitParameterMap();
    }

    public String getRemoteUser() {
        return externalContext.getRemoteUser();
    }

    public Object getRequest() {
        return externalContext.getRequest();
    }

    public String getRequestContextPath() {
        return externalContext.getRequestContextPath();
    }

    public Map getRequestCookieMap() {
        return externalContext.getRequestCookieMap();
    }

    public Map getRequestHeaderMap() {
        return externalContext.getRequestHeaderMap();
    }

    public Map getRequestHeaderValuesMap() {
        return externalContext.getRequestHeaderValuesMap();
    }

    public Locale getRequestLocale() {
        return externalContext.getRequestLocale();
    }

    public Iterator getRequestLocales() {
        return externalContext.getRequestLocales();
    }

    public Map getRequestMap() {
        return externalContext.getRequestMap();
    }

    public Map getRequestParameterMap() {
        return externalContext.getRequestParameterMap();
    }

    public Iterator getRequestParameterNames() {
        return externalContext.getRequestParameterNames();
    }

    public Map getRequestParameterValuesMap() {
        return externalContext.getRequestParameterValuesMap();
    }

    public String getRequestPathInfo() {
        return externalContext.getRequestPathInfo();
    }

    public String getRequestServletPath() {
        return externalContext.getRequestServletPath();
    }

    public URL getResource(String path) throws MalformedURLException {
        return externalContext.getResource(path);
    }

    public InputStream getResourceAsStream(String path) {
        return externalContext.getResourceAsStream(path);
    }

    public Set getResourcePaths(String path) {
        return externalContext.getResourcePaths(path);
    }

    public Object getResponse() {
        return externalContext.getResponse();
    }

    public Object getSession(boolean create) {
        return externalContext.getSession(create);
    }

    public Map getSessionMap() {
        return externalContext.getSessionMap();
    }

    public Principal getUserPrincipal() {
        return externalContext.getUserPrincipal();
    }

    public boolean isUserInRole(String role) {
        return externalContext.isUserInRole(role);
    }

    public void log(String message) {
        externalContext.log(message);
    }

    public void log(String message, Throwable exception) {
        externalContext.log(message, exception);
    }

    public void redirect(String url) throws IOException {
        externalContext.redirect(url);
    }

    public void setExternalContext(ExternalContext externalContext) {
        this.externalContext = externalContext;
    }
}
