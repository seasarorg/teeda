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
package org.seasar.teeda.core.spike;

import java.io.File;
import java.io.FileReader;
import java.util.List;

import javax.xml.transform.stream.StreamSource;

import junit.framework.AssertionFailedError;

import org.custommonkey.xmlunit.AbstractNodeTester;
import org.custommonkey.xmlunit.CountingNodeTester;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceListener;
import org.custommonkey.xmlunit.ElementNameAndTextQualifier;
import org.custommonkey.xmlunit.HTMLDocumentBuilder;
import org.custommonkey.xmlunit.IgnoreTextAndAttributeValuesDifferenceListener;
import org.custommonkey.xmlunit.NodeTest;
import org.custommonkey.xmlunit.NodeTestException;
import org.custommonkey.xmlunit.TolerantSaxDocumentBuilder;
import org.custommonkey.xmlunit.Transform;
import org.custommonkey.xmlunit.Validator;
import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * Example XMLUnit XMLTestCase code Demonstrates use of:<br />
 * <ul>
 * <li>XMLTestCase: assertXMLEqual(), assertXMLNotEqual(), assertXpathExists(),
 * assertXpathNotExists(), assertXpathEvaluatesTo(), assertXpathsEqual(),
 * assertXpathsNotEqual(), assertNodeTestPasses()</li>
 * <li>Diff: similar(), identical()</li>
 * <li>DetailedDiff: getAllDifferences()</li>
 * <li>DifferenceListener: use with Diff class,
 * IgnoreTextAndAttributeValuesDifferenceListener implementation</li>
 * <li>ElementQualifier: use with Diff class, ElementNameAndTextQualifier
 * implementation</li>
 * <li>Transform: constructors, getResultDocument(), use with Diff class</li>
 * <li>Validator: constructor, isValid()</li>
 * <li>TolerantSaxDocumentBuilder and HTMLDocumentBuilder usage</li>
 * <li>NodeTest: CountingNodeTester and custom implementations</li>
 * <li>XMLUnit static methods: buildDocument(), buildControlDocument(),
 * buildTestDocument(), setIgnoreWhitespace()</li>
 * </ul>
 * <br />
 * Examples and more at <a
 * href="http://xmlunit.sourceforge.net"/>xmlunit.sourceforge.net</a>
 */
public class XmlUnitLearningTest extends XMLTestCase {
    public void testForEquality() throws Exception {
        String myControlXML = "<msg><uuid>0x00435A8C</uuid></msg>";
        String myTestXML = "<msg><localId>2376</localId></msg>";
        try {
            assertXMLEqual("comparing test xml to control xml", myControlXML,
                    myTestXML);
            fail();
        } catch (AssertionFailedError expected) {
        }
        assertXMLNotEqual("test xml not similar to control xml", myControlXML,
                myTestXML);
    }

    public void testIdentical() throws Exception {
        String myControlXML = "<struct><int>3</int><boolean>false</boolean></struct>";
        String myTestXML = "<struct><boolean>false</boolean><int>3</int></struct>";
        Diff myDiff = new Diff(myControlXML, myTestXML);
        assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
        try {
            assertTrue("but are they identical? " + myDiff, myDiff.identical());
            fail();
        } catch (AssertionFailedError expected) {
        }
    }

    public void testAllDifferences() throws Exception {
        String myControlXML = "<news><item id=\"1\">War</item>"
                + "<item id=\"2\">Plague</item><item id=\"3\">Famine</item></news>";
        String myTestXML = "<news><item id=\"1\">Peace</item>"
                + "<item id=\"2\">Health</item><item id=\"3\">Plenty</item></news>";
        DetailedDiff myDiff = new DetailedDiff(compareXML(myControlXML,
                myTestXML));
        List allDifferences = myDiff.getAllDifferences();
        System.out.println(myDiff.toString());
        assertEquals(myDiff.toString(), 3, allDifferences.size());
        assertEquals(true, allDifferences.get(0) instanceof Difference);
    }

    public void testCompareToSkeletonXML() throws Exception {
        String myControlXML = "<location><street-address>22 any street</street-address><postcode>XY00 99Z</postcode></location>";
        String myTestXML = "<location><street-address>20 east cheap</street-address><postcode>EC3M 1EB</postcode></location>";
        DifferenceListener myDifferenceListener = new IgnoreTextAndAttributeValuesDifferenceListener();
        Diff myDiff = new Diff(myControlXML, myTestXML);
        myDiff.overrideDifferenceListener(myDifferenceListener);
        assertTrue("test XML matches control skeleton XML " + myDiff, myDiff
                .similar());
    }

    public void testRepeatedChildElements() throws Exception {
        String myControlXML = "<suite><test status=\"pass\">FirstTestCase</test><test status=\"pass\">SecondTestCase</test></suite>";
        String myTestXML = "<suite><test status=\"pass\">SecondTestCase</test><test status=\"pass\">FirstTestCase</test></suite>";

        assertXMLNotEqual(
                "Repeated child elements in different sequence order are not equal by default",
                myControlXML, myTestXML);

        Diff myDiff = new Diff(myControlXML, myTestXML);
        myDiff.overrideElementQualifier(new ElementNameAndTextQualifier());
        assertXMLEqual(
                "But they are equal when an ElementQualifier controls which test element is compared with each control element",
                myDiff, true);
    }

