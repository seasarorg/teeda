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
package org.seasar.teeda.ajax;

import java.util.ArrayList;
import java.util.HashMap;

import org.seasar.framework.mock.servlet.MockHttpServletResponse;
import org.seasar.framework.unit.S2FrameworkTestCase;

public class AjaxUtilTest extends S2FrameworkTestCase {

    public void testToString_null() throws Exception {
        assertEquals("null", AjaxUtil.toJson(null));
    }

    public void testToJson_boolean() throws Exception {
        assertEquals("true", AjaxUtil.toJson(Boolean.TRUE));
        assertEquals("false", AjaxUtil.toJson(Boolean.FALSE));
    }

    public void testToJson_string() throws Exception {
        assertEquals("\"a\"", AjaxUtil.toJson("a"));
    }

    public void testToJson_float() throws Exception {
        assertEquals("1.0", AjaxUtil.toJson(new Float(1.0f)));
        try {
            AjaxUtil.toJson(new Float(Float.NaN));
            fail();
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    public void testToJson_double() throws Exception {
        assertEquals("1.0", AjaxUtil.toJson(new Double(1.0d)));
        try {
            AjaxUtil.toJson(new Double(Double.NaN));
            fail();
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    public void testToJson_number() throws Exception {
        assertEquals("100", AjaxUtil.toJson(new Integer(100)));
    }

    public void testToJson_array() throws Exception {
        assertEquals("[]", AjaxUtil.toJson(new Object[0]));
        assertEquals("[1]", AjaxUtil.toJson(new Integer[] { new Integer(1) }));
        assertEquals("[1,2]", AjaxUtil.toJson(new Integer[] { new Integer(1),
                new Integer(2) }));
    }

    public void testToJson_collection() throws Exception {
        assertEquals("[]", AjaxUtil.toJson(new ArrayList()));
        ArrayList list = new ArrayList();
        list.add(new Integer(1));
        assertEquals("[1]", AjaxUtil.toJson(list));
    }

    public void testToJson_map() throws Exception {
        assertEquals("{}", AjaxUtil.toJson(new HashMap()));
        HashMap map = new HashMap();
        map.put("aaa", new Integer(1));
        assertEquals("{aaa:1}", AjaxUtil.toJson(map));
        map.put("bbb", new Integer(2));
        assertEquals("{aaa:1,bbb:2}", AjaxUtil.toJson(map));
        map.put("bbb", new HashMap());
        assertEquals("{aaa:1,bbb:{}}", AjaxUtil.toJson(map));
    }

    public void testToJson_PrimitiveArray() throws Exception {
        assertEquals("[1]", AjaxUtil.toJson(new int[] { 1 }));
        assertEquals("[1,2,3]", AjaxUtil.toJson(new int[] { 1, 2, 3 }));
        assertEquals("[true]", AjaxUtil.toJson(new boolean[] { true }));
        assertEquals("[true,false,true]", AjaxUtil.toJson(new boolean[] { true,
                false, true }));
        assertEquals("[1.11]", AjaxUtil.toJson(new double[] { 1.11 }));
        assertEquals("[1.11,2.22,3.33]", AjaxUtil.toJson(new double[] { 1.11,
                2.22, 3.33 }));

        assertEquals(
                "{bbb:[11,22,33],ccc:[true,false,true],ddd:[1.11,2.22,3.33]}",
                AjaxUtil.toJson(new Test2()));
    }

    public void testToJson_bean() throws Exception {
        String s = AjaxUtil.toJson(new MyBean());
        System.out.println(s);
        assertTrue(s.indexOf("aaa:null") > 0);
        assertTrue(s.indexOf("bbb:null") > 0);

        MyBean bean = new MyBean();
        bean.setBbb(new MyBean());
        s = AjaxUtil.toJson(bean);
        System.out.println(s);
    }

    public void testQuote() throws Exception {
        assertEquals("\"a\"", AjaxUtil.quote("a"));
        assertEquals("\"\\t\"", AjaxUtil.quote("\t"));
        assertEquals("\"\\n\"", AjaxUtil.quote("\n"));
        assertEquals("\"\\u0000\"", AjaxUtil.quote("\0"));
        assertEquals("\"\\\"\"", AjaxUtil.quote("\""));
        assertEquals("\"\\\\\"", AjaxUtil.quote("\\"));
        assertEquals("\"\\/\"", AjaxUtil.quote("/"));
    }

    public void testSetContentType() throws Exception {
        MockHttpServletResponse response = getResponse();
        AjaxUtil.setContentType(response, null);
        assertEquals(AjaxConstants.CONTENT_TYPE_JSON, response.getContentType());

        response.setContentType(null);
        AjaxUtil.setContentType(response, "{\"value\":2,\"name\":\"a\"}");
        assertEquals(AjaxConstants.CONTENT_TYPE_JSON, response.getContentType());

        response.setContentType(null);
        AjaxUtil.setContentType(response,
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        assertEquals(AjaxConstants.CONTENT_TYPE_XML, response.getContentType());

        response.setContentType(null);
        AjaxUtil.setContentType(response, "<html><body></body></html>");
        assertEquals(AjaxConstants.CONTENT_TYPE_HTML, response.getContentType());

        response.setContentType(null);
        AjaxUtil.setContentType(response, "aaaaa");
        assertEquals(AjaxConstants.CONTENT_TYPE_TEXT, response.getContentType());
    }

    public static class MyBean {
        private String aaa;

        private MyBean bbb;

        public String getAaa() {
            return aaa;
        }

        public void setAaa(String aaa) {
            this.aaa = aaa;
        }

        public MyBean getBbb() {
            return bbb;
        }

        public void setBbb(MyBean bbb) {
            this.bbb = bbb;
        }
    }

    public static class Test1 {
        private String aaa = "hoge";

        private int bbb = 100;

        private boolean ccc = true;

        private double ddd = 3.14;

        public String getAaa() {
            return aaa;
        }

        public int getBbb() {
            return bbb;
        }

        public boolean isCcc() {
            return ccc;
        }

        public double getDdd() {
            return ddd;
        }
    }

    public static class Test2 {

        private int[] bbb = { 11, 22, 33 };

        private boolean[] ccc = { true, false, true };

        private double[] ddd = { 1.11, 2.22, 3.33 };

        public int[] getBbb() {
            return bbb;
        }

        public boolean[] getCcc() {
            return ccc;
        }

        public double[] getDdd() {
            return ddd;
        }
    }
}