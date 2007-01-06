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
package org.seasar.teeda.it.learning.local;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.httpclient.NameValuePair;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author manhole
 */
public class FirstTest extends TestCase {

    public void testNone() throws Exception {
    }

    public void no_testGoogle() throws Exception {
        WebClient webClient = new WebClient();
        HtmlPage page = (HtmlPage) webClient.getPage(new URL(
                "http://www.google.co.jp/"));

        assertEquals("Google", page.getTitleText());
        assertEquals("UTF-8", page.getPageEncoding());

        WebResponse webResponse = page.getWebResponse();
        assertEquals("ISO-8859-1", webResponse.getContentCharSet());
        List responseHeaders = webResponse.getResponseHeaders();
        for (Iterator it = responseHeaders.iterator(); it.hasNext();) {
            NameValuePair pair = (NameValuePair) it.next();
            System.out.println("header: " + pair);
        }
        assertEquals("text/html", webResponse.getContentType());

        //化ける
        //System.out.println(webResponse.getContentAsString());

        // これなら化けない
        // HtmlPage#getPageEncoding でmetaタグからcharsetを取得しているようだ
        System.out.println(new String(webResponse.getResponseBody(), page
                .getPageEncoding()));
    }

}
