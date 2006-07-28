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

import org.seasar.teeda.it.AbstractTestCase;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

/**
 * @author manhole
 */
public class InputSecretTest extends AbstractTestCase {

    public static Test suite() throws Exception {
        return setUpTestSuite(InputSecretTest.class);
    }

    public void testRender() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/render/inputSecret.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        // ## Assert ##
        HtmlPage page1 = getHtmlPage(webClient, url);

        final String body = getBody(page1).trim();
        System.out.println(body);
        assertEquals("inputSecret.jsp", page1.getTitleText());

        HtmlPasswordInput secretA1 = (HtmlPasswordInput) page1
                .getHtmlElementById("secretA");
        HtmlPasswordInput secretB1 = (HtmlPasswordInput) page1
                .getHtmlElementById("secretB");
        assertEquals("", secretA1.getValueAttribute());
        assertEquals("", secretB1.getValueAttribute());

        secretA1.setValueAttribute("abc");
        secretB1.setValueAttribute("defg");

        //        final String expected = readText("testRender.html");
        //        Diff diff = diff(expected, body);
        //        assertEquals(diff.toString(), true, diff.similar());

        HtmlSubmitInput submit1 = (HtmlSubmitInput) page1
                .getHtmlElementById("submit1");

        HtmlPage page2 = (HtmlPage) submit1.click();
        System.out.println(getBody(page2).trim());

        HtmlPasswordInput secretA2 = (HtmlPasswordInput) page2
                .getHtmlElementById("secretA");
        HtmlPasswordInput secretB2 = (HtmlPasswordInput) page2
                .getHtmlElementById("secretB");
        assertEquals("", secretA2.getValueAttribute());
        assertEquals("redisplay", "defg", secretB2.getValueAttribute());
    }

}
