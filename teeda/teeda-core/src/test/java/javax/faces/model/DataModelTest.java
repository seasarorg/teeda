/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

public class DataModelTest extends TestCase {

    public void testAddModelListener() throws Exception {

        TargetDataModel model = new TargetDataModel();
        MockDataModelListener listener = new MockDataModelListener();
        model.addDataModelListener(listener);

        DataModelListener[] listeners = model.getDataModelListeners();
        assertEquals(listener, listeners[0]);

        try {
            model.addDataModelListener(null);
            fail();
        } catch (NullPointerException e) {
            assertTrue(true);
        }

    }

    public void testGetDataModelListeners() throws Exception {

        TargetDataModel model = new TargetDataModel();
        model.addDataModelListener(new MockDataModelListener("1"));
        model.addDataModelListener(new MockDataModelListener("2"));

        DataModelListener[] listeners = model.getDataModelListeners();
        assertEquals("1", listeners[0].toString());
        assertEquals("2", listeners[1].toString());

    }

    public void testRemoveDataModelListeners() throws Exception {

        TargetDataModel model = new TargetDataModel();
        MockDataModelListener listener = new MockDataModelListener();
        model.addDataModelListener(listener);

        model.removeDataModelListener(listener);

        try {
            model.removeDataModelListener(null);
            fail();
        } catch (NullPointerException e) {
            assertTrue(true);
        }
    }

    private static class TargetDataModel extends DataModel {

        public TargetDataModel() {
            super();
        }

        public int getRowCount() {
            return 0;
        }

        public Object getRowData() {
            return null;
        }

        public int getRowIndex() {
            return 0;
        }

        public Object getWrappedData() {
            return null;
        }

        public boolean isRowAvailable() {
            return false;
        }

        public void setRowIndex(int rowIndex) {
        }

        public void setWrappedData(Object data) {
        }

    }

}
