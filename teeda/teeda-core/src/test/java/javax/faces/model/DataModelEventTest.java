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

public class DataModelEventTest extends TestCase {

    public void testGetDataModel() throws Exception {
        DataModel model = new MockDataModel("a");
        DataModelEvent event = new DataModelEvent(model, 1, "b");

        assertEquals(model, event.getDataModel());
        assertEquals("a", event.getDataModel().toString());

    }

    public void testGetRowData() throws Exception {
        DataModel model = new MockDataModel("b");
        DataModelEvent event = new DataModelEvent(model, 1, "c");

        assertEquals("c", event.getRowData());

    }

    public void testGetRowIndex() throws Exception {
        DataModel model = new MockDataModel("c");
        DataModelEvent event = new DataModelEvent(model, 1, "d");

        assertEquals(1, event.getRowIndex());
    }

    private static class MockDataModel extends DataModel {

        private String name_ = null;

        public MockDataModel() {
            super();
        }

        public MockDataModel(String name) {
            super();
            name_ = name;
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

        public String toString() {
            return name_;
        }

        public void setRowIndex(int rowIndex) {
        }

        public void setWrappedData(Object data) {
        }
    }

}
