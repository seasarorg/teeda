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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.custommonkey.xmlunit.Diff;
import org.seasar.framework.util.InputStreamReaderUtil;
import org.seasar.framework.util.ReaderUtil;
import org.seasar.framework.util.ResourceNotFoundRuntimeException;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.teeda.core.unit.JettyServerSetup;
import org.seasar.teeda.core.unit.WebApplicationTestSetup;
import org.seasar.teeda.core.unit.xmlunit.DifferenceListenerChain;
import org.seasar.teeda.core.unit.xmlunit.IgnoreJsessionidDifferenceListener;
import org.seasar.teeda.core.unit.xmlunit.RegexpDifferenceListener;
import org.seasar.teeda.core.unit.xmlunit.TextTrimmingDifferenceListener;
import org.seasar.teeda.core.util.MavenUtil;
import org.seasar.teeda.core.util.SocketUtil;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author manhole
 */
public abstract class AbstractTestCase extends TestCase {

    private static int port_ = SocketUtil.findFreePort();

    private static String baseUrl_ = "http://localhost:" + port_ + "/";

    protected static WebApplicationTestSetup setUpTestSuite(
            final Class testClass) {
        if (testClass == null) {
            throw new NullPointerException("testClass");
        }
        TestSuite testSuite = new TestSuite(testClass);
        File pomFile = MavenUtil.getProjectPomFile(testClass);
        return setUpTestSuite(testSuite, pomFile);
    }

    protected static WebApplicationTestSetup setUpTestSuite(
            TestSuite testSuite, File pomFile) {
        JettyServerSetup jettySetup = new JettyServerSetup(testSuite);
        jettySetup.setPort(port_);
        File webapp = new File(pomFile.getParentFile(),
                "target/teeda-integration-test");
        jettySetup.setWebapp(webapp);

        WebApplicationTestSetup webApplicationTestSetup = new WebApplicationTestSetup(
                jettySetup);
        webApplicationTestSetup.setPomFile(pomFile);
        return webApplicationTestSetup;
    }

    protected URL getUrl(String path) throws MalformedURLException {
        return new URL(baseUrl_ + path);
    }

    protected String getBody(HtmlPage page) throws UnsupportedEncodingException {
        WebResponse webResponse = page.getWebResponse();
        String body = new String(webResponse.getResponseBody(), page
                .getPageEncoding());
        return body;
    }

    protected String readText(String s) {
        String pathByClass = getClass().getName().replace('.', '/') + "_" + s;
        InputStream is = null;
        try {
            is = ResourceUtil.getResourceAsStream(pathByClass);
        } catch (ResourceNotFoundRuntimeException e) {
            String pathByPackage = getClass().getPackage().getName().replace(
                    '.', '/')
                    + "/" + s;
            is = ResourceUtil.getResourceAsStream(pathByPackage);
        }
        Reader reader = InputStreamReaderUtil.create(is, "UTF-8");
        return ReaderUtil.readText(reader);
    }

    protected URL getFileAsUrl(String s) {
        String fileNameByClass = getClass().getName().replace('.', '/') + "_"
                + s;
        try {
            return ResourceUtil.getResource(fileNameByClass);
        } catch (ResourceNotFoundRuntimeException e) {
            String fileNameByPackage = getClass().getPackage().getName()
                    .replace('.', '/')
                    + "/" + s;
            return ResourceUtil.getResource(fileNameByPackage);
        }
    }

    protected Diff diff(final String expected, final String actual)
            throws SAXException, IOException, ParserConfigurationException {
        Diff diff = new Diff(expected, actual);
        DifferenceListenerChain chain = new DifferenceListenerChain();
        chain.addDifferenceListener(new TextTrimmingDifferenceListener());
        chain.addDifferenceListener(new IgnoreJsessionidDifferenceListener());
        chain.addDifferenceListener(new RegexpDifferenceListener());
        diff.overrideDifferenceListener(chain);
        return diff;
    }

}
