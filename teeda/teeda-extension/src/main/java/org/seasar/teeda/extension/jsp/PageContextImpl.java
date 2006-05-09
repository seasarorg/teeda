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
package org.seasar.teeda.extension.jsp;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;
import javax.servlet.jsp.tagext.BodyContent;

import org.seasar.framework.log.Logger;
import org.seasar.framework.util.EnumerationAdapter;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.exception.NoHttpSessionRuntimeException;
import org.seasar.teeda.extension.exception.UndefinedScopeRuntimeException;

public class PageContextImpl extends PageContext {

    private static Logger logger = Logger.getLogger(PageContextImpl.class);

    private Servlet servlet;

    private ServletConfig config;

    private ServletContext context;

    private boolean needsSession;

    private String errorPageURL;

    private boolean autoFlush;

    private int bufferSize;

    private Map attributes = new HashMap();

    private ServletRequest request;

    private ServletResponse response;

    private HttpSession session;

    private boolean included;

    private JspWriter out;

    private Stack bodyContentStack = new Stack();

    public PageContextImpl() {
    }

    public void initialize(Servlet servlet, ServletRequest request,
            ServletResponse response, String errorPageURL)
            throws IllegalStateException, IllegalArgumentException, IOException {

        initialize(servlet, request, response, errorPageURL, true,
                JspConstants.DEFAULT_BUFFER_SIZE, true);
    }

    public void initialize(Servlet servlet, ServletRequest request,
            ServletResponse response, String errorPageURL,
            boolean needsSession, int bufferSize, boolean autoFlush)
            throws IOException, IllegalStateException, IllegalArgumentException {

        this.servlet = servlet;
        this.config = servlet.getServletConfig();
        this.context = config.getServletContext();
        this.needsSession = needsSession;
        this.errorPageURL = errorPageURL;
        this.bufferSize = bufferSize;
        this.autoFlush = autoFlush;
        this.request = request;
        this.response = response;

        if (request instanceof HttpServletRequest && needsSession) {
            this.session = ((HttpServletRequest) request).getSession();
        }
        out = new JspWriterImpl(response, bufferSize, autoFlush);
        setAttribute(OUT, out);
        setAttribute(REQUEST, request);
        setAttribute(RESPONSE, response);
        if (session != null) {
            setAttribute(SESSION, session);
        }
        setAttribute(PAGE, servlet);
        setAttribute(CONFIG, config);
        setAttribute(PAGECONTEXT, this);
        setAttribute(APPLICATION, context);
        included = request.getAttribute(JspConstants.INCLUDE_SERVLET_PATH) != null;
    }

