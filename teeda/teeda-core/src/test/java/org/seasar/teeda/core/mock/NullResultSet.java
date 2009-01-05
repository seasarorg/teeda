/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/**
 * @author shot
 */
public class NullResultSet implements ResultSet {

    public boolean next() throws SQLException {
        return false;
    }

    public void close() throws SQLException {

    }

    public boolean wasNull() throws SQLException {
        return false;
    }

    public String getString(int arg0) throws SQLException {
        return null;
    }

    public boolean getBoolean(int arg0) throws SQLException {
        return false;
    }

    public byte getByte(int arg0) throws SQLException {
        return 0;
    }

    public short getShort(int arg0) throws SQLException {
        return 0;
    }

    public int getInt(int arg0) throws SQLException {
        return 0;
    }

    public long getLong(int arg0) throws SQLException {
        return 0;
    }

    public float getFloat(int arg0) throws SQLException {
        return 0;
    }

    public double getDouble(int arg0) throws SQLException {
        return 0;
    }

    public BigDecimal getBigDecimal(int arg0, int arg1) throws SQLException {
        return null;
    }

    public byte[] getBytes(int arg0) throws SQLException {
        return null;
    }

    public Date getDate(int arg0) throws SQLException {
        return null;
    }

    public Time getTime(int arg0) throws SQLException {
        return null;
    }

    public Timestamp getTimestamp(int arg0) throws SQLException {
        return null;
    }

    public InputStream getAsciiStream(int arg0) throws SQLException {
        return null;
    }

    public InputStream getUnicodeStream(int arg0) throws SQLException {
        return null;
    }

    public InputStream getBinaryStream(int arg0) throws SQLException {
        return null;
    }

    public String getString(String arg0) throws SQLException {
        return null;
    }

    public boolean getBoolean(String arg0) throws SQLException {
        return false;
    }

    public byte getByte(String arg0) throws SQLException {
        return 0;
    }

    public short getShort(String arg0) throws SQLException {
        return 0;
    }

    public int getInt(String arg0) throws SQLException {
        return 0;
    }

    public long getLong(String arg0) throws SQLException {
        return 0;
    }

    public float getFloat(String arg0) throws SQLException {
        return 0;
    }

    public double getDouble(String arg0) throws SQLException {
        return 0;
    }

    public BigDecimal getBigDecimal(String arg0, int arg1) throws SQLException {
        return null;
    }

    public byte[] getBytes(String arg0) throws SQLException {
        return null;
    }

    public Date getDate(String arg0) throws SQLException {
        return null;
    }

    public Time getTime(String arg0) throws SQLException {
        return null;
    }

    public Timestamp getTimestamp(String arg0) throws SQLException {
        return null;
    }

    public InputStream getAsciiStream(String arg0) throws SQLException {
        return null;
    }

    public InputStream getUnicodeStream(String arg0) throws SQLException {
        return null;
    }

    public InputStream getBinaryStream(String arg0) throws SQLException {
        return null;
    }

    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    public void clearWarnings() throws SQLException {

    }

    public String getCursorName() throws SQLException {
        return null;
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        return new NullResultSetMetaData();
    }

    public Object getObject(int arg0) throws SQLException {
        return null;
    }

    public Object getObject(String arg0) throws SQLException {
        return null;
    }

    public int findColumn(String arg0) throws SQLException {
        return 0;
    }

    public Reader getCharacterStream(int arg0) throws SQLException {
        return null;
    }

    public Reader getCharacterStream(String arg0) throws SQLException {
        return null;
    }

    public BigDecimal getBigDecimal(int arg0) throws SQLException {
        return null;
    }

    public BigDecimal getBigDecimal(String arg0) throws SQLException {
        return null;
    }

    public boolean isBeforeFirst() throws SQLException {
        return false;
    }

    public boolean isAfterLast() throws SQLException {
        return false;
    }

    public boolean isFirst() throws SQLException {
        return false;
    }

    public boolean isLast() throws SQLException {
        return false;
    }

    public void beforeFirst() throws SQLException {

    }

    public void afterLast() throws SQLException {

    }

    public boolean first() throws SQLException {
        return false;
    }

    public boolean last() throws SQLException {
        return false;
    }

    public int getRow() throws SQLException {
        return 0;
    }

    public boolean absolute(int arg0) throws SQLException {
        return false;
    }

    public boolean relative(int arg0) throws SQLException {
        return false;
    }

    public boolean previous() throws SQLException {
        return false;
    }

    public void setFetchDirection(int arg0) throws SQLException {

    }

    public int getFetchDirection() throws SQLException {
        return 0;
    }

    public void setFetchSize(int arg0) throws SQLException {

    }

    public int getFetchSize() throws SQLException {
        return 0;
    }

    public int getType() throws SQLException {
        return 0;
    }

    public int getConcurrency() throws SQLException {
        return 0;
    }

    public boolean rowUpdated() throws SQLException {
        return false;
    }

    public boolean rowInserted() throws SQLException {
        return false;
    }

    public boolean rowDeleted() throws SQLException {
        return false;
    }

    public void updateNull(int arg0) throws SQLException {

    }

    public void updateBoolean(int arg0, boolean arg1) throws SQLException {

    }

    public void updateByte(int arg0, byte arg1) throws SQLException {

    }

    public void updateShort(int arg0, short arg1) throws SQLException {

    }

    public void updateInt(int arg0, int arg1) throws SQLException {

    }

