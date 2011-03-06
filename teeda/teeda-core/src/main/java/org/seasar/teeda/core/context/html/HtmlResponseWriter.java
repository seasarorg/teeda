/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import org.seasar.framework.util.ArrayUtil;
import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.EmptyElementUtil;
import org.seasar.teeda.core.util.HTMLEncodeUtil;

/**
 * @author manhole
 * @author shot
 * @author yone
 */
// TODO handle "javascript: xxxx" attribute (really necessary?)
public class HtmlResponseWriter extends ResponseWriter {

    private static final char[] reserved = { ';', '/', '?', ':', '@', '&', '=',
            '+', '$', ',' };

    private static final char[] unescape;

    static {
        unescape = new char[reserved.length + 1];
        System.arraycopy(reserved, 0, unescape, 0, reserved.length);
        unescape[unescape.length - 1] = '#';
    }

    private Writer writer;

    private String contentType;

    private String characterEncoding;

    private boolean startTagOpening;

    private boolean shouldEscape = DEFAULT_ESCAPE;

    private static final boolean DEFAULT_ESCAPE = true;

    public void startElement(final String name,
            final UIComponent componentForElement) throws IOException {
        AssertionUtil.assertNotNull("name", name);
        final Writer writer = getWriter();
        closeStartTagIfOpening(writer);
        writer.write("<");
        writer.write(name);
        // TODO need "style" element ?
        if ("script".equalsIgnoreCase(name)) {
            shouldEscape = false;
        } else {
            shouldEscape = true;
        }
        startTagOpening = true;
    }

    protected void closeStartTagIfOpening(final Writer writer)
            throws IOException {
        if (startTagOpening) {
            writer.write(">");
            startTagOpening = false;
        }
    }

    public void endElement(final String name) throws IOException {
        AssertionUtil.assertNotNull("name", name);
        final Writer writer = getWriter();
        if (startTagOpening) {
            if (EmptyElementUtil.isEmptyElement(name)) {
                writer.write(" />");
            } else {
                writer.write(">");
                writer.write("</" + name + ">");
            }
            startTagOpening = false;
        } else {
            writer.write("</" + name + ">");
        }
        shouldEscape = DEFAULT_ESCAPE;
    }

    public void writeAttribute(final String name, final Object value,
            final String property) throws IOException {
        AssertionUtil.assertNotNull("name", name);
        if (!startTagOpening) {
            throw new IllegalStateException(
                    "there is no currently open element");
        }
        final String strValue = (value == null) ? "" : value.toString();
        final Writer writer = getWriter();
        writer.write(" ");
        writer.write(name);
        writer.write("=\"");
        writer.write(escapeAttribute(strValue));
        writer.write("\"");
    }

    public void writeURIAttribute(final String name, final Object value,
            final String property) throws IOException {
        AssertionUtil.assertNotNull("name", name);
        if (!startTagOpening) {
            throw new IllegalStateException(
                    "there is no currently open element");
        }
        final Writer writer = getWriter();
        final String strValue = value.toString();

        writer.write(" ");
        writer.write(name);
        writer.write("=\"");
        if (StringUtil.startsWithIgnoreCase(strValue, "javascript:")) {
            writer.write(escapeAttribute(strValue));
        } else {
            writer.write(encodeURIAttribute(strValue));
        }
        writer.write("\"");
    }

    public void writeComment(final Object comment) throws IOException {
        AssertionUtil.assertNotNull("comment", comment);
        final Writer writer = getWriter();
        closeStartTagIfOpening(writer);
        writer.write("<!--");
        writer.write(comment.toString());
        writer.write("-->");
    }

    public void writeText(final Object text, final String property)
            throws IOException {
        AssertionUtil.assertNotNull("text", text);
        final Writer writer = getWriter();
        closeStartTagIfOpening(writer);
        String str = text.toString();
        if (shouldEscape) {
            str = htmlSpecialChars(str);
        }
        writer.write(str);
    }

    public void writeText(final char text[], final int off, final int len)
            throws IOException {
        AssertionUtil.assertNotNull("text", text);
        writeText(new String(text, off, len), null);
    }

