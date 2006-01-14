package org.seasar.teeda.core.mock;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.FacesException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletResponse;
import org.seasar.framework.mock.servlet.MockServletContext;

public class MockExternalContextImpl extends MockExternalContext {

    private MockServletContext context_;

    private MockHttpServletRequest request_;

    private HttpServletResponse response_;

    private Map applicationMap_;

    private Map requestParameterMap_;

    private Map requestCookieMap_;

    private Map sessionMap_;

    private Map requestMap_;

    public MockExternalContextImpl(MockServletContext context,
            MockHttpServletRequest request, MockHttpServletResponse response) {
        context_ = context;
        request_ = request;
        response_ = response;
        applicationMap_ = new MockApplicationMap(context_);
    }

    public void addRequestCookieMap(Cookie cookie) {
        requestParameterMap_.put(cookie.getName(), cookie);
    }

    public void setRequestCookieMap(Map map) {
        requestParameterMap_ = map;
    }

    public void addRequestParameterMap(String key, String value) {
        requestParameterMap_.put(key, value);
    }

    public void setRequestParameterMap(Map map) {
        requestParameterMap_ = map;
    }

    public void dispatch(String requestURI) throws IOException, FacesException {
        throw new UnsupportedOperationException();
    }

    public String encodeActionURL(String sb) {
        throw new UnsupportedOperationException();
    }

    public String encodeNamespace(String aValue) {
        throw new UnsupportedOperationException();
    }

    public String encodeResourceURL(String url) {
        return response_.encodeURL(url);
    }

    public Map getApplicationMap() {
        return applicationMap_;
    }

    public String getAuthType() {

        return request_.getAuthType();

    }

    public Object getContext() {

        return context_;

    }

    public String getInitParameter(String name) {

        return context_.getInitParameter(name);

    }

    public Map getInitParameterMap() {
        Map parameterMap = new HashMap();
        Enumeration names = context_.getInitParameterNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            parameterMap.put(name, context_.getInitParameter(name));
        }
        return Collections.unmodifiableMap(parameterMap);
    }

    public String getRemoteUser() {
        return request_.getRemoteUser();
    }

    public Object getRequest() {
        return request_;
    }

    public String getRequestContextPath() {
        return request_.getContextPath();
    }

    public Map getRequestCookieMap() {
        return requestCookieMap_;
    }

    public Map getRequestHeaderMap() {
        throw new UnsupportedOperationException();
    }

    public Map getRequestHeaderValuesMap() {
        throw new UnsupportedOperationException();
    }

    public Locale getRequestLocale() {
        return request_.getLocale();
    }

    public Iterator getRequestLocales() {
        return new LocalesIterator(request_.getLocales());
    }

    public Map getRequestMap() {
        if (requestMap_ == null) {
            requestMap_ = new HashMap();
        }
        return requestMap_;
    }

    public Map getRequestParameterMap() {

        return requestParameterMap_;

    }

    public Iterator getRequestParameterNames() {

        throw new UnsupportedOperationException();

    }

    public Map getRequestParameterValuesMap() {

        throw new UnsupportedOperationException();

    }

    public String getRequestPathInfo() {

        return request_.getPathInfo();

    }

    public String getRequestServletPath() {

        return request_.getServletPath();

    }

    public URL getResource(String path) throws MalformedURLException {

        return context_.getResource(path);

    }

    public InputStream getResourceAsStream(String path) {

        return context_.getResourceAsStream(path);

    }

    public Set getResourcePaths(String path) {

        throw new UnsupportedOperationException();

    }

    public Object getResponse() {

        return response_;

    }

    public Object getSession(boolean create) {

        return request_.getSession(create);

    }

    public Map getSessionMap() {

        if (sessionMap_ == null) {
            HttpSession session = request_.getSession(true);
            sessionMap_ = new MockSessionMap(session);
        }
        return sessionMap_;

    }

    public java.security.Principal getUserPrincipal() {

        return request_.getUserPrincipal();

    }

    public boolean isUserInRole(String role) {

        return request_.isUserInRole(role);

    }

    public void log(String message) {

        context_.log(message);

    }

    public void log(String message, Throwable throwable) {
        context_.log(message, throwable);
    }

    public void redirect(String requestURI) throws IOException {
        throw new UnsupportedOperationException();
    }

    private class LocalesIterator implements Iterator {
        public LocalesIterator(Enumeration locales) {
            this.locales = locales;
        }

        private Enumeration locales;

        public boolean hasNext() {
            return locales.hasMoreElements();
        }

        public Object next() {
            return locales.nextElement();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

}
