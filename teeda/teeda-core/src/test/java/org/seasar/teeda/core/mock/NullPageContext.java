/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
import java.util.Enumeration;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;

/**
 * @author shot
 * 
 */
public class NullPageContext extends PageContext {

    public void initialize(Servlet arg0, ServletRequest arg1,
            ServletResponse arg2, String arg3, boolean arg4, int arg5,
            boolean arg6) throws IOException, IllegalStateException,
            IllegalArgumentException {
    }

    public void release() {
    }

    public HttpSession getSession() {
        return null;
    }

    public Object getPage() {
        return null;
    }

    public ServletRequest getRequest() {
        return null;
    }

    public ServletResponse getResponse() {
        return null;
    }

    public Exception getException() {
        return null;
    }

    public ServletConfig getServletConfig() {
        return null;
    }

    public ServletContext getServletContext() {
        return null;
    }

    public void forward(String arg0) throws ServletException, IOException {
    }

    public void include(String arg0) throws ServletException, IOException {
    }

    public void include(String arg0, boolean arg1) throws ServletException,
            IOException {
    }

    public void handlePageException(Exception arg0) throws ServletException,
            IOException {
    }

    public void handlePageException(Throwable arg0) throws ServletException,
            IOException {
    }

    public void setAttribute(String arg0, Object arg1) {
    }

    public void setAttribute(String arg0, Object arg1, int arg2) {
    }

    public Object getAttribute(String arg0) {
        return null;
    }

    public Object getAttribute(String arg0, int arg1) {
        return null;
    }

    public Object findAttribute(String arg0) {
        return null;
    }

    public void removeAttribute(String arg0) {
    }

    public void removeAttribute(String arg0, int arg1) {
    }

    public int getAttributesScope(String arg0) {
        return 0;
    }

    public Enumeration getAttributeNamesInScope(int arg0) {
        return null;
    }

    public JspWriter getOut() {
        return null;
    }

    public ExpressionEvaluator getExpressionEvaluator() {
        return null;
    }

    public VariableResolver getVariableResolver() {
        return null;
    }

}
