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

import org.seasar.framework.convention.NamingConvention;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.TextNode;

/**
 * @author shot
 *
 */
public class OutputTextFactory extends AbstractElementProcessorFactory {

    private static final String TAG_NAME = "outputText";

    private NamingConvention nc;

    public OutputTextFactory() {
    }

    public boolean isMatch(ElementNode elementNode, PageDesc pageDesc,
            ActionDesc actionDesc) {
        if (!JsfConstants.SPAN_ELEM.equalsIgnoreCase(elementNode.getTagName())) {
            return false;
        }
        if (pageDesc == null) {
            return false;
        }
        final String id = elementNode.getId();
        if (id == null) {
            return false;
        }
        if (isLabelSpan(id, elementNode)) {
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
        if (pageDesc == null) {
            return;
        }
        final String id = elementNode.getId();
        final boolean isLabel = isLabelSpan(id, elementNode);
        if (isLabel) {
            TextNode firstTextNode = elementNode.getFirstTextNode();
            properties.put(JsfConstants.VALUE_ATTR, firstTextNode.getValue());
        } else {
            properties.put(JsfConstants.VALUE_ATTR, getBindingExpression(
                    pageDesc.getPageName(), id));
        }
        final ElementNode parent = elementNode.getParent();
        if (parent != null) {
            final String tagName = parent.getTagName();
            if (id.endsWith("Label")
                    && tagName.equalsIgnoreCase(JsfConstants.ANCHOR_ELEM)) {
                LabelFactoryUtil.storeLabelAttributesTo(properties,
                        elementNode, pageDesc, nc);
            }
        }
    }

    protected static boolean isLabelSpan(final String id,
            final ElementNode elementNode) {
        final ElementNode parent = elementNode.getParent();
        if (parent == null) {
            return false;
        }
        final String tagName = parent.getTagName();
        if (id.endsWith("Label")
                && tagName.equalsIgnoreCase(JsfConstants.ANCHOR_ELEM)) {
            return true;
        } else {
            return false;
        }
    }

    public void setNamingConvention(NamingConvention namingConvention) {
        this.nc = namingConvention;
    }

    protected String getTagName() {
        return TAG_NAME;
    }

    protected String getUri() {
        return ExtensionConstants.TEEDA_EXTENSION_URI;
    }
}