/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.spike;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.GZIPOutputStream;

import junit.framework.TestCase;

import org.seasar.framework.util.Base64Util;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.framework.util.OutputStreamUtil;
import org.seasar.teeda.core.util.ObjectInputStreamUtil;
import org.seasar.teeda.core.util.ObjectOutputStreamUtil;

/**
 * @author shot
 * 
 */
public class Base64LearningTest extends TestCase {

    public void testEncodeAndDecode() throws Exception {
        String[] s = new String[] { "a", "b", "c" };
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream gzipOut = null;
        ObjectOutputStream oos = null;
        oos = ObjectOutputStreamUtil.getOutputStream(bos);
        ObjectOutputStreamUtil.writeObject(oos, s);
        String str = Base64Util.encode(bos.toByteArray());
        OutputStreamUtil.close(oos);
        OutputStreamUtil.close(gzipOut);
        OutputStreamUtil.close(bos);

        byte[] data = Base64Util.decode(str);
        final InputStream bis = new ByteArrayInputStream(data);
        InputStream is = null;
        ObjectInputStream ois = null;
        ois = ObjectInputStreamUtil.getInputStream(bis);
        Object o = ObjectInputStreamUtil.readObject(ois);
        InputStreamUtil.close(ois);
        InputStreamUtil.close(is);
        InputStreamUtil.close(bis);

        assertNotNull(o);
        String[] s2 = (String[]) o;
        assertTrue(s2.length == 3);
        assertEquals("a", s2[0]);
        assertEquals("b", s2[1]);
        assertEquals("c", s2[2]);
    }

    public void testEncodeAndDecode2() throws Exception {
        Hoge h1 = new Hoge("a", "A");
        Hoge h2 = new Hoge("b", "B");
        Hoge h3 = new Hoge("c", "C");
        Hoge[] s = new Hoge[] { h1, h2, h3 };
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream gzipOut = null;
        ObjectOutputStream oos = null;
        oos = ObjectOutputStreamUtil.getOutputStream(bos);
        ObjectOutputStreamUtil.writeObject(oos, s);
        String str = Base64Util.encode(bos.toByteArray());
        OutputStreamUtil.close(oos);
        OutputStreamUtil.close(gzipOut);
        OutputStreamUtil.close(bos);

        byte[] data = Base64Util.decode(str);
        final InputStream bis = new ByteArrayInputStream(data);
        InputStream is = null;
        ObjectInputStream ois = null;
        ois = ObjectInputStreamUtil.getInputStream(bis);
        Object o = ObjectInputStreamUtil.readObject(ois);
        InputStreamUtil.close(ois);
        InputStreamUtil.close(is);
        InputStreamUtil.close(bis);

        assertNotNull(o);
        Hoge[] s2 = (Hoge[]) o;
        assertTrue(s2.length == 3);
        assertEquals("a", s2[0].getName());
        assertEquals("b", s2[1].getName());
        assertEquals("c", s2[2].getName());
    }

    public static class Hoge implements Serializable {
        private String name;

        private String value;

        public Hoge(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
