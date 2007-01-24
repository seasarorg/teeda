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
 * @author shot
 * @author manhole
 */
public class SelectOneMenuTest extends TeedaWebTestCase {

    public static Test suite() throws Exception {
        return setUpTest(SelectOneMenuTest.class);
    }

    public void testSelectValueAndSubmit() throws Exception {
        // ## Arrange ##
        TeedaWebTester tester = new TeedaWebTester();

        // ## Act ##
        tester.beginAt(getBaseUrl(), "view/select/selectOneMenu.html");
        tester.dumpHtml();

        tester.assertSelectedOptionValueEqualsByName("form:aaaItems", "1");
        tester.selectOptionValueByName("form:aaaItems", "2");
        tester.submitByName("form:doAction");
        tester.dumpHtml();

        // ## Assert ##
        tester.assertTextEqualsById("aaa", "2");
        tester.assertSelectedOptionValueEqualsByName("form:aaaItems", "2");
    }

    /*
     * https://www.seasar.org/issues/browse/TEEDA-234
     */
    public void testJIRA234() throws Exception {
        // ## Arrange ##
        TeedaWebTester tester = new TeedaWebTester();

        // ## Act ##
        tester.beginAt(getBaseUrl(), "view/select/selectOneMenuFrom.html");
        tester.dumpHtml();

        tester.assertSelectedOptionValueEqualsByName("form:aaaItems", "2");
        tester.selectOptionValueByName("form:aaaItems", "3");
        tester.submitById("doAction");
        tester.dumpHtml();

        // ## Assert ##
        tester.assertTextEqualsById("aaa", "3");
        tester.assertTextEqualsById("aaaLabel", "CCCC");
    }

}
