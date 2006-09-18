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
import org.seasar.teeda.unit.web.TeedaWebTestCase;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

/**
 * @author manhole
 */
public class SelectManyListboxTest extends TeedaWebTestCase {

    public static Test suite() throws Exception {
        return setUpTestSuite(SelectManyListboxTest.class);
    }

    public void testRender1() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/render/selectManyListbox1.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        // ## Assert ##
        HtmlPage page1 = getHtmlPage(webClient, url);

        final String body1 = getBody(page1).trim();
        System.out.println(body1);
        assertEquals("selectManyListbox1.jsp", page1.getTitleText());

        final String expected = readText("testRender1.html");
        Diff diff = diff(expected, body1);
        assertEquals(diff.toString(), true, diff.similar());

        // 1

        {
            HtmlSelect aaa = getAaa(page1);
            assertEquals(true, aaa.isMultipleSelectEnabled());
            assertEquals(3, aaa.getOptionSize());
            assertEquals(false, aaa.getOption(0).isSelected());
            assertEquals(true, aaa.getOption(1).isSelected());
            assertEquals(false, aaa.getOption(2).isSelected());
            aaa.getOption(1).setSelected(false);
            aaa.getOption(2).setSelected(true);
        }
        assertEquals("[2]", getSelectedString(page1));

        HtmlSubmitInput submit1 = (HtmlSubmitInput) page1
                .getHtmlElementById("submit1");

        // 2

        HtmlPage page2 = (HtmlPage) submit1.click();
        final String body2 = getBody(page2);
        System.out.println(body2.trim());

        {
            HtmlSelect aaa = getAaa(page2);
            assertEquals(true, aaa.isMultipleSelectEnabled());
            assertEquals(3, aaa.getOptionSize());
            assertEquals(false, aaa.getOption(0).isSelected());
            assertEquals(false, aaa.getOption(1).isSelected());
            assertEquals(true, aaa.getOption(2).isSelected());
            aaa.getOption(0).setSelected(true);
        }
        assertEquals("[3]", getSelectedString(page2));

        HtmlSubmitInput submit2 = (HtmlSubmitInput) page2
                .getHtmlElementById("submit1");

        // 3 TODO

        HtmlPage page3 = (HtmlPage) submit2.click();
        final String body3 = getBody(page3);
        System.out.println(body3.trim());

        {
            HtmlSelect aaa = getAaa(page3);
            assertEquals(true, aaa.isMultipleSelectEnabled());
            assertEquals(3, aaa.getOptionSize());
            assertEquals(true, aaa.getOption(0).isSelected());
            assertEquals(false, aaa.getOption(1).isSelected());
            assertEquals(true, aaa.getOption(2).isSelected());
            aaa.getOption(1).setSelected(true);
            aaa.getOption(2).setSelected(false);
        }
        assertEquals("[1, 3]", getSelectedString(page3));

        HtmlSubmitInput submit3 = (HtmlSubmitInput) page3
                .getHtmlElementById("submit1");

        // 4

        HtmlPage page4 = (HtmlPage) submit3.click();
        final String body4 = getBody(page4);
        System.out.println(body4.trim());

        {
            HtmlSelect aaa = getAaa(page4);
            assertEquals(true, aaa.isMultipleSelectEnabled());
            assertEquals(3, aaa.getOptionSize());
            assertEquals(true, aaa.getOption(0).isSelected());
            assertEquals(true, aaa.getOption(1).isSelected());
            assertEquals(false, aaa.getOption(2).isSelected());
        }

        assertEquals("[1, 2]", getSelectedString(page4));

    }

    public void testRender2() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/render/selectManyListbox2.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        // ## Assert ##
        HtmlPage page1 = getHtmlPage(webClient, url);

        final String body1 = getBody(page1).trim();
        System.out.println(body1);
        assertEquals("selectManyListbox2.jsp", page1.getTitleText());

        final String expected = readText("testRender2.html");
        Diff diff = diff(expected, body1);
        assertEquals(diff.toString(), true, diff.similar());

        // 1

        {
            HtmlSelect aaa = getAaa(page1);
            assertEquals(true, aaa.isMultipleSelectEnabled());
            assertEquals(4, aaa.getOptionSize());
            assertEquals(false, aaa.getOption(0).isSelected());
            assertEquals(true, aaa.getOption(1).isSelected());
            assertEquals(false, aaa.getOption(2).isSelected());
            assertEquals(false, aaa.getOption(3).isSelected());
            assertEquals(true, aaa.getOption(3).isDisabled());
            aaa.getOption(1).setSelected(true);
            aaa.getOption(2).setSelected(true);
        }
        assertEquals("[2]", getSelectedString(page1));

        HtmlSubmitInput submit1 = (HtmlSubmitInput) page1
                .getHtmlElementById("submit1");

        // 2

        HtmlPage page2 = (HtmlPage) submit1.click();
        final String body2 = getBody(page2);
        System.out.println(body2.trim());

        {
            HtmlSelect aaa = getAaa(page1);
            assertEquals(false, aaa.getOption(0).isSelected());
            assertEquals(true, aaa.getOption(1).isSelected());
            assertEquals(true, aaa.getOption(2).isSelected());
            assertEquals(false, aaa.getOption(3).isSelected());
        }
        assertEquals("[2, 3]", getSelectedString(page2));
    }

    private HtmlSelect getAaa(HtmlPage page) {
        return (HtmlSelect) page.getHtmlElementById("aaa");
    }

    private String getSelectedString(HtmlPage page) {
        return page.getHtmlElementById("selectedString").asText();
    }

}
