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

import java.util.ArrayList;
import java.util.List;

import org.seasar.framework.util.AssertionUtil;

/**
 * @author shot
 */
public abstract class DataModel {

    private List listeners = null;

    public DataModel() {
        listeners = new ArrayList();
    }

    public abstract int getRowCount();

    public abstract Object getRowData();

    public abstract int getRowIndex();

    public abstract Object getWrappedData();

    public abstract boolean isRowAvailable();

    public abstract void setRowIndex(int rowIndex);

    public abstract void setWrappedData(Object data);

    public void addDataModelListener(DataModelListener listener) {
        AssertionUtil.assertNotNull("listener", listener);
        listeners.add(listener);
    }

    public DataModelListener[] getDataModelListeners() {
        DataModelListener[] dataModelListeners = null;
        if (listeners == null) {
            dataModelListeners = new DataModelListener[0];
        } else {
            dataModelListeners = (DataModelListener[]) this.listeners
                    .toArray(new DataModelListener[this.listeners.size()]);
        }
        return dataModelListeners;
    }

    public void removeDataModelListener(DataModelListener listener) {
        AssertionUtil.assertNotNull("listener", listener);
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

}
