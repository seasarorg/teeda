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
package org.seasar.teeda.extension.html.factory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.seasar.framework.container.S2Container;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.TextNode;
import org.seasar.teeda.extension.util.TeedaExtensionConfiguration;

/**
 * @author shot
 *
 */
public class OutputTextFactory extends AbstractElementProcessorFactory {

    private static final String TAG_NAME = "outputText";

    private static final Set acceptableElements = new HashSet();
    static {
        acceptableElements.add(JsfConstants.SPAN_ELEM);
        acceptableElements.add(JsfConstants.DIV_ELEM);
        acceptableElements.add(JsfConstants.CAPTION_ELEM);
    }

    private S2Container container;

    public OutputTextFactory() {
    }

    public boolean isMatch(ElementNode elementNode, PageDesc pageDesc,
            ActionDesc actionDesc) {
        final String tagName = elementNode.getTagName();
        if (TeedaExtensionConfiguration.getInstance().outputTextSpanOnly &&
                !tagName.equals(JsfConstants.SPAN_ELEM)) {
            return false;
        } else if (!acceptableElements.contains(tagName.toLowerCase())) {
            return false;
        }
        if (pageDesc == null) {
            return false;
        }
        final String id = elementNode.getId();
        if (id == null) {
            return false;
        }
        if (isLabel(id, elementNode)) {
            return true;
        }
        return pageDesc.hasProperty(id);
    }

    public boolean isLeaf() {
        return true;
    }

    protected void customizeProperties(Map properties, ElementNode elementNode,
            PageDesc pageDesc, ActionDesc actionDesc) {
        super
                .customizeProperties(properties, elementNode, pageDesc,
                        actionDesc);
        properties.put("tagName", elementNode.getTagName());

        if (pageDesc == null) {
            return;
        }
        final String id = elementNode.getId();
        if (pageDesc.hasProperty(id)) {
            properties.put(JsfConstants.VALUE_ATTR, getBindingExpression(
                    pageDesc.getPageName(), id));
        } else {
            final String key = toNormalizeId(id);
            TextNode firstTextNode = elementNode.getFirstTextNode();
            properties.put(JsfConstants.VALUE_ATTR, getLabelExpression(key,
                    pageDesc));
        }
    }

    protected boolean isLabel(final String id, final ElementNode elementNode) {
        final String key = toNormalizeId(id);
        if (!TeedaExtensionConfiguration.getInstance().outputTextLabelUnderAnchorOnly) {
            return key.endsWith("Label");
        }

        final ElementNode parent = elementNode.getParent();
        if (parent == null) {
            return false;
        }
        final String tagName = parent.getTagName();
        if (key.endsWith("Label") &&
                tagName.equalsIgnoreCase(JsfConstants.ANCHOR_ELEM)) {
            return true;
        } else {
            return false;
        }
    }

    protected String toNormalizeId(String id) {
        final int pos = id.lastIndexOf('-');
        if (pos >= 0) {
            return id.substring(0, pos);
        }
        return id;
    }

    public void setContainer(S2Container container) {
        this.container = container.getRoot();
    }

    protected String getTagName() {
        return TAG_NAME;
    }

    protected String getUri() {
        return ExtensionConstants.TEEDA_EXTENSION_URI;
    }
}