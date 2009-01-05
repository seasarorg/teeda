/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.DocumentNode;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.ElementProcessorFactory;
import org.seasar.teeda.extension.html.HtmlDesc;
import org.seasar.teeda.extension.html.HtmlNode;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.TagProcessor;
import org.seasar.teeda.extension.html.TagProcessorAssembler;
import org.seasar.teeda.extension.html.TextNode;
import org.seasar.teeda.extension.html.processor.ViewProcessor;

/**
 * @author higa
 *
 */
public class TagProcessorAssemblerImpl implements TagProcessorAssembler {

    private ElementProcessorFactory[] factories = new ElementProcessorFactory[0];

    public ElementProcessorFactory[] getFactories() {
        return factories;
    }

    public void setFactories(ElementProcessorFactory[] factories) {
        this.factories = factories;
    }

    public TagProcessor assemble(HtmlDesc htmlDesc, PageDesc pageDesc,
            ActionDesc actionDesc) {
        HtmlNode rootNode = htmlDesc.getHtmlNode();
        ViewProcessor rootProcessor = new ViewProcessor();
        assembleTagProcessor(rootProcessor, rootNode, pageDesc, actionDesc);
        rootProcessor.endElement();
        return rootProcessor;
    }

    protected void assembleTagProcessor(ElementProcessor parentProcessor,
            HtmlNode node, PageDesc pageDesc, ActionDesc actionDesc) {
        if (node instanceof TextNode) {
            assembleTagProcessor(parentProcessor, (TextNode) node);
        } else if (node instanceof DocumentNode) {
            assembleTagProcessor(parentProcessor, (DocumentNode) node,
                    pageDesc, actionDesc);
        } else {
            assembleTagProcessor(parentProcessor, (ElementNode) node, pageDesc,
                    actionDesc);
        }
    }

    protected void assembleTagProcessor(ElementProcessor parentProcessor,
            TextNode textNode) {
        parentProcessor.addText(textNode.getValue());
    }

    protected void assembleTagProcessor(ElementProcessor parentProcessor,
            DocumentNode node, PageDesc pageDesc, ActionDesc actionDesc) {
        String docType = node.getDocType();
        if (docType != null) {
            parentProcessor.addText(docType);
        }
        for (int i = 0; i < node.getChildSize(); i++) {
            HtmlNode child = node.getChild(i);
            assembleTagProcessor(parentProcessor, child, pageDesc, actionDesc);
        }
    }

    protected void assembleTagProcessor(ElementProcessor parentProcessor,
            ElementNode elementNode, PageDesc pageDesc, ActionDesc actionDesc) {
        for (int i = 0; i < factories.length; ++i) {
            ElementProcessorFactory factory = factories[i];
            if (factory.isMatch(elementNode, pageDesc, actionDesc)) {
                ElementProcessor elementProcessor = factory.createProcessor(
                        elementNode, pageDesc, actionDesc);
                parentProcessor.addElement(elementProcessor);
                if (!factory.isLeaf()) {
                    assembleElementNodeChildren(elementProcessor, elementNode,
                            pageDesc, actionDesc);
                }
                elementProcessor.endElement();
                return;
            }
        }
        assembleElementNodeAsText(parentProcessor, elementNode, pageDesc,
                actionDesc);
    }

    protected void assembleElementNodeAsText(ElementProcessor parentProcessor,
            ElementNode elementNode, PageDesc pageDesc, ActionDesc actionDesc) {
        if (elementNode.getChildSize() == 0) {
            parentProcessor.addText(elementNode.getCompleteTagString());
        } else {
            parentProcessor.addText(elementNode.getStartTagString());
            assembleElementNodeChildren(parentProcessor, elementNode, pageDesc,
                    actionDesc);
            parentProcessor.addText(elementNode.getEndTagString());
        }
    }

    protected void assembleElementNodeChildren(
            ElementProcessor parentProcessor, ElementNode elementNode,
            PageDesc pageDesc, ActionDesc actionDesc) {
        for (int i = 0; i < elementNode.getChildSize(); ++i) {
            assembleTagProcessor(parentProcessor, elementNode.getChild(i),
                    pageDesc, actionDesc);
        }
    }
}