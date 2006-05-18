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

import java.util.Map;
import java.util.Stack;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.html.HtmlNode;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class HtmlNodeHandler extends DefaultHandler {

	private Stack nodeStack = new Stack();

	private HtmlNode root;

	public HtmlNodeHandler() {
	}

	public HtmlNode getRoot() {
		return root;
	}

	public void startElement(String namespaceURI, String localName,
			String qName, Attributes attributes) {

        Map props = HtmlNodeUtil.convertMap(attributes);
        if (root == null) {
            ElementNodeImpl n = new ElementNodeImpl(qName, props);
            root = n;
            push(n);
        } else {
            ElementNodeImpl parent = peek();
            if (attributes.getValue(JsfConstants.ID_ATTR) != null) {
                ElementNodeImpl elementNode = new ElementNodeImpl(qName, props);
                parent.addElement(elementNode);
                push(elementNode);
            } else {
                parent.addText(HtmlNodeUtil.getStartTagString(qName, props));
                parent.incrementChildTextSize();
            }
        }
        
	}

	protected ElementNodeImpl peek() {
		return (ElementNodeImpl) nodeStack.peek();
	}

	protected ElementNodeImpl pop() {
		return (ElementNodeImpl) nodeStack.pop();
	}
    
    protected void push(ElementNodeImpl node) {
        nodeStack.push(node);
    }

	public void characters(char[] buffer, int start, int length) {
        if (root == null) {
            return;
        }
		String text = new String(buffer, start, length);
        ElementNodeImpl tagNode = peek();
        tagNode.addText(text);
	}

	public void endElement(String namespaceURI, String localName, String qName) {
        ElementNodeImpl current = peek();
        if (current.getChildTextSize() == 0) {
            current.endElement();
            pop();
        } else {
            current.addText(HtmlNodeUtil.getEndTagString(qName));
            current.decrementChildTextSize();
        }
	}

	public void error(SAXParseException e) throws SAXException {
		throw e;
	}

	public void warning(SAXParseException e) throws SAXException {
		System.err.println(e);
	}
}