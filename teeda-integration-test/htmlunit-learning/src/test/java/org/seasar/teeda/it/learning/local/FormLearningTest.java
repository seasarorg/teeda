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
package org.seasar.teeda.it.learning.local;

import java.net.URL;
import java.util.List;

import org.jaxen.XPath;
import org.seasar.teeda.unit.web.TeedaWebTestCase;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.xpath.HtmlUnitXPath;

/**
 * @author manhole
 */
public class FormLearningTest extends TeedaWebTestCase {

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

    public void testGetMultiForm_GetForms() throws Exception {
        // ## Arrange ##
        URL url = getFileAsUrl("getMultiForm.html");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        // ## Assert ##
        HtmlPage page = (HtmlPage) webClient.getPage(url);

        final List forms = page.getForms();
        assertEquals(2, forms.size());
        final HtmlForm form1 = (HtmlForm) forms.get(0);
        final HtmlForm form2 = (HtmlForm) forms.get(1);
        assertEquals("aaaForm", form1.getId());
        assertEquals("bbbForm", form2.getId());
    }

    public void testGetMultiForm_XPath() throws Exception {
        // ## Arrange ##
        URL url = getFileAsUrl("getMultiForm.html");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        // ## Assert ##
        HtmlPage page = (HtmlPage) webClient.getPage(url);

        final HtmlForm form1 = (HtmlForm) new HtmlUnitXPath(".//form[1]")
            .selectSingleNode(page);
        final HtmlForm form2 = (HtmlForm) new HtmlUnitXPath(".//form[2]")
            .selectSingleNode(page);
        assertEquals("aaaForm", form1.getId());
        assertEquals("bbbForm", form2.getId());
    }

    public void testGetMultiFormInsideDiv_GetForms() throws Exception {
        // ## Arrange ##
        URL url = getFileAsUrl("getMultiFormInsideDiv.html");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        // ## Assert ##
        HtmlPage page = (HtmlPage) webClient.getPage(url);

        final List forms = page.getForms();
        assertEquals(2, forms.size());
        final HtmlForm form1 = (HtmlForm) forms.get(0);
        final HtmlForm form2 = (HtmlForm) forms.get(1);
        assertEquals("aaaForm", form1.getId());
        assertEquals("bbbForm", form2.getId());
    }

    public void testGetMultiFormInsideDiv_XPath() throws Exception {
        // ## Arrange ##
        URL url = getFileAsUrl("getMultiFormInsideDiv.html");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        // ## Assert ##
        HtmlPage page = (HtmlPage) webClient.getPage(url);

        {
            final HtmlForm form1 = (HtmlForm) new HtmlUnitXPath(".//form[1]")
                .selectSingleNode(page);
            final HtmlForm form2 = (HtmlForm) new HtmlUnitXPath(".//form[2]")
                .selectSingleNode(page);
            assertEquals("aaaForm", form1.getId());
            try {
                assertEquals("bbbForm", form2.getId());
                fail();
            } catch (NullPointerException e) {
            }
        }
        {
            final List form1 = (List) new HtmlUnitXPath(".//form[1]")
                .selectNodes(page);
            assertEquals(2, form1.size());
        }
        {
            final HtmlForm form1 = (HtmlForm) new HtmlUnitXPath("//div[2]/form")
                .selectSingleNode(page);
            assertEquals("bbbForm", form1.getId());
        }
        {
            final HtmlForm form1 = (HtmlForm) new HtmlUnitXPath(
                "/descendant::form[1] ").selectSingleNode(page);
            final HtmlForm form2 = (HtmlForm) new HtmlUnitXPath(
                "/descendant::form[2] ").selectSingleNode(page);
            assertEquals("aaaForm", form1.getId());
            assertEquals("bbbForm", form2.getId());
        }
    }

}
