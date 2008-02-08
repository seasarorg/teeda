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

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.exception.StreamClosedException;

public class BodyContentImpl extends BodyContent {

    private static final int DEFAULT_BUFFER_SIZE = JspConstants.DEFAULT_BUFFER_SIZE;

    private char[] cb;

    private int nextChar = 0;

    private boolean closed = false;

    public BodyContentImpl(JspWriter writer) {
        this(writer, DEFAULT_BUFFER_SIZE);
    }

    public BodyContentImpl(JspWriter writer, int bufferSize) {
        super(writer);
        cb = new char[bufferSize];
    }

    protected void ensureOpen() throws IOException {
        if (closed) {
            throw new StreamClosedException();
        }
    }

    public void write(int c) throws IOException {
        ensureOpen();
        reallocBufferIfNeed(1);
        cb[nextChar++] = (char) c;
    }

    protected void reallocBufferIfNeed(int len) {
        int available = cb.length - nextChar;
        if (len < available) {
            return;
        }
        char[] tmp = null;
        if (len <= cb.length * 3 + available) {
            tmp = new char[cb.length * 4];
        } else {
            tmp = new char[cb.length + len];
        }
        System.arraycopy(cb, 0, tmp, 0, cb.length);
        cb = tmp;
        tmp = null;
    }

    public void write(char[] cbuf, int off, int len) throws IOException {
        ensureOpen();
        if ((off < 0) || (off > cbuf.length) || (len < 0)
                || ((off + len) > cbuf.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }
        reallocBufferIfNeed(len);
        System.arraycopy(cbuf, off, cb, nextChar, len);
        nextChar += len;
    }

    public void write(char buf[]) throws IOException {
        write(buf, 0, buf.length);
    }

    public void write(String s, int off, int len) throws IOException {
        ensureOpen();
        reallocBufferIfNeed(len);
        s.getChars(off, off + len, cb, nextChar);
        nextChar += len;
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

    public void clear() throws IOException {
        clearBuffer();
    }

    public void clearBuffer() throws IOException {
        nextChar = 0;
    }

    public void close() throws IOException {
        cb = null;
        closed = true;
    }

    public int getRemaining() {
        return bufferSize - nextChar;
    }

    public Reader getReader() {
        return new CharArrayReader(cb, 0, nextChar);
    }

    public String getString() {
        return new String(cb, 0, nextChar);
    }

    public void writeOut(Writer out) throws IOException {
        out.write(cb, 0, nextChar);
    }
}