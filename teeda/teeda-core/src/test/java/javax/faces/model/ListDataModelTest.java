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
package javax.faces.model;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockDataModelListener;

public class ListDataModelTest extends TestCase {

    public void testIsRowAvailable() throws Exception {
        List list = new ArrayList();
        list.add("a");
        list.add("b");

        ListDataModel model = new ListDataModel(list);
        model.setRowIndex(0);
        assertTrue(model.isRowAvailable());

        model.setRowIndex(list.size() - 1);
        assertTrue(model.isRowAvailable());

        model.setRowIndex(-1);
        assertFalse(model.isRowAvailable());

    }

    public void testGetRowCount() throws Exception {
        List list = new ArrayList();
        list.add("1");
        list.add("2");

        ListDataModel model = new ListDataModel(list);
        assertEquals(list.size(), model.getRowCount());

        model = new ListDataModel();
        assertEquals(-1, model.getRowCount());
    }

    public void testGetRowData() throws Exception {
        List list = new ArrayList();
        list.add("a");
        list.add("b");

        ListDataModel model = new ListDataModel(list);
        model.setRowIndex(1);

        assertEquals(list.get(1), model.getRowData());

        model = new ListDataModel();

        assertEquals(null, model.getRowData());

        model = new ListDataModel(list);
        model.setRowIndex(3);
        try {
            model.getRowData();
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    public void testGetRowIndex() throws Exception {
        List list = new ArrayList();
        list.add("a");
        list.add("b");

        ListDataModel model = new ListDataModel(list);
        model.setRowIndex(1);
        assertEquals(1, model.getRowIndex());

        model = new ListDataModel();
        assertEquals(-1, model.getRowIndex());
    }

    public void testSetRowIndex() throws Exception {
        List list = new ArrayList();
        list.add("1");
        list.add("2");

        ListDataModel model = new ListDataModel(list);

        MockDataModelListener listener = new MockDataModelListener();
        model.addDataModelListener(listener);

        model.setRowIndex(1);

        DataModelEvent event = listener.getDataModelEvent();

        assertEquals("2", event.getRowData());
        assertEquals(1, event.getRowIndex());

        try {
            model.setRowIndex(-2);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        ListDataModel model2 = new ListDataModel();
        MockDataModelListener listener2 = new MockDataModelListener();
        model2.addDataModelListener(listener2);

        model2.setRowIndex(1);

        assertNull(listener2.getDataModelEvent());

        List list3 = new ArrayList();
        list3.add("d");
        list3.add("e");

        ListDataModel model3 = new ListDataModel(list3);
        MockDataModelListener listener3 = new MockDataModelListener();
        model3.addDataModelListener(listener3);

        model3.setRowIndex(3);

        DataModelEvent event3 = listener3.getDataModelEvent();
        assertNull(event3.getRowData());
        assertEquals(3, event3.getRowIndex());
    }

    public void testGetWrappedData() throws Exception {
        List list = new ArrayList();
        list.add("a");
        list.add("b");

        ListDataModel model = new ListDataModel();
        model.setWrappedData(list);
        assertEquals(list, model.getWrappedData());

    }

    public void testSetWrappedData() throws Exception {
        List list = new ArrayList();
        list.add("1");
        list.add("2");
        list.add("3");

        ListDataModel model = new ListDataModel();
        MockDataModelListener listener = new MockDataModelListener();
        model.addDataModelListener(listener);

        model.setWrappedData(list);
        model.setRowIndex(1);
        assertEquals(1, model.getRowIndex());
        assertNotNull(model.getWrappedData());

        DataModelEvent event = listener.getDataModelEvent();
        assertNotNull(event);
        assertEquals("2", event.getRowData());
        assertEquals(1, event.getRowIndex());

    }

}
