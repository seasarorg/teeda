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
package org.seasar.teeda.extension.html.factory;

import java.util.HashMap;
import java.util.Map;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.BindingUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.processor.ElementProcessorImpl;

/**
 * @author shot
 *
 */
public class SelectOneListboxFactory extends AbstractElementProcessorFactory {

    private static final String TAG_NAME = "selectOneListbox";

    private static final String TAG_NAME_SELECT_ITEMS = "selectItems";

    public SelectOneListboxFactory() {
    }

    public boolean isMatch(ElementNode elementNode, PageDesc pageDesc,
            ActionDesc actionDesc) {
        if (!JsfConstants.SELECT_ELEM
                .equalsIgnoreCase(elementNode.getTagName())) {
            return false;
        }
        String items = elementNode.getId();
        if (pageDesc != null && pageDesc.hasItemsProperty(items)) {
            return (elementNode.getProperty(JsfConstants.MULTIPLE_ATTR) == null);
        }
        return false;
    }

    protected ElementProcessor createProcessor(ElementNode elementNode,
            PageDesc pageDesc, ActionDesc actionDesc, String uri, String tagName) {
        String items = elementNode.getId();
        Class tagClass = getTagClass(uri, tagName);
        Map props = createProperties(elementNode, pageDesc, actionDesc);
        ElementProcessor parentProcessor = new ElementProcessorImpl(tagClass,
                props);
        if (!pageDesc.hasItemsProperty(items)) {
            return parentProcessor;
        }
        Map childProps = new HashMap();
        Class childTagClass = getTagClass(
                ExtensionConstants.TEEDA_EXTENSION_URI, TAG_NAME_SELECT_ITEMS);
        ElementProcessor childProcessor = new ElementProcessorImpl(
                childTagClass, childProps);
        String pageName = pageDesc.getPageName();
        childProps.put(JsfConstants.VALUE_ATTR, BindingUtil.getExpression(
                pageName, items));
        parentProcessor.addElement(childProcessor);
        return parentProcessor;
    }

    protected void customizeProperties(Map properties, ElementNode elementNode,
            PageDesc pageDesc, ActionDesc actionDesc) {
        super
                .customizeProperties(properties, elementNode, pageDesc,
                        actionDesc);
        String pageName = pageDesc.getPageName();
        String id = elementNode.getId();
        String target = id.substring(0, id.length()
                - ExtensionConstants.ITEMS_SUFFIX.length());
        properties.put(JsfConstants.VALUE_ATTR, getBindingExpression(pageName,
                target));
    }

    protected String getTagName() {
        return TAG_NAME;
    }

    protected String getUri() {
        return JsfConstants.JSF_HTML_URI;
    }

    public boolean isLeaf() {
        return true;
    }
}
