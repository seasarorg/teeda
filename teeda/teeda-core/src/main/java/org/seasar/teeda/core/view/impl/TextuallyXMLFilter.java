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
package org.seasar.teeda.core.view.impl;

import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.XMLFilterImpl;

/**
 * @author koichik
 */
public class TextuallyXMLFilter extends XMLFilterImpl implements LexicalHandler {

	protected int inEntity;

	public TextuallyXMLFilter(final XMLReader parent)
			throws SAXNotRecognizedException, SAXNotSupportedException {
		super(parent);

		setProperty("http://xml.org/sax/properties/lexical-handler", this);
		setFeature("http://apache.org/xml/features/scanner/notify-char-refs",
				true);
		setFeature(
				"http://cyberneko.org/html/features/scanner/notify-builtin-refs",
				true);
	}

	public void characters(final char[] ch, final int start, final int length)
			throws SAXException {
		if (inEntity == 0) {
			super.characters(ch, start, length);
		}
	}

	public void startEntity(final String name) throws SAXException {
		final String entityRef = "&" + name + ";";
		super.characters(entityRef.toCharArray(), 0, entityRef.length());
		++inEntity;
	}

	public void endEntity(String name) throws SAXException {
		--inEntity;
	}

	public void startDTD(final String name, final String publicId,
			final String systemId) throws SAXException {
		final StringBuffer buf = new StringBuffer(128);
		buf.append("<!DOCTYPE ").append(name);
		if (publicId != null) {
			buf.append(" PUBLIC \"").append(publicId).append("\"");
		}
		if (systemId != null) {
			if (publicId == null) {
				buf.append(" SYSTEM");
			}
			buf.append(" \"").append(systemId).append("\"");
		}
		buf.append(">\n");
		final String docTypeDecl = new String(buf);
		super.characters(docTypeDecl.toCharArray(), 0, docTypeDecl.length());
	}

	public void endDTD() throws SAXException {
	}

	public void startCDATA() throws SAXException {
	}

	public void endCDATA() throws SAXException {
	}

	public void comment(final char[] ch, final int start, final int length)
			throws SAXException {
		final StringBuffer buf = new StringBuffer(length + 7);
		buf.append("<!--").append(ch, start, length).append("-->");
		final String comment = new String(buf);
		super.characters(comment.toCharArray(), 0, comment.length());
	}
}