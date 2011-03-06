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
package javax.faces.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;

import javax.faces.FacesException;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.NullResultSet;

/**
 * @author shot
 */
public class ResultSetDataModelTest extends TestCase {

    //TODO testing for ResultSetMap, setRowIndex
    public void testResultSetDataModel_notUpdatable() throws Exception {
        NoScrollableResultSet resultSet = new NoScrollableResultSet();
        try {
            new ResultSetDataModel(resultSet);
            fail();
        } catch (IllegalArgumentException expected) {
            assertTrue(true);
        }
    }

    public void testGetRowCount_alwaysReturnMinus1() throws Exception {
        assertEquals(-1, new ResultSetDataModel().getRowCount());
    }

    public void testGetRowData_resultSetNull() throws Exception {
        ResultSetDataModel model = new ResultSetDataModel();
        assertNull(model.getRowData());
    }

    public void testGetRowData_indexNotInRange() throws Exception {
        ResultSetDataModel model = new ResultSetDataModel(
                new AbsoluteAlwaysFalseResultSet());
        model.setRowIndex(-1);
        try {
            model.getRowData();
            fail();
        } catch (IllegalArgumentException expected) {
            assertTrue(true);
        }
    }

    public void testGetRowData_normal() throws Exception {
        ResultSetDataModel model = new ResultSetDataModel(new NormalResultSet());
        assertTrue(model.getRowData() instanceof AbstractMap);

    }

    public void testIsRowAvailable_resultSetIsNull() throws Exception {
        ResultSetDataModel model = new ResultSetDataModel();
        assertFalse(model.isRowAvailable());
    }

    public void testIsRowAvailable_indexLessThan0() throws Exception {
        ResultSetDataModel model = new ResultSetDataModel(new NormalResultSet());
        model.setRowIndex(-1);
        assertFalse(model.isRowAvailable());
    }

    public void testIsRowAvailable_throwSqlException() throws Exception {
        try {
            // in the time of instanciation, ResultSetDataModel call
            // isRowAvailable
            new ResultSetDataModel(new AbsoluteThrowExceptionResultSet());
            fail();
        } catch (FacesException expected) {
            assertTrue(true);
        }
    }

    private static class NoScrollableResultSet extends NullResultSet {
        public int getType() throws SQLException {
            return ResultSet.TYPE_SCROLL_INSENSITIVE;
        }
    }

    private static class AbsoluteAlwaysFalseResultSet extends NullResultSet {
        public int getType() throws SQLException {
            return ResultSet.TYPE_SCROLL_SENSITIVE;
        }

        public boolean absolute(int arg0) throws SQLException {
            return false;
        }
    }

    private static class NormalResultSet extends NullResultSet {
        public int getType() throws SQLException {
            return ResultSet.TYPE_SCROLL_SENSITIVE;
        }

        public boolean absolute(int arg0) throws SQLException {
            return true;
        }
    }

    private static class AbsoluteThrowExceptionResultSet extends NullResultSet {
        public int getType() throws SQLException {
            return ResultSet.TYPE_SCROLL_SENSITIVE;
        }

        public boolean absolute(int arg0) throws SQLException {
            throw new SQLException();
        }
    }

}
