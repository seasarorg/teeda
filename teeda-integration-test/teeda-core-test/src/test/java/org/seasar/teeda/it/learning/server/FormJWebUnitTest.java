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
package org.seasar.teeda.it.learning.server;

import junit.framework.Test;
import net.sourceforge.jwebunit.WebTester;

import org.seasar.teeda.unit.web.TeedaWebTestCase;

/**
 * @author manhole
 */
public class FormJWebUnitTest extends TeedaWebTestCase {

    public static Test suite() throws Exception {
        return setUpTestSuite(FormJWebUnitTest.class);
    }

    public void testFormSubmitByName() throws Exception {
        final WebTester webTester = new WebTester();
        webTester.getTestContext().setBaseUrl(getBaseUrl());
        webTester.beginAt("learning/FormTest_formSubmit1.html");
        webTester.dumpHtml();
        webTester.assertTitleEquals("form submit 1");
        webTester.submit("doSubmitName");
        webTester.dumpHtml();
        webTester.assertTitleEquals("end");
    }

    public void testFormSubmitById() throws Exception {
        final WebTester webTester = new WebTester();
        webTester.getTestContext().setBaseUrl(getBaseUrl());
        webTester.beginAt("learning/FormTest_formSubmit1.html");
        webTester.assertTitleEquals("form submit 1");
        webTester.clickButton("doSubmitId");
        webTester.assertTitleEquals("end");
    }

    public void testFormSubmitParam() throws Exception {
        // ## Arrange ##
        final WebTester webTester = new WebTester();
        webTester.getTestContext().setBaseUrl(getBaseUrl());

        // ## Act ##
        webTester.beginAt("learning/FormTest_formSubmitParam.html");
        webTester.assertTitleEquals("form submit param");
        webTester.setTextField("a", "a_value");
        webTester.clickButton("doSubmitId");

        // ## Assert ##
        webTester.assertTitleEquals("form submit param");
    }

}
