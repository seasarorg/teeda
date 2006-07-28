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
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * @author shot
 */
public class RenderedTest extends AbstractTestCase {

    public static Test suite() throws Exception {
        return setUpTestSuite(RenderedTest.class);
    }

    public void testRender() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/render/rendered.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        // ## Assert ##
        HtmlPage page = getHtmlPage(webClient, url);
        final String body1 = getBody(page).trim();
        System.out.println(body1);
        //        assertEquals("rendered.jsp", page.getTitleText());

        HtmlTextInput textA1 = (HtmlTextInput) page.getHtmlElementById("a");
        //        assertEquals("", textA1.getValueAttribute());
        textA1.setValueAttribute("123");

        HtmlSubmitInput submit1 = (HtmlSubmitInput) page
                .getHtmlElementById("submit1");
        page = (HtmlPage) submit1.click();

        final String body2 = getBody(page);
        System.out.println(body2.trim());
        HtmlSpan textA2 = (HtmlSpan) page.getHtmlElementById("b1");
        //        assertEquals("hoge", textA2.getValueAttribute());
        System.out.println(textA2.asText());
    }
}
