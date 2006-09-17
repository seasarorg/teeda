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
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.embedder.MavenEmbedder;
import org.apache.maven.embedder.MavenEmbedderConsoleLogger;
import org.apache.maven.embedder.MavenEmbedderException;
import org.apache.maven.embedder.MavenEmbedderLogger;
import org.apache.maven.model.Build;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuildingException;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.seasar.framework.util.InputStreamReaderUtil;
import org.seasar.framework.util.ReaderUtil;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.teeda.core.unit.xmlunit.DifferenceListenerChain;
import org.seasar.teeda.core.unit.xmlunit.HtmlDomUtil;
import org.seasar.teeda.core.unit.xmlunit.IgnoreJsessionidDifferenceListener;
import org.seasar.teeda.core.unit.xmlunit.RegexpDifferenceListener;
import org.seasar.teeda.core.unit.xmlunit.TextTrimmingDifferenceListener;
import org.seasar.teeda.it.unit.JettyServerSetup;
import org.seasar.teeda.it.unit.WebApplicationTestSetup;
import org.seasar.teeda.it.util.MavenUtil;
import org.seasar.teeda.it.util.SocketUtil;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author manhole
 */
public abstract class AbstractTestCase extends TestCase {

    private static int port_ = SocketUtil.findFreePort();

    private static String baseUrl_ = "http://localhost:" + port_ + "/";

    private static final String ENCODING = "UTF-8";

    protected static Test setUpTestSuite(final Class testClass)
        throws Exception {
        if (testClass == null) {
            throw new NullPointerException("testClass");
        }
        TestSuite testSuite = new TestSuite(testClass);
        File pomFile = MavenUtil.getProjectPomFile(testClass);
        return setUpTestSuite(testSuite, pomFile);
    }

    protected static Test setUpTestSuite(final TestSuite testSuite,
        final File pomFile) throws MavenEmbedderException,
        ArtifactResolutionException, ArtifactNotFoundException,
        ProjectBuildingException {

        MavenEmbedder maven = new MavenEmbedder();
        maven.setClassLoader(Thread.currentThread().getContextClassLoader());
        MavenEmbedderLogger mavenLogger = new MavenEmbedderConsoleLogger();
        mavenLogger.setThreshold(MavenEmbedderLogger.LEVEL_ERROR);
        maven.setLogger(mavenLogger);
        maven.start();

        MavenProject mavenProject = maven.readProjectWithDependencies(pomFile);
        final Build build = mavenProject.getBuild();
        // xxxx/target
        final String buildDirectory = build.getDirectory();
        final String finalName = build.getFinalName();

        maven.stop();

        JettyServerSetup jettySetup = new JettyServerSetup(testSuite);
        jettySetup.setPort(port_);
        File webapp = new File(buildDirectory, finalName);
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
        final WebResponse webResponse = page.getWebResponse();
        final String pageEncoding = page.getPageEncoding();
        final String body = new String(webResponse.getResponseBody(),
            pageEncoding);
        return body;
    }

    protected String readText(String fileName) {
        return readText(getClass(), fileName, ENCODING);
    }

    protected URL getFileAsUrl(String s) {
        final String fileNameByClass = getClass().getName().replace('.', '/')
            + "_" + s;
        if (ResourceUtil.isExist(fileNameByClass)) {
            return ResourceUtil.getResource(fileNameByClass);
        } else {
            final String fileNameByPackage = getClass().getPackage().getName()
                .replace('.', '/')
                + "/" + s;
            return ResourceUtil.getResource(fileNameByPackage);
        }
    }

    protected Diff diff(final String expected, final String actual)
        throws SAXException, IOException, ParserConfigurationException {
        Document cDoc = XMLUnit.buildDocument(XMLUnit.getControlParser(),
            new StringReader(expected));
        Document tDoc = XMLUnit.buildDocument(XMLUnit.getTestParser(),
            new StringReader(actual));
        HtmlDomUtil.removeBlankTextNode(cDoc.getChildNodes());
        HtmlDomUtil.removeBlankTextNode(tDoc.getChildNodes());
        Diff diff = new Diff(cDoc, tDoc);
        DifferenceListenerChain chain = new DifferenceListenerChain();
        chain.addDifferenceListener(new TextTrimmingDifferenceListener());
        chain.addDifferenceListener(new IgnoreJsessionidDifferenceListener());
        chain.addDifferenceListener(new RegexpDifferenceListener());
        diff.overrideDifferenceListener(chain);
        return diff;
    }

    protected HtmlPage getHtmlPage(WebClient webClient, URL url)
        throws IOException {
        try {
            return (HtmlPage) webClient.getPage(url);
        } catch (FailingHttpStatusCodeException e) {
            final WebResponse response = e.getResponse();
            e.printStackTrace();
            final byte[] bodyBytes = response.getResponseBody();
            final String body = new String(bodyBytes, ENCODING);
            System.out.println(body);
            throw e;
        }
    }

    protected String readText(Class clazz, String fileName, String encoding) {
        String pathByClass = clazz.getName().replace('.', '/') + "_" + fileName;
        java.io.InputStream is = null;
        if (ResourceUtil.isExist(pathByClass)) {
            is = ResourceUtil.getResourceAsStream(pathByClass);
        } else {
            String pathByPackage = clazz.getPackage().getName().replace('.',
                '/')
                + "/" + fileName;
            is = ResourceUtil.getResourceAsStream(pathByPackage);
        }
        Reader reader = InputStreamReaderUtil.create(is, encoding);
        return ReaderUtil.readText(reader);
    }

}
