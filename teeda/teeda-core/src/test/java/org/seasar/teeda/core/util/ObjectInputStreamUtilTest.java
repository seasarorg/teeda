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
package org.seasar.teeda.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class ObjectInputStreamUtilTest extends TeedaTestCase {

    /**
     * Constructor for ObjectInputStreamUtilTest.
     * 
     * @param name
     */
    public ObjectInputStreamUtilTest(String name) {
        super(name);
    }

    public void testGetObject_Deserialize() throws Exception {
        String path = convertPath("testObjectInputStreamUtil.ser");
        InputStream is = ResourceUtil.getResourceAsStream(path);
        Object o = ObjectInputStreamUtil.getObject(is);
        assertNotNull(o);
    }

    public void testGetObject_IO_fail() throws Exception {
        InputStream is = new IOExceptionOccurInputStream();
        try {
            Object o = ObjectInputStreamUtil.getObject(is);
            fail();
        } catch (IORuntimeException e) {
            success();
        }
    }

    // Serialized object
    public static class Hoge implements Serializable {
        private String name_;

        public void setName(String name) {
            name_ = name;
        }

        public String getName() {
            return name_;
        }
    }

    private static class IOExceptionOccurInputStream extends InputStream {

        public int available() throws IOException {
            return super.available();
        }

        public void close() throws IOException {
            super.close();
        }

        public synchronized void mark(int arg0) {
            super.mark(arg0);
        }

        public boolean markSupported() {
            return super.markSupported();
        }

        public int read(byte[] arg0, int arg1, int arg2) throws IOException {
            throw new IOException();
        }

        public int read(byte[] arg0) throws IOException {
            throw new IOException();
        }

        public synchronized void reset() throws IOException {
            super.reset();
        }

        public long skip(long arg0) throws IOException {
            return super.skip(arg0);
        }

        public int read() throws IOException {
            throw new IOException();
        }

    }
}
