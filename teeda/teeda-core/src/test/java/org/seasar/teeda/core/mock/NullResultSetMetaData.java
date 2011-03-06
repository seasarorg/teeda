/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author shot
 */
public class NullResultSetMetaData implements ResultSetMetaData {

    public int getColumnCount() throws SQLException {
        return 0;
    }

    public boolean isAutoIncrement(int arg0) throws SQLException {
        return false;
    }

    public boolean isCaseSensitive(int arg0) throws SQLException {
        return false;
    }

    public boolean isSearchable(int arg0) throws SQLException {
        return false;
    }

    public boolean isCurrency(int arg0) throws SQLException {
        return false;
    }

    public int isNullable(int arg0) throws SQLException {
        return 0;
    }

    public boolean isSigned(int arg0) throws SQLException {
        return false;
    }

    public int getColumnDisplaySize(int arg0) throws SQLException {
        return 0;
    }

    public String getColumnLabel(int arg0) throws SQLException {
        return null;
    }

    public String getColumnName(int arg0) throws SQLException {
        return null;
    }

    public String getSchemaName(int arg0) throws SQLException {
        return null;
    }

    public int getPrecision(int arg0) throws SQLException {
        return 0;
    }

    public int getScale(int arg0) throws SQLException {
        return 0;
    }

    public String getTableName(int arg0) throws SQLException {
        return null;
    }

    public String getCatalogName(int arg0) throws SQLException {
        return null;
    }

    public int getColumnType(int arg0) throws SQLException {
        return 0;
    }

    public String getColumnTypeName(int arg0) throws SQLException {
        return null;
    }

    public boolean isReadOnly(int arg0) throws SQLException {
        return false;
    }

    public boolean isWritable(int arg0) throws SQLException {
        return false;
    }

    public boolean isDefinitelyWritable(int arg0) throws SQLException {
        return false;
    }

    public String getColumnClassName(int arg0) throws SQLException {
        return null;
    }

}
