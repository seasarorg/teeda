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
import com.gargoylesoftware.htmlunit.html.HtmlLabel;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author yone
 */
public class OutputLabelInGridTest extends AbstractTestCase {

    public static Test suite() throws Exception {
        return setUpTestSuite(OutputLabelInGridTest.class);
    }

    public void testOutputLabelInGrid() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/render/outputLabelInGrid.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        HtmlPage page = getHtmlPage(webClient, url);

        // ## Assert ##
        final String body = getBody(page).trim();
        System.out.println(body);

        final String expected = readText("testRender.html");
        Diff diff = diff(expected, body);
        assertEquals(diff.toString(), true, diff.similar());

        HtmlLabel label1 = (HtmlLabel) page.getHtmlElementById("lbl1");
        assertEquals("label", label1.getTagName());
        assertEquals("foo", label1.getForAttribute());

        HtmlLabel label2 = (HtmlLabel) page.getHtmlElementById("lbl2");
        assertEquals("label", label2.getTagName());
        assertEquals("bar", label2.getForAttribute());

        // for debug
        /*
         Iterator elements = page.getAllHtmlChildElements();
         while(elements.hasNext()) {
         HtmlElement element = (HtmlElement)elements.next();
         if(element instanceof HtmlLabel) {
         HtmlLabel lbl = (HtmlLabel)element;
         System.out.println("Label for["+lbl.getForAttribute()+"]");
         }
         }
         */
    }

}
