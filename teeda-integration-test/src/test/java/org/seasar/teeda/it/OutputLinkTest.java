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
package org.seasar.teeda.it;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Test;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.unit.DifferenceListenerChain;
import org.seasar.teeda.core.unit.IgnoreJsessionidDifferenceListener;
import org.seasar.teeda.core.unit.xmlunit.TextTrimmingDifferenceListener;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author manhole
 */
public class OutputLinkTest extends AbstractTestCase {

    public static Test suite() throws Exception {
        return setUpTestSuite(OutputLinkTest.class);
    }

    public void testOutputLink() throws Exception {
        // ## Arrange ##
        URL url = getUrl("faces/outputLink1.jsp");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        HtmlPage page = (HtmlPage) webClient.getPage(url);

        // ## Assert ##
        final String body = getBody(page).trim();
        System.out.println(body);
        assertEquals("this is outputLink1.jsp", page.getTitleText());

        final String expected = readText("testOutputLink.html");
        Diff diff = diff(expected, body);
        assertEquals(diff.toString(), true, diff.similar());
    }

    private Diff diff(final String expected, final String body)
        throws SAXException, IOException, ParserConfigurationException {
        Diff diff = new Diff(expected, body);
        DifferenceListenerChain chain = new DifferenceListenerChain();
        chain.addDifferenceListener(new TextTrimmingDifferenceListener());
        chain.addDifferenceListener(new IgnoreJsessionidDifferenceListener());
        diff.overrideDifferenceListener(chain);
        return diff;
    }

    public void testDiff1() throws Exception {
        Diff diff = diff("<a> <b></b> </a>", "<a><b></b></a>");
        assertEquals(diff.toString(), true, diff.similar());
    }

    public void testDiff2() throws Exception {
        Diff diff = diff("<a> <b></b> </a>", "<a><b></b></a>");
        diff.overrideDifferenceListener(new TextTrimmingDifferenceListener());
        assertEquals(diff.toString(), true, diff.similar());
    }

}
