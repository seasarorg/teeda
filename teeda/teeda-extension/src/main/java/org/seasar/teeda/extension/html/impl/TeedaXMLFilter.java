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

import org.seasar.framework.util.StringUtil;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.XMLFilterImpl;

/**
 * @author koichik
 */
public class TeedaXMLFilter extends XMLFilterImpl implements LexicalHandler {

    protected static final char[] BEGIN_COMMENT = "<!--".toCharArray();

    protected static final char[] END_COMMENT = "-->".toCharArray();

    protected static final char[] BEGIN_CDATA = "<![CDATA[".toCharArray();

    protected static final char[] END_CDATA = "]]>".toCharArray();

    protected static final String LINE_SEP = System
            .getProperty("line.separator");

    protected int depth;

    public TeedaXMLFilter(final XMLReader parent) {
        super(parent);
    }

    public void startDTD(final String name, final String publicId,
            final String systemId) throws SAXException {
        final StringBuffer buf = new StringBuffer(128);
        buf.append("<!DOCTYPE ").append(name);
        if (!StringUtil.isEmpty(publicId)) {
            buf.append(" PUBLIC \"").append(publicId).append("\"");
        }
        if (!StringUtil.isEmpty(systemId)) {
            if (StringUtil.isEmpty(publicId)) {
                buf.append(" SYSTEM");
            }
            buf.append(" \"").append(systemId).append("\"");
        }
        buf.append(">").append(LINE_SEP);
        final String docTypeDecl = new String(buf);
        super.characters(docTypeDecl.toCharArray(), 0, docTypeDecl.length());
        ++depth;
    }

    public void endDTD() throws SAXException {
        --depth;
    }

    public void characters(final char[] ch, final int start, final int length)
            throws SAXException {
        if (depth == 0) {
            super.characters(ch, start, length);
        }
    }

    public void startEntity(final String name) throws SAXException {
        final String entityRef = new String(new StringBuffer(name.length() + 2)
                .append('&').append(name).append(';'));
        characters(entityRef.toCharArray(), 0, entityRef.length());
        ++depth;
    }

    public void endEntity(final String name) throws SAXException {
        --depth;
    }

    public void startCDATA() throws SAXException {
        characters(BEGIN_CDATA, 0, BEGIN_CDATA.length);
    }

    public void endCDATA() throws SAXException {
        characters(END_CDATA, 0, END_CDATA.length);
    }

    public void comment(final char[] ch, final int start, final int length)
            throws SAXException {
        characters(BEGIN_COMMENT, 0, BEGIN_COMMENT.length);
        characters(ch, start, length);
        characters(END_COMMENT, 0, END_COMMENT.length);
    }

    public void processingInstruction(final String target, final String data)
            throws SAXException {
        final String pi = new String(new StringBuffer(target.length() +
                data.length() + 5).append("<?").append(target).append(' ')
                .append(data) +
                "?>");
        characters(pi.toCharArray(), 0, pi.length());
    }

}
