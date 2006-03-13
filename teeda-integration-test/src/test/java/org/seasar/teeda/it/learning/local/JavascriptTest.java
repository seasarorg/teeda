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
import java.util.ArrayList;
import java.util.List;

import org.seasar.teeda.it.AbstractTestCase;
import org.seasar.teeda.it.MyWebClient;

import com.gargoylesoftware.htmlunit.CollectingAlertHandler;
import com.gargoylesoftware.htmlunit.KeyValuePair;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author manhole
 */
public class JavascriptTest extends AbstractTestCase {

    public void testOnloadAlert() throws Exception {
        // ## Arrange ##
        URL url = getFileAsUrl("onload.html");
        System.out.println(url);

        // ## Act ##
        WebClient webClient = new WebClient();
        List collectedAlerts = new ArrayList();
        webClient.setAlertHandler(new CollectingAlertHandler(collectedAlerts));

        HtmlPage page = (HtmlPage) webClient.getPage(url);

        // ## Assert ##
        assertEquals("alert sample", page.getTitleText());
        assertEquals(1, collectedAlerts.size());
        assertEquals("foo", collectedAlerts.get(0));
    }

    public void testFormObj() throws Exception {
        // ## Arrange ##
        URL url = getFileAsUrl("formObj.html");
        System.out.println(url);

        WebClient webClient = new WebClient();
        List collectedAlerts = new ArrayList();
        webClient.setAlertHandler(new CollectingAlertHandler(collectedAlerts));

        // ## Act ##

        HtmlPage page = (HtmlPage) webClient.getPage(url);
        HtmlInput input = (HtmlInput) page.getHtmlElementById("button1");
        input.click();

        // ## Assert ##
        assertEquals(1, collectedAlerts.size());
        System.out.println(collectedAlerts);
        assertEquals("fooForm", collectedAlerts.get(0));
    }

    public void testSubmitByButton() throws Exception {
        // ## Arrange ##
        URL url = getFileAsUrl("submitByButton.html");
        System.out.println(url);

        WebClient webClient = new WebClient();
        List collectedAlerts = new ArrayList();
        webClient.setAlertHandler(new CollectingAlertHandler(collectedAlerts));

        // ## Act ##
        HtmlPage page = (HtmlPage) webClient.getPage(url);
        HtmlInput input = (HtmlInput) page.getHtmlElementById("button1");

        HtmlPage page2 = (HtmlPage) input.click();

        // ## Assert ##
        assertEquals("end", page2.getTitleText());
        assertEquals(getFileAsUrl("end.html"), page2.getWebResponse().getUrl());
    }

    public void testSubmitByLink() throws Exception {
        // ## Arrange ##
        URL url = getFileAsUrl("submitByLink.html");
        System.out.println(url);

        MyWebClient webClient = new MyWebClient();
        List collectedAlerts = new ArrayList();
        webClient.setAlertHandler(new CollectingAlertHandler(collectedAlerts));

        // ## Act ##
        HtmlPage page = (HtmlPage) webClient.getPage(url);
        HtmlAnchor link = (HtmlAnchor) page.getHtmlElementById("link1");

        assertEquals(0, collectedAlerts.size());
        HtmlPage page2 = (HtmlPage) link.click();

        // ## Assert ##
        assertEquals(1, collectedAlerts.size());
        assertEquals("end", page2.getTitleText());
        assertEquals(getFileAsUrl("end.html"), page2.getWebResponse().getUrl());

        KeyValuePair linkMarker = webClient
            .getRequestParameter("fooForm:__link_clicked__");
        assertNotNull(linkMarker);
        assertEquals("fooForm:link1", linkMarker.getValue());
    }

    public void testSubmitByLink2() throws Exception {
        // ## Arrange ##
        URL url = getFileAsUrl("submitByLink2.html");
        System.out.println(url);

        MyWebClient webClient = new MyWebClient();
        List collectedAlerts = new ArrayList();
        webClient.setAlertHandler(new CollectingAlertHandler(collectedAlerts));

        // ## Act ##
        HtmlPage page = (HtmlPage) webClient.getPage(url);
        HtmlAnchor link = (HtmlAnchor) page.getHtmlElementById("fooForm:link1");

        assertEquals(0, collectedAlerts.size());
        HtmlPage page2 = (HtmlPage) link.click();

        // ## Assert ##
        assertEquals(1, collectedAlerts.size());
        assertEquals("end", page2.getTitleText());
        assertEquals(getFileAsUrl("end.html"), page2.getWebResponse().getUrl());

        KeyValuePair linkMarker = webClient
            .getRequestParameter("fooForm:__link_clicked__");
        assertNotNull(linkMarker);
        assertEquals("fooForm:link1", linkMarker.getValue());

        KeyValuePair paramA = webClient.getRequestParameter("a");
        assertEquals("1", paramA.getValue());
    }

}
