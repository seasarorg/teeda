/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.seasar.framework.util.ArrayUtil;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.DocumentNode;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementNodeDecision;
import org.seasar.teeda.extension.html.HtmlNode;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class HtmlNodeHandler extends DefaultHandler {

    private Stack nodeStack = new Stack();

    private HtmlNode root;

    private Map dtdPaths = new HashMap();

    private List forceElementNodeTagName = new ArrayList();

    private Stack forceElementNodeStack = new Stack();

    private ElementNodeDecision[] decisions = new ElementNodeDecision[0];

    private static final String XHTML_DTD_RESOURCES_PATH = "org/seasar/teeda/extension/resource/xhtml1/";

    public HtmlNodeHandler() {
        initialize();
    }

    public HtmlNode getRoot() {
        return root;
    }

    public void startDocument() throws SAXException {
        super.startDocument();
        DocumentNode docNode = new DocumentNodeImpl();
        nodeStack.push(docNode);
    }

    public void startElement(String namespaceURI, String localName,
            String qName, Attributes attributes) {
        Map props = HtmlNodeUtil.convertMap(attributes);
        if (root == null) {
            ElementNode n = new ElementNodeImpl(namespaceURI, localName, qName,
                    props);
            root = n;
            DocumentNode docNode = (DocumentNode) nodeStack.peek();
            docNode.addChild(n);
            push(n);
        } else {
            ElementNode parent = peek();
            if (isElementNode(parent, namespaceURI, localName, qName,
                    attributes)) {
                ElementNodeImpl elementNode = new ElementNodeImpl(namespaceURI,
                        localName, qName, props);
                parent.addElement(elementNode);
                push(elementNode);
                if (forceElementNodeTagName.contains(qName)) {
                    forceElementNodeStack.push(elementNode);
                }
            } else {
                parent.addText(HtmlNodeUtil.getStartTagString(qName, props));
                parent.incrementChildTextSize();
            }
        }
    }

    private boolean isElementNode(ElementNode parent, String namespaceURI,
            String localName, String qName, Attributes attributes) {
        if (attributes.getValue(JsfConstants.ID_ATTR) != null) {
            return true;
        }
        if (!StringUtil.isEmpty(namespaceURI)
                && !namespaceURI.equals(ExtensionConstants.XHTML_URI)) {
            return true;
        }
        if (!forceElementNodeStack.isEmpty()) {
            return true;
        }
        for (int i = 0; i < decisions.length; i++) {
            ElementNodeDecision dec = decisions[i];
            if (dec.isElementNode(parent, qName, attributes)) {
                return true;
            }
        }
        return false;
    }

    public InputSource resolveEntity(String publicId, String systemId)
            throws SAXException {
        String dtdPath = null;
        if (publicId != null) {
            dtdPath = (String) dtdPaths.get(publicId);
        }
        if (dtdPath == null) {
            return null;
        }
        return new InputSource(ResourceUtil.getResourceAsStream(dtdPath));
    }

    protected ElementNode peek() {
        return (ElementNode) nodeStack.peek();
    }

    protected ElementNode pop() {
        return (ElementNode) nodeStack.pop();
    }

    protected void push(ElementNode node) {
        nodeStack.push(node);
    }

    public void characters(char[] buffer, int start, int length) {
        if (root == null) {
            DocumentNode docNode = (DocumentNode) nodeStack.peek();
            docNode.addText(new String(buffer, start, length));
            return;
        }
        String text = new String(buffer, start, length);
        ElementNode tagNode = peek();
        tagNode.addText(text);
    }

    public void endElement(String namespaceURI, String localName, String qName) {
        ElementNode current = peek();
        if (current.getChildTextSize() == 0) {
            current.endElement();
            pop();
            if (!forceElementNodeStack.isEmpty()
                    && current == forceElementNodeStack.peek()) {
                forceElementNodeStack.pop();
            }
        } else {
            current.addText(HtmlNodeUtil.getEndTagString(qName));
            current.decrementChildTextSize();
        }
    }

    public void endDocument() throws SAXException {
        super.endDocument();
        DocumentNode docNode = (DocumentNode) nodeStack.pop();
        root = docNode;
    }

    public void error(SAXParseException e) throws SAXException {
        throw e;
    }

    public void warning(SAXParseException e) throws SAXException {
        System.err.println(e);
    }

    public void registerDtdPath(String publicId, String dtdPath) {
        dtdPaths.put(publicId, dtdPath);
    }

    protected void initialize() {
        initializeDtdPathForXhtml1();
    }

    protected void initializeDtdPathForXhtml1() {
        dtdPaths.put("-//W3C//DTD XHTML 1.0 Frameset//EN",
                XHTML_DTD_RESOURCES_PATH + "xhtml1-frameset.dtd");
        dtdPaths.put("-//W3C//DTD XHTML 1.0 Strict//EN",
                XHTML_DTD_RESOURCES_PATH + "xhtml1-strict.dtd");
        dtdPaths.put("-//W3C//DTD XHTML 1.0 Transitional//EN",
                XHTML_DTD_RESOURCES_PATH + "xhtml1-transitional.dtd");
        dtdPaths.put("-//W3C//ENTITIES Latin 1 for XHTML//EN",
                XHTML_DTD_RESOURCES_PATH + "xhtml-lat1.ent");
        dtdPaths.put("-//W3C//ENTITIES Symbols for XHTML//EN",
                XHTML_DTD_RESOURCES_PATH + "xhtml-symbol.ent");
        dtdPaths.put("-//W3C//ENTITIES Special for XHTML//EN",
                XHTML_DTD_RESOURCES_PATH + "xhtml-special.ent");
    }

    public void addForceElementNodeTagName(String tagName) {
        forceElementNodeTagName.add(tagName);
    }

    public void setElementNodeDecisions(ElementNodeDecision[] decisions) {
        this.decisions = decisions;
    }

    public void addElementNodeDecision(ElementNodeDecision decision) {
        this.decisions = (ElementNodeDecision[]) ArrayUtil.add(decisions,
                decision);
    }

}
