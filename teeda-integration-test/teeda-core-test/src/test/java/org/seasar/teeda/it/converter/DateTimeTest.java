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
package org.seasar.teeda.it.converter;

import java.net.URL;

import junit.framework.Test;

import org.seasar.teeda.unit.web.TeedaWebTestCase;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * @author manhole
 */
public class DateTimeTest extends TeedaWebTestCase {

    public static Test suite() throws Exception {
        return setUpTest(DateTimeTest.class);
    }

    public void testRender() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/converter/dateTime.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        // ## Assert ##
        HtmlPage page1 = getHtmlPage(webClient, url);

        final String body1 = getBody(page1).trim();
        System.out.println(body1);
        assertEquals("dateTime.jsp", page1.getTitleText());

        HtmlTextInput aaa1 = getAaa(page1);
        assertEquals("", aaa1.getValueAttribute());
        aaa1.setValueAttribute("2011.12.23");

        HtmlSubmitInput submit1 = (HtmlSubmitInput) page1
                .getHtmlElementById("submit1");

        // 2

        HtmlPage page2 = (HtmlPage) submit1.click();
        final String body2 = getBody(page2);
        System.out.println(body2.trim());

        HtmlTextInput aaa2 = getAaa(page2);
        assertEquals("2011.12.23", aaa2.getValueAttribute());
        HtmlSpan bbb2 = getBbb(page2);
        assertEquals("2011/12/23", bbb2.asText());
    }

    private HtmlSpan getBbb(HtmlPage page2) {
        return (HtmlSpan) page2.getHtmlElementById("bbb");
    }

    private HtmlTextInput getAaa(HtmlPage page1) {
        return (HtmlTextInput) page1.getHtmlElementById("aaa");
    }

}
