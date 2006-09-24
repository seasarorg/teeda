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
package org.seasar.teeda.extension.el;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import junitx.framework.ObjectAssert;

/**
 * @author shot
 * 
 */
public class ExtensionPropertyResolverTest extends TestCase {

    public void testGetAndSetValue_Array() throws Exception {
        final ExtensionPropertyResolver resolver = new ExtensionPropertyResolver();
        final HogeDto h1 = new HogeDto("a", 1, true);
        final HogeDto h2 = new HogeDto("b", 2, false);
        final HogeDto h3 = new HogeDto("c", 3, true);
        final HogeDto[] h = new HogeDto[] { h1, h2, h3 };
        final HogePage page = new HogePage();
        page.setDto(h);
        final Object value = resolver.getValue(page, "dto");
        System.out.println(value);
        ObjectAssert.assertInstanceOf("シリアライズされStringであること", String.class,
                value);

        final HogePage page2 = new HogePage();
        resolver.setValue(page2, "dto", value);
        final HogeDto[] hogeDto = page2.getDto();
        assertEquals("a", hogeDto[0].getName());
        assertTrue(hogeDto[0].getNum() == 1);
        assertTrue(hogeDto[0].isB());
        assertEquals("b", hogeDto[1].getName());
        assertTrue(hogeDto[1].getNum() == 2);
        assertFalse(hogeDto[1].isB());
        assertEquals("c", hogeDto[2].getName());
        assertTrue(hogeDto[2].getNum() == 3);
        assertTrue(hogeDto[2].isB());
    }

    public void testGetAndSetValue_Array_null() throws Exception {
        final ExtensionPropertyResolver resolver = new ExtensionPropertyResolver();
        final HogePage page = new HogePage();
        page.setDto(null);
        final Object value = resolver.getValue(page, "dto");
        System.out.println(value);

        final HogePage page2 = new HogePage();
        resolver.setValue(page2, "dto", value);
        final HogeDto[] hogeDto = page2.getDto();
        assertEquals(null, hogeDto);
    }

    public void testGetAndSetValue_List() throws Exception {
        final ExtensionPropertyResolver resolver = new ExtensionPropertyResolver();
        final List list1 = new ArrayList();
        {
            final HogeDto h1 = new HogeDto("a", 1, true);
            list1.add(h1);
        }
        {
            final HogeDto h1 = new HogeDto("b", 2, false);
            list1.add(h1);
        }
        {
            final HogeDto h1 = new HogeDto("c", 3, true);
            list1.add(h1);
        }
        final HogePage page1 = new HogePage();
        page1.setList(list1);

        final Object value = resolver.getValue(page1, "list");
        System.out.println(value);
        ObjectAssert.assertInstanceOf("シリアライズされStringであること", String.class,
                value);

        final HogePage page2 = new HogePage();
        resolver.setValue(page2, "list", value);
        final List list2 = page2.getList();
        assertEquals(3, list2.size());
        {
            final HogeDto dto = (HogeDto) list2.get(0);
            assertEquals("a", dto.getName());
            assertTrue(dto.getNum() == 1);
            assertTrue(dto.isB());
        }
        {
            final HogeDto dto = (HogeDto) list2.get(1);
            assertEquals("b", dto.getName());
            assertTrue(dto.getNum() == 2);
            assertFalse(dto.isB());
        }
        {
            final HogeDto dto = (HogeDto) list2.get(2);
            assertEquals("c", dto.getName());
            assertTrue(dto.getNum() == 3);
            assertTrue(dto.isB());
        }
    }

    public void testGetValue_Array_null() throws Exception {
        // ## Arrange ##
        final ExtensionPropertyResolver resolver = new ExtensionPropertyResolver();

        final HogePage page1 = new HogePage();
        page1.setDto(null);

        // ## Act ##
        final Object value = resolver.getValue(page1, "dto");

        // ## Assert ##
        assertEquals(null, value);
    }

    public void testGetValue_List_null() throws Exception {
        // ## Arrange ##
        final ExtensionPropertyResolver resolver = new ExtensionPropertyResolver();

        final HogePage page1 = new HogePage();
        page1.setList(null);

        // ## Act ##
        final Object value = resolver.getValue(page1, "list");

        // ## Assert ##
        assertEquals(null, value);
    }

    public void testCopyToMap() throws Exception {
        // ## Arrange ##
        final ExtensionPropertyResolver resolver = new ExtensionPropertyResolver();
        final HogeDto dto = new HogeDto("aa", 123, true);
        dto.xxx = "x";
        dto.yyy = "y";
        dto.setZzz("z");

        // ## Act ##
        final Map map = new HashMap();
        resolver.copyToMap(dto, map);

        // ## Assert ##
        assertEquals("aa", map.get("name"));
        assertEquals(new Integer(123), map.get("num"));
        assertEquals(Boolean.TRUE, map.get("b"));
        assertEquals(null, map.get("x"));
        assertEquals(null, map.get("y"));
        assertEquals(null, map.get("x"));
    }

    public void testCopyToBean() throws Exception {
        // ## Arrange ##
        final ExtensionPropertyResolver resolver = new ExtensionPropertyResolver();
        final Map map = new HashMap();
        map.put("name", "AAA");
        map.put("b", Boolean.TRUE);
        map.put("num", new Integer(7650));
        map.put("xxx", "x");
        map.put("yyy", "y");
        map.put("zzz", "z");

        // ## Act ##
        final HogeDto dto = new HogeDto();
        resolver.copyToBean(map, dto);

        // ## Assert ##
        assertEquals("AAA", dto.getName());
        assertEquals(7650, dto.getNum());
        assertEquals(true, dto.isB());
        assertEquals(null, dto.xxx);
        assertEquals(null, dto.yyy);
        assertEquals(null, dto.zzz);
    }

    public void testCopyToBean_AnotherType() throws Exception {
        // ## Arrange ##
        final ExtensionPropertyResolver resolver = new ExtensionPropertyResolver();
        final Map map = new HashMap();
        map.put("name", new Integer(9987));
        map.put("num", "1234");

        // ## Act ##
        final HogeDto dto = new HogeDto();
        resolver.copyToBean(map, dto);

        // ## Assert ##
        assertEquals(0, dto.getNum());
    }

    public void testConvertToBean() throws Exception {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##
    }

    public static class HogePage implements Serializable {

        private static final long serialVersionUID = 1L;

        private HogeDto[] dto;

        private List list;

        public HogePage() {
        }

        public HogeDto[] getDto() {
            return dto;
        }

        public void setDto(final HogeDto[] dto) {
            this.dto = dto;
        }

        public List getList() {
            return list;
        }

        public void setList(List list) {
            this.list = list;
        }

    }

    public static class HogeDto implements Serializable {

        private static final long serialVersionUID = 1L;

        private String name;

        private int num;

        private boolean b;

        private String xxx;

        // read only
        private String yyy;

        // write only 
        private String zzz;

        public boolean isB() {
            return b;
        }

        public void setB(final boolean b) {
            this.b = b;
        }

        public int getNum() {
            return num;
        }

        public void setNum(final int num) {
            this.num = num;
        }

        public HogeDto() {
        }

        public HogeDto(final String name, final int num, final boolean b) {
            this.name = name;
            this.num = num;
            this.b = b;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getYyy() {
            return yyy;
        }

        public void setZzz(String ccc) {
            this.zzz = ccc;
        }
    }
}
