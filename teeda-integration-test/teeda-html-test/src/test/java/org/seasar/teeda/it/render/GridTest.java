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
        tester.getTestContext().setBaseUrl(getBaseUrl());

        // ## Act ##
        // ## Assert ##
        tester.beginAt("view/grid/grid.html");
        tester.dumpHtml();
        _assertTables(tester);

        tester.clickButton("doSomething");

        // --------

        tester.dumpHtml();
        // 値が再現されていること
        _assertTables(tester);
    }

    private void _assertTables(TeedaWebTester tester) {
        tester.assertTableEquals("fooGridLeftHeaderTable", new String[][] { {
            "AAA", "BBB", "CCC" } });
        tester.assertTableEquals("fooGridRightHeaderTable", new String[][] { {
            "DDD", "EEE", "FFF" } });
        tester.assertTableEquals("fooGridLeftBodyTable", new String[][] {
            { "a1", "b1", "c1" }, { "a2", "b2", "c2" }, { "a3", "b3", "c3" },
            { "a4", "b4", "c4" } });
        // JavaScriptやhidden値があるため、assertTableMatchとする
        tester.assertTableMatch("fooGridRightBodyTable", new String[][] {
            { "d1", "11,111,111", "f1" }, { "d2", "2,222", "f2" },
            { "d3", "33,333", "f3" }, { "d4", "44", "f4" } });
    }

}