    public void release() {
        try {
            if (included) {
                ((JspWriterImpl) out).flushBuffer();
            } else {
                out.flush();
            }
        } catch (IOException ex) {
            logger.log(ex);
        }
        servlet = null;
        config = null;
        context = null;
        needsSession = false;
        errorPageURL = null;
        bufferSize = JspWriter.DEFAULT_BUFFER;
        autoFlush = true;
        request = null;
        response = null;
        session = null;
        out = null;
        attributes.clear();
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public Object getAttribute(String name, int scope) {
        switch (scope) {
        case PAGE_SCOPE:
            return attributes.get(name);
        case REQUEST_SCOPE:
            return request.getAttribute(name);
        case SESSION_SCOPE:
            if (session == null) {
                throw new NoHttpSessionRuntimeException();
            }
            return session.getAttribute(name);
        case APPLICATION_SCOPE:
            return context.getAttribute(name);
        default:
            throw new UndefinedScopeRuntimeException(scope);
        }
    }

    public void setAttribute(String name, Object attribute) {
        attributes.put(name, attribute);
    }

    public void setAttribute(String name, Object o, int scope) {
        switch (scope) {
        case PAGE_SCOPE:
            attributes.put(name, o);
            break;
        case REQUEST_SCOPE:
            request.setAttribute(name, o);
            break;
        case SESSION_SCOPE:
            if (session == null) {
                throw new NoHttpSessionRuntimeException();
            }
            session.setAttribute(name, o);
            break;
        case APPLICATION_SCOPE:
            context.setAttribute(name, o);
            break;
        default:
            throw new UndefinedScopeRuntimeException(scope);
        }
    }

    public void removeAttribute(String name, int scope) {
        switch (scope) {
        case PAGE_SCOPE:
            attributes.remove(name);
            break;
        case REQUEST_SCOPE:
            request.removeAttribute(name);
            break;
        case SESSION_SCOPE:
            if (session == null) {
                throw new NoHttpSessionRuntimeException();
            }
            session.removeAttribute(name);
            break;
        case APPLICATION_SCOPE:
            context.removeAttribute(name);
            break;
        default:
            throw new UndefinedScopeRuntimeException(scope);
        }
    }

    public int getAttributesScope(String name) {
        if (getAttribute(name) != null) {
            return PAGE_SCOPE;
        } else if (request.getAttribute(name) != null) {
            return REQUEST_SCOPE;
        } else if (session != null && session.getAttribute(name) != null) {
            return SESSION_SCOPE;
        } else if (context.getAttribute(name) != null) {
            return APPLICATION_SCOPE;
        }
        return 0;
    }

    public Object findAttribute(String name) {
        Object o = getAttribute(name);
        if (o != null) {
            return o;
        }
        o = request.getAttribute(name);
        if (o != null) {
            return o;
        }
        if (session != null) {
            o = session.getAttribute(name);
            if (o != null) {
                return o;
            }
        }
        return context.getAttribute(name);
    }

    public Enumeration getAttributeNamesInScope(int scope) {
        switch (scope) {
        case PAGE_SCOPE:
            return new EnumerationAdapter(attributes.keySet().iterator());
        case REQUEST_SCOPE:
            return request.getAttributeNames();
        case SESSION_SCOPE:
            if (session != null) {
                return session.getAttributeNames();
            }
            throw new NoHttpSessionRuntimeException();
        case APPLICATION_SCOPE:
            return context.getAttributeNames();
        default:
            throw new UndefinedScopeRuntimeException(scope);
        }
    }

    public void removeAttribute(String name) {
        try {
            removeAttribute(name, PAGE_SCOPE);
            removeAttribute(name, REQUEST_SCOPE);
            if (session != null) {
                removeAttribute(name, SESSION_SCOPE);
            }
            removeAttribute(name, APPLICATION_SCOPE);
        } catch (Exception ignore) {
        }
    }

    public JspWriter getOut() {
        return out;
    }

    public HttpSession getSession() {
        return session;
    }

    public Servlet getServlet() {
        return servlet;
    }

    public ServletConfig getServletConfig() {
        return config;
    }

    public ServletContext getServletContext() {
        return context;
    }

    public ServletRequest getRequest() {
        return request;
    }

    public ServletResponse getResponse() {
        return response;
    }

    public Exception getException() {
        return (Exception) request.getAttribute(EXCEPTION);
    }

    public Object getPage() {
        return servlet;
    }

    public void include(String relativePath) throws ServletException,
            IOException {

        include(relativePath, true);
    }

    public void include(String relativePath, boolean flush)
            throws ServletException, IOException {

        String resourcePath = getContextRelativePath(relativePath);
        RequestDispatcher rd = request.getRequestDispatcher(resourcePath);
        rd.include(request, new ServletResponseWrapperInclude(response, out));
    }

    protected String getContextRelativePath(String relativePath) {
        if (relativePath.startsWith("/")) {
            return relativePath;
        }
        if (!(request instanceof HttpServletRequest)) {
            return (relativePath);
        }
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = (String) request
                .getAttribute(JspConstants.INCLUDE_SERVLET_PATH);
        if (uri == null) {
            uri = req.getServletPath();
        }
        return uri.substring(0, uri.lastIndexOf('/')) + '/' + relativePath;

    }

    public void forward(String relativePath) throws ServletException,
            IOException {

        try {
            out.clear();
        } catch (IOException ex) {
            IllegalStateException ise = new IllegalStateException(ex.toString());
            ise.initCause(ex);
            throw ise;
        }
        while (response instanceof ServletResponseWrapperInclude) {
            response = ((ServletResponseWrapperInclude) response).getResponse();
        }

        String path = getContextRelativePath(relativePath);
        String includeUri = (String) request
                .getAttribute(JspConstants.INCLUDE_SERVLET_PATH);
        if (includeUri != null) {
            request.removeAttribute(JspConstants.INCLUDE_SERVLET_PATH);
        }
        try {
            context.getRequestDispatcher(path).forward(request, response);
        } finally {
            if (includeUri != null) {
                request.setAttribute(JspConstants.INCLUDE_SERVLET_PATH,
                        includeUri);
            }
            request.setAttribute(JspConstants.FORWARD_SEEN, "true");
        }
    }

    public BodyContent pushBody() {
        BodyContent bodyContent = new BodyContentImpl(out);
        bodyContentStack.push(bodyContent);
        out = bodyContent;
        return bodyContent;
    }

    public JspWriter popBody() {
        out = ((BodyContent) bodyContentStack.pop()).getEnclosingWriter();
        return out;
    }

    public void handlePageException(Exception ex) throws IOException,
            ServletException {

        handlePageException((Throwable) ex);
    }

    public void handlePageException(Throwable t) throws IOException,
            ServletException {

        request.setAttribute(JsfConstants.JSP_EXCEPTION, t);
        if (errorPageURL != null && !errorPageURL.equals("")) {
            try {
                forward(errorPageURL);
            } catch (IllegalStateException ise) {
                include(errorPageURL);
            }
        } else {
            if (t instanceof IOException) {
                throw (IOException) t;
            }
            if (t instanceof ServletException) {
                throw (ServletException) t;
            }
            if (t instanceof RuntimeException) {
                throw (RuntimeException) t;
            }
            if (t instanceof JspException) {
                Throwable rootCause = ((JspException) t).getRootCause();
                if (rootCause != null) {
                    throw new ServletException(t.getMessage(), rootCause);
                }
                throw new ServletException(t);
            }
            throw new ServletException(t);
        }
    }

    public boolean isNeedsSession() {
        return needsSession;
    }

    public boolean isAutoFlush() {
        return autoFlush;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public ExpressionEvaluator getExpressionEvaluator() {
        return null;
    }

    public VariableResolver getVariableResolver() {
        return null;
    }
    
    
}