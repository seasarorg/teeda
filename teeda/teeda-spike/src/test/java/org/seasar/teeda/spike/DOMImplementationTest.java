package org.seasar.teeda.spike;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

public class DOMImplementationTest extends TestCase {

    public void testToKnowDOMImplementation() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        System.out.println(factory);
        assertNotNull(factory);

        DocumentBuilder builder = factory.newDocumentBuilder();
        System.out.println(builder);
        assertNotNull(builder);

        Properties properties = System.getProperties();
        for (Iterator it = new TreeMap(properties).entrySet().iterator(); it
                .hasNext();) {
            Map.Entry entry = (Entry) it.next();
            System.out.println(entry.getKey() + "=[" + entry.getValue() + "]");
        }
    }

}
