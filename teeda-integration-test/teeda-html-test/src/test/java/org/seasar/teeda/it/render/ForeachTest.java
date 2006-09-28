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
public class ForeachTest extends TeedaWebTestCase {

    public static Test suite() throws Exception {
        return setUpTest(ForeachTest.class);
    }

    public void testRender() throws Exception {
        // ## Arrange ##
        TeedaWebTester tester = new TeedaWebTester();
        tester.getTestContext().setBaseUrl(getBaseUrl());

        // ## Act ##
        // ## Assert ##
        tester.beginAt("view/foreach/foreachArray.html");
        tester.dumpHtml();
        final String[][] table = new String[][] { { "1", "aa1", "bb1" },
            { "2", "aa2", "bb2" }, { "3", "aa3", "bb3" } };

        tester.assertTableEquals("foreachTable", table);
        tester.clickButton("doSomething");

        // --------

        tester.dumpHtml();
        // inputになっていない"fooNo"項目が再度表示されていること
        tester.assertTableEquals("foreachTable", table);
    }

    public void testInputList() throws Exception {
        // ## Arrange ##
        TeedaWebTester tester = new TeedaWebTester();
        tester.getTestContext().setBaseUrl(getBaseUrl());

        // ## Act ##
        // ## Assert ##
        tester.beginAt("view/foreach/foreachList.html");
        tester.dumpHtml();
        final String[][] table = new String[][] { { "1", "aa1", "bb1" },
            { "2", "aa2", "bb2" }, { "3", "aa3", "bb3" } };

        tester.assertTableEquals("foreachTable", table);
        tester.clickButton("doSomething");

        // --------

        tester.dumpHtml();
        // inputになっていない"fooNo"項目が再度表示されていること
        tester.assertTableEquals("foreachTable", table);
    }

}
