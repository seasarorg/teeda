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
package org.seasar.teeda.core.mock;

import java.io.IOException;
import java.io.OutputStream;

import javax.faces.context.ResponseStream;

/**
 * @author shot
 * 
 */
public class MockResponseStream extends ResponseStream {

    private final OutputStream out_;

    public MockResponseStream(final OutputStream out) {
        out_ = out;
    }

    public void write(int b) throws IOException {
        out_.write(b);
    }

    public void close() throws IOException {
        out_.close();
    }

    public void flush() throws IOException {
        out_.flush();
    }

    public void write(byte[] bytes) throws IOException {
        out_.write(bytes, 0, bytes.length);
    }

    public void write(byte[] bytes, int off, int len) throws IOException {
        out_.write(bytes, off, len);
    }

}
