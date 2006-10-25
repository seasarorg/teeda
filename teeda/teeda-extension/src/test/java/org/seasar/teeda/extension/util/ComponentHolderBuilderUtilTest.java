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
package org.seasar.teeda.extension.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import junitx.framework.ArrayAssert;
import junitx.framework.ListAssert;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * @author shot
 */
public class ComponentHolderBuilderUtilTest extends TestCase {

    public void testBuild_listString() throws Exception {
        List value = new ArrayList();
        value.add("aaa");
        value.add("bbb");
        value.add("ccc");
        ComponentHolder holder = ComponentHolderBuilderUtil.build(value);
        assertNotNull(holder);
        ListAssert.assertEquals(value, holder.getValue());
        System.out.println(holder.getComponentClassName());
    }

    public void testBuild_listNull() throws Exception {
        List value = new ArrayList();
        ComponentHolder holder = ComponentHolderBuilderUtil.build(value);
        assertNotNull(holder);
        ListAssert.assertEquals(value, holder.getValue());
        System.out.println(holder.getComponentClassName());
    }

    public void testBuild_listDto() throws Exception {
        List value = new ArrayList();
        {
            HogeDto dto = new HogeDto();
            dto.setName("aaa");
            value.add(dto);
        }
        {
            HogeDto dto = new HogeDto();
            dto.setName("bbb");
            value.add(dto);
        }
        {
            HogeDto dto = new HogeDto();
            dto.setName("ccc");
            value.add(dto);
        }
        ComponentHolder holder = ComponentHolderBuilderUtil.build(value);
        assertNotNull(holder);
        for (Iterator itr = holder.getValue().iterator(); itr.hasNext();) {
            Object o = itr.next();
            assertTrue(o instanceof Map);
            Map map = (Map) o;
            assertNotNull(map);
            assertNotNull(map.get("name"));
            System.out.println(map.get("name"));
        }
    }

    public void testBuild_array() throws Exception {
        Object[] value = new Object[] { "aaa", "bbb", "ccc" };
        ComponentHolder holder = ComponentHolderBuilderUtil.build(value);
        assertNotNull(holder);
        Object[] o = holder.getValue().toArray(
                new Object[holder.getValue().size()]);
        ArrayAssert.assertEquals(value, o);
    }

    public void testBuild_arrayEmpty() throws Exception {
        HogeDto[] value = new HogeDto[] { new HogeDto() };
        ComponentHolder holder = ComponentHolderBuilderUtil.build(value);
        assertNotNull(holder);
        assertEquals(HogeDto.class.getName(), holder.getComponentClassName());
    }

    public void testBuild_promitiveArray() throws Exception {
        BeanDesc bd = BeanDescFactory.getBeanDesc(HogeDto.class);
        PropertyDesc pd = bd.getPropertyDesc("array");
        Object value = pd.getValue(new HogeDto());
        ComponentHolder holder = ComponentHolderBuilderUtil.build(value);
        assertNotNull(holder);
        Object[] o = holder.getValue().toArray(
                new Object[holder.getValue().size()]);
        Integer[] expected = new Integer[] { new Integer(1), new Integer(2),
                new Integer(3) };
        ArrayAssert.assertEquals(expected, o);
    }

    public void testBuild_arrayDto() throws Exception {
        Object[] value = new Object[3];
        {
            HogeDto dto = new HogeDto();
            dto.setName("aaa");
            value[0] = dto;
        }
        {
            HogeDto dto = new HogeDto();
            dto.setName("bbb");
            value[1] = dto;
        }
        {
            HogeDto dto = new HogeDto();
            dto.setName("ccc");
            value[2] = dto;
        }
        ComponentHolder holder = ComponentHolderBuilderUtil.build(value);
        assertNotNull(holder);
        for (Iterator itr = holder.getValue().iterator(); itr.hasNext();) {
            Object o = itr.next();
            assertTrue(o instanceof Map);
            Map map = (Map) o;
            assertNotNull(map);
            assertNotNull(map.get("name"));
            System.out.println(map.get("name"));
        }
    }

    public static class HogeDto {
        private String name;

        private int[] array = new int[] { 1, 2, 3 };

        public int[] getArray() {
            return array;
        }

        public void setArray(int[] array) {
            this.array = array;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
