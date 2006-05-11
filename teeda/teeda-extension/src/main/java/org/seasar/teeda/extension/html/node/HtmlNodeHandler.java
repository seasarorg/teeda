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
package org.seasar.teeda.extension.html.node;

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
            TagNode r = new TagNode(qName, props); 
            root = r;
            push(r);
        } else {
            TagNode parent = peekTagNode();
            if (attributes.getValue(JsfConstants.ID_ATTR) != null) {
                TagNode tagNode = new TagNode(qName, props);
                parent.addTagNode(tagNode);
                push(tagNode);
            } else {
                parent.addText(HtmlNodeUtil.getStartTagString(qName, props));
                push(new TextProcessingNode());
            }
        }
        
	}

	protected HtmlNode peek() {
		return (HtmlNode) nodeStack.peek();
	}
    
    protected TagNode peekTagNode() {
        for (int i = nodeStack.size() - 1; i >= 0; --i) {
            HtmlNode n = (HtmlNode) nodeStack.get(i);
            if (n instanceof TagNode) {
                return (TagNode) n;
            }
        }
        throw new IllegalStateException();
    }

	protected HtmlNode pop() {
		return (HtmlNode) nodeStack.pop();
	}
    
    protected void push(HtmlNode htmlNode) {
        nodeStack.push(htmlNode);
    }

	public void characters(char[] buffer, int start, int length) {
        if (root == null) {
            return;
        }
		String text = new String(buffer, start, length);
        TagNode tagNode = peekTagNode();
        tagNode.addText(text);
	}

	public void endElement(String namespaceURI, String localName, String qName) {
        HtmlNode current = pop();
        if (current instanceof TagNode) {
            ((TagNode) current).endElement();
        } else {
            TagNode parent = peekTagNode();
            parent.addText(HtmlNodeUtil.getEndTagString(qName));
        }
	}

	public void error(SAXParseException e) throws SAXException {
		throw e;
	}

	public void warning(SAXParseException e) throws SAXException {
		System.err.println(e);
	}
}