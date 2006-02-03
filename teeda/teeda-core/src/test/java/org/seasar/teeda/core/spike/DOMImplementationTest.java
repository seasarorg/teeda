package org.seasar.teeda.core.spike;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

public class DOMImplementationTest extends TestCase {

    public DOMImplementationTest(String name) {
        super(name);
    }

    public void testToKnowDOMImplementation() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        assertTrue(factory.getClass().getName().startsWith("org.apache.xerces"));
        assertTrue(builder.getClass().getName().startsWith("org.apache.xerces"));
    }
}
