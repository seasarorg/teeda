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

import java.net.URL;

import org.seasar.teeda.it.AbstractTestCase;

import junit.framework.Test;
import junitx.framework.StringAssert;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

/**
 * @author manhole
 */
public class OutputTextTest extends AbstractTestCase {

    public static Test suite() throws Exception {
        return setUpTestSuite(OutputTextTest.class);
    }

    public void testOutputText() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/render/outputText.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        HtmlPage page = (HtmlPage) webClient.getPage(url);

        // ## Assert ##
        final String body = getBody(page).trim();
        System.out.println(body);
        assertEquals("this is outputText.jsp", page.getTitleText());

        HtmlSpan span = (HtmlSpan) page.getHtmlElementById("hello");
        assertEquals("Hello OutputText", span.asText());
    }

    public void testOutputTextJa() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/render/outputTextJa.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        HtmlPage page = (HtmlPage) webClient.getPage(url);

        // ## Assert ##
        final String body = getBody(page).trim();
        System.out.println(body);
        assertEquals("outputTextJa.jspです", page.getTitleText());

        HtmlSpan span = (HtmlSpan) page.getHtmlElementById("hello");
        assertEquals("こんにちはOutputText", span.asText());
    }

    public void testOutputTextNoId() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/render/outputTextNoId.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        HtmlPage page = (HtmlPage) webClient.getPage(url);

        // ## Assert ##
        final String body = getBody(page).trim();
        System.out.println(body);
        assertEquals("outputTextNoId.jsp", page.getTitleText());

        StringAssert.assertNotContains("<span>", body);
    }

}
