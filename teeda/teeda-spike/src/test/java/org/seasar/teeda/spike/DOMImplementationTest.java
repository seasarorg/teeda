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
