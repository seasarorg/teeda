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
package teeda.test;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import org.apache.xerces.xni.XNIException;

import junit.framework.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author manhole
 */
public class FirstTest extends AbstractTestCase {

    public static Test suite() throws Exception {
        return setUpTestSuite(FirstTest.class);
    }

    public void testA() throws Exception {
        URL url = getUrl("a.html");
        System.out.println(url);

        WebClient webClient = new WebClient();

        // ## Act ##
        try {
            HtmlPage page1 = (HtmlPage) webClient.getPage(url);
            System.out.println(getBody(page1));

            // ## Assert ##
            assertEquals("this is a.html", page1.getTitleText());
        } catch (XNIException e) {
            printXNIException(e);
            fail();
        }
    }

    private void printXNIException(XNIException e) {
        e.printStackTrace();
        Exception cause = e.getException();
        if (cause instanceof InvocationTargetException) {
            Throwable targetException = ((InvocationTargetException) cause)
                .getTargetException();
            targetException.printStackTrace();
            if (targetException instanceof XNIException) {
                printXNIException((XNIException) targetException);
            }
        }
    }

}
