/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
 * @author yone
 */
public class Hello2Test extends TeedaWebTestCase {

	public static Test suite() throws Exception {
		return setUpTest(Hello2Test.class);
	}

    // <span te:invisible="true">の場合，spanタグを出力しないTest
    public void testInvisibleRender() throws Exception {
        // ## Arrange ##
        TeedaWebTester tester = new TeedaWebTester();

        // ## Act ##
        tester.beginAt(getBaseUrl(), "view/hello/hello2.html");
        tester.dumpHtml();

        // ## Assert ##
        tester.assertTextEqualsById("pp", "こんにちはInvisibleTest");
    }
    
    // <span te:omittag="true">の場合，spanタグを出力しないTest
    public void testOmittagRender() throws Exception {
        // ## Arrange ##
        TeedaWebTester tester = new TeedaWebTester();

        // ## Act ##
        tester.beginAt(getBaseUrl(), "view/hello/hello2.html");
        tester.dumpHtml();

        // ## Assert ##
        tester.assertTextEqualsById("pp2", "こんにちはInvisibleTest");
    }
    
    // <span te:invisible="false" te:omittag="false">の場合，spanタグを出力するTest
    public void testInvisibleAndOmittagRender() throws Exception {
        // ## Arrange ##
        TeedaWebTester tester = new TeedaWebTester();

        // ## Act ##
        tester.beginAt(getBaseUrl(), "view/hello/hello2.html");
        tester.dumpHtml();

        // ## Assert ##
        tester.assertTextEqualsById("aaa-3", "InvisibleTest");
    }

    // <span te:invisible="true" te:omittag="false">の場合，spanタグを出力しないTest
    public void testInvisibleTrueAndOmittagFalseRender() throws Exception {
        // ## Arrange ##
        TeedaWebTester tester = new TeedaWebTester();

        // ## Act ##
        tester.beginAt(getBaseUrl(), "view/hello/hello2.html");
        tester.dumpHtml();

        // ## Assert ##
        tester.assertTextEqualsById("pp4", "こんにちはInvisibleTest");
    }
    // <span te:invisible="false" te:omittag="true">の場合，spanタグを出力しないTest
    public void testInvisibleFalseAndOmittagTrueRender() throws Exception {
        // ## Arrange ##
        TeedaWebTester tester = new TeedaWebTester();

        // ## Act ##
        tester.beginAt(getBaseUrl(), "view/hello/hello2.html");
        tester.dumpHtml();

        // ## Assert ##
        tester.assertTextEqualsById("pp5", "こんにちはInvisibleTest");
    }
}
