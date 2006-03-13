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
package org.seasar.teeda.it.learning.local;

import java.net.URL;

import org.jaxen.XPath;
import org.seasar.teeda.it.AbstractTestCase;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.xpath.HtmlUnitXPath;

/**
 * @author manhole
 */
public class FormTest extends AbstractTestCase {

    public void testFormSubmit() throws Exception {
        // ## Arrange ##
        URL url = getFileAsUrl("formSubmit1.html");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        HtmlPage page1 = (HtmlPage) webClient.getPage(url);
        HtmlForm form = page1.getFormByName("myFormName");

        HtmlSubmitInput button = (HtmlSubmitInput) form
                .getInputByName("doSubmitName");
        HtmlPage page2 = (HtmlPage) button.click();

        // ## Assert ##
        assertEquals("end", page2.getTitleText());
        assertEquals(getFileAsUrl("end.html"), page2.getWebResponse().getUrl());
    }

    public void testFormSubmit_UsingIdAttribute() throws Exception {
        // ## Arrange ##
        URL url = getFileAsUrl("formSubmit1.html");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        HtmlPage page1 = (HtmlPage) webClient.getPage(url);
        HtmlForm form = (HtmlForm) page1.getHtmlElementById("myFormId");

        HtmlSubmitInput button = (HtmlSubmitInput) form
                .getHtmlElementById("doSubmitId");
        HtmlPage page2 = (HtmlPage) button.click();

        // ## Assert ##
        assertEquals("end", page2.getTitleText());
        assertEquals(getFileAsUrl("end.html"), page2.getWebResponse().getUrl());
    }

    public void testFormSubmit_UsingXPath() throws Exception {
        // ## Arrange ##
        URL url = getFileAsUrl("formSubmit1.html");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        HtmlPage page1 = (HtmlPage) webClient.getPage(url);
        XPath xpath = new HtmlUnitXPath("//form[@id='myFormId']");
        HtmlForm form = (HtmlForm) xpath.selectSingleNode(page1);

        HtmlSubmitInput button = (HtmlSubmitInput) form
                .getHtmlElementById("doSubmitId");
        HtmlPage page2 = (HtmlPage) button.click();

        // ## Assert ##
        assertEquals("end", page2.getTitleText());
        assertEquals(getFileAsUrl("end.html"), page2.getWebResponse().getUrl());
    }

}
