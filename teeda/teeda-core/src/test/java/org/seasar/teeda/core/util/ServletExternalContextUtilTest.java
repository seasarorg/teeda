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
package org.seasar.teeda.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.ViewHandler;
import javax.faces.mock.servlet.MockServletRequest;
import javax.faces.mock.servlet.MockServletRequestImpl;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletResponse;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class ServletExternalContextUtilTest extends TeedaTestCase {

    public void testSetCharacterEncoding() {
        MockHttpServletRequest request = getRequest();
        request.addHeader("Content-Type", "text/html; charset=Windows-31J");
        ServletExternalContextUtil.setCharacterEncoding(request);
        assertEquals("Windows-31J", request.getCharacterEncoding());
        MockServletRequest request2 = new MockServletRequestImpl(
                getServletContext(), "/");
        ServletExternalContextUtil.setCharacterEncoding(request2);
        assertEquals(MockServletRequest.DEFAULT_CHARACTER_ENCODING, request2
                .getCharacterEncoding());
    }

    public void testSetCharacterEncoding2() {
        MockHttpServletResponse response = getResponse();
        response.setContentType("text/html; charset=Windows-31J");
        boolean b = ServletExternalContextUtil.setCharacterEncoding(response);
        assertTrue(b);
        assertEquals("Windows-31J", response.getCharacterEncoding());
    }

    public void testIsHttpServletRequest() {
        assertTrue(ServletExternalContextUtil
                .isHttpServletRequest(new EmptyHttpServletRequest()));
        assertFalse(ServletExternalContextUtil
                .isHttpServletRequest(new EmptyServletRequest()));
        assertFalse(ServletExternalContextUtil.isHttpServletRequest(null));
    }

    public void testIsHttpServeltResponse() {
        assertTrue(ServletExternalContextUtil
                .isHttpServletResponse(new EmptyHttpServletResponse()));
        assertFalse(ServletExternalContextUtil
                .isHttpServletResponse(new EmptyServletResponse()));
        assertFalse(ServletExternalContextUtil.isHttpServletResponse(null));
    }

    public void testGetEncodingFromContentType() {
        assertEquals("Windows-31J", ServletExternalContextUtil
                .getEncodingFromContentType("text/html; charset=Windows-31J"));
        assertEquals("Windows-31J", ServletExternalContextUtil
                .getEncodingFromContentType("text/html;charset=Windows-31J"));
        assertNull(ServletExternalContextUtil
                .getEncodingFromContentType("text/html;"));
    }

    public void testGetEncodingFromSession() {
        MockHttpServletRequest request = getRequest();
        HttpSession session = request.getSession(true);
        session.setAttribute(ViewHandler.CHARACTER_ENCODING_KEY, "Windows-31J");
        String encoding = ServletExternalContextUtil
                .getEncodingFromSession(request);
        assertEquals("Windows-31J", encoding);
    }

    private static class EmptyHttpServletRequest implements HttpServletRequest {

        public String getAuthType() {
            return null;
        }

        public Cookie[] getCookies() {
            return null;
        }

        public long getDateHeader(String arg0) {
            return 0;
        }

        public String getHeader(String arg0) {
            return null;
        }

        public Enumeration getHeaders(String arg0) {
            return null;
        }

        public Enumeration getHeaderNames() {
            return null;
        }

        public int getIntHeader(String arg0) {
            return 0;
        }

        public String getMethod() {
            return null;
        }

        public String getPathInfo() {
            return null;
        }

        public String getPathTranslated() {
            return null;
        }

        public String getContextPath() {
            return null;
        }

        public String getQueryString() {
            return null;
        }

        public String getRemoteUser() {
            return null;
        }

        public boolean isUserInRole(String arg0) {
            return false;
        }

        public Principal getUserPrincipal() {
            return null;
        }

        public String getRequestedSessionId() {
            return null;
        }

        public String getRequestURI() {
            return null;
        }

        public StringBuffer getRequestURL() {
            return null;
        }

        public String getServletPath() {
            return null;
        }

        public HttpSession getSession(boolean arg0) {
            return null;
        }

        public HttpSession getSession() {
            return null;
        }

        public boolean isRequestedSessionIdValid() {
            return false;
        }

        public boolean isRequestedSessionIdFromCookie() {
            return false;
        }

        public boolean isRequestedSessionIdFromURL() {
            return false;
        }

        public boolean isRequestedSessionIdFromUrl() {
            return false;
        }

        public Object getAttribute(String arg0) {
            return null;
        }

        public Enumeration getAttributeNames() {
            return null;
        }

        public String getCharacterEncoding() {
            return null;
        }

        public void setCharacterEncoding(String arg0)
                throws UnsupportedEncodingException {
        }

        public int getContentLength() {
            return 0;
        }

        public String getContentType() {
            return null;
        }

        public ServletInputStream getInputStream() throws IOException {
            return null;
        }

        public String getParameter(String arg0) {
            return null;
        }

        public Enumeration getParameterNames() {
            return null;
        }

        public String[] getParameterValues(String arg0) {
            return null;
        }

        public Map getParameterMap() {
            return null;
        }

        public String getProtocol() {
            return null;
        }

        public String getScheme() {
            return null;
        }

        public String getServerName() {
            return null;
        }

        public int getServerPort() {
            return 0;
        }

        public BufferedReader getReader() throws IOException {
            return null;
        }

        public String getRemoteAddr() {
            return null;
        }

        public String getRemoteHost() {
            return null;
        }

        public void setAttribute(String arg0, Object arg1) {
        }

        public void removeAttribute(String arg0) {
        }

        public Locale getLocale() {
            return null;
        }

        public Enumeration getLocales() {
            return null;
        }

        public boolean isSecure() {
            return false;
        }

        public RequestDispatcher getRequestDispatcher(String arg0) {
            return null;
        }

        public String getRealPath(String arg0) {
            return null;
        }

        public int getRemotePort() {
            return 0;
        }

        public String getLocalName() {
            return null;
        }

        public String getLocalAddr() {
            return null;
        }

        public int getLocalPort() {
            return 0;
        }

    }

    private static class EmptyServletRequest implements ServletRequest {

        public Object getAttribute(String arg0) {
            return null;
        }

        public Enumeration getAttributeNames() {
            return null;
        }

        public String getCharacterEncoding() {
            return null;
        }

        public void setCharacterEncoding(String arg0)
                throws UnsupportedEncodingException {
        }

        public int getContentLength() {
            return 0;
        }

        public String getContentType() {
            return null;
        }

        public ServletInputStream getInputStream() throws IOException {
            return null;
        }

        public String getParameter(String arg0) {
            return null;
        }

        public Enumeration getParameterNames() {
            return null;
        }

        public String[] getParameterValues(String arg0) {
            return null;
        }

        public Map getParameterMap() {
            return null;
        }

        public String getProtocol() {
            return null;
        }

        public String getScheme() {
            return null;
        }

        public String getServerName() {
            return null;
        }

        public int getServerPort() {
            return 0;
        }

        public BufferedReader getReader() throws IOException {
            return null;
        }

        public String getRemoteAddr() {
            return null;
        }

        public String getRemoteHost() {
            return null;
        }

        public void setAttribute(String arg0, Object arg1) {
        }

        public void removeAttribute(String arg0) {
        }

        public Locale getLocale() {
            return null;
        }

        public Enumeration getLocales() {
            return null;
        }

        public boolean isSecure() {
            return false;
        }

        public RequestDispatcher getRequestDispatcher(String arg0) {
            return null;
        }

        public String getRealPath(String arg0) {
            return null;
        }

        public int getRemotePort() {
            return 0;
        }

        public String getLocalName() {
            return null;
        }

        public String getLocalAddr() {
            return null;
        }

        public int getLocalPort() {
            return 0;
        }

    }

    private static class EmptyHttpServletResponse implements
            HttpServletResponse {

        public void addCookie(Cookie arg0) {
        }

        public boolean containsHeader(String arg0) {
            return false;
        }

        public String encodeURL(String arg0) {
            return null;
        }

        public String encodeRedirectURL(String arg0) {
            return null;
        }

        public String encodeUrl(String arg0) {
            return null;
        }

        public String encodeRedirectUrl(String arg0) {
            return null;
        }

        public void sendError(int arg0, String arg1) throws IOException {
        }

        public void sendError(int arg0) throws IOException {
        }

        public void sendRedirect(String arg0) throws IOException {
        }

        public void setDateHeader(String arg0, long arg1) {
        }

        public void addDateHeader(String arg0, long arg1) {
        }

        public void setHeader(String arg0, String arg1) {
        }

        public void addHeader(String arg0, String arg1) {
        }

        public void setIntHeader(String arg0, int arg1) {
        }

        public void addIntHeader(String arg0, int arg1) {
        }

        public void setStatus(int arg0) {
        }

        public void setStatus(int arg0, String arg1) {
        }

        public String getCharacterEncoding() {
            return null;
        }

        public String getContentType() {
            return null;
        }

        public ServletOutputStream getOutputStream() throws IOException {
            return null;
        }

        public PrintWriter getWriter() throws IOException {
            return null;
        }

        public void setCharacterEncoding(String arg0) {
        }

        public void setContentLength(int arg0) {
        }

        public void setContentType(String arg0) {
        }

        public void setBufferSize(int arg0) {
        }

        public int getBufferSize() {
            return 0;
        }

        public void flushBuffer() throws IOException {
        }

        public void resetBuffer() {
        }

        public boolean isCommitted() {
            return false;
        }

        public void reset() {
        }

        public void setLocale(Locale arg0) {
        }

        public Locale getLocale() {
            return null;
        }

    }

    private static class EmptyServletResponse implements ServletResponse {

        public String getCharacterEncoding() {
            return null;
        }

        public String getContentType() {
            return null;
        }

        public ServletOutputStream getOutputStream() throws IOException {
            return null;
        }

        public PrintWriter getWriter() throws IOException {
            return null;
        }

        public void setCharacterEncoding(String arg0) {
        }

        public void setContentLength(int arg0) {
        }

        public void setContentType(String arg0) {
        }

        public void setBufferSize(int arg0) {
        }

        public int getBufferSize() {
            return 0;
        }

        public void flushBuffer() throws IOException {
        }

        public void resetBuffer() {
        }

        public boolean isCommitted() {
            return false;
        }

        public void reset() {
        }

        public void setLocale(Locale arg0) {
        }

        public Locale getLocale() {
            return null;
        }

    }

}