    public void pending_testXSLTransformation() throws Exception {
        String myInputXML = "...";
        File myStylesheetFile = new File("...");
        Transform myTransform = new Transform(myInputXML, myStylesheetFile);
        String myExpectedOutputXML = "...";
        Diff myDiff = new Diff(myExpectedOutputXML, myTransform);
        assertTrue("XSL transformation worked as expected " + myDiff, myDiff
                .similar());
    }

    public void pending_testAnotherXSLTransformation() throws Exception {
        File myInputXMLFile = new File("...");
        File myStylesheetFile = new File("...");
        Transform myTransform = new Transform(new StreamSource(myInputXMLFile),
                new StreamSource(myStylesheetFile));
        Document myExpectedOutputXML = XMLUnit.buildDocument(XMLUnit
                .getControlParser(), new FileReader("..."));
        Diff myDiff = new Diff(myExpectedOutputXML, myTransform
                .getResultDocument());
        assertTrue("XSL transformation worked as expected " + myDiff, myDiff
                .similar());
    }

    public void pending_testValidation() throws Exception {
        XMLUnit.getTestDocumentBuilderFactory().setValidating(true);
        // As the document is parsed it is validated against its referenced DTD
        Document myTestDocument = XMLUnit.buildTestDocument("...");
        String mySystemId = "...";
        String myDTDUrl = new File("...").toURL().toExternalForm();
        Validator myValidator = new Validator(myTestDocument, mySystemId,
                myDTDUrl);
        assertTrue("test document validates against unreferenced DTD",
                myValidator.isValid());
    }

    public void testXPaths() throws Exception {
        String mySolarSystemXML = "<solar-system><planet name='Earth' position='3' supportsLife='yes'/>"
                + "<planet name='Venus' position='4'/></solar-system>";
        assertXpathExists("//planet[@name='Earth']", mySolarSystemXML);
        assertXpathNotExists("//star[@name='alpha centauri']", mySolarSystemXML);
        assertXpathsEqual("//planet[@name='Earth']", "//planet[@position='3']",
                mySolarSystemXML);
        assertXpathsNotEqual("//planet[@name='Venus']",
                "//planet[@supportsLife='yes']", mySolarSystemXML);
    }

    public void testXPathValues() throws Exception {
        String myJavaFlavours = "<java-flavours><jvm current='some platforms'>1.1.x</jvm>"
                + "<jvm current='no'>1.2.x</jvm><jvm current='yes'>1.3.x</jvm>"
                + "<jvm current='yes' latest='yes'>1.4.x</jvm></java-flavours>";
        assertXpathEvaluatesTo("1.4.x", "//jvm[@latest='yes']", myJavaFlavours);
        assertXpathEvaluatesTo("2", "count(//jvm[@current='yes'])",
                myJavaFlavours);
        assertXpathValuesEqual("//jvm[4]/@latest", "//jvm[4]/@current",
                myJavaFlavours);
        assertXpathValuesNotEqual("//jvm[2]/@current", "//jvm[3]/@current",
                myJavaFlavours);
    }

    public void testXpathsInHTML() throws Exception {
        String someBadlyFormedHTML = "<html><title>Ugh</title><body><h1>Heading<ul><li id='1'>Item One<li id='2'>Item Two";
        TolerantSaxDocumentBuilder tolerantSaxDocumentBuilder = new TolerantSaxDocumentBuilder(
                XMLUnit.getTestParser());
        HTMLDocumentBuilder htmlDocumentBuilder = new HTMLDocumentBuilder(
                tolerantSaxDocumentBuilder);
        Document wellFormedDocument = htmlDocumentBuilder
                .parse(someBadlyFormedHTML);
        assertXpathEvaluatesTo("Item One", "/html/body//li[@id='1']",
                wellFormedDocument);
    }

    public void testCountingNodeTester() throws Exception {
        String testXML = "<fibonacci><val>1</val><val>2</val><val>3</val>"
                + "<val>5</val><val>9</val></fibonacci>";
        CountingNodeTester countingNodeTester = new CountingNodeTester(5);
        assertNodeTestPasses(testXML, countingNodeTester, Node.TEXT_NODE);
    }

    public void testCustomNodeTester() throws Exception {
        String testXML = "<fibonacci><val>1</val><val>2</val><val>3</val>"
                + "<val>5</val><val>8</val></fibonacci>";
        NodeTest nodeTest = new NodeTest(testXML);
        assertNodeTestPasses(nodeTest, new FibonacciNodeTester(), new short[] {
                Node.TEXT_NODE, Node.ELEMENT_NODE }, true);
    }

    private class FibonacciNodeTester extends AbstractNodeTester {
        private int nextVal = 1, lastVal = 1;

        public void testText(Text text) throws NodeTestException {
            int val = Integer.parseInt(text.getData());
            if (nextVal != val) {
                throw new NodeTestException("Incorrect sequence value", text);
            }
            nextVal = val + lastVal;
            lastVal = val;
        }

        public void testElement(Element element) throws NodeTestException {
            String name = element.getLocalName();
            if ("fibonacci".equals(name) || "val".equals(name)) {
                return;
            }
            throw new NodeTestException("Unexpected element", element);
        }

        public void noMoreNodes(NodeTest nodeTest) throws NodeTestException {
        }
    }
}
