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
package org.seasar.teeda.it.learning.server;

import java.net.URL;

import junit.framework.Test;

import org.seasar.teeda.unit.web.TeedaWebTestCase;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

/**
 * @author manhole
 */
public class FormTest extends TeedaWebTestCase {

    public static Test suite() throws Exception {
        return setUpTest(FormTest.class);
    }

    public void testFormSubmit() throws Exception {
        URL url = getUrl("learning/FormTest_formSubmit1.html");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        HtmlPage page1 = (HtmlPage) webClient.getPage(url);
        assertEquals("form submit 1", page1.getTitleText());
        HtmlForm form = (HtmlForm) page1.getHtmlElementById("myFormId");
        HtmlSubmitInput button = (HtmlSubmitInput) form
                .getHtmlElementById("doSubmitId");
        HtmlPage page2 = (HtmlPage) button.click();

        // ## Assert ##
        assertEquals("end", page2.getTitleText());
        assertEquals(getUrl("learning/end.html"), page2.getWebResponse()
                .getUrl());
    }

    public void testFormSubmitParam() throws Exception {
        // ## Arrange ##
        URL url = getUrl("learning/FormTest_formSubmitParam.html");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        HtmlPage page1 = (HtmlPage) webClient.getPage(url);
        assertEquals("form submit param", page1.getTitleText());
        HtmlForm form = (HtmlForm) page1.getHtmlElementById("myFormId");
        HtmlInput a = (HtmlInput) form.getHtmlElementById("a");
        a.setNodeValue("a_value");
        HtmlSubmitInput button = (HtmlSubmitInput) form
                .getHtmlElementById("doSubmitId");
        HtmlPage page2 = (HtmlPage) button.click();

        // ## Assert ##
        assertEquals(url, page2.getWebResponse().getUrl());
        assertEquals("form submit param", page2.getTitleText());
    }

}
