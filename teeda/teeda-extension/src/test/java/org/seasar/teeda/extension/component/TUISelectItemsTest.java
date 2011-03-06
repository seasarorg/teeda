/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 *
 */
public class TUISelectItemsTest extends TeedaTestCase {

    public void testGetValue_selectItems() throws Exception {
        TUISelectItems items = new TUISelectItems();
        SelectItem item = new SelectItem();
        item.setLabel("aaa");
        items.setValue(item);
        Object value = items.getValue();
        assertNotNull(value);
        assertTrue(value instanceof SelectItem);
        SelectItem si = (SelectItem) value;
        assertEquals("aaa", si.getLabel());
    }

    public void testGetValue_dto() throws Exception {
        TUISelectItems items = new TUISelectItems();
        {
            List list = new ArrayList();
            HogeDto h = new HogeDto();
            h.setLabel("aaa");
            h.setValue("1");
            list.add(h);
            items.setValue(list);
        }
        //Assert
        List list = (List) items.getValue();
        assertNotNull(list);
        SelectItem si = (SelectItem) list.get(0);
        System.out.println(si.getLabel());
        assertNotNull(si.getLabel());
        assertEquals("", si.getValue());
        si = (SelectItem) list.get(1);
        assertEquals("aaa", si.getLabel());
        assertEquals("1", si.getValue());
    }

    public void testGetValue_map() throws Exception {
        List list = new ArrayList();
        Map map = new HashMap();
        map.put("label", "aaa");
        map.put("value", "1");
        list.add(map);
        TUISelectItems items = new TUISelectItems();
        items.setValue(list);
        List list2 = (List) items.getValue();
        assertNotNull(list2);
        SelectItem si = (SelectItem) list2.get(1);
        assertEquals("aaa", si.getLabel());
        assertEquals("1", si.getValue());
    }

    public void testGetValue_map_array() throws Exception {
        Map[] array = new Map[1];
        Map map = new HashMap();
        map.put("label", "aaa");
        map.put("value", "1");
        array[0] = map;
        TUISelectItems items = new TUISelectItems();
        items.setValue(array);
        List list2 = (List) items.getValue();
        assertNotNull(list2);
        SelectItem si = (SelectItem) list2.get(1);
        assertEquals("aaa", si.getLabel());
        assertEquals("1", si.getValue());
    }

    public void testGetValue_required() throws Exception {
        List list = new ArrayList();
        HogeDto h1 = new HogeDto();
        h1.setLabel("aaa");
        h1.setValue("1");
        list.add(h1);
        TUISelectItems items = new TUISelectItems();
        items.setNullLabelRequired(false);
        items.setValue(list);
        List list2 = (List) items.getValue();
        assertNotNull(list2);
        SelectItem si = (SelectItem) list2.get(0);
        assertEquals("aaa", si.getLabel());
        assertEquals("1", si.getValue());
    }

    public void testGetValue_nullLabelRequired() throws Exception {
        TUISelectItems items = new TUISelectItems();
        items.setNullLabelRequired(true);
        List list = (List) items.getValue();
        assertNotNull(list);
        assertTrue(list.size() == 1);
        assertNotNull(list.get(0));
        assertNotNull(((SelectItem) list.get(0)).getLabel());
    }

    public void testGetValue_notNullLabelRequiredWithCollection()
            throws Exception {
        TUISelectItems items = new TUISelectItems();
        items.setValue(new ArrayList());
        items.setNullLabelRequired(false);
        List list = (List) items.getValue();
        assertNotNull(list);
        assertTrue(list.size() == 1);
    }

    public void testGetValue_notNullLabelRequiredWithMap() throws Exception {
        TUISelectItems items = new TUISelectItems();
        items.setValue(new HashMap());
        items.setNullLabelRequired(false);
        List list = (List) items.getValue();
        assertNotNull(list);
        assertTrue(list.size() == 1);
    }

    public void testGetValue_notNullLabelRequired() throws Exception {
        TUISelectItems items = new TUISelectItems();
        items.setNullLabelRequired(false);
        List list = (List) items.getValue();
        assertNotNull(list);
        assertTrue(list.size() == 1);
    }

    public static class HogeDto {
        private String label;

        private String value;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }
}