    public void updateLong(int arg0, long arg1) throws SQLException {

    }

    public void updateFloat(int arg0, float arg1) throws SQLException {

    }

    public void updateDouble(int arg0, double arg1) throws SQLException {

    }

    public void updateBigDecimal(int arg0, BigDecimal arg1) throws SQLException {

    }

    public void updateString(int arg0, String arg1) throws SQLException {

    }

    public void updateBytes(int arg0, byte[] arg1) throws SQLException {

    }

    public void updateDate(int arg0, Date arg1) throws SQLException {

    }

    public void updateTime(int arg0, Time arg1) throws SQLException {

    }

    public void updateTimestamp(int arg0, Timestamp arg1) throws SQLException {

    }

    public void updateAsciiStream(int arg0, InputStream arg1, int arg2)
            throws SQLException {

    }

    public void updateBinaryStream(int arg0, InputStream arg1, int arg2)
            throws SQLException {

    }

    public void updateCharacterStream(int arg0, Reader arg1, int arg2)
            throws SQLException {

    }

    public void updateObject(int arg0, Object arg1, int arg2)
            throws SQLException {

    }

    public void updateObject(int arg0, Object arg1) throws SQLException {

    }

    public void updateNull(String arg0) throws SQLException {

    }

    public void updateBoolean(String arg0, boolean arg1) throws SQLException {

    }

    public void updateByte(String arg0, byte arg1) throws SQLException {

    }

    public void updateShort(String arg0, short arg1) throws SQLException {

    }

    public void updateInt(String arg0, int arg1) throws SQLException {

    }

    public void updateLong(String arg0, long arg1) throws SQLException {

    }

    public void updateFloat(String arg0, float arg1) throws SQLException {

    }

    public void updateDouble(String arg0, double arg1) throws SQLException {

    }

    public void updateBigDecimal(String arg0, BigDecimal arg1)
            throws SQLException {

    }

    public void updateString(String arg0, String arg1) throws SQLException {

    }

    public void updateBytes(String arg0, byte[] arg1) throws SQLException {

    }

    public void updateDate(String arg0, Date arg1) throws SQLException {

    }

    public void updateTime(String arg0, Time arg1) throws SQLException {

    }

    public void updateTimestamp(String arg0, Timestamp arg1)
            throws SQLException {

    }

    public void updateAsciiStream(String arg0, InputStream arg1, int arg2)
            throws SQLException {

    }

    public void updateBinaryStream(String arg0, InputStream arg1, int arg2)
            throws SQLException {

    }

    public void updateCharacterStream(String arg0, Reader arg1, int arg2)
            throws SQLException {

    }

    public void updateObject(String arg0, Object arg1, int arg2)
            throws SQLException {

    }

    public void updateObject(String arg0, Object arg1) throws SQLException {

    }

    public void insertRow() throws SQLException {

    }

    public void updateRow() throws SQLException {

    }

    public void deleteRow() throws SQLException {

    }

    public void refreshRow() throws SQLException {

    }

    public void cancelRowUpdates() throws SQLException {

    }

    public void moveToInsertRow() throws SQLException {

    }

    public void moveToCurrentRow() throws SQLException {

    }

    public Statement getStatement() throws SQLException {
        return null;
    }

    public Object getObject(int arg0, Map arg1) throws SQLException {
        return null;
    }

    public Ref getRef(int arg0) throws SQLException {
        return null;
    }

    public Blob getBlob(int arg0) throws SQLException {
        return null;
    }

    public Clob getClob(int arg0) throws SQLException {
        return null;
    }

    public Array getArray(int arg0) throws SQLException {
        return null;
    }

    public Object getObject(String arg0, Map arg1) throws SQLException {
        return null;
    }

    public Ref getRef(String arg0) throws SQLException {
        return null;
    }

    public Blob getBlob(String arg0) throws SQLException {
        return null;
    }

    public Clob getClob(String arg0) throws SQLException {
        return null;
    }

    public Array getArray(String arg0) throws SQLException {
        return null;
    }

    public Date getDate(int arg0, Calendar arg1) throws SQLException {
        return null;
    }

    public Date getDate(String arg0, Calendar arg1) throws SQLException {
        return null;
    }

    public Time getTime(int arg0, Calendar arg1) throws SQLException {
        return null;
    }

    public Time getTime(String arg0, Calendar arg1) throws SQLException {
        return null;
    }

    public Timestamp getTimestamp(int arg0, Calendar arg1) throws SQLException {
        return null;
    }

    public Timestamp getTimestamp(String arg0, Calendar arg1)
            throws SQLException {
        return null;
    }

    public URL getURL(int arg0) throws SQLException {
        return null;
    }

    public URL getURL(String arg0) throws SQLException {
        return null;
    }

    public void updateRef(int arg0, Ref arg1) throws SQLException {

    }

    public void updateRef(String arg0, Ref arg1) throws SQLException {

    }

    public void updateBlob(int arg0, Blob arg1) throws SQLException {

    }

    public void updateBlob(String arg0, Blob arg1) throws SQLException {

    }

    public void updateClob(int arg0, Clob arg1) throws SQLException {

    }

    public void updateClob(String arg0, Clob arg1) throws SQLException {

    }

    public void updateArray(int arg0, Array arg1) throws SQLException {

    }

    public void updateArray(String arg0, Array arg1) throws SQLException {

    }

}
