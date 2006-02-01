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
import java.util.zip.GZIPInputStream;

import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class GZIPInputStreamUtilTest extends TeedaTestCase {

    public void testGetInputStream() throws Exception {
        String path = convertPath("testGZIPInputStreamUtil.gz");
        InputStream is = ResourceUtil.getResourceAsStream(path);
        InputStream o = null;
        try {
            o = GZIPInputStreamUtil.getInputStream(is);
            assertNotNull(o);
            assertTrue(o instanceof GZIPInputStream);
        } finally {
            is.close();
            o.close();
        }
    }

    public void testGetInputStream_throwIOException() throws Exception {
        InputStream In = new IOExceptionOccurInputStream();
        try {
            GZIPInputStreamUtil.getInputStream(In);
            fail();
        } catch (IORuntimeException e) {
            success();
        }
    }

    private static class IOExceptionOccurInputStream extends InputStream {

        public int read() throws IOException {
            throw new IOException();
        }

    }
}
