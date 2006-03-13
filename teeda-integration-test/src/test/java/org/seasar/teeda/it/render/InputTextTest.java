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
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * @author manhole
 */
public class InputTextTest extends AbstractTestCase {

    public static Test suite() throws Exception {
        return setUpTestSuite(InputTextTest.class);
    }

    public void testRender() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/render/inputText.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        // ## Assert ##
        HtmlPage page1 = (HtmlPage) webClient.getPage(url);

        final String body1 = getBody(page1).trim();
        System.out.println(body1);
        assertEquals("inputText.jsp", page1.getTitleText());

        final String expected = readText("testRender.html");
        Diff diff = diff(expected, body1);
        assertEquals(diff.toString(), true, diff.similar());

        HtmlTextInput textA1 = (HtmlTextInput) page1
                .getHtmlElementById("textA");
        assertEquals("", textA1.getValueAttribute());
        textA1.setValueAttribute("aa bbb");

        HtmlSubmitInput submit1 = (HtmlSubmitInput) page1
                .getHtmlElementById("submit1");

        HtmlPage page2 = (HtmlPage) submit1.click();
        final String body2 = getBody(page2);
        System.out.println(body2.trim());

        HtmlTextInput textA2 = (HtmlTextInput) page2
                .getHtmlElementById("textA");
        assertEquals("aa bbb", textA2.getValueAttribute());
        HtmlSpan outputA2 = (HtmlSpan) page2.getHtmlElementById("outputA");
        assertEquals("aa bbb", outputA2.asText());
    }

    public void testCalculate() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/render/inputText2.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        // ## Assert ##
        HtmlPage page1 = (HtmlPage) webClient.getPage(url);

        final String body = getBody(page1).trim();
        System.out.println(body);
        assertEquals("inputText2.jsp", page1.getTitleText());

        HtmlTextInput x1 = (HtmlTextInput) page1.getHtmlElementById("x");
        assertEquals("", x1.getValueAttribute());
        HtmlTextInput y1 = (HtmlTextInput) page1.getHtmlElementById("y");
        assertEquals("", y1.getValueAttribute());
        HtmlSpan result1 = (HtmlSpan) page1.getHtmlElementById("result");
        assertEquals("", result1.asText());
        x1.setValueAttribute("4");
        y1.setValueAttribute("70");

        HtmlSubmitInput multiply1 = (HtmlSubmitInput) page1
                .getHtmlElementById("multiply");

        HtmlPage page2 = (HtmlPage) multiply1.click();
        System.out.println(getBody(page2).trim());

        HtmlTextInput x2 = (HtmlTextInput) page2.getHtmlElementById("x");
        assertEquals("4", x2.getValueAttribute());
        HtmlTextInput y2 = (HtmlTextInput) page2.getHtmlElementById("y");
        assertEquals("70", y2.getValueAttribute());
        HtmlSpan result2 = (HtmlSpan) page2.getHtmlElementById("result");
        assertEquals("4 * 70 = 280", "280", result2.asText());

        HtmlSubmitInput subtract2 = (HtmlSubmitInput) page1
                .getHtmlElementById("subtract");
        HtmlPage page3 = (HtmlPage) subtract2.click();
        System.out.println(getBody(page3).trim());

        HtmlTextInput x3 = (HtmlTextInput) page3.getHtmlElementById("x");
        assertEquals("4", x3.getValueAttribute());
        HtmlTextInput y3 = (HtmlTextInput) page3.getHtmlElementById("y");
        assertEquals("70", y3.getValueAttribute());
        HtmlSpan result3 = (HtmlSpan) page3.getHtmlElementById("result");
        assertEquals("4 - 70 = -66", "-66", result3.asText());
    }

}
