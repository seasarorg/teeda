/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

public class ResultDataModel extends DataModel {

    private Result result = null;

    private int index = -1;

    private SortedMap[] rows_ = null;

    public ResultDataModel() {
        this(null);
    }

    public ResultDataModel(Result result) {
        super();
        setWrappedData(result);
    }

    public int getRowCount() {
        return (result != null) ? rows_.length : -1;
    }

    public Object getRowData() {
        if (result == null) {
            return null;
        }

        if (!isRowAvailable()) {
            throw new IllegalArgumentException();
        }
        return rows_[index];
    }

    public int getRowIndex() {
        return index;
    }

    public Object getWrappedData() {
        return result;
    }

    public boolean isRowAvailable() {
        if (result == null) {
            return false;
        }
        return (index >= 0 && index < rows_.length);
    }

    public void setRowIndex(int rowIndex) {

        if (rowIndex < -1) {
            throw new IllegalArgumentException();
        }

        int oldIndex = index;
        index = rowIndex;

        if (result == null) {
            return;
        }

        DataModelListener[] listeners = getDataModelListeners();
        if ((oldIndex != index) && (listeners != null)) {
            Object rowData = null;
            if (isRowAvailable()) {
                rowData = getRowData();
            }

            DataModelEvent event = new DataModelEvent(this, index, rowData);
            for (int i = 0; i < listeners.length; i++) {
                listeners[i].rowSelected(event);
            }
        }

    }

    public void setWrappedData(Object data) {
        if (data == null) {
            setRowIndex(-1);
        } else {
            result = (Result) data;
            rows_ = result.getRows();
            setRowIndex(0);
        }
    }

}
