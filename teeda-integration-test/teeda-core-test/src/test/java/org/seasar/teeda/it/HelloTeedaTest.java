/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.teeda.it;

import java.net.URL;

import junit.framework.Test;
import junitx.framework.StringAssert;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.unit.xmlunit.TextTrimmingDifferenceListener;
import org.seasar.teeda.unit.web.TeedaWebTestCase;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

/**
 * @author manhole
 */
public class HelloTeedaTest extends TeedaWebTestCase {

    public static Test suite() throws Exception {
        return setUpTest(HelloTeedaTest.class);
    }

    public void testHelloWorld() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/helloTeeda.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        HtmlPage page = getHtmlPage(webClient, url);

        // ## Assert ##
        final String body = getBody(page).trim();
        System.out.println(body);
        StringAssert.assertContains("Hello Teeda!", body);

        HtmlSpan span = (HtmlSpan) page.getHtmlElementById("hello");
        System.out.println(span);
        assertEquals("Hello Teeda!", span.asText());
        assertEquals("this is helloTeeda.jsp", page.getTitleText());

        final String expected = readText("testHelloTeeda.html");
        Diff diff = new Diff(expected, body);
        diff.overrideDifferenceListener(new TextTrimmingDifferenceListener());
        assertEquals(diff.toString(), true, diff.similar());
    }

    public void testHelloWorldJa() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/helloTeedaJa.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        HtmlPage page = getHtmlPage(webClient, url);

        // ## Assert ##
        final String body = getBody(page).trim();
        System.out.println(body);
        StringAssert.assertContains("こんにちは、てぃーだ!", body);
        StringAssert.assertContains("こんにちは、てぃーだ!", page.getWebResponse()
                .getContentAsString());

        HtmlSpan span = (HtmlSpan) page.getHtmlElementById("hello");
        System.out.println(span);
        assertEquals("こんにちは、てぃーだ!", span.asText());
        assertEquals("これはhelloTeedaJa.jspです", page.getTitleText());

        final String expected = readText("testHelloTeedaJa.html");
        Diff diff = new Diff(expected, body);
        diff.overrideDifferenceListener(new TextTrimmingDifferenceListener());
        assertEquals(diff.toString(), true, diff.similar());
    }

}
