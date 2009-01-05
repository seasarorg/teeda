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
package org.seasar.teeda.core.util;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.seasar.framework.exception.IORuntimeException;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class ObjectOutputStreamUtilTest extends TeedaTestCase {

    public void testGetOutputStream() throws Exception {
        OutputStream out = new MockOutputStream();
        ObjectOutputStream o = ObjectOutputStreamUtil.getOutputStream(out);
        assertNotNull(o);
    }

    public void testGetOutputStream_IO_fail() throws Exception {
        OutputStream out = new IOExceptionOccureOutputStream();
        try {
            ObjectOutputStreamUtil.getOutputStream(out);
            fail();
        } catch (IORuntimeException e) {
            success();
        }
    }

    public static class MockOutputStream extends OutputStream {

        public void write(int arg0) throws IOException {
        }

    }

    public static class IOExceptionOccureOutputStream extends OutputStream {

        public void write(int arg0) throws IOException {
            throw new IOException();
        }

    }

}