    protected String htmlSpecialChars(final String s) {
        return HTMLEncodeUtil.encode(s, true, true);
    }

    protected String escapeAttribute(final String s) {
        return HTMLEncodeUtil.encode(s, false, false);
    }

    public ResponseWriter cloneWithWriter(final Writer writer) {
        AssertionUtil.assertNotNull("writer", writer);
        final HtmlResponseWriter clone = new HtmlResponseWriter();
        clone.setWriter(writer);
        clone.setContentType(getContentType());
        clone.setCharacterEncoding(getCharacterEncoding());
        return clone;
    }

    public void write(final char[] cbuf, final int off, final int len)
            throws IOException {
        final Writer writer = getWriter();
        closeStartTagIfOpening(writer);
        writer.write(cbuf, off, len);
    }

    public void write(final char[] cbuf) throws IOException {
        final Writer writer = getWriter();
        closeStartTagIfOpening(writer);
        writer.write(cbuf);
    }

    public void write(final int c) throws IOException {
        final Writer writer = getWriter();
        closeStartTagIfOpening(writer);
        writer.write(c);
    }

    public void write(final String str) throws IOException {
        final Writer writer = getWriter();
        closeStartTagIfOpening(writer);
        writer.write(str);
    }

    public void write(final String str, final int off, final int len)
            throws IOException {
        final Writer writer = getWriter();
        closeStartTagIfOpening(writer);
        writer.write(str, off, len);
    }

    public void flush() throws IOException {
        final Writer writer = getWriter();
        closeStartTagIfOpening(writer);
    }

    public void close() throws IOException {
        final Writer writer = getWriter();
        closeStartTagIfOpening(writer);
        writer.close();
    }

    public void startDocument() throws IOException {
    }

    public void endDocument() throws IOException {
        closeStartTagIfOpening(getWriter());
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(final String contentType) {
        this.contentType = contentType;
    }

    public String getCharacterEncoding() {
        if (characterEncoding == null) {
            characterEncoding = JsfConstants.DEFAULT_ENCODING;
        }
        return characterEncoding;
    }

    public void setCharacterEncoding(final String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(final Writer writer) {
        this.writer = writer;
    }

    protected String encodeURIAttribute(final String url) throws IOException {
        final char[] chars = url.toCharArray();
        final StringBuffer sb = new StringBuffer(url.length() + 100);
        final int length = chars.length;
        final String encoding = getCharacterEncoding();
        for (int i = 0; i < length; i++) {
            final char c = chars[i];
            if (ArrayUtil.contains(unescape, c)) {
                sb.append(c);
                if ('?' == c) {
                    if (i < length) {
                        sb.append(encodeQueryString(url.substring(i + 1)));
                    }
                    break;
                }
            } else {
                sb.append(URLEncoder.encode(String.valueOf(c), encoding));
            }
        }
        return new String(sb);
    }

    public String encodeQueryString(final String s) throws IOException {
        final char[] chars = s.toCharArray();
        final StringBuffer sb = new StringBuffer(s.length() + 32);
        final String encoding = getCharacterEncoding();
        for (int i = 0; i < chars.length; i++) {
            final char c = chars[i];
            switch (c) {
            case ' ': // 32
                sb.append("%20");
                break;
            case '!': // 33
                sb.append("!");
                break;
            case '#': // 35
                sb.append("#");
                break;
            case '%': // 37
                sb.append("%");
                break;
            case '&': // 38
                //sb.append("&");
                sb.append("&amp;");
                break;
            case '\'': // 39
                sb.append("'");
                break;
            case '(': // 40
                sb.append("(");
                break;
            case ')': // 41
                sb.append(")");
                break;
            case '+': // 43
                sb.append("+");
                break;
            case '/': // 47
                sb.append("/");
                break;
            case '=': // 61
                sb.append("=");
                break;
            case '~': // 126
                sb.append("~");
                break;
            default:
                sb.append(URLEncoder.encode(String.valueOf(c), encoding));
                break;
            }
        }
        return new String(sb);
    }

    public String toString() {
        return writer.toString();
    }

}
