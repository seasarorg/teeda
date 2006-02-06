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

import javax.servlet.http.Cookie;

import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletResponse;
import org.seasar.framework.mock.servlet.MockServletContext;

/**
 * @author manhole
 */
public class MockExternalContextWrapper extends MockExternalContext {

    private MockExternalContext mockExternalContext_;

    public MockExternalContextWrapper(MockExternalContext mockExternalContext) {
        mockExternalContext_ = mockExternalContext;
    }

    public void addRequestCookieMap(Cookie cookie) {
        mockExternalContext_.addRequestCookieMap(cookie);
    }

    public void addRequestParameterMap(String key, String value) {
        mockExternalContext_.addRequestParameterMap(key, value);
    }

    public void dispatch(String path) throws IOException {
        mockExternalContext_.dispatch(path);
    }

    public String encodeActionURL(String url) {
        return mockExternalContext_.encodeActionURL(url);
    }

    public String encodeNamespace(String name) {
        return mockExternalContext_.encodeNamespace(name);
    }

    public String encodeResourceURL(String url) {
        return mockExternalContext_.encodeResourceURL(url);
    }

    public boolean equals(Object obj) {
        return mockExternalContext_.equals(obj);
    }

    public Map getApplicationMap() {
        return mockExternalContext_.getApplicationMap();
    }

    public String getAuthType() {
        return mockExternalContext_.getAuthType();
    }

    public Object getContext() {
        return mockExternalContext_.getContext();
    }

    public String getInitParameter(String name) {
        return mockExternalContext_.getInitParameter(name);
    }

    public Map getInitParameterMap() {
        return mockExternalContext_.getInitParameterMap();
    }

    public String getRemoteUser() {
        return mockExternalContext_.getRemoteUser();
    }

    public Object getRequest() {
        return mockExternalContext_.getRequest();
    }

    public String getRequestContextPath() {
        return mockExternalContext_.getRequestContextPath();
    }

    public Map getRequestCookieMap() {
        return mockExternalContext_.getRequestCookieMap();
    }

    public Map getRequestHeaderMap() {
        return mockExternalContext_.getRequestHeaderMap();
    }

    public Map getRequestHeaderValuesMap() {
        return mockExternalContext_.getRequestHeaderValuesMap();
    }

    public Locale getRequestLocale() {
        return mockExternalContext_.getRequestLocale();
    }

    public Iterator getRequestLocales() {
        return mockExternalContext_.getRequestLocales();
    }

    public Map getRequestMap() {
        return mockExternalContext_.getRequestMap();
    }

    public Map getRequestParameterMap() {
        return mockExternalContext_.getRequestParameterMap();
    }

    public Iterator getRequestParameterNames() {
        return mockExternalContext_.getRequestParameterNames();
    }

    public Map getRequestParameterValuesMap() {
        return mockExternalContext_.getRequestParameterValuesMap();
    }

    public String getRequestPathInfo() {
        return mockExternalContext_.getRequestPathInfo();
    }

    public String getRequestServletPath() {
        return mockExternalContext_.getRequestServletPath();
    }

    public URL getResource(String path) throws MalformedURLException {
        return mockExternalContext_.getResource(path);
    }

    public InputStream getResourceAsStream(String path) {
        return mockExternalContext_.getResourceAsStream(path);
    }

    public Set getResourcePaths(String path) {
        return mockExternalContext_.getResourcePaths(path);
    }

    public Object getResponse() {
        return mockExternalContext_.getResponse();
    }

    public Object getSession(boolean create) {
        return mockExternalContext_.getSession(create);
    }

    public Map getSessionMap() {
        return mockExternalContext_.getSessionMap();
    }

    public Principal getUserPrincipal() {
        return mockExternalContext_.getUserPrincipal();
    }

    public int hashCode() {
        return mockExternalContext_.hashCode();
    }

    public boolean isUserInRole(String role) {
        return mockExternalContext_.isUserInRole(role);
    }

    public void log(String message, Throwable exception) {
        mockExternalContext_.log(message, exception);
    }

    public void log(String message) {
        mockExternalContext_.log(message);
    }

    public void redirect(String url) throws IOException {
        mockExternalContext_.redirect(url);
    }

    public void setRequestCookieMap(Map map) {
        mockExternalContext_.setRequestCookieMap(map);
    }

    public void setRequestParameterMap(Map map) {
        mockExternalContext_.setRequestParameterMap(map);
    }

    public String toString() {
        return mockExternalContext_.toString();
    }

    public void setMockServletContext(MockServletContext mockServletContext) {
        mockExternalContext_.setMockServletContext(mockServletContext);
    }

    public MockServletContext getMockServletContext() {
        return mockExternalContext_.getMockServletContext();
    }

    public void setMockHttpServletRequest(
            MockHttpServletRequest mockHttpServletRequest) {
        mockExternalContext_.setMockHttpServletRequest(mockHttpServletRequest);
    }

    public MockHttpServletRequest getMockHttpServletRequest() {
        return mockExternalContext_.getMockHttpServletRequest();
    }

    public void setMockHttpServletResponse(
            MockHttpServletResponse mockHttpServletResponse) {
        mockExternalContext_
                .setMockHttpServletResponse(mockHttpServletResponse);
    }

    public MockHttpServletResponse getMockHttpServletResponse() {
        return mockExternalContext_.getMockHttpServletResponse();
    }

}
