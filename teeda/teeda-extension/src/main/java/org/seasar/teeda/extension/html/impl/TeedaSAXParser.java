/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import org.apache.xerces.parsers.SAXParser;
import org.apache.xerces.util.XMLAttributesImpl;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLParserConfiguration;

public class TeedaSAXParser extends SAXParser {

    protected static final String LINE_SEP = System
            .getProperty("line.separator");

    public TeedaSAXParser(final XMLParserConfiguration config) {
        super(config);
    }

    public void xmlDecl(final String version, final String encoding,
            final String standalone, final Augmentations augs)
            throws XNIException {
        final StringBuffer buf = new StringBuffer(128);
        buf.append("<?xml");
        if (version != null) {
            buf.append(" version=\"").append(version).append("\"");
        }
        if (encoding != null) {
            buf.append(" encoding=\"").append(encoding).append("\"");
        }
        if (standalone != null) {
            buf.append(" standalone=\"").append(standalone).append("\"");
        }
        buf.append("?>").append(LINE_SEP);
        final String xmlDecl = buf.toString();
        super.characters(new XMLString(xmlDecl.toCharArray(), 0, xmlDecl
                .length()), augs);
    }

    public void startElement(final QName element,
            final XMLAttributes attributes, final Augmentations augs)
            throws XNIException {
        super.startElement(element, new TeedaXMLAttributesImpl(
                (XMLAttributesImpl) attributes), augs);
    }

}