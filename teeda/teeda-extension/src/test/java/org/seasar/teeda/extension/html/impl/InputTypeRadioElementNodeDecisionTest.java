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
package org.seasar.teeda.extension.html.impl;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.seasar.teeda.extension.html.ElementNode;
import org.xml.sax.Attributes;

/**
 * @author shot
 */
public class InputTypeRadioElementNodeDecisionTest extends TestCase {

    public void testIsElementNode1() throws Exception {
        InputTypeRadioElementNodeDecision dec = new InputTypeRadioElementNodeDecision();
        ElementNode node = new ElementNodeImpl("input", new HashMap());
        MockAttributes attributes = new MockAttributes();
        attributes.addValue("type", "radio");
        attributes.addValue("name", "aaa");
        assertTrue(dec.isElementNode(node, "input", attributes));
    }

    public void testIsElementNode2() throws Exception {
        InputTypeRadioElementNodeDecision dec = new InputTypeRadioElementNodeDecision();
        ElementNode node = new ElementNodeImpl("input", new HashMap());
        MockAttributes attributes = new MockAttributes();
        attributes.addValue("name", "aaa");
        assertFalse(dec.isElementNode(node, "input", attributes));
    }

    public void testIsElementNode3() throws Exception {
        InputTypeRadioElementNodeDecision dec = new InputTypeRadioElementNodeDecision();
        ElementNode node = new ElementNodeImpl("input", new HashMap());
        MockAttributes attributes = new MockAttributes();
        attributes.addValue("type", "radio");
        assertFalse(dec.isElementNode(node, "input", attributes));
    }

    public void testIsElementNode4() throws Exception {
        InputTypeRadioElementNodeDecision dec = new InputTypeRadioElementNodeDecision();
        ElementNode node = new ElementNodeImpl("aaa", new HashMap());
        MockAttributes attributes = new MockAttributes();
        attributes.addValue("name", "aaa");
        attributes.addValue("type", "radio");
        assertFalse(dec.isElementNode(node, "aaa", attributes));
    }

    public void testIsElementNode5() throws Exception {
        InputTypeRadioElementNodeDecision dec = new InputTypeRadioElementNodeDecision();
        ElementNode node = new ElementNodeImpl("input", new HashMap());
        MockAttributes attributes = new MockAttributes();
        attributes.addValue("name", "aaa");
        attributes.addValue("type", "text");
        assertFalse(dec.isElementNode(node, "input", attributes));
    }

    public static class MockAttributes implements Attributes {

        private Map values = new HashMap();

        public int getIndex(String qName) {
            return 0;
        }

        public int getIndex(String uri, String localName) {
            return 0;
        }

        public int getLength() {
            return 0;
        }

        public String getLocalName(int index) {
            return null;
        }

        public String getQName(int index) {
            return null;
        }

        public String getType(int index) {
            return null;
        }

        public String getType(String qName) {
            return null;
        }

        public String getType(String uri, String localName) {
            return null;
        }

        public String getURI(int index) {
            return null;
        }

        public String getValue(int index) {
            return null;
        }

        public String getValue(String qName) {
            return (String) values.get(qName);
        }

        public void addValue(String qName, String value) {
            values.put(qName, value);
        }

        public String getValue(String uri, String localName) {
            return null;
        }

    }
}
