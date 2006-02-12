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

import java.util.Stack;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.view.JsfConfig;
import org.seasar.teeda.core.view.TagProcessor;
import org.seasar.teeda.core.view.TagSelector;
import org.seasar.teeda.core.view.ViewTemplateFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class TagProcessorHandler extends DefaultHandler {
	
	private TagSelectors tagSelectors;
	
	private JsfConfig jsfConfig;

	private Stack processorStack = new Stack();

	private TagProcessor root;

	public TagProcessorHandler(TagSelectors tagSelectors, JsfConfig jsfConfig,
			ViewTemplateFactory viewTemplateFactory) {
	
		this.tagSelectors = tagSelectors;
		this.jsfConfig = jsfConfig;
		root = new ViewProcessor(jsfConfig, viewTemplateFactory);
	}

	public TagProcessor getRoot() {
		return root;
	}

	public void startElement(String namespaceURI, String localName,
			String qName, Attributes attributes) {

		TagSelector selector = tagSelectors.getTagSelector(namespaceURI,
				localName, qName, attributes);
		TagProcessor processor = null;
		if (selector != null) {
			processor = selector.createProcessor();
		}
		if (!processorStack.isEmpty()) {
			TagProcessor parentProcessor = peekProcessor();
			if (parentProcessor != null) {
                if (processor instanceof ElementProcessor &&
                        JsfConstants.BR_ELEM.equalsIgnoreCase(qName) &&
						attributes.getLength() == 0) {
                    addText(parentProcessor, JsfConstants.BR_TAG);
                } else {
                    parentProcessor.addChild(processor);
                }
			}
		} else {
			root.addChild(processor);
		}
		processor.setup(namespaceURI, localName, qName, attributes, jsfConfig);
		processorStack.push(processor);
	}

	protected TagProcessor peekProcessor() {
		return (TagProcessor) processorStack.peek();
	}

	protected TagProcessor popProcessor() {
		return (TagProcessor) processorStack.pop();
	}

	public void characters(char[] buffer, int start, int length) {
		TagProcessor processor = null;
		if (!processorStack.isEmpty()) {
			processor = peekProcessor();
			if (processor == null) {
				return;
			}
		} else {
			processor = root;	
		}
		String text = new String(buffer, start, length);
        addText(processor, text);
	}
    
    protected void addText(TagProcessor processor, String text) {
        TagProcessor child = getPreviousChildProcessor(processor);
        if (child instanceof ElementProcessor) {
            ((ElementProcessor) child).addAfterContents(text);
        } else if (child instanceof TextProcessor) {
            ((TextProcessor) child).addValue(text);
        } else {
            processor.addChild(new TextProcessor(text));
        }
    }
    
    protected TextProcessor getPreviousChildTextProcessor(TagProcessor processor) {
        TagProcessor child = getPreviousChildProcessor(processor);
        if (child instanceof TextProcessor) {
            return (TextProcessor) child;
        }
        return null;
    }
    
    protected TagProcessor getPreviousChildProcessor(TagProcessor processor) {
        if (processor.getChildCount() > 0) {
            return processor.getChild(processor.getChildCount() - 1);
        }
        return null;
    }

	public void endElement(String namespaceURI, String localName, String qName) {
		popProcessor();
	}

	public void error(SAXParseException e) throws SAXException {
		throw e;
	}

	public void warning(SAXParseException e) throws SAXException {
		System.err.println(e);
	}
}
