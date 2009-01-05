/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.servlet.ServletResponse;
import javax.servlet.jsp.JspWriter;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.exception.StreamClosedException;

public class JspWriterImpl extends JspWriter {

    private Writer out;

    private ServletResponse response;

    private char cb[];

    private int nextChar;

    private boolean closed = false;

    private String encoding;

    public JspWriterImpl() {
        super(JspConstants.DEFAULT_BUFFER_SIZE, true);
    }

    public JspWriterImpl(ServletResponse response) {
        this(response, JspConstants.DEFAULT_BUFFER_SIZE, true);
    }

    public JspWriterImpl(ServletResponse response, int size, boolean autoFlush) {
        super(size, autoFlush);
        this.response = response;
        cb = new char[size];
        nextChar = 0;
    }

    public JspWriterImpl(ServletResponse response, int size, boolean autoFlush,
            String encoding) {
        this(response, size, autoFlush);
        this.encoding = encoding;
    }

    protected final void flushBuffer() throws IOException {
        if (bufferSize == 0) {
            return;
        }
        ensureOpen();
        if (nextChar == 0) {
            return;
        }
        initOut();
        out.write(cb, 0, nextChar);
        nextChar = 0;
    }

    protected void initOut() throws IOException {
        if (out == null) {
            String enc = encoding;
            if (encoding == null || "null".equals(encoding)) {
                enc = response.getCharacterEncoding();
            }
            if (enc != null) {
                out = new OutputStreamWriter(response.getOutputStream(), enc);
            } else {
                out = response.getWriter();
            }
        }
    }

    public final void clear() throws IOException {
        clearBuffer();
    }

    public void clearBuffer() throws IOException {
        ensureOpen();
        nextChar = 0;
    }

    public void flush() throws IOException {
        flushBuffer();
        if (out != null) {
            out.flush();
            response.flushBuffer();
        }
    }

    public void close() throws IOException {
        if (response == null || closed) {
            return;
        }
        flush();
        if (out != null) {
            out.close();
        }
        out = null;
        response = null;
        closed = true;
    }

    public int getRemaining() {
        return bufferSize - nextChar;
    }

    protected void ensureOpen() throws IOException {
        if (response == null || closed) {
            throw new StreamClosedException();
        }
    }

    public void write(int c) throws IOException {
        ensureOpen();
        if (bufferSize == 0) {
            initOut();
            out.write(c);
        } else {
            checkBufferOverflow();
            cb[nextChar++] = (char) c;
        }
    }

    protected void checkBufferOverflow() throws IOException {
        if (nextChar >= bufferSize) {
            if (autoFlush) {
                flushBuffer();
            } else {
                throw new IOException("buffer overflow");
            }
        }
    }

    public void write(char cbuf[], int off, int len) throws IOException {
        ensureOpen();
        if (bufferSize == 0) {
            initOut();
            out.write(cbuf, off, len);
            return;
        }
        if ((off < 0) || (off > cbuf.length) || (len < 0)
                || ((off + len) > cbuf.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }
        int b = off;
        int t = off + len;
        while (b < t) {
            int d = Math.min(bufferSize - nextChar, t - b);
            System.arraycopy(cbuf, b, cb, nextChar, d);
            b += d;
            nextChar += d;
            checkBufferOverflow();
        }
    }

    public void write(char buf[]) throws IOException {
        write(buf, 0, buf.length);
    }

    public void write(String s, int off, int len) throws IOException {
        ensureOpen();
        if (bufferSize == 0) {
            initOut();
            out.write(s, off, len);
            return;
        }
        int b = off, t = off + len;
        while (b < t) {
            int d = Math.min(bufferSize - nextChar, t - b);
            s.getChars(b, b + d, cb, nextChar);
            b += d;
            nextChar += d;
            checkBufferOverflow();
        }
    }

    public void write(String s) throws IOException {
        write(s, 0, s.length());
    }

    public void newLine() throws IOException {
        write(JsfConstants.LINE_SP);
    }

    public void print(boolean b) throws IOException {
        write(b ? "true" : "false");
    }

    public void print(char c) throws IOException {
        write(String.valueOf(c));
    }

    public void print(int i) throws IOException {
        write(String.valueOf(i));
    }

    public void print(long l) throws IOException {
        write(String.valueOf(l));
    }

    public void print(float f) throws IOException {
        write(String.valueOf(f));
    }

    public void print(double d) throws IOException {
        write(String.valueOf(d));
    }

    public void print(char[] s) throws IOException {
        write(s);
    }

    public void print(String s) throws IOException {
        if (s == null) {
            s = "null";
        }
        write(s);
    }

    public void print(Object o) throws IOException {
        write(String.valueOf(o));
    }

    public void println() throws IOException {
        newLine();
    }

    public void println(boolean b) throws IOException {
        print(b);
        println();
    }

    public void println(char c) throws IOException {
        print(c);
        println();
    }

    public void println(int i) throws IOException {
        print(i);
        println();
    }

    public void println(long l) throws IOException {
        print(l);
        println();
    }

    public void println(float f) throws IOException {
        print(f);
        println();
    }

    public void println(double d) throws IOException {
        print(d);
        println();
    }

    public void println(char[] s) throws IOException {
        print(s);
        println();
    }

    public void println(String s) throws IOException {
        print(s);
        println();
    }

    public void println(Object o) throws IOException {
        print(o);
        println();
    }
}