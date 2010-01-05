/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
import java.util.Random;

import junit.framework.Test;

import org.seasar.teeda.unit.web.TeedaWebTestCase;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * @author manhole
 */
public class AddManyTest extends TeedaWebTestCase {

    public static Test suite() throws Exception {
        return setUpTest(AddManyTest.class);
    }

    /*
     * 同時に複数アクセスした際にも正しく足し算の結果を得られること。
     */
    public void testRender() throws Exception {
        // webClient.addRequestHeader("Accept-Language", "en,ja;q=0.5");
        // ## Arrange ##
        final String jsessionId;
        final URL url = getUrl("view/add/add.html");
        System.out.println(url);

        // ## Act ##
        {
            final WebClient webClient = new WebClient();
            HtmlPage page1 = getHtmlPage(webClient, url);
            final String val = page1.getWebResponse().getResponseHeaderValue(
                "Set-Cookie");
            System.out.println("--> " + val);
            jsessionId = getJSessionId(val);
            System.out.println(jsessionId);

            final HtmlTextInput arg1 = getArg1(page1);
            final HtmlTextInput arg2 = getArg2(page1);

            arg1.setValueAttribute("22");
            arg2.setValueAttribute("45");

            HtmlSubmitInput submit1 = getSubmit(page1);

            final HtmlPage page2 = (HtmlPage) submit1.click();
            final HtmlSpan result = (HtmlSpan) page2
                .getHtmlElementById("result");
            assertEquals("67", result.asText());
        }
        final Random random = new Random();
        final List list = new ArrayList();
        for (int i = 0; i < 10; i++) {

            final Thread t = new Thread(new Runnable() {

                public void run() {
                    try {
                        final WebClient webClient = new WebClient();
                        webClient.addRequestHeader("Cookie",
                            "$Version=0; JSESSIONID=" + jsessionId
                                + "; $Path=/");
                        for (int i = 0; i < 3; i++) {
                            HtmlPage page1 = getHtmlPage(webClient, url);
                            //webClient.removeRequestHeader("Cookie");

                            final HtmlTextInput arg1 = getArg1(page1);
                            final HtmlTextInput arg2 = getArg2(page1);

                            final int i1 = random.nextInt(10000);
                            final int i2 = random.nextInt(10000);
                            arg1.setValueAttribute("" + i1);
                            arg2.setValueAttribute("" + i2);

                            HtmlSubmitInput submit1 = getSubmit(page1);

                            final HtmlPage page2 = (HtmlPage) submit1.click();
                            final HtmlSpan result = (HtmlSpan) page2
                                .getHtmlElementById("result");
                            assertEquals(i1 + " + " + i2 + " => " + (i1 + i2)
                                + "であること", "" + (i1 + i2), result.asText());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            list.add(t);
        }

        // ## Act ##
        for (final Iterator it = list.iterator(); it.hasNext();) {
            final Thread t = (Thread) it.next();
            t.start();
        }
        for (final Iterator it = list.iterator(); it.hasNext();) {
            final Thread t = (Thread) it.next();
            t.join();
        }
    }

    private HtmlSubmitInput getSubmit(HtmlPage page1) {
        return (HtmlSubmitInput) page1.getHtmlElementById("doCalculate");
    }

    private HtmlTextInput getArg2(HtmlPage page1) {
        return (HtmlTextInput) page1.getHtmlElementById("arg2");
    }

    private HtmlTextInput getArg1(HtmlPage page1) {
        return (HtmlTextInput) page1.getHtmlElementById("arg1");
    }

    private String getJSessionId(final String val) {
        final String s = "JSESSIONID=";
        if (!val.startsWith(s)) {
            throw new IllegalArgumentException(val);
        }
        final int pos = val.indexOf(';');
        if (-1 < pos) {
            return val.substring(s.length(), pos);
        }
        return val.substring(s.length());
    }

}
