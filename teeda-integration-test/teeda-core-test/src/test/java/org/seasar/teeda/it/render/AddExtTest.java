/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
 */
public class AddExtTest extends TeedaWebTestCase {

    public static Test suite() throws Exception {
        return setUpTest(AddExtTest.class);
    }

    public void testDummy() {
        assertTrue(true);
    }

    //TODO add Teeda Extension test.
    public void _testRender() throws Exception {
        // ## Arrange ##
        TeedaWebTester tester = new TeedaWebTester();

        // ## Act ##
        tester.beginAt(getBaseUrl(), "faces/render/addExt.jsp");
        tester.dumpHtml();

        tester.setTextById("text1", "1m");
        tester.setTextById("text2", "1m");

        tester.submitById("doCalculate");
        tester.dumpHtml();

        // ## Assert ##
        tester.assertTextEqualsById("text1", "1,000,000");
        tester.assertTextEqualsById("text2", "1,000,000");
    }

}
