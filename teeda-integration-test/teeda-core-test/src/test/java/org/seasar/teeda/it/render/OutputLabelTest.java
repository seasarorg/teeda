/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
import org.seasar.teeda.unit.web.TeedaWebTestCase;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

/**
 * @author manhole
 */
public class OutputLabelTest extends TeedaWebTestCase {

    public static Test suite() throws Exception {
        return setUpTest(OutputLabelTest.class);
    }

    public void testRender() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/render/outputLabel.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        HtmlPage page = getHtmlPage(webClient, url);

        // ## Assert ##
        final String body = getBody(page).trim();
        System.out.println(body);

        HtmlSpan span = (HtmlSpan) page.getHtmlElementById("hello");
        assertEquals("Hello OutputLabel", span.asText());

        final String expected = readText("testRender.html");
        Diff diff = diff(expected, body);
        assertEquals(diff.toString(), true, diff.similar());
    }

}
