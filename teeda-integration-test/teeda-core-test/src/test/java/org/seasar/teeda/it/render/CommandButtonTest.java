/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * @author manhole
 */
public class CommandButtonTest extends TeedaWebTestCase {

    public static Test suite() throws Exception {
        return setUpTest(CommandButtonTest.class);
    }

    public void testRender() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/render/commandButton.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        // ## Assert ##
        HtmlPage page1 = getHtmlPage(webClient, url);

        final String body = getBody(page1).trim();
        System.out.println(body);
        assertEquals("commandButton.jsp", page1.getTitleText());

        HtmlTextInput input1 = (HtmlTextInput) page1
                .getHtmlElementById("text1");
        assertEquals("123", input1.getValueAttribute());

        final String expected = readText("testRender.html");
        Diff diff = diff(expected, body);
        assertEquals(diff.toString(), true, diff.similar());

        HtmlSubmitInput submit = (HtmlSubmitInput) page1
                .getHtmlElementById("button1");

        HtmlPage page2 = (HtmlPage) submit.click();
        System.out.println(getBody(page2).trim());

        HtmlTextInput input2 = (HtmlTextInput) page2
                .getHtmlElementById("text1");
        assertEquals("123 + 1", "124", input2.getValueAttribute());
    }

}
