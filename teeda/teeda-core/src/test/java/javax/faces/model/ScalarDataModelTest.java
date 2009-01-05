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

/**
 * @author shot
 */
public class ScalarDataModelTest extends TestCase {

    public void testIsRowAvailable_succeed() throws Exception {
        ScalarDataModel model = new ScalarDataModel();
        model.setWrappedData(new Object());
        model.setRowIndex(0);
        assertTrue(model.isRowAvailable());
    }

    public void testIsRowAvailable_wrappedDataUnavailable() throws Exception {
        ScalarDataModel model = new ScalarDataModel();
        model.setRowIndex(0);
        assertFalse(model.isRowAvailable());
    }

    public void testIsRowAvailable_wrappedDataNull() throws Exception {
        ScalarDataModel model = new ScalarDataModel();
        model.setWrappedData(null);
        assertFalse(model.isRowAvailable());
    }

    public void testGetRowCount_wrappedDataAvailable() throws Exception {
        ScalarDataModel model = new ScalarDataModel();
        model.setWrappedData(new Object());
        assertEquals(1, model.getRowCount());
    }

    public void testGetRowCount_wrappedDataUnavailable() throws Exception {
        ScalarDataModel model = new ScalarDataModel();
        model.setWrappedData(null);
        assertEquals(-1, model.getRowCount());
    }

    public void testGetRowData_succeed() throws Exception {
        ScalarDataModel model = new ScalarDataModel();
        model.setWrappedData("a");
        assertEquals("a", model.getRowData());
    }

    public void testGetRowData_null() throws Exception {
        ScalarDataModel model = new ScalarDataModel();
        assertNull(model.getRowData());
    }

    public void testGetRowData_illegalStateRowData() throws Exception {
        ScalarDataModel model = new ScalarDataModel();
        model.setWrappedData(new Object());
        model.setRowIndex(1);
        try {
            model.getRowData();
            fail();
        } catch (IllegalArgumentException expected) {
            assertTrue(true);
        }
    }

    public void testGetRowIndex_getCurrentRow() throws Exception {
        ScalarDataModel model = new ScalarDataModel();
        model.setWrappedData("b");
        assertEquals(0, model.getRowIndex());
    }

    public void testGetRowIndex_noWrappedData() throws Exception {
        ScalarDataModel model = new ScalarDataModel();
        assertEquals(-1, model.getRowIndex());
    }

    public void testSetWrappedData_detached() throws Exception {
        ScalarDataModel model = new ScalarDataModel();
        model.setWrappedData(null);
        assertEquals(-1, model.getRowIndex());
    }

    public void testSetWrappedData_currentIndexZero() throws Exception {
        ScalarDataModel model = new ScalarDataModel();
        model.setWrappedData(new Object());
        assertEquals(0, model.getRowIndex());
    }

}
