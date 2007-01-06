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
package org.seasar.teeda.core.unit.xmlunit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.DifferenceListener;
import org.custommonkey.xmlunit.XMLUnit;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author manhole
 */
public class DiffTest extends TestCase {

    private DifferenceListener differenceListener_;

    protected void setUp() throws Exception {
        super.setUp();
        DifferenceListenerChain chain = new DifferenceListenerChain();
        chain.addDifferenceListener(new TextTrimmingDifferenceListener());
        chain.addDifferenceListener(new IgnoreJsessionidDifferenceListener());
        chain.addDifferenceListener(new RegexpDifferenceListener());
        differenceListener_ = chain;
    }

    public void test1() throws Exception {
        // ## Arrange ##
        final String a = readText(getClass(),
            "SelectManyListboxTest_expected.html", "UTF-8");
        final String b = readText(getClass(),
            "SelectManyListboxTest_actual.html", "UTF-8");

        Diff diff = createDiff(a, b);
        diff.overrideDifferenceListener(differenceListener_);

        // ## Act ##
        // ## Assert ##
        assertEquals(diff.toString(), true, diff.similar());
    }

    private Diff createDiff(final String c, final String t)
        throws SAXException, IOException, ParserConfigurationException {
        Document cDoc = XMLUnit.buildDocument(XMLUnit.getControlParser(),
            new StringReader(c));
        Document tDoc = XMLUnit.buildDocument(XMLUnit.getTestParser(),
            new StringReader(t));
        HtmlDomUtil.removeBlankTextNode(cDoc.getChildNodes());
        HtmlDomUtil.removeBlankTextNode(tDoc.getChildNodes());
        return new Diff(cDoc, tDoc);
    }

    public void testDomLearning1() throws Exception {
        Document document = XMLUnit.buildDocument(XMLUnit.getControlParser(),
            new StringReader("<a> <b/><b/></a>"));
        {
            NodeList childNodes = document.getChildNodes();
            assertEquals(1, childNodes.getLength());
            Node node = childNodes.item(0);
            assertEquals("a", node.getNodeName());

            NodeList childNodes2 = node.getChildNodes();
            assertEquals(3, childNodes2.getLength());
            assertEquals("#text", childNodes2.item(0).getNodeName());
            assertEquals("b", childNodes2.item(1).getNodeName());
            assertEquals("b", childNodes2.item(2).getNodeName());

            assertEquals(Node.TEXT_NODE, childNodes2.item(0).getNodeType());
            assertEquals(Node.ELEMENT_NODE, childNodes2.item(1).getNodeType());

            HtmlDomUtil.removeNode(childNodes2.item(0));
            assertEquals(2, childNodes2.getLength());
            assertEquals("b", childNodes2.item(0).getNodeName());
            assertEquals("b", childNodes2.item(1).getNodeName());
        }
        {
            NodeList childNodes = document.getChildNodes();
            assertEquals(1, childNodes.getLength());
            Node node = childNodes.item(0);
            assertEquals("a", node.getNodeName());

            NodeList childNodes2 = node.getChildNodes();
            assertEquals(2, childNodes2.getLength());
            assertEquals("b", childNodes2.item(0).getNodeName());
            assertEquals("b", childNodes2.item(1).getNodeName());
        }
    }

    public void testDomLearning2() throws Exception {
        Document document = XMLUnit.buildDocument(XMLUnit.getControlParser(),
            new StringReader("<a>\n\t<b/> <b/> </a>"));
        HtmlDomUtil.removeBlankTextNode(document.getChildNodes());
        {
            NodeList childNodes = document.getChildNodes();
            assertEquals(1, childNodes.getLength());
            Node node = childNodes.item(0);
            assertEquals("a", node.getNodeName());

            NodeList childNodes2 = node.getChildNodes();
            assertEquals(2, childNodes2.getLength());
            assertEquals("b", childNodes2.item(0).getNodeName());
            assertEquals("b", childNodes2.item(1).getNodeName());
        }
    }

    public void testDomLearning3() throws Exception {
        Document document = XMLUnit.buildDocument(XMLUnit.getControlParser(),
            new StringReader("<a> <b> <c/> <c/> </b> </a>"));
        HtmlDomUtil.removeBlankTextNode(document.getChildNodes());
        {
            NodeList childNodes = document.getChildNodes();
            assertEquals(1, childNodes.getLength());
            Node node1 = childNodes.item(0);
            assertEquals("a", node1.getNodeName());

            NodeList childNodes2 = node1.getChildNodes();
            assertEquals(1, childNodes2.getLength());
            Node node2 = childNodes2.item(0);
            assertEquals("b", node2.getNodeName());

            NodeList childNodes3 = node2.getChildNodes();
            assertEquals(2, childNodes3.getLength());
            assertEquals("c", childNodes3.item(0).getNodeName());
            assertEquals("c", childNodes3.item(1).getNodeName());
        }
    }

    String readText(Class clazz, String fileName, String encoding)
        throws IOException {
        final String pathByClass = clazz.getName().replace('.', '/') + "_"
            + fileName;
        final ClassLoader classLoader = Thread.currentThread()
            .getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream(pathByClass);
        if (is == null) {
            String pathByPackage = clazz.getPackage().getName().replace('.',
                '/')
                + "/" + fileName;
            is = classLoader.getResourceAsStream(pathByPackage);
        }
        return readText(new InputStreamReader(is, encoding));
    }

    String readText(Reader reader) throws IOException {
        final BufferedReader in = new BufferedReader(reader);
        final StringBuffer out = new StringBuffer(100);
        try {
            char[] buf = new char[2048];
            int n;
            while ((n = in.read(buf)) >= 0) {
                out.append(buf, 0, n);
            }
        } finally {
            in.close();
        }
        return out.toString();
    }
}
