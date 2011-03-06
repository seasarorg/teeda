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
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * @author manhole
 */
public class InputHiddenTest extends TeedaWebTestCase {

    public static Test suite() throws Exception {
        return setUpTest(InputHiddenTest.class);
    }

    public void testRender() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/render/inputHidden.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        // ## Assert ##
        HtmlPage page1 = getHtmlPage(webClient, url);

        final String body1 = getBody(page1).trim();
        System.out.println(body1);
        assertEquals("inputHidden.jsp", page1.getTitleText());

        final String expected = readText("testRender.html");
        Diff diff = diff(expected, body1);
        assertEquals(diff.toString(), true, diff.similar());

        HtmlTextInput textA1 = (HtmlTextInput) page1
                .getHtmlElementById("textA");
        HtmlHiddenInput hiddenA1 = (HtmlHiddenInput) page1
                .getHtmlElementById("hiddenA");
        assertEquals("", textA1.getValueAttribute());
        assertEquals("", hiddenA1.getValueAttribute());
        textA1.setValueAttribute("abc1234");

        HtmlSubmitInput submit1 = (HtmlSubmitInput) page1
                .getHtmlElementById("submit1");

        HtmlPage page2 = (HtmlPage) submit1.click();
        final String body2 = getBody(page2);
        System.out.println(body2.trim());

        HtmlTextInput textA2 = (HtmlTextInput) page2
                .getHtmlElementById("textA");
        assertEquals("abc1234", textA2.getValueAttribute());
        HtmlHiddenInput hiddenA2 = (HtmlHiddenInput) page2
                .getHtmlElementById("hiddenA");
        assertEquals("abc1234", hiddenA2.getValueAttribute());
        HtmlSpan outputA2 = (HtmlSpan) page2.getHtmlElementById("outputA");
        assertEquals("abc1234", outputA2.asText());
    }

}
