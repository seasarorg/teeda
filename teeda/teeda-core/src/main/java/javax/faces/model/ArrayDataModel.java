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

public class ArrayDataModel extends DataModel {

    private Object[] array = null;

    private int index = -1;

    public ArrayDataModel() {
        this(null);
    }

    public ArrayDataModel(Object[] array) {
        super();
        setWrappedData(array);
    }

    public int getRowCount() {
        return (array != null) ? array.length : -1;
    }

    public Object getRowData() {
        if (array == null) {
            return null;
        }

        if (!isRowAvailable()) {
            throw new IllegalArgumentException();
        }

        return array[index];
    }

    public int getRowIndex() {
        return index;
    }

    public Object getWrappedData() {
        return array;
    }

    public boolean isRowAvailable() {
        if (array == null) {
            return false;
        }
        return (index >= 0 && index < array.length);
    }

    public void setWrappedData(Object data) {
        array = (Object[]) data;
        int index = (data != null) ? 0 : -1;
        setRowIndex(index);
    }

    public void setRowIndex(int rowIndex) {
        if (rowIndex < -1) {
            throw new IllegalArgumentException();
        }

        int oldIndex = index;
        index = rowIndex;

        if (array == null) {
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
}
