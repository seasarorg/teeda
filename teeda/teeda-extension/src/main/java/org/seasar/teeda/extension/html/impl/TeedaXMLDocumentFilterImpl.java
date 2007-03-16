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
package org.seasar.teeda.extension.html.impl;

import org.apache.xerces.util.XMLAttributesImpl;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLDocumentFilter;
import org.apache.xerces.xni.parser.XMLDocumentSource;
import org.seasar.teeda.core.JsfConstants;

/**
 * @author shot
 *
 */
public class TeedaXMLDocumentFilterImpl implements XMLDocumentFilter {

    protected int inEntity;

    private XMLDocumentHandler orgHandler;

    private String xmlEncoding = JsfConstants.DEFAULT_ENCODING;

    private static final String SEP = System.getProperty("line.separator");

    public TeedaXMLDocumentFilterImpl() {
    }

    public void startElement(QName element, XMLAttributes attributes,
            Augmentations augs) throws XNIException {
        orgHandler.startElement(element, new TeedaXMLAttributesImpl(
                (XMLAttributesImpl) attributes), augs);
    }

    public void characters(XMLString text, Augmentations augs)
            throws XNIException {
        if (inEntity == 0) {
            orgHandler.characters(text, augs);
        }
    }

    public void comment(XMLString text, Augmentations augs) throws XNIException {
        final StringBuffer buf = new StringBuffer(text.length + 7);
        buf.append("<!--").append(text.ch, text.offset, text.length).append(
                "-->");
        final String comment = new String(buf);
        orgHandler.characters(new XMLString(comment.toCharArray(), 0, comment
                .length()), augs);

    }

    public void doctypeDecl(String rootElement, String publicId,
            String systemId, Augmentations augs) throws XNIException {
        final StringBuffer buf = new StringBuffer(128);
        buf.append("<!DOCTYPE ").append(rootElement);
        if (publicId != null) {
            buf.append(" PUBLIC \"").append(publicId).append("\"");
        }
        if (systemId != null) {
            if (publicId == null) {
                buf.append(" SYSTEM");
            }
            buf.append(" \"").append(systemId).append("\"");
        }
        buf.append(">");
        buf.append(SEP);
        final String docTypeDecl = new String(buf);
        orgHandler.characters(new XMLString(docTypeDecl.toCharArray(), 0,
                docTypeDecl.length()), augs);
    }

    public void emptyElement(QName element, XMLAttributes attributes,
            Augmentations augs) throws XNIException {
        orgHandler.emptyElement(element, new TeedaXMLAttributesImpl(
                (XMLAttributesImpl) attributes), augs);
    }

    public void endCDATA(Augmentations augs) throws XNIException {
        orgHandler.endCDATA(augs);
    }

    public void endDocument(Augmentations augs) throws XNIException {
        orgHandler.endDocument(augs);
    }

    public void endElement(QName element, Augmentations augs)
            throws XNIException {
        orgHandler.endElement(element, augs);
    }

    public void endGeneralEntity(String name, Augmentations augs)
            throws XNIException {
        --inEntity;
    }

    public void ignorableWhitespace(XMLString text, Augmentations augs)
            throws XNIException {
        orgHandler.ignorableWhitespace(text, augs);
    }

    public void processingInstruction(String target, XMLString data,
            Augmentations augs) throws XNIException {
        orgHandler.processingInstruction(target, data, augs);
    }

    public void startCDATA(Augmentations augs) throws XNIException {
        orgHandler.startCDATA(augs);
    }

    public void startDocument(XMLLocator locator, String encoding,
            NamespaceContext namespaceContext, Augmentations augs)
            throws XNIException {
        orgHandler.startDocument(locator, xmlEncoding, namespaceContext, augs);
    }

    public void startGeneralEntity(String name,
            XMLResourceIdentifier identifier, String encoding,
            Augmentations augs) throws XNIException {
        final String entityRef = "&" + name + ";";
        orgHandler.characters(new XMLString(entityRef.toCharArray(), 0,
                entityRef.length()), augs);
        ++inEntity;
    }

    public void textDecl(String version, String encoding, Augmentations augs)
            throws XNIException {
        orgHandler.textDecl(version, xmlEncoding, augs);
    }

    public void xmlDecl(String version, String encoding, String standalone,
            Augmentations augs) throws XNIException {
        StringBuffer buf = new StringBuffer(128);
        if (version != null) {
            buf.append("<?xml version=\"");
            buf.append(version);
            buf.append("\"");
        }
        if (encoding != null) {
            buf.append(" encoding=\"");
            buf.append(encoding);
            buf.append("\"");
            xmlEncoding = encoding;
        }
        if (standalone != null) {
            buf.append(" standalone=\"");
            buf.append(standalone);
            buf.append("\"");
        }
        buf.append("?>");
        buf.append(SEP);
        String xml = buf.toString();
        orgHandler.characters(
                new XMLString(xml.toCharArray(), 0, xml.length()), augs);
    }

    public XMLDocumentHandler getDocumentHandler() {
        return orgHandler;
    }

    public void setDocumentHandler(XMLDocumentHandler handler) {
        this.orgHandler = handler;
    }

    public XMLDocumentSource getDocumentSource() {
        return orgHandler.getDocumentSource();
    }

    public void setDocumentSource(XMLDocumentSource source) {
        orgHandler.setDocumentSource(source);
    }

}
