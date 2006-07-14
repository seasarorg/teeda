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

import junit.framework.Test;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.it.AbstractTestCase;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author manhole
 */
public class SubviewTest extends AbstractTestCase {

    public static Test suite() throws Exception {
        return setUpTestSuite(SubviewTest.class);
    }

    public void testRender1() throws Exception {
        // ## Arrange ##
        final URL url = getUrl("faces/render/subview1_0.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        // ## Assert ##
        HtmlPage page1 = getHtmlPage(webClient, url);
        final String body = getBody(page1).trim();
        System.out.println(body);

        final String expected = readText("testRender1.html");
        Diff diff = diff(expected, body);
        assertEquals(diff.toString(), true, diff.similar());

    }

}
