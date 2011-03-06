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
package org.seasar.teeda.extension.html.factory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.processor.ForEachElementProcessor;

/**
 * @author higa
 *
 */
public class ForEachFactory extends AbstractElementProcessorFactory {

    private static final Set acceptableElements = new HashSet();
    static {
        acceptableElements.add(JsfConstants.DIV_ELEM);
        acceptableElements.add(JsfConstants.SPAN_ELEM);
        acceptableElements.add(JsfConstants.TABLE_ELEM);
        acceptableElements.add(JsfConstants.TBODY_ELEM);
        acceptableElements.add(JsfConstants.TR_ELEM);
        acceptableElements.add(JsfConstants.UL_ELEM);
        acceptableElements.add(JsfConstants.OL_ELEM);
        acceptableElements.add(JsfConstants.DL_ELEM);
    }

    private static final String TAG_NAME = "forEach";

    public boolean isMatch(ElementNode elementNode, PageDesc pageDesc,
            ActionDesc actionDesc) {
        if (elementNode == null || pageDesc == null) {
            return false;
        }
        final String elementName = elementNode.getTagName();
        if (!acceptableElements.contains(elementName.toLowerCase())) {
            return false;
        }
        String id = elementNode.getId();
        if (id == null) {
            return false;
        }
        return id.endsWith(ExtensionConstants.ITEMS_SUFFIX) &&
                pageDesc.hasItemsProperty(id);
    }

    protected void customizeProperties(Map properties, ElementNode elementNode,
            PageDesc pageDesc, ActionDesc actionDesc) {
        super
                .customizeProperties(properties, elementNode, pageDesc,
                        actionDesc);
        if (pageDesc == null) {
            return;
        }
        String id = elementNode.getId();
        properties.put(ExtensionConstants.PAGE_NAME_ATTR, pageDesc
                .getPageName());
        properties.put(ExtensionConstants.ITEMS_NAME_ATTR, id);
    }

    protected String getTagName() {
        return TAG_NAME;
    }

    protected String getUri() {
        return ExtensionConstants.TEEDA_EXTENSION_URI;
    }

    protected ElementProcessor createProcessor(ElementNode elementNode,
            PageDesc pageDesc, ActionDesc actionDesc, String uri, String tagName) {
        Class tagClass = getTagClass(uri, tagName);
        Map props = createProperties(elementNode, pageDesc, actionDesc);
        ElementProcessor processor = new ForEachElementProcessor(tagClass,
                props, elementNode.getTagName());
        customizeProcessor(processor, elementNode, pageDesc, actionDesc);
        return processor;
    }

}