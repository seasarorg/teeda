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
package org.seasar.teeda.core.render;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.faces.application.StateManager.SerializedView;
import javax.faces.internal.FacesConfigOptions;

import org.seasar.framework.util.InputStreamUtil;
import org.seasar.teeda.core.render.html.StructureAndState;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class Base64EncodeConverterTest extends TeedaTestCase {

    public void testDecode_blank() throws Exception {
        Base64EncodeConverter converter = new Base64EncodeConverter();
        final String encoded = converter.getAsEncodeString("");
        System.out.println(encoded);
        final Object decoded = converter.getAsDecodeObject(encoded);
        System.out.println(decoded);
    }

    public void testEncodeAndDecode() throws Exception {
        Base64EncodeConverter encodeConverter = new Base64EncodeConverter();
        final String encoded = encodeConverter.getAsEncodeString(new Integer(
                7650));
        final Object decoded = encodeConverter.getAsDecodeObject(encoded);
        assertEquals(new Integer(7650), decoded);
    }

    public void testEncodeAndDecode_NoCompressed() throws Exception {
        // ## Arrange ##
        SerializedView serializedView = getApplication().getStateManager().new SerializedView(
                new AAA("aaaStructure"), new AAA("bbbState"));
        Base64EncodeConverter converter = new Base64EncodeConverter() {
            protected boolean isCompressRequested() {
                return false;
            }
        };

        // ## Act ##  
        final String encodedString = converter
                .getAsEncodeString(new StructureAndState(serializedView));
        final StructureAndState decoded = (StructureAndState) converter
                .getAsDecodeObject(encodedString);

        // ## Assert ##
        AAA aaa = (AAA) decoded.getStructure();
        AAA bbb = (AAA) decoded.getState();
        assertEquals("aaaStructure", aaa.getName());
        assertEquals("bbbState", bbb.getName());
    }

    public void testEncodeAndDecode_Compressed() throws Exception {
        // ## Arrange ##
        SerializedView serializedView = getApplication().getStateManager().new SerializedView(
                new AAA("aaa"), new AAA("bbb"));
        Base64EncodeConverter converter = new Base64EncodeConverter() {
            protected boolean isCompressRequested() {
                return true;
            }
        };

        // ## Act ##
        final String encodedString = converter
                .getAsEncodeString(new StructureAndState(serializedView));
        final StructureAndState decoded = (StructureAndState) converter
                .getAsDecodeObject(encodedString);

        // ## Assert ##
        AAA aaa = (AAA) decoded.getStructure();
        AAA bbb = (AAA) decoded.getState();
        assertEquals("aaa", aaa.getName());
        assertEquals("bbb", bbb.getName());
    }

    public void testIsCompressRequested() throws Exception {
        FacesConfigOptions.setCompressState(true);
        Base64EncodeConverter converter = new Base64EncodeConverter();
        assertTrue(converter.isCompressRequested());

        FacesConfigOptions.setCompressState(false);
        assertFalse(converter.isCompressRequested());
    }

    public void testLearningGZIP() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gzipOut = new GZIPOutputStream(baos);
        gzipOut.write("12345".getBytes());
        gzipOut.finish(); // important.
        final byte[] out = baos.toByteArray();

        ByteArrayInputStream bais = new ByteArrayInputStream(out);
        GZIPInputStream gzipIn = new GZIPInputStream(bais);
        final byte[] read = InputStreamUtil.getBytes(gzipIn);
        assertEquals("12345", new String(read));
    }

    public static class AAA implements Serializable {

        private static final long serialVersionUID = 1L;

        private String name;

        public AAA(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
