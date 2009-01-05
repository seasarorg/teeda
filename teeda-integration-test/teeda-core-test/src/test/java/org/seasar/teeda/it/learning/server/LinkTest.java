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
package org.seasar.teeda.it.learning.server;

import junit.framework.Test;

import org.seasar.teeda.unit.web.TeedaWebTestCase;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author manhole
 */
public class LinkTest extends TeedaWebTestCase {

    public static Test suite() throws Exception {
        return setUpTest(LinkTest.class);
    }

    public void testLink() throws Exception {
        // ## Arrange ##
        WebClient webClient = new WebClient();
        HtmlPage page1 = (HtmlPage) webClient
                .getPage(getUrl("learning/LinkTest_1.html"));
        HtmlElement link = page1.getHtmlElementById("a");
        HtmlAnchor htmlAnchor = (HtmlAnchor) link;

        // ## Act ##
        HtmlPage page2 = (HtmlPage) htmlAnchor.click();

        // ## Assert ##
        assertEquals("link 2", page2.getTitleText());
    }

}
