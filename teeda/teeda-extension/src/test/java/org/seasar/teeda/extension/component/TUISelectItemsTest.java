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
package org.seasar.teeda.extension.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import junit.framework.TestCase;

/**
 * @author shot
 * 
 */
public class TUISelectItemsTest extends TestCase {

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
        List list = new ArrayList();
        HogeDto h1 = new HogeDto();
        h1.setLabel("aaa");
        h1.setValue("1");
        list.add(h1);
        TUISelectItems items = new TUISelectItems();
        items.setValue(list);
        List list2 = (List) items.getValue();
        assertNotNull(list2);
        SelectItem si = (SelectItem) list2.get(0);
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
        SelectItem si = (SelectItem) list2.get(0);
        assertEquals("aaa", si.getLabel());
        assertEquals("1", si.getValue());
    }

    public void testGetValue_nullLabel() throws Exception {
        List list = new ArrayList();
        HogeDto h1 = new HogeDto();
        h1.setNullLabel("nnnn");
        list.add(h1);
        TUISelectItems items = new TUISelectItems();
        items.setValue(list);
        List list2 = (List) items.getValue();
        assertNotNull(list2);
        SelectItem si = (SelectItem) list2.get(0);
        assertEquals("nnnn", si.getLabel());
    }
    
    public static class HogeDto {
        private String label;

        private String value;

        private String nullLabel;

        public String getNullLabel() {
            return nullLabel;
        }

        public void setNullLabel(String nullLabel) {
            this.nullLabel = nullLabel;
        }

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
