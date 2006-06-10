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
package org.seasar.teeda.ajax.translator;

import java.util.ArrayList;
import java.util.List;

import org.seasar.framework.mock.servlet.MockHttpServletResponse;
import org.seasar.teeda.ajax.AjaxConstants;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class AjaxTranslatorTest extends TeedaTestCase {

    public void testSimpleString() throws Exception {
        assertEquals("\"a\"", AjaxTranslator.translate("a"));
    }

    public void testSimpleNumber() throws Exception {
        assertEquals("1", AjaxTranslator.translate(new Integer(1)));
    }

    public void testSimpleNull() throws Exception {
        assertNull(AjaxTranslator.translate(null));
    }

    public void testSimpleArray() throws Exception {
        Object[] array = new Object[] { "a", "b", new Integer(3) };
        assertEquals("[\"a\",\"b\",3]", AjaxTranslator.translate(array));
    }

    public void testSimpleList() throws Exception {
        List list = new ArrayList();
        list.add("a");
        list.add("b");
        list.add(new Integer(3));
        assertEquals("[\"a\",\"b\",3]", AjaxTranslator.translate(list));
    }

    public void testSimpleBoolean() throws Exception {
        Boolean b = new Boolean(true);
        assertEquals("true", AjaxTranslator.translate(b));
    }

    public void testSimpleObject() throws Exception {
        Hoge hoge = new Hoge();
        hoge.setName("a");
        hoge.setValue(new Integer(2));
        System.out.println(AjaxTranslator.translate(hoge));
        assertEquals("{\"value\":2,\"name\":\"a\"}", AjaxTranslator.translate(hoge));
    }

    public void testSetContentType() throws Exception {
        MockHttpServletResponse response = getResponse();
        AjaxTranslator.setContentType(response, null);
        assertEquals(AjaxConstants.CONTENT_TYPE_JSON, response.getContentType());
        
        response.setContentType(null);
        AjaxTranslator.setContentType(response, "{\"value\":2,\"name\":\"a\"}");
        assertEquals(AjaxConstants.CONTENT_TYPE_JSON, response.getContentType());
        
        response.setContentType(null);
        AjaxTranslator.setContentType(response, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        assertEquals(AjaxConstants.CONTENT_TYPE_XML, response.getContentType());

        response.setContentType(null);
        AjaxTranslator.setContentType(response, "<html><body></body></html>");
        assertEquals(AjaxConstants.CONTENT_TYPE_HTML, response.getContentType());

        response.setContentType(null);
        AjaxTranslator.setContentType(response, "aaaaa");
        assertEquals(AjaxConstants.CONTENT_TYPE_TEXT, response.getContentType());
    }
    
    public static class Hoge {
        private String name;

        private Object value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

    }
}
