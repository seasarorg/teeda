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
package org.seasar.teeda.unit.web;

import java.net.URL;

import junit.framework.AssertionFailedError;
import net.sourceforge.jwebunit.html.Table;

import org.seasar.framework.util.ResourceUtil;

/**
 * @author manhole
 */
public class TeedaWebTesterTest extends TeedaWebTestCase {

    private TeedaWebTester tester;
    private String baseUrl;

    protected void setUp() throws Exception {
        super.setUp();
        tester = new TeedaWebTester();
        baseUrl = ResourceUtil.getBuildDir(getClass()).toURL().toString();
        tester.getTestContext().setBaseUrl(baseUrl);
    }

    public void testHello() throws Exception {
        // ## Arrange ##
        final String relativeUrl = getFileAsRelativeUrl("hello.html");

        // ## Act ##
        tester.beginAt(relativeUrl);
        tester.dumpHtml();

        // ## Assert ##
        tester.assertTitleEquals("Hello page.");

        // これは完全一致で無くてもOK
        tester.assertTextInElement("aaa", "eeda");

        // これは完全一致ならOK
        boolean ok = true;
        try {
            tester.assertTextEquals("aaa", "eeda");
            ok = false;
        } catch (AssertionFailedError e) {
            ok = true;
            System.out.println(e.getMessage());
        }
        assertEquals(true, ok);
        tester.assertTextEquals("aaa", "Teeda!");
    }

    /*
     * htmlの先頭へBOMを付けるとUTF-8と認識するようなコードがWebResponseImplに
     * あるが、ここで試した感じでは効いていない。
     * 
     * EF BB BFが、3F 3F 3Fになってしまっているようだ。
     * 
     * Webサーバがレスポンスにcharsetを含めていれば問題ないため、
     * ここでは深追いしない。
     */
    public void __testHelloJa() throws Exception {
        // ## Arrange ##
        final String relativeUrl = getFileAsRelativeUrl("helloJa.html");

        // ## Act ##
        tester.beginAt(relativeUrl);
        tester.dumpHtml();

        // ## Assert ##
        tester.assertTitleEquals("Hello page.");

        // これは完全一致で無くてもOK
        tester.assertTextInElement("aaa", "eeda");

        // これは完全一致ならOK
        boolean ok = true;
        try {
            tester.assertTextEquals("aaa", "eeda");
            ok = false;
        } catch (AssertionFailedError e) {
            ok = true;
            System.out.println(e.getMessage());
        }
        assertEquals(true, ok);
        tester.assertTextEquals("aaa", "Teeda!");
    }

    public void testAttribute() throws Exception {
        // ## Arrange ##
        final String relativeUrl = getFileAsRelativeUrl("attribute.html");

        // ## Act ##
        tester.beginAt(relativeUrl);
        tester.dumpHtml();

        // ## Assert ##
        tester.assertAttributeEquals("aaa", "x", "xxx");
        tester.assertAttributeEquals("aaa", "y", "z");
        boolean ok = true;
        try {
            tester.assertAttributeEquals("aaa", "y", "123");
            ok = false;
        } catch (AssertionFailedError e) {
        }
        assertEquals(true, ok);
        try {
            tester.assertAttributeEquals("aaa", "ggg", "123");
            ok = false;
        } catch (AssertionFailedError e) {
        }
        assertEquals(true, ok);
        try {
            tester.assertAttributeEquals("unknown", "ggg", "123");
            ok = false;
        } catch (AssertionFailedError e) {
        }
        assertEquals(true, ok);
    }

    public void testTable() throws Exception {
        // ## Arrange ##
        final String relativeUrl = getFileAsRelativeUrl("table1.html");

        // ## Act ##
        tester.beginAt(relativeUrl);
        tester.dumpHtml();

        // ## Assert ##
        // jwebunitのAPIでは、summary属性からもtableを取得できる
        final Table tableById = tester.getTable("aaa");
        final Table tableBySummary = tester.getTable("bbb");
        tableById.assertEquals(tableBySummary);

        //            assertEquals(null, tester.getTableById("bbb"));
        //            tester.getTable("aaa").assertEquals(tester.getTableById("aaa"));
    }

    public void testSetTextById() throws Exception {
        // ## Arrange ##
        final String relativeUrl = getFileAsRelativeUrl("textfield.html");

        // ## Act ##
        tester.beginAt(relativeUrl);
        tester.dumpHtml();

        // ## Assert ##
        tester.assertAttributeEquals("aaaId", "value", "aaaValue");
        tester.setTextById("aaaId", "foo");
        tester.assertAttributeEquals("aaaId", "value", "foo");
    }

    private String getFileAsRelativeUrl(final String file) {
        final URL url = getFileAsUrl(file);
        final String fullUrl = url.toString();
        String relativeUrl = null;
        if (fullUrl.startsWith(baseUrl)) {
            relativeUrl = fullUrl.substring(baseUrl.length());
        }
        System.out.println(relativeUrl);
        return relativeUrl;
    }

}
