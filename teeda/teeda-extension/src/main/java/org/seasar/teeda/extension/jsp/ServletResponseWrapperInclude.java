/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.jsp.JspWriter;

public class ServletResponseWrapperInclude extends HttpServletResponseWrapper {

    private PrintWriter writer;

    public ServletResponseWrapperInclude(ServletResponse response,
            JspWriter jspWriter) {

        super((HttpServletResponse) response);
        this.writer = new PrintWriter(jspWriter);
    }

    /**
     * Returns a wrapper around the JspWriter of the including page.
     */
    public java.io.PrintWriter getWriter() throws java.io.IOException {
        return writer;
    }

    public ServletOutputStream getOutputStream() throws java.io.IOException {
        throw new IllegalStateException();
    }
}