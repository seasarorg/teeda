/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
