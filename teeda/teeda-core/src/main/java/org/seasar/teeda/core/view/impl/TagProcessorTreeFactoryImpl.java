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

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;

import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.exception.SAXRuntimeException;
import org.seasar.teeda.core.view.JsfConfig;
import org.seasar.teeda.core.view.TagProcessor;
import org.seasar.teeda.core.view.TagProcessorTreeFactory;
import org.seasar.teeda.core.view.TagSelector;
import org.seasar.teeda.core.view.ViewTemplateFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * @author higa
 *  
 */
public class TagProcessorTreeFactoryImpl implements TagProcessorTreeFactory {

	private TagSelectors tagSelectors = new TagSelectors();

	private String encoding = "Windows-31J";

	private JsfConfig jsfConfig;
	
	private ViewTemplateFactory viewTemplateFactory;

	public TagProcessorTreeFactoryImpl() {
	}

	public TagProcessor createTagProcessorTree(InputStream is) {
		XMLReader reader = createReader();
		TagProcessorHandler handler = new TagProcessorHandler(tagSelectors,
				jsfConfig, viewTemplateFactory);
		reader.setContentHandler(handler);
		try {
			reader.parse(new InputSource(is));
		} catch (SAXException ex) {
			throw new SAXRuntimeException(ex);
		} catch (IOException ex) {
			throw new IORuntimeException(ex);
		}
		return handler.getRoot();
	}

	public void addTagSelector(TagSelector tagSelector) {
		tagSelectors.addTagSelector(tagSelector);
	}
	
	public TagSelector getTagSelector(String namespaceURI, String localName,
			String qName, Attributes attributes) {
		
		return tagSelectors.getTagSelector(namespaceURI, localName, qName, attributes);
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setJsfConfig(JsfConfig jsfConfig) {
		this.jsfConfig = jsfConfig;
	}
	
	public void setViewTemplateFactory(ViewTemplateFactory viewTemplateFactory) {
		this.viewTemplateFactory = viewTemplateFactory;
	}

	protected XMLReader createReader() {
		XMLReader reader = null;
		try {
			reader = new TextuallyXMLFilter(new SAXParser());
			reader.setProperty(
					"http://cyberneko.org/html/properties/default-encoding",
					encoding);
			reader.setProperty(
					"http://cyberneko.org/html/properties/names/attrs",
					"default");
			reader.setProperty(
					"http://cyberneko.org/html/properties/names/elems",
					"match");
		} catch (SAXException ex) {
			throw new SAXRuntimeException(ex);
		}
		return reader;
	}
}