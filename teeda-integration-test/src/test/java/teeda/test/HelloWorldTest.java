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
package teeda.test;

import java.net.URL;

import junit.framework.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

/**
 * @author manhole
 */
public class HelloWorldTest extends AbstractTestCase {

    public static Test suite() throws Exception {
        return setUpTestSuite(HelloWorldTest.class);
    }

    public void testHelloWorld() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/helloWorld.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        HtmlPage page = (HtmlPage) webClient.getPage(url);

        // ## Assert ##
        final String body = getBody(page).trim();
        System.out.println(body);
        assertEquals(true, -1 < body.indexOf("Hello World!"));

        HtmlSpan span = (HtmlSpan) page.getHtmlElementById("hello");
        System.out.println(span);
        assertEquals("Hello World!", span.asText());

        // TODO
        //assertEquals("this is helloWorld.jsp", page.getTitleText());
    }

    public void testHelloWorldJa() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/helloWorldJa.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        HtmlPage page = (HtmlPage) webClient.getPage(url);

        // ## Assert ##
        final String body = getBody(page).trim();
        System.out.println(body);
        assertEquals(true, -1 < body.indexOf("こんにちは 世界!"));

        HtmlSpan span = (HtmlSpan) page.getHtmlElementById("hello");
        System.out.println(span);
        assertEquals("こんにちは 世界!", span.asText());
    }

}
