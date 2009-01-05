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
package org.seasar.teeda.unit.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import org.seasar.teeda.util.MavenUtil;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author manhole
 */
public abstract class TeedaWebTestCase extends TestCase {

    private static final String ENCODING = "UTF-8";

    public static Test setUpTest(final Class testClass) throws Exception {
        if (testClass == null) {
            throw new NullPointerException("testClass");
        }
        final TestSuite testSuite = new TestSuite(testClass);
        final File pomFile = MavenUtil.getProjectPomFile(testClass);
        return setUpTest(testSuite, pomFile);
    }

    public static Test setUpTest(final TestSuite testSuite, final File pomFile)
        throws MavenEmbedderException, ArtifactResolutionException,
        ArtifactNotFoundException, ProjectBuildingException {

        final MavenEmbedder maven = new MavenEmbedder();
        maven.setClassLoader(Thread.currentThread().getContextClassLoader());
        final MavenEmbedderLogger mavenLogger = new MavenEmbedderConsoleLogger();
        mavenLogger.setThreshold(MavenEmbedderLogger.LEVEL_ERROR);
        maven.setLogger(mavenLogger);
        maven.start();

        final MavenProject mavenProject = maven
            .readProjectWithDependencies(pomFile);
        final Build build = mavenProject.getBuild();
        // xxxx/target
        final String buildDirectory = build.getDirectory();
        final String finalName = build.getFinalName();

        maven.stop();

        final JettyServerSetup jettySetup = new JettyServerSetup(testSuite);
        jettySetup.setPort(WebTestEnvironment.getPort());
        final File webapp = new File(buildDirectory, finalName);
        jettySetup.setWebapp(webapp);

        final WebApplicationTestSetup webApplicationTestSetup = new WebApplicationTestSetup(
            jettySetup);
        webApplicationTestSetup.setPomFile(pomFile);
        return webApplicationTestSetup;
    }

    protected URL getUrl(final String path) throws MalformedURLException {
        return new URL(getBaseUrl() + path);
    }

    protected String getBody(final HtmlPage page)
        throws UnsupportedEncodingException {
        final WebResponse webResponse = page.getWebResponse();
        final String pageEncoding = page.getPageEncoding();
        final String body = new String(webResponse.getResponseBody(),
            pageEncoding);
        return body;
    }

    protected Diff diff(final String expected, final String actual)
        throws SAXException, IOException, ParserConfigurationException {
        final Document cDoc = XMLUnit.buildDocument(XMLUnit.getControlParser(),
            new StringReader(expected));
        final Document tDoc = XMLUnit.buildDocument(XMLUnit.getTestParser(),
            new StringReader(actual));
        HtmlDomUtil.removeBlankTextNode(cDoc.getChildNodes());
        HtmlDomUtil.removeBlankTextNode(tDoc.getChildNodes());
        final Diff diff = new Diff(cDoc, tDoc);
        final DifferenceListenerChain chain = new DifferenceListenerChain();
        chain.addDifferenceListener(new TextTrimmingDifferenceListener());
        chain.addDifferenceListener(new IgnoreJsessionidDifferenceListener());
        chain.addDifferenceListener(new RegexpDifferenceListener());
        diff.overrideDifferenceListener(chain);
        return diff;
    }

    protected HtmlPage getHtmlPage(final WebClient webClient, final URL url)
        throws IOException {
        try {
            final Page page = webClient.getPage(url);
            if (page instanceof HtmlPage) {
                return (HtmlPage) page;
            }
            final WebResponse webResponse = page.getWebResponse();
            System.err.println("StatusCode=" + webResponse.getStatusCode());
            System.err.println("StatusMessage="
                + webResponse.getStatusMessage());
            final String s = new String(webResponse.getResponseBody());
            System.err.println("[[" + s + "]]");
            throw new ClassCastException(page.getClass().getName());
        } catch (final FailingHttpStatusCodeException e) {
            final WebResponse response = e.getResponse();
            e.printStackTrace();
            final byte[] bodyBytes = response.getResponseBody();
            final String body = new String(bodyBytes, ENCODING);
            System.out.println(body);
            throw e;
        }
    }

    protected String readText(final String fileName) {
        return readText(getClass(), fileName, ENCODING);
    }

    protected URL getFileAsUrl(final String fileName) {
        final String path = resolvePath(getClass(), fileName);
        return ResourceUtil.getResource(path);
    }

    protected String resolvePath(final Class clazz, final String fileName) {
        final String classNamePath = clazz.getName().replace('.', '/');
        {
            final String pathByClass = classNamePath + "_" + fileName;
            if (ResourceUtil.isExist(pathByClass)) {
                return pathByClass;
            }
        }
        {
            final String pathByClass = classNamePath + "-" + fileName;
            if (ResourceUtil.isExist(pathByClass)) {
                return pathByClass;
            }
        }
        final String pathByPackage = clazz.getPackage().getName().replace('.',
            '/')
            + "/" + fileName;
        return pathByPackage;
    }

    protected String readText(final Class clazz, final String fileName,
        final String encoding) {
        final String path = resolvePath(getClass(), fileName);
        final InputStream is = ResourceUtil.getResourceAsStream(path);
        final Reader reader = InputStreamReaderUtil.create(is, encoding);
        return ReaderUtil.readText(reader);
    }

    protected String getBaseUrl() {
        return "http://localhost:" + getPort() + "/";
    }

    protected int getPort() {
        return WebTestEnvironment.getPort();
    }

}
