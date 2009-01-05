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
package org.seasar.teeda.core.mock;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.servlet.jsp.tagext.BodyContent;

/**
 * @author yone
 */
public class MockBodyContent extends BodyContent {

    public MockBodyContent() {
        super(new MockJspWriter());
    }

    public MockBodyContent(MockJspWriter writer) {
        super(writer);
    }

    public Reader getReader() {
        return null;
    }

    public String getString() {
        return null;
    }

    public void writeOut(Writer arg0) throws IOException {
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

    public void close() throws IOException {
    }

    public int getRemaining() {
        return 0;
    }

    public void write(char[] cbuf, int off, int len) throws IOException {
    }

}
