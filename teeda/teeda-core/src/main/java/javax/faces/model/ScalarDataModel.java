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

/**
 * @author shot
 */
public class ScalarDataModel extends DataModel {

    private Object scalar_ = null;

    private int index_ = -1;

    public ScalarDataModel() {
        this(null);
    }

    public ScalarDataModel(Object scalar) {
        super();
        setWrappedData(scalar);
    }

    public int getRowCount() {
        return (scalar_ != null) ? 1 : -1;
    }

    public Object getRowData() {
        if (scalar_ == null) {
            return null;
        }
        if (!isRowAvailable()) {
            throw new IllegalArgumentException();
        }
        return scalar_;
    }

    public int getRowIndex() {
        return (scalar_ != null) ? 0 : -1;
    }

    public Object getWrappedData() {
        return scalar_;
    }

    public boolean isRowAvailable() {
        if (scalar_ == null) {
            return false;
        }
        return (index_ == 0) ? true : false;
    }

    public void setRowIndex(int index) {
        if (index < -1) {
            throw new IllegalArgumentException();
        }

        int oldIndex = index_;
        index_ = index;

        if (scalar_ == null) {
            return;
        }

        DataModelListener[] listeners = getDataModelListeners();
        if ((oldIndex != index_) && (listeners != null)) {
            DataModelEvent event = new DataModelEvent(this, index_, scalar_);
            for (int i = 0; i < listeners.length; i++) {
                listeners[i].rowSelected(event);
            }
        }
    }

    public void setWrappedData(Object data) {
        if (data == null) {
            scalar_ = null;
            setRowIndex(-1);
        } else {
            scalar_ = data;
            setRowIndex(0);
        }
    }
}
