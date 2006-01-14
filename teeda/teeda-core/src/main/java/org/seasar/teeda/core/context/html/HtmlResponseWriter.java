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
package org.seasar.teeda.core.context.html;

import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

/**
 * @author manhole
 */
public class HtmlResponseWriter extends ResponseWriter {

    private Writer writer_;

    private String contentType_;

    private String characterEncoding_;

    private boolean startTagOpening_;

    private static final String DEFAULT_CHARACTER_ENCODING = "UTF-8";

    public void startElement(String name, UIComponent componentForElement)
            throws IOException {
        if (name == null) {
            throw new NullPointerException("name");
        }
        Writer writer = getWriter();
        closeOpeningStartTag(writer);
        writer.write("<");
        writer.write(name);

        startTagOpening_ = true;
    }

    protected void closeOpeningStartTag(Writer writer) throws IOException {
        if (startTagOpening_) {
            writer.write(">");
            startTagOpening_ = false;
        }
    }

    public void endElement(String name) throws IOException {
        if (name == null) {
            throw new NullPointerException("name");
        }
        Writer writer = getWriter();
        if (startTagOpening_) {
            writer.write("/>");
            startTagOpening_ = false;
        } else {
            writer.write("</" + name + ">");
        }
    }

    public void writeAttribute(String name, Object value, String property)
            throws IOException {
        if (name == null) {
            throw new NullPointerException("name");
        }
        if (!startTagOpening_) {
            throw new IllegalStateException(
                    "there is no currently open element");
        }
        Writer writer = getWriter();
        writer.write(" ");
        writer.write(name);
        writer.write("=\"");
        writer.write(escapeHtml(value.toString()));
        writer.write("\"");
    }

    public void writeURIAttribute(String name, Object value, String property)
            throws IOException {
        if (name == null) {
            throw new NullPointerException("name");
        }
        if (!startTagOpening_) {
            throw new IllegalStateException(
                    "there is no currently open element");
        }
        Writer writer = getWriter();
        String strValue = value.toString();
        strValue = URLEncoder.encode(strValue, getCharacterEncoding());

        writer.write(" ");
        writer.write(name);
        writer.write("=\"");
        writer.write(strValue);
        writer.write("\"");
    }

    public void writeComment(Object comment) throws IOException {
        if (comment == null) {
            throw new NullPointerException("comment");
        }
        Writer writer = getWriter();
        closeOpeningStartTag(writer);
        writer.write("<!--");
        writer.write(comment.toString());
        writer.write("-->");
    }

    public void writeText(Object text, String property) throws IOException {
        if (text == null) {
            throw new NullPointerException("text");
        }
        Writer writer = getWriter();
        closeOpeningStartTag(writer);
        writer.write(escapeHtml(text.toString()));
    }

    public void writeText(char text[], int off, int len) throws IOException {
        if (text == null) {
            throw new NullPointerException("text");
        }
        writeText(new String(text, off, len), null);
    }

    protected String escapeHtml(String s) {
        char[] chars = s.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            switch (c) {
            case '<':
                sb.append("&lt;");
                break;
            case '>':
                sb.append("&gt;");
                break;
            case '&':
                sb.append("&amp;");
                break;
            case '"':
                sb.append("&quot;");
                break;
            case '\'':
                sb.append("&#39;");
                break;
            default:
                sb.append(c);
                break;
            }
        }
        return new String(sb);
    }

    public ResponseWriter cloneWithWriter(Writer writer) {
        if (writer == null) {
            throw new NullPointerException("writer");
        }
        HtmlResponseWriter newResponseWriter = new HtmlResponseWriter();
        newResponseWriter.setWriter(writer);
        newResponseWriter.setContentType(getContentType());
        newResponseWriter.setCharacterEncoding(getCharacterEncoding());
        return newResponseWriter;
    }

    public void write(char[] cbuf, int off, int len) throws IOException {
        getWriter().write(cbuf, off, len);
    }

    public void flush() throws IOException {
        getWriter().flush();
    }

    public void close() throws IOException {
        getWriter().close();
    }

    public void startDocument() throws IOException {
    }

    public void endDocument() throws IOException {
        closeOpeningStartTag(getWriter());
    }

    public String getContentType() {
        return contentType_;
    }

    public void setContentType(String contentType) {
        contentType_ = contentType;
    }

    public String getCharacterEncoding() {
        if (characterEncoding_ == null) {
            return DEFAULT_CHARACTER_ENCODING;
        }
        return characterEncoding_;
    }

    public void setCharacterEncoding(String characterEncoding) {
        characterEncoding_ = characterEncoding;
    }

    public Writer getWriter() {
        return writer_;
    }

    public void setWriter(Writer writer) {
        writer_ = writer;
    }

}
