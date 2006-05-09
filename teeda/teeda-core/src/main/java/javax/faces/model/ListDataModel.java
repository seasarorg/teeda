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

import java.util.List;

public class ListDataModel extends DataModel {

    private List list = null;

    int index = -1;

    public ListDataModel() {
        this(null);
    }

    public ListDataModel(List list) {
        super();
        setWrappedData(list);
    }

    public int getRowCount() {
        return (list != null) ? list.size() : -1;
    }

    public Object getRowData() {
        if (list == null) {
            return null;
        }

        if (!isRowAvailable()) {
            throw new IllegalArgumentException();
        }
        return list.get(index);
    }

    public int getRowIndex() {
        return index;
    }

    public Object getWrappedData() {
        return list;
    }

    public boolean isRowAvailable() {
        if (list == null) {
            return false;
        }
        return (index >= 0 && index < list.size());
    }

    public void setRowIndex(int rowIndex) {

        if (rowIndex < -1) {
            throw new IllegalArgumentException();
        }

        int oldIndex = index;
        index = rowIndex;

        if (list == null) {
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
        list = (List) data;
        int index = (list != null) ? 0 : -1;
        setRowIndex(index);
    }

}
