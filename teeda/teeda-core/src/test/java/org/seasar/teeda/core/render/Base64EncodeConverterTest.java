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
package org.seasar.teeda.core.render;

import java.io.Serializable;

import javax.faces.application.StateManager;
import javax.faces.application.StateManager.SerializedView;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class Base64EncodeConverterTest extends TeedaTestCase {

    public void testEncodeAndDecode() throws Exception {
        AAA target1 = new AAA("aaa");
        AAA target2 = new AAA("bbb");
        StateManager s = getApplication().getStateManager();
        SerializedView sView = s.new SerializedView(target1, target2);
        Base64EncodeConverter converter = new Base64EncodeConverter();
        String encodedString = converter.getAsEncodeString(sView);
        AAA aaa = (AAA) converter.getAsDecodeObject(encodedString);
        assertEquals("aaa", aaa.getName());
    }

    //EOFException?
    public void fixme_testEncodeAndDecode_compressed() throws Exception {
        getServletContext().setInitParameter(JsfConstants.COMPRESS_STATE_ATTR,
                "true");
        AAA target1 = new AAA("aaa");
        AAA target2 = new AAA("bbb");
        StateManager s = getApplication().getStateManager();
        SerializedView sView = s.new SerializedView(target1, target2);
        Base64EncodeConverter converter = new Base64EncodeConverter();
        String encodedString = converter.getAsEncodeString(sView);
        AAA aaa = (AAA) converter.getAsDecodeObject(encodedString);
        assertEquals("aaa", aaa.getName());
    }

    public void testIsCompressRequested() throws Exception {
        getServletContext().setInitParameter(JsfConstants.COMPRESS_STATE_ATTR,
                "true");
        Base64EncodeConverter converter = new Base64EncodeConverter();
        assertTrue(converter.isCompressRequested());

        getServletContext().setInitParameter(JsfConstants.COMPRESS_STATE_ATTR,
                "false");
        assertFalse(converter.isCompressRequested());
    }

    public static class AAA implements Serializable {
        private String name_;

        public AAA(String name) {
            name_ = name;
        }

        public String getName() {
            return name_;
        }
    }
}
