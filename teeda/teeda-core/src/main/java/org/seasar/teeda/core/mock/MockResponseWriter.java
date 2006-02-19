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
import java.io.PrintWriter;
import java.io.Writer;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import org.seasar.framework.util.SPrintWriter;

/**
 * @author manhole
 */
public class MockResponseWriter extends ResponseWriter {

    private PrintWriter writer = new SPrintWriter();

    public String getContentType() {
        return null;
    }

    public String getCharacterEncoding() {
        return null;
    }

    public void flush() throws IOException {
        writer.flush();
    }

    public void startDocument() throws IOException {
    }

    public void endDocument() throws IOException {
    }

    public void startElement(String s, UIComponent uicomponent)
        throws IOException {
    }

    public void endElement(String s) throws IOException {
    }

    public void writeAttribute(String s, Object obj, String s1)
        throws IOException {
    }

    public void writeURIAttribute(String s, Object obj, String s1)
        throws IOException {
    }

    public void writeComment(Object obj) throws IOException {
    }

    public void writeText(Object obj, String s) throws IOException {
    }

    public void writeText(char[] ac, int i, int j) throws IOException {
    }

    public ResponseWriter cloneWithWriter(Writer writer) {
        return null;
    }

    public void write(char[] cbuf, int off, int len) throws IOException {
        writer.write(cbuf, off, len);
    }

    public void close() throws IOException {
        writer.close();
    }

}
