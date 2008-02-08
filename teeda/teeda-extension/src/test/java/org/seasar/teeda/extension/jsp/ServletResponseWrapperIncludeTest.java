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

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import org.seasar.framework.mock.servlet.MockHttpServletResponse;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class ServletResponseWrapperIncludeTest extends TeedaTestCase {

    public void test() throws Exception {
        MockJspWriter writer = new MockJspWriter(0, false);
        MockHttpServletResponse response = getResponse();
        ServletResponseWrapperInclude wrapper = new ServletResponseWrapperInclude(
                response, writer);
        assertNotNull(wrapper.getWriter());

        try {
            wrapper.getOutputStream();
            fail();
        } catch (IllegalStateException expected) {
            success();
        }
    }

    private static class MockJspWriter extends JspWriter {

        protected MockJspWriter(int arg0, boolean arg1) {
            super(arg0, arg1);
        }

        public void newLine() throws IOException {
        }

        public void print(boolean arg0) throws IOException {
        }

        public void print(char arg0) throws IOException {
        }

        public void print(int arg0) throws IOException {
        }

        public void print(long arg0) throws IOException {
        }

        public void print(float arg0) throws IOException {
        }

        public void print(double arg0) throws IOException {
        }

        public void print(char[] arg0) throws IOException {
        }

        public void print(String arg0) throws IOException {
        }

        public void print(Object arg0) throws IOException {
        }

        public void println() throws IOException {
        }

        public void println(boolean arg0) throws IOException {
        }

        public void println(char arg0) throws IOException {
        }

        public void println(int arg0) throws IOException {
        }

        public void println(long arg0) throws IOException {
        }

        public void println(float arg0) throws IOException {
        }

        public void println(double arg0) throws IOException {
        }

        public void println(char[] arg0) throws IOException {
        }

        public void println(String arg0) throws IOException {
        }

        public void println(Object arg0) throws IOException {
        }

        public void clear() throws IOException {
        }

        public void clearBuffer() throws IOException {
        }

        public void flush() throws IOException {
        }

        public void close() throws IOException {
        }

        public int getRemaining() {
            return 0;
        }

        public void write(char[] arg0, int arg1, int arg2) throws IOException {
        }

    }
}
