/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;

/**
 * @author manhole
 */
public class InputTextareaTest extends TeedaWebTestCase {

    public static Test suite() throws Exception {
        return setUpTest(InputTextareaTest.class);
    }

    public void testRender() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/render/inputTextarea.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        // ## Assert ##
        HtmlPage page1 = getHtmlPage(webClient, url);

        final String body1 = getBody(page1).trim();
        System.out.println(body1);
        assertEquals("inputTextarea.jsp", page1.getTitleText());

        final String expected = readText("testRender.html");
        Diff diff = diff(expected, body1);
        assertEquals(diff.toString(), true, diff.similar());

        HtmlTextArea submitA1 = (HtmlTextArea) page1
                .getHtmlElementById("textareaA");
        assertEquals("", submitA1.getText());

        submitA1.setText("a\n\nbb");

        HtmlSubmitInput submitA = (HtmlSubmitInput) page1
                .getHtmlElementById("submitA");

        HtmlPage page2 = (HtmlPage) submitA.click();
        String body2 = getBody(page2);
        System.out.println(body2.trim());

        HtmlTextArea submitA2 = (HtmlTextArea) page2
                .getHtmlElementById("textareaA");
        assertEquals("a\n\nbb", submitA2.getText());
        HtmlSpan outputA2 = (HtmlSpan) page2.getHtmlElementById("outputA");
        // XXX
        //assertEquals("a\n\nbb", outputA2.asText());
        assertEquals("a bb", outputA2.asText());
    }

}
