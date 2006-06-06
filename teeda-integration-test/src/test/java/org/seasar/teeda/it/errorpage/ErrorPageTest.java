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
package org.seasar.teeda.it.errorpage;

import java.net.URL;

import junit.framework.Test;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;
import org.seasar.teeda.it.AbstractTestCase;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

/**
 * @author manhole
 */
public class ErrorPageTest extends AbstractTestCase {

    public static Test suite() throws Exception {
        return setUpTestSuite(ErrorPageTest.class);
    }

    public void test1() throws Exception {
        S2Container container = S2ContainerFactory.create("app.dicon");
        container.init();
    }

    public void testRender() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/errorpage/throwException.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        // ## Assert ##
        HtmlPage page1 = (HtmlPage) webClient.getPage(url);

        final String body1 = getBody(page1).trim();
        System.out.println(body1);
        assertEquals("throwException.jsp", page1.getTitleText());

        HtmlSubmitInput submit1 = (HtmlSubmitInput) page1
                .getHtmlElementById("submit1");

        // 2

        HtmlPage page2 = (HtmlPage) submit1.click();
        final String body2 = getBody(page2);
        System.out.println(body2.trim());
        assertEquals("errorPage.jsp", page2.getTitleText());
    }

}
