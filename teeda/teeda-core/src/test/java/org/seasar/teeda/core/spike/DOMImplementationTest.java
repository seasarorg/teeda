package org.seasar.teeda.core.spike;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;
import junitx.framework.StringAssert;

public class DOMImplementationTest extends TestCase {

    public void testToKnowDOMImplementation() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        final String factoryName = factory.getClass().getName();
        final String builderName = builder.getClass().getName();
        if (factoryName.startsWith("com.sun")) {
            // jdk 1.5
            StringAssert.assertStartsWith("com.sun.org.apache.xerces",
                    factoryName);
            StringAssert.assertStartsWith("com.sun.org.apache.xerces",
                    builderName);
        } else {
            // before 1.5
            StringAssert.assertStartsWith("org.apache.xerces", factoryName);
            StringAssert.assertStartsWith("org.apache.xerces", builderName);
        }
    }

}
