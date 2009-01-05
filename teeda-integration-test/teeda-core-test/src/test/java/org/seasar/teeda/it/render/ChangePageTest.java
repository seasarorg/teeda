/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

/**
 * @author shot
 */
public class ChangePageTest extends TeedaWebTestCase {

    public static Test suite() throws Exception {
        return setUpTest(ChangePageTest.class);
    }

    public void testRender() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/render/changePage1.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        // ## Assert ##
        HtmlPage page1 = getHtmlPage(webClient, url);

        final String body = getBody(page1).trim();
        System.out.println(body);
        assertEquals("changePage1.jsp", page1.getTitleText());

        HtmlForm form = (HtmlForm) page1
                .getHtmlElementById("changingPagesForm1");
        HtmlSubmitInput button = (HtmlSubmitInput) form
                .getHtmlElementById("button1");
        HtmlPage page2 = (HtmlPage) button.click();
        System.out.println(getBody(page2).trim());

        assertEquals("changePage1_result.jsp", page2.getTitleText());
    }

}
