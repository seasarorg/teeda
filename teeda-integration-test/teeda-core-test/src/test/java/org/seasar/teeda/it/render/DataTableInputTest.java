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

import org.custommonkey.xmlunit.Diff;
import org.jaxen.JaxenException;
import org.seasar.teeda.unit.web.TeedaWebTestCase;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.xpath.HtmlUnitXPath;

/**
 * @author manhole
 */
public class DataTableInputTest extends TeedaWebTestCase {

    public static Test suite() throws Exception {
        return setUpTest(DataTableInputTest.class);
    }

    public void testRender4() throws Exception {
        URL url = getUrl("faces/render/dataTable4.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // 1

        HtmlPage page1 = getHtmlPage(webClient, url);

        final String body1 = getBody(page1).trim();
        System.out.println(body1);
        assertEquals("dataTable4.jsp", page1.getTitleText());

        final String expected = readText("testRender4.html");
        Diff diff = diff(expected, body1);
        assertEquals(diff.toString(), true, diff.similar());

        {
            final HtmlTextInput a = getInputA(page1);
            final HtmlTextInput b = getInputB(page1);
            final HtmlTextInput c = getInputC(page1);
            assertEquals("1000", a.getValueAttribute());
            assertEquals("2000", b.getValueAttribute());
            assertEquals("3000", c.getValueAttribute());

            c.setValueAttribute("3003");
            assertEquals("3003", c.getValueAttribute());
        }

        // 2

        final HtmlPage page2 = (HtmlPage) getSubmit(page1).click();
        final String body2 = getBody(page2).trim();
        System.out.println(body2);
        {
            final HtmlTextInput a = getInputA(page2);
            final HtmlTextInput b = getInputB(page2);
            final HtmlTextInput c = getInputC(page2);
            assertEquals("1000", a.getValueAttribute());
            assertEquals("2000", b.getValueAttribute());
            assertEquals("3003", c.getValueAttribute());
        }
    }

    private HtmlSubmitInput getSubmit(HtmlPage page1) {
        return (HtmlSubmitInput) page1.getHtmlElementById("submit1");
    }

    private HtmlForm getForm(HtmlPage page1) {
        return (HtmlForm) page1.getForms().get(0);
    }

    private HtmlTextInput getInputA(final HtmlPage page1) throws JaxenException {
        final HtmlForm form = getForm(page1);
        return (HtmlTextInput) new HtmlUnitXPath(".//input[@type='text']")
                .selectNodes(form).get(0);
    }

    private HtmlTextInput getInputB(final HtmlPage page1) throws JaxenException {
        final HtmlForm form = getForm(page1);
        return (HtmlTextInput) new HtmlUnitXPath(".//input[@type='text']")
                .selectNodes(form).get(1);
    }

    private HtmlTextInput getInputC(final HtmlPage page1) throws JaxenException {
        final HtmlForm form = getForm(page1);
        return (HtmlTextInput) new HtmlUnitXPath(".//input[@type='text']")
                .selectNodes(form).get(2);
    }

}
