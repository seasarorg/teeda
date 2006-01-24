package org.seasar.teeda.core.mock;

import javax.faces.model.DataModel;

public class MockDataModel extends DataModel {

    private String name_;

    private int rowCount_;

    private Object rowData_;

    private boolean rowAvailable_;

    public MockDataModel() {
    }

    public MockDataModel(String name) {
        name_ = name;
    }

    public int getRowCount() {
        return rowCount_;
    }

    public Object getRowData() {
        return rowData_;
    }

    public int getRowIndex() {
        return 0;
    }

    public Object getWrappedData() {
        return null;
    }

    public boolean isRowAvailable() {
        return rowAvailable_;
    }

    public String toString() {
        return name_;
    }

    public void setRowIndex(int rowIndex) {
    }

    public void setWrappedData(Object data) {
    }

    public void setRowCount(int rowCount) {
        rowCount_ = rowCount;
    }

    public void setRowData(Object rowData) {
        rowData_ = rowData;
    }

    public void setRowAvailable(boolean rowAvailable) {
        rowAvailable_ = rowAvailable;
    }

}
