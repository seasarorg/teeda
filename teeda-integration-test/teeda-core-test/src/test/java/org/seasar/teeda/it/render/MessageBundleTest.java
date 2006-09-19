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

import org.seasar.teeda.unit.web.TeedaWebTestCase;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

/**
 * @author manhole
 */
public class MessageBundleTest extends TeedaWebTestCase {

    public static Test suite() throws Exception {
        return setUpTest(MessageBundleTest.class);
    }

    public void testRender1() throws Exception {
        // ## Arrange ##
        final URL url = getUrl("faces/render/bundle.jsp");
        System.out.println(url);

        final WebClient webClient = new WebClient();

        // ## Act ##
        // ## Assert ##

        // "en" locale
        {
            webClient.addRequestHeader("Accept-Language", "en,ja;q=0.5");
            HtmlPage page1 = getHtmlPage(webClient, url);
            final String body = getBody(page1).trim();
            System.out.println(body);

            assertEquals("Title", page1.getTitleText());
            HtmlSpan span = (HtmlSpan) page1.getHtmlElementById("greeting");
            assertEquals("Hello!", span.asText());
        }
        // "ja" locale
        {
            webClient.addRequestHeader("Accept-Language", "ja,en;q=0.5");
            HtmlPage page1 = getHtmlPage(webClient, url);
            final String body = getBody(page1).trim();
            System.out.println(body);

            assertEquals("タイトル", page1.getTitleText());
            HtmlSpan span = (HtmlSpan) page1.getHtmlElementById("greeting");
            assertEquals("こんにちは!", span.asText());
        }
    }

}
