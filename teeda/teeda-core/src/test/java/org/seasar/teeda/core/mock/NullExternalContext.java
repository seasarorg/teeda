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
package org.seasar.teeda.core.mock;

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

/**
 * @author manhole
 */
public class NullExternalContext extends ExternalContext {

    public void dispatch(String path) throws IOException {

    }

    public String encodeActionURL(String url) {
        return null;
    }

    public String encodeNamespace(String name) {
        return null;
    }

    public String encodeResourceURL(String url) {
        return null;
    }

    public Map getApplicationMap() {
        return null;
    }

    public String getAuthType() {
        return null;
    }

    public Object getContext() {
        return null;
    }

    public String getInitParameter(String name) {
        return null;
    }

    public Map getInitParameterMap() {
        return null;
    }

    public String getRemoteUser() {
        return null;
    }

    public Object getRequest() {
        return null;
    }

    public String getRequestContextPath() {
        return null;
    }

    public Map getRequestCookieMap() {
        return null;
    }

    public Map getRequestHeaderMap() {
        return null;
    }

    public Map getRequestHeaderValuesMap() {
        return null;
    }

    public Locale getRequestLocale() {
        return null;
    }

    public Iterator getRequestLocales() {
        return null;
    }

    public Map getRequestMap() {
        return null;
    }

    public Map getRequestParameterMap() {
        return null;
    }

    public Iterator getRequestParameterNames() {
        return null;
    }

    public Map getRequestParameterValuesMap() {
        return null;
    }

    public String getRequestPathInfo() {
        return null;
    }

    public String getRequestServletPath() {
        return null;
    }

    public URL getResource(String path) throws MalformedURLException {
        return null;
    }

    public InputStream getResourceAsStream(String path) {
        return null;
    }

    public Set getResourcePaths(String path) {
        return null;
    }

    public Object getResponse() {
        return null;
    }

    public Object getSession(boolean create) {
        return null;
    }

    public Map getSessionMap() {
        return null;
    }

    public Principal getUserPrincipal() {
        return null;
    }

    public boolean isUserInRole(String role) {
        return false;
    }

    public void log(String message) {
    }

    public void log(String message, Throwable exception) {
    }

    public void redirect(String url) throws IOException {
    }

}
