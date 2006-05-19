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
package org.seasar.teeda.core.context.portlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author shot
 * 
 */
public class PortletExternalContextImpl extends ExternalContext {

    //TODO impl these.

    public PortletExternalContextImpl(PortletContext context,
            PortletRequest request, PortletResponse response) {
        // TODO Auto-generated constructor stub
    }

    public void dispatch(String path) throws IOException {
        // TODO Auto-generated method stub

    }

    public String encodeActionURL(String url) {
        // TODO Auto-generated method stub
        return null;
    }

    public String encodeNamespace(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    public String encodeResourceURL(String url) {
        // TODO Auto-generated method stub
        return null;
    }

    public Map getApplicationMap() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getAuthType() {
        // TODO Auto-generated method stub
        return null;
    }

    public Object getContext() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getInitParameter(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    public Map getInitParameterMap() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getRemoteUser() {
        // TODO Auto-generated method stub
        return null;
    }

    public Object getRequest() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getRequestContextPath() {
        // TODO Auto-generated method stub
        return null;
    }

    public Map getRequestCookieMap() {
        // TODO Auto-generated method stub
        return null;
    }

    public Map getRequestHeaderMap() {
        // TODO Auto-generated method stub
        return null;
    }

    public Map getRequestHeaderValuesMap() {
        // TODO Auto-generated method stub
        return null;
    }

    public Locale getRequestLocale() {
        // TODO Auto-generated method stub
        return null;
    }

    public Iterator getRequestLocales() {
        // TODO Auto-generated method stub
        return null;
    }

    public Map getRequestMap() {
        // TODO Auto-generated method stub
        return null;
    }

    public Map getRequestParameterMap() {
        // TODO Auto-generated method stub
        return null;
    }

    public Iterator getRequestParameterNames() {
        // TODO Auto-generated method stub
        return null;
    }

    public Map getRequestParameterValuesMap() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getRequestPathInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getRequestServletPath() {
        // TODO Auto-generated method stub
        return null;
    }

    public URL getResource(String path) throws MalformedURLException {
        // TODO Auto-generated method stub
        return null;
    }

    public InputStream getResourceAsStream(String path) {
        // TODO Auto-generated method stub
        return null;
    }

    public Set getResourcePaths(String path) {
        // TODO Auto-generated method stub
        return null;
    }

    public Object getResponse() {
        // TODO Auto-generated method stub
        return null;
    }

    public Object getSession(boolean create) {
        // TODO Auto-generated method stub
        return null;
    }

    public Map getSessionMap() {
        // TODO Auto-generated method stub
        return null;
    }

    public Principal getUserPrincipal() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isUserInRole(String role) {
        // TODO Auto-generated method stub
        return false;
    }

    public void log(String message) {
        // TODO Auto-generated method stub

    }

    public void log(String message, Throwable exception) {
        // TODO Auto-generated method stub

    }

    public void redirect(String url) throws IOException {
        // TODO Auto-generated method stub

    }

}
