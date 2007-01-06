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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Test;

import org.seasar.teeda.unit.web.TeedaWebTestCase;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * @author yone
 */
public class ConverterTest extends TeedaWebTestCase {

    private static final DateFormat df1_ = new SimpleDateFormat("yyyyMMdd");

    private static final DateFormat df2_ = new SimpleDateFormat("yyyy/MM/dd");

    public static Test suite() throws Exception {
        return setUpTest(ConverterTest.class);
    }

    public void testRender() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/render/converter.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        // ## Assert ##
        HtmlPage page1 = getHtmlPage(webClient, url);

        final String body = getBody(page1).trim();
        System.out.println(body);
        assertEquals("converter.jsp", page1.getTitleText());
        HtmlTextInput input1 = (HtmlTextInput) page1
                .getHtmlElementById("text1");

        HtmlSpan span1 = (HtmlSpan) page1.getHtmlElementById("result");

        final Date sysdate = new Date();
        assertEquals(df1_.format(sysdate), input1.getValueAttribute());
        assertEquals(df2_.format(sysdate), span1.asText());

        input1.setValueAttribute("20061231");
        HtmlSubmitInput submit = (HtmlSubmitInput) page1
                .getHtmlElementById("button1");

        HtmlPage page2 = (HtmlPage) submit.click();
        System.out.println(getBody(page2).trim());
        HtmlTextInput input2 = (HtmlTextInput) page2
                .getHtmlElementById("text1");
        HtmlSpan span2 = (HtmlSpan) page2.getHtmlElementById("result");

        assertEquals("20061231", input2.getValueAttribute());
        assertEquals("2006/12/31", span2.asText());
    }

}
