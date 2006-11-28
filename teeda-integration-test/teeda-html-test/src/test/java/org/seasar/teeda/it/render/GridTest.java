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
package org.seasar.teeda.it.render;

import junit.framework.Test;
import net.sourceforge.jwebunit.html.Cell;
import net.sourceforge.jwebunit.html.Row;
import net.sourceforge.jwebunit.html.Table;

import org.seasar.teeda.unit.web.TeedaWebTestCase;
import org.seasar.teeda.unit.web.TeedaWebTester;

/**
 * @author manhole
 */
public class GridTest extends TeedaWebTestCase {

    public static Test suite() throws Exception {
        return setUpTest(GridTest.class);
    }

    public void testRender() throws Exception {
        // ## Arrange ##
        TeedaWebTester tester = new TeedaWebTester();

        // ## Act ##
        // ## Assert ##
        tester.beginAt(getBaseUrl(), "view/grid/grid.html");
        tester.dumpHtml();
        _assertRenderTables(tester);

        tester.submitById("doSomething");

        // --------

        tester.dumpHtml();
        // 値が再現されていること
        _assertRenderTables(tester);
    }

    private void _assertRenderTables(TeedaWebTester tester) {
        tester.assertTableEqualsById("fooGridLeftHeaderTable",
            new String[][] { { "AAA", "BBB", "CCC" } });
        tester.assertTableEqualsById("fooGridRightHeaderTable",
            new String[][] { { "DDD", "EEE", "FFF" } });
        tester.assertTableEqualsById("fooGridLeftBodyTable", new String[][] {
            { "a1", "b1", "c1" }, { "a2", "b2", "c2" }, { "a3", "b3", "c3" },
            { "a4", "b4", "c4" } });
        // JavaScriptやhidden値があるため、assertTableMatchとする
        tester.assertTableMatchById("fooGridRightBodyTable", new String[][] {
            { "d1", "11,111,111", "f1" }, { "d2", "2,222", "f2" },
            { "d3", "33,333", "f3" }, { "d4", "44", "f4" } });
    }

    /*
     * validationやconversionエラーが発生した際に、 値が消えてしまう問題。
     */
    public void testValidation() throws Exception {
        // ## Arrange ##
        TeedaWebTester tester = new TeedaWebTester();

        // ## Act ##
        // ## Assert ##
        tester.beginAt(getBaseUrl(), "view/grid/gridValidation.html");
        tester.dumpHtml();
        tester.assertTableEqualsById("fooGridRightBodyTable", new String[][] {
            { "b1", "c1", "d1", "1" }, { "b2", "c2", "d2", "2" },
            { "b3", "c3", "d3", "3" }, { "b4", "c4", "d4", "4" } });

        /*
         * BigDecimalのプロパティへ文字列を入力することで、変換エラーを起こす。
         */
        tester.setTextByName("gridForm:fooGrid:3:eee", "aaa");
        // _assertTables(tester);

        tester.submitById("doSomething");

        // --------

        tester.dumpHtml();
        tester.assertTableEqualsById("fooGridRightBodyTable", new String[][] {
            { "b1", "c1", "d1", "1" }, { "b2", "c2", "d2", "2" },
            { "b3", "c3", "d3", "3" }, { "b4", "c4", "d4", "aaa" } });
        // 値が再現されていること
        // _assertTables(tester);
    }

    /*
     * https://www.seasar.org/issues/browse/TEEDA-150
     * 
     * Gridのサイズが0に変更されたら、0サイズでレンダされること。
     */
    public void testSizeChange() throws Exception {
        // ## Arrange ##
        TeedaWebTester tester = new TeedaWebTester();

        // ## Act ##
        // ## Assert ##
        tester.beginAt(getBaseUrl(), "view/grid/gridSize.html");
        tester.dumpHtml();
        tester.assertTableEqualsById("fooGridRightBodyTable", new String[][] {
            { "0", "aa0", "0" }, { "1", "aa1", "10" }, { "2", "aa2", "20" } });
        tester.assertTextEqualsByName("gridForm:itemSize", "3");
        tester.setTextByName("gridForm:itemSize", "0");
        tester.submitById("doChangeSize");

        // --------

        tester.dumpHtml();
        tester.assertTextEqualsByName("gridForm:itemSize", "0");
        tester
            .assertTableEqualsById("fooGridRightBodyTable", new String[][] {});
    }

    public void testMultiRowRender() throws Exception {
        // ## Arrange ##
        TeedaWebTester tester = new TeedaWebTester();

        // ## Act ##
        // ## Assert ##
        tester.beginAt(getBaseUrl(), "view/grid/multiRowGrid.html");
        tester.dumpHtml();

        Table table = tester.getTableById("fooGridXYRightHeaderTable");
        assertTrue(table.getRowCount() == 2);

        Table expectedTable = new Table();

        Row row1 = new Row();
        Cell bbb = new Cell("bbb");
        Cell ccc = new Cell("ccc", 1, 2);
        row1.appendCell(bbb);
        row1.appendCell(ccc);
        expectedTable.appendRow(row1);

        Row row2 = new Row();
        Cell eee = new Cell("eee");
        row2.appendCell(eee);
        expectedTable.appendRow(row2);

        tester
            .assertTableEqualsById("fooGridXYRightHeaderTable", expectedTable);
    }

}
