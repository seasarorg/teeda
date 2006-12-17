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

import java.util.Map;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.HtmlNode;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.util.LayoutUtil;

/**
 * @author shot
 */
public class SelectOneRadioFactory extends AbstractElementProcessorFactory {

    private static final String TAG_NAME = "selectOneRadio";

    protected String getTagName() {
        return TAG_NAME;
    }

    protected String getUri() {
        return ExtensionConstants.TEEDA_EXTENSION_URI;
    }

    public boolean isMatch(ElementNode elementNode, PageDesc pageDesc,
            ActionDesc actionDesc) {
        if (!JsfConstants.SPAN_ELEM.equalsIgnoreCase(elementNode.getTagName())) {
            return false;
        }
        String id = elementNode.getId();
        if (id == null) {
            return false;
        }
        if (pageDesc == null) {
            return false;
        }
        String items = id + ExtensionConstants.ITEMS_SUFFIX;
        if (!pageDesc.hasItemsProperty(items)) {
            return false;
        }
        int elementNodeCount = 0;
        for (int i = 0; i < elementNode.getChildSize(); i++) {
            HtmlNode child = elementNode.getChild(i);
            if (child instanceof ElementNode) {
                elementNodeCount++;
                ElementNode node = (ElementNode) child;
                String childTagName = node.getTagName();
                if (!JsfConstants.INPUT_ELEM.equalsIgnoreCase(childTagName)
                        || !JsfConstants.RADIO_VALUE.equalsIgnoreCase(node
                                .getProperty(JsfConstants.TYPE_ATTR))) {
                    return false;
                }
                String name = node.getProperty(JsfConstants.NAME_ATTR);
                if (name == null || !name.equals(id)) {
                    return false;
                }
            }
        }
        if (elementNodeCount == 0) {
            return false;
        }
        return true;
    }

    protected void customizeProperties(Map properties, ElementNode elementNode,
            PageDesc pageDesc, ActionDesc actionDesc) {
        super
                .customizeProperties(properties, elementNode, pageDesc,
                        actionDesc);
        if (pageDesc == null) {
            return;
        }
        final String pageName = pageDesc.getPageName();
        final String id = elementNode.getId();
        final String items = id + ExtensionConstants.ITEMS_SUFFIX;
        properties.put(JsfConstants.VALUE_ATTR, getBindingExpression(pageName,
                id));
        properties.put(ExtensionConstants.ITEMS_ATTR, getBindingExpression(
                pageName, items));
        if (properties.containsKey(JsfConstants.STYLE_ATTR)) {
            LayoutUtil.styleToLayout(properties);
        }
    }

    public boolean isLeaf() {
        return true;
    }

}
