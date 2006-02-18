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

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import javax.faces.FacesException;
import javax.faces.internal.ResultSetEntries;
import javax.faces.internal.ResultSetKeys;
import javax.faces.internal.ResultSetValues;

/**
 * @author shot
 */
public class ResultSetDataModel extends DataModel {

    private ResultSet resultSet_ = null;

    private int index_ = -1;

    private boolean isUpdated_ = false;

    public ResultSetDataModel() {
        this(null);
    }

    public ResultSetDataModel(ResultSet resultSet) {
        super();
        if (resultSet != null) {
            assertUpdatable(resultSet);
            assertScrollable(resultSet);
        }
        setWrappedData(resultSet);
    }

    public int getRowCount() {
        return -1;
    }

    public Object getRowData() {

        if (resultSet_ == null) {
            return null;
        }

        if (!isRowAvailable()) {
            throw new IllegalArgumentException();
        }

        try {
            return new ResultSetMap(String.CASE_INSENSITIVE_ORDER);
        } catch (SQLException e) {
            throw new FacesException();
        }
    }

    public int getRowIndex() {
        return index_;
    }

    public Object getWrappedData() {
        return resultSet_;
    }

    public boolean isRowAvailable() {

        if (resultSet_ == null || index_ < 0) {
            return false;
        }

        try {
            return resultSet_.absolute(index_ + 1);
        } catch (SQLException e) {
            throw new FacesException();
        }
    }

    public void setRowIndex(int rowIndex) {

        if (rowIndex < 1) {
            throw new IllegalArgumentException();
        }

        if (isUpdated_) {
            updateResultSetIfNeeded();
        }

        int oldIndex = index_;
        index_ = rowIndex;

        DataModelListener[] listeners = getDataModelListeners();
        if ((oldIndex != index_) && (listeners != null)) {
            Object rowData = null;
            if (isRowAvailable()) {
                rowData = getRowData();
            }

            DataModelEvent event = new DataModelEvent(this, index_, rowData);
            for (int i = 0; i < listeners.length; i++) {
                listeners[i].rowSelected(event);
            }
        }
    }

    public void setWrappedData(Object data) {

        if (data == null) {
            setRowIndex(-1);
        } else {
            resultSet_ = (ResultSet) data;
            setRowIndex(0);
        }

    }

    private void updateResultSetIfNeeded() {
        try {
            if (resultSet_ != null && !resultSet_.rowDeleted()) {
                resultSet_.updateRow();
                isUpdated_ = false;
            }
        } catch (SQLException e) {
            throw new FacesException();
        }
    }

    private static void assertUpdatable(ResultSet rs) {
        try {
            int concurrency = rs.getConcurrency();
            if (concurrency != ResultSet.CONCUR_UPDATABLE) {
                throw new IllegalArgumentException();
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException();
        }
    }

    private static void assertScrollable(ResultSet rs) {
        try {
            int type = rs.getType();
            if (type != ResultSet.TYPE_SCROLL_SENSITIVE) {
                throw new IllegalArgumentException();
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException();
        }

    }

    public class ResultSetMap extends TreeMap {

        private int mapIndex_ = 0;

        private ResultSetMetaData metaData_ = null;

        public ResultSetMap(Comparator comparator) throws SQLException {
            super(comparator);
            mapIndex_ = index_;
            resultSet_.absolute(mapIndex_ + 1);
            metaData_ = getMetaData();
            for (int i = 1; i <= metaData_.getColumnCount(); i++) {
                super.put(metaData_.getColumnName(i), metaData_
                        .getColumnName(i));
            }
        }

        public boolean containsValue(Object value) {
            Object key = null;
            for (Iterator itr = keySet().iterator(); itr.hasNext();) {
                key = itr.next();
                if ((key == null && value == null) || (key.equals(value))) {
                    return true;
                }
            }
            return false;
        }

        public Set entrySet() {
            return new ResultSetEntries(this);
        }

        public Object get(Object key) {
            if (!containsKey(key)) {
                return null;
            }

            try {
                resultSet_.absolute(index_ + 1);
                return resultSet_.getObject((String) realKey(key));
            } catch (SQLException e) {
                throw new FacesException();
            }
        }

        public Set keySet() {
            return new ResultSetKeys(this);
        }

        public Object put(Object key, Object value) {
            if ((!containsKey(key))) {
                throw new IllegalArgumentException();
            }

            try {
                resultSet_.absolute(index_ + 1);
                String realKey = (String) realKey(key);
                resultSet_.updateObject(realKey, value);
                isUpdated_ = true;
                return resultSet_.getObject(realKey);
            } catch (SQLException e) {
                throw new FacesException();
            }
        }

        public Collection values() {
            return new ResultSetValues(this);
        }

        public void clear() {
            throw new UnsupportedOperationException();
        }

        public Object remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        public Object realKey(Object key) {
            return super.get(key);
        }

        public Iterator realKeys() {
            return super.keySet().iterator();
        }

        private ResultSetMetaData getMetaData() {

            try {
                if (metaData_ == null) {
                    metaData_ = resultSet_.getMetaData();
                }
            } catch (SQLException e) {
                throw new FacesException();
            }
            return metaData_;
        }

    }

}
