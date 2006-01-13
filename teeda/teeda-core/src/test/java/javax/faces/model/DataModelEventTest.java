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
