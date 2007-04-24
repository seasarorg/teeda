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
package org.seasar.teeda.it.render;

import java.net.URL;

import junit.framework.Test;
import junitx.framework.StringAssert;

import org.seasar.teeda.unit.web.TeedaWebTestCase;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author manhole
 */
public class VerbatimTest extends TeedaWebTestCase {

    public static Test suite() throws Exception {
        return setUpTest(VerbatimTest.class);
    }

    public void testRender() throws Exception {
        // ## Arrange ##
        final URL url = getUrl("faces/render/verbatim.jsp");
        System.out.println(url);

        final WebClient webClient = new WebClient();

        // ## Act ##
        final HtmlPage page = getHtmlPage(webClient, url);

        // ## Assert ##
        final String body = getBody(page).trim();
        System.out.println(body);
        assertEquals("verbatim", page.getTitleText());
        final int aaaPos = body.indexOf("aaa");
        assertEquals(true, -1 < aaaPos);
        final int bbbPos = body.indexOf("bbb", aaaPos);
        assertEquals(true, -1 < bbbPos);
        final int cccPos = body.indexOf("ccc", bbbPos);
        assertEquals(true, -1 < cccPos);
        final int dddPos = body.indexOf("ddd", cccPos);
        assertEquals(true, -1 < dddPos);
        final int eeePos = body.indexOf("eee", dddPos);
        assertEquals(true, -1 < eeePos);
        // verbatimはspamにならないこと
        StringAssert.assertNotContains("<span", body);
    }

}
