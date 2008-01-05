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

import java.net.URL;

import junit.framework.Test;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.unit.web.MyWebClient;
import org.seasar.teeda.unit.web.TeedaWebTestCase;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author manhole
 */
public class OutputLinkTest extends TeedaWebTestCase {

    public static Test suite() throws Exception {
        return setUpTest(OutputLinkTest.class);
    }

    public void testRender() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/render/outputLink1.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        HtmlPage page = getHtmlPage(webClient, url);

        // ## Assert ##
        final String body = getBody(page).trim();
        System.out.println(body);
        assertEquals("outputLink1.jsp", page.getTitleText());

        final String expected = readText("testOutputLink.html");
        Diff diff = diff(expected, body);
        assertEquals(diff.toString(), true, diff.similar());
    }

    public void testLinkClink() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/render/outputLink1.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();
        HtmlPage page1 = getHtmlPage(webClient, url);

        HtmlAnchor link = (HtmlAnchor) page1.getHtmlElementById("link1");

        // ## Act ##
        HtmlPage page2 = (HtmlPage) link.click();

        // ## Assert ##
        final String body = getBody(page2).trim();
        System.out.println(body);
        assertEquals("outputLink2.jsp", page2.getTitleText());
    }

    public void testParam1() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/render/outputLinkParam.jsp");
        System.out.println(url);

        MyWebClient webClient = new MyWebClient();

        // ## Act ##
        HtmlPage page = getHtmlPage(webClient, url);

        // ## Assert ##
        final String body = getBody(page).trim();
        System.out.println(body);
        assertEquals("outputLinkParam.jsp", page.getTitleText());

        HtmlAnchor link = (HtmlAnchor) page.getHtmlElementById("link1");
        assertEquals("xyz", link.asText());

        final String expected = readText("testParam.html");
        Diff diff = diff(expected, body);
        assertEquals(diff.toString(), true, diff.similar());
    }

    public void testParamJa() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/render/outputLinkParamJa.jsp");
        System.out.println(url);

        MyWebClient webClient = new MyWebClient();

        // ## Act ##
        HtmlPage page = getHtmlPage(webClient, url);

        // ## Assert ##
        final String body = getBody(page).trim();
        System.out.println(body);
        assertEquals("outputLinkParamJa.jsp", page.getTitleText());

        HtmlAnchor link = (HtmlAnchor) page.getHtmlElementById("link1");
        assertEquals("わをん", link.asText());

        final String expected = readText("testParamJa.html");
        Diff diff = diff(expected, body);
        assertEquals(diff.toString(), true, diff.similar());
    }

}
