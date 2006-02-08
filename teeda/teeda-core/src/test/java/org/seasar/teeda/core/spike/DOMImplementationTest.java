package org.seasar.teeda.core.spike;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import junitx.framework.StringAssert;

import org.apache.commons.lang.SystemUtils;

public class DOMImplementationTest extends TestCase {

    public void testToKnowDOMImplementation() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        final String factoryName = factory.getClass().getName();
        final String builderName = builder.getClass().getName();
        try {
            if (SystemUtils.isJavaVersionAtLeast(1.5F)) {
                StringAssert.assertStartsWith("com.sun.org.apache.xerces",
                        factoryName);
                StringAssert.assertStartsWith("com.sun.org.apache.xerces",
                        builderName);
            } else {
                StringAssert.assertStartsWith("org.apache.xerces", factoryName);
                StringAssert.assertStartsWith("org.apache.xerces", builderName);
            }
        } catch (AssertionFailedError e) {
            Properties properties = System.getProperties();
            for (Iterator it = new TreeMap(properties).entrySet().iterator(); it
                    .hasNext();) {
                Map.Entry entry = (Entry) it.next();
                System.out.println(entry.getKey() + "=[" + entry.getValue()
                        + "]");
            }
            throw e;
        }
    }
}
