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
package javax.faces.model;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockDataModelListener;

public class ArrayDataModelTest extends TestCase {

    public void testIsRowAvailable() throws Exception {
        Object[] arrays = new Object[] { "1", "2" };
        ArrayDataModel model = new ArrayDataModel(arrays);
        model.setRowIndex(0);
        assertTrue(model.isRowAvailable());

        model.setRowIndex(arrays.length - 1);
        assertTrue(model.isRowAvailable());

        model.setRowIndex(-1);
        assertFalse(model.isRowAvailable());
    }

    public void testGetRowCount() throws Exception {
        Object[] arrays = new Object[] { "1", "2" };
        ArrayDataModel model = new ArrayDataModel(arrays);
        assertEquals(arrays.length, model.getRowCount());

        model = new ArrayDataModel();
        assertEquals(-1, model.getRowCount());
    }

    public void testGetRowData() throws Exception {
        Object[] arrays = new Object[] { "1", "2" };
        ArrayDataModel model = new ArrayDataModel(arrays);
        model.setRowIndex(1);

        assertEquals(arrays[1], model.getRowData());

        model = new ArrayDataModel();

        assertEquals(null, model.getRowData());

        model = new ArrayDataModel(arrays);
        model.setRowIndex(3);
        try {
            model.getRowData();
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    public void testGetRowIndex() throws Exception {
        ArrayDataModel model = new ArrayDataModel(new Object[] { "a", "b" });
        model.setRowIndex(1);
        assertEquals(1, model.getRowIndex());

        model = new ArrayDataModel();
        assertEquals(-1, model.getRowIndex());
    }

    public void testSetRowIndex() throws Exception {
        Object[] arrays = new Object[] { "1", "2" };
        ArrayDataModel model = new ArrayDataModel(arrays);

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

        ArrayDataModel model2 = new ArrayDataModel();
        MockDataModelListener listener2 = new MockDataModelListener();
        model2.addDataModelListener(listener2);

        model2.setRowIndex(1);

        assertNull(listener2.getDataModelEvent());

        ArrayDataModel model3 = new ArrayDataModel(new Object[] { "a", "b" });
        MockDataModelListener listener3 = new MockDataModelListener();
        model3.addDataModelListener(listener3);

        model3.setRowIndex(3);

        DataModelEvent event3 = listener3.getDataModelEvent();
        assertNull(event3.getRowData());
        assertEquals(3, event3.getRowIndex());
    }

    public void testGetWrappedData() throws Exception {
        Object[] arrays = new Object[] { "3", "4" };
        ArrayDataModel model = new ArrayDataModel();
        model.setWrappedData(arrays);
        assertEquals(arrays, model.getWrappedData());

    }

    public void testSetWrappedData() throws Exception {
        Object[] arrays = new Object[] { "3", "4" };
        ArrayDataModel model = new ArrayDataModel();
        MockDataModelListener listener = new MockDataModelListener();
        model.addDataModelListener(listener);

        model.setWrappedData(arrays);
        assertEquals(0, model.getRowIndex());
        assertNotNull(model.getWrappedData());

        DataModelEvent event = listener.getDataModelEvent();
        assertNotNull(event);
        assertEquals("3", event.getRowData());
        assertEquals(0, event.getRowIndex());

    }

}
