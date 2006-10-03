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
public class ConvertIntegerTest extends TeedaWebTestCase {

    public static Test suite() throws Exception {
        return setUpTest(ConvertIntegerTest.class);
    }

    public void testInputText() throws Exception {
        // ## Arrange ##
        TeedaWebTester tester = new TeedaWebTester();
        tester.getTestContext().setBaseUrl(getBaseUrl());

        // ## Act ##
        tester.beginAt("view/converter/inputText.html");
        tester.dumpHtml();
        tester.assertTitleMatch("inputText");

        // ## Assert ##
        tester.assertTextEquals("aaa", "123");
        doAssert(tester);
    }

    private void doAssert(TeedaWebTester tester) {
        tester.setTextById("aaa", "a");
        tester.setTextById("bbb", "b");
        tester.setTextById("ccc", "c");
        tester.setTextById("ddd", "d");
        tester.submit();
        tester.dumpHtml();

        tester.assertMatchInElement("aaaMessage", "aaa");
        tester.assertMatchInElement("bbbMessage", "bbbTitle");
        tester.assertMatchInElement("cccMessage", "cccLabel");
        tester.assertMatchInElement("dddMessage", "dddLabel");
        tester.assertNoMatchInElement("dddMessage", "dddTitle");
    }

    public void testInputTextarea() throws Exception {
        // ## Arrange ##
        TeedaWebTester tester = new TeedaWebTester();
        tester.getTestContext().setBaseUrl(getBaseUrl());

        // ## Act ##
        tester.beginAt("view/converter/inputTextarea.html");
        tester.dumpHtml();
        tester.assertTitleMatch("inputTextarea");

        // ## Assert ##
        tester.assertTextEquals("aaa", "123");
        doAssert(tester);
    }

    public void testInputHidden() throws Exception {
        // ## Arrange ##
        TeedaWebTester tester = new TeedaWebTester();
        tester.getTestContext().setBaseUrl(getBaseUrl());

        // ## Act ##
        tester.beginAt("view/converter/inputHidden.html");
        tester.dumpHtml();
        tester.assertTitleMatch("inputHidden");

        // ## Assert ##
        tester.assertTextEquals("aaa", "123");
        doAssert(tester);
    }

    public void testInputSecret() throws Exception {
        // ## Arrange ##
        TeedaWebTester tester = new TeedaWebTester();
        tester.getTestContext().setBaseUrl(getBaseUrl());

        // ## Act ##
        tester.beginAt("view/converter/inputSecret.html");
        tester.dumpHtml();
        tester.assertTitleMatch("inputSecret");

        // ## Assert ##
        tester.assertTextEquals("aaa", "");
        doAssert(tester);
    }

}
