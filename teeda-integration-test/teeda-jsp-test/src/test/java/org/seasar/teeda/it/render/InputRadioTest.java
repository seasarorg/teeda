/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.Test;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.unit.web.TeedaWebTestCase;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

/**
 * https://www.seasar.org/issues/browse/TEEDA-290
 * 
 * @author manhole
 */
public class InputRadioTest extends TeedaWebTestCase {

    public static Test suite() throws Exception {
        return setUpTest(InputRadioTest.class);
    }

    public void testRender1() throws Exception {
        // ## Arrange ##
        final URL url = getUrl("faces/render/inputRadio.jsp");
        System.out.println(url);

        final WebClient webClient = new WebClient();

        // ## Act ##
        // ## Assert ##
        final HtmlPage page1 = getHtmlPage(webClient, url);

        final String body1 = getBody(page1).trim();
        System.out.println(body1);
        assertEquals("inputRadio", page1.getTitleText());

        final String expected = readText("testRender.html");
        final Diff diff = diff(expected, body1);
        assertEquals(diff.toString(), true, diff.similar());

        // 1

        {
            final HtmlRadioButtonInput radios[] = getRadioElementsByName(page1,
                    "inputRadioForm:aaaa");
            assertEquals(3, radios.length);
            assertEquals(false, radios[0].isChecked());
            assertEquals(true, radios[1].isChecked());
            assertEquals(false, radios[2].isChecked());
            assertEquals("2", getBbb(page1));

            radios[2].setChecked(true);
            assertEquals(false, radios[1].isChecked());
            assertEquals(true, radios[2].isChecked());
        }
        final HtmlSubmitInput submit1 = (HtmlSubmitInput) page1
                .getHtmlElementById("submit1");

        // 2

        final HtmlPage page2 = (HtmlPage) submit1.click();
        final String body2 = getBody(page2);
        System.out.println(body2.trim());
        {
            final HtmlRadioButtonInput radios[] = getRadioElementsByName(page2,
                    "inputRadioForm:aaaa");
            assertEquals(3, radios.length);
            assertEquals(false, radios[0].isChecked());
            assertEquals(false, radios[1].isChecked());
            assertEquals(true, radios[2].isChecked());
            assertEquals("3", getBbb(page2));
        }
    }

    private HtmlRadioButtonInput[] getRadioElementsByName(final HtmlPage page,
            final String radioName) {
        final List elements = new ArrayList();
        for (final Iterator it = page.getAllHtmlChildElements(); it.hasNext();) {
            final HtmlElement element = (HtmlElement) it.next();
            //System.out.println(element);
            if ("input".equals(element.getTagName())) {
                final String type = element.getAttributeValue("type");
                if ("radio".equals(type)) {
                    final String name = element.getAttributeValue("name");
                    if (radioName.equals(name)) {
                        elements.add(element);
                    }
                }
            }
        }
        return (HtmlRadioButtonInput[]) elements
                .toArray(new HtmlRadioButtonInput[elements.size()]);
    }

    private String getBbb(final HtmlPage page) {
        return page.getHtmlElementById("bbb").asText();
    }

}
