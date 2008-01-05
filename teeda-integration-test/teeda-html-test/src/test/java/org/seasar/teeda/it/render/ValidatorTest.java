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

import java.util.Locale;

import junit.framework.Test;

import org.seasar.teeda.unit.web.TeedaWebTestCase;
import org.seasar.teeda.unit.web.TeedaWebTester;

/**
 * @author shot
 */
public class ValidatorTest extends TeedaWebTestCase {

    public static Test suite() throws Exception {
        return setUpTest(ValidatorTest.class);
    }

    private Locale defaultLocale;

    protected void setUp() throws Exception {
        super.setUp();
        defaultLocale = Locale.getDefault();
    }

    protected void tearDown() throws Exception {
        Locale.setDefault(defaultLocale);
        super.tearDown();
    }

    public void testValidatorTargetOn() throws Exception {
        // ## Arrange ##
        Locale.setDefault(Locale.JAPAN);
        TeedaWebTester tester = new TeedaWebTester();
        //tester.setLocale(Locale.JAPAN);

        // ## Act ##
        tester.beginAt(getBaseUrl(), "view/validator/validator.html");
        tester.dumpHtml();

        tester.setTextById("aaa", "0");
        tester.setTextById("bbb", "0");

        tester.submitByName("validatorForm:doValidate");

        // ## Assert ##
        tester.dumpHtml();
        tester.assertTextInElementById("allMessages", "aaa");
        tester.assertTextInElementById("allMessages", "bbb");
        //tester.assertTextPresent("aaaは0よりも大きくなくてはいけません。");
        //tester.assertTextPresent("bbbは0よりも大きくなくてはいけません。");
    }

    public void testValidatorTargetOff() throws Exception {
        // ## Arrange ##
        TeedaWebTester tester = new TeedaWebTester();
        tester.setLocale(Locale.JAPAN);

        // ## Act ##
        tester.beginAt(getBaseUrl(), "view/validator/validator.html");
        tester.dumpHtml();

        tester.setTextById("aaa", "0");
        tester.setTextById("bbb", "0");

        tester.submitByName("validatorForm:doNoValidate");

        // ## Assert ##
        tester.dumpHtml();
        tester.assertElementNotPresentById("allMessages");
        //tester.assertTextNotInElementById("allMessages", "aaa");
        //tester.assertTextNotInElementById("allMessages", "bbb");
        //tester.assertTextNotPresent("aaaは0よりも大きくなくてはいけません。");
        //tester.assertTextNotPresent("bbbは0よりも大きくなくてはいけません。");
    }

}
