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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.Test;

import org.seasar.teeda.unit.web.TeedaWebTestCase;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * https://www.seasar.org/issues/browse/TEEDA-240
 * 
 * @author manhole
 */
public class SecurityTest extends TeedaWebTestCase {

    public static Test suite() throws Exception {
        return setUpTest(SecurityTest.class);
    }

    /**
     * WEB-INF/web.xmlが見えてはならない。
     */
    public void testWebXml() throws Exception {
        assertProtection("faces/WEB-INF/web.xml");
    }

    public void testWebXml2() throws Exception {
        assertProtection("faces/WEB-INF%2Fweb.xml");
    }

    public void testWebXml3() throws Exception {
        assertProtection("faces%2FWEB-INF%2Fweb.xml");
    }

    public void testWebXml4() throws Exception {
        assertProtection("faces/./WEB-INF/web.xml");
    }

    public void testWebXml5() throws Exception {
        assertProtection("faces/../WEB-INF/web.xml");
    }

    public void testWebXml6() throws Exception {
        assertProtection("WEB-INF/web.xml");
    }

    /**
     * META-INF/配下が見えてはならない。
     */
    public void testMetaInf() throws Exception {
        assertProtection("faces/META-INF/aaa.txt");
    }

    public void testMetaInf2() throws Exception {
        assertProtection("META-INF/aaa.txt");
    }

    public void testAppDicon() throws Exception {
        assertProtection("faces/WEB-INF/classes/app.dicon");
    }

    private void assertProtection(final String s) throws MalformedURLException,
            IOException, UnsupportedEncodingException {
        // ## Arrange ##
        final URL url = getUrl(s);
        System.out.println(url);

        final WebClient webClient = new WebClient();
        webClient.setThrowExceptionOnFailingStatusCode(false);

        // ## Act ##
        // ## Assert ##
        final Page page = webClient.getPage(url);
        final WebResponse webResponse = page.getWebResponse();
        final String body;
        if (page instanceof HtmlPage) {
            body = getBody((HtmlPage) page).trim();
        } else {
            final byte[] bytes = webResponse.getResponseBody();
            body = new String(bytes);
        }
        System.out.println(body);
        assertEquals(404, webResponse.getStatusCode());
    }

}
