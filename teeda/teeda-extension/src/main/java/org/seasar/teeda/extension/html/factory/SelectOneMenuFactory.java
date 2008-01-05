/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
import org.seasar.teeda.extension.html.PageDesc;

/**
 * @author shot
 *
 */
public class SelectOneMenuFactory extends AbstractElementProcessorFactory {

    private static final String TAG_NAME = "selectOneMenu";

    public SelectOneMenuFactory() {
    }

    public boolean isMatch(ElementNode elementNode, PageDesc pageDesc,
            ActionDesc actionDesc) {
        if (!JsfConstants.SELECT_ELEM
                .equalsIgnoreCase(elementNode.getTagName())) {
            return false;
        }
        if (pageDesc == null) {
            return false;
        }
        String id = elementNode.getId();
        if (id.lastIndexOf(ExtensionConstants.ITEMS_SUFFIX) < 0) {
            id = id + ExtensionConstants.ITEMS_SUFFIX;
        }
        if (pageDesc.hasItemsProperty(id) || pageDesc.hasMapItemsProperty(id)) {
            return (elementNode.getProperty(JsfConstants.MULTIPLE_ATTR) == null);
        }
        return false;
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
        final String itemsName;
        final String labelName;
        if (id.endsWith(ExtensionConstants.ITEMS_SUFFIX)) {
            itemsName = id;
            labelName = id.substring(0, id.length()
                    - ExtensionConstants.ITEMS_SUFFIX.length())
                    + ExtensionConstants.LABEL_SUFFIX;
        } else {
            itemsName = id + ExtensionConstants.ITEMS_SUFFIX;
            labelName = id + ExtensionConstants.LABEL_SUFFIX;
        }
        String target = itemsName.substring(0, itemsName.length()
                - ExtensionConstants.ITEMS_SUFFIX.length());
        properties.put(JsfConstants.VALUE_ATTR, getBindingExpression(pageName,
                target));
        properties.put(ExtensionConstants.ITEMS_ATTR, getBindingExpression(
                pageName, itemsName));

        properties.put(ExtensionConstants.PAGE_NAME_ATTR, pageName);
        properties.put(ExtensionConstants.LABEL_NAME_ATTR, labelName);
    }

    protected String getTagName() {
        return TAG_NAME;
    }

    protected String getUri() {
        return ExtensionConstants.TEEDA_EXTENSION_URI;
    }

    public boolean isLeaf() {
        return true;
    }

}
