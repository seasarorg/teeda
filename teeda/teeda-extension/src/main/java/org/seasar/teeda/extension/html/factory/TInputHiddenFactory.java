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
import org.seasar.teeda.extension.html.PageDesc;

/**
 * @author higa
 * @author shot
 */
public class TInputHiddenFactory extends AbstractElementProcessorFactory {

    private static final String TAG_NAME = "inputHidden";

    public boolean isMatch(ElementNode elementNode, PageDesc pageDesc,
            ActionDesc actionDesc) {
        if (!JsfConstants.INPUT_ELEM.equalsIgnoreCase(elementNode.getTagName())) {
            return false;
        }
        if (!JsfConstants.HIDDEN_VALUE.equalsIgnoreCase(elementNode
                .getProperty(JsfConstants.TYPE_ATTR))) {
            return false;
        }
        if (pageDesc == null) {
            return false;
        }
        final String id = elementNode.getId();
        if (!id.endsWith(ExtensionConstants.SAVE_SUFFIX)) {
            return false;
        }
        final String items = id.substring(0, id
                .indexOf(ExtensionConstants.SAVE_SUFFIX));
        return (pageDesc != null) && (pageDesc.hasItemsProperty(items));
    }

    protected void customizeProperties(Map properties, ElementNode elementNode,
            PageDesc pageDesc, ActionDesc actionDesc) {
        super
                .customizeProperties(properties, elementNode, pageDesc,
                        actionDesc);
        String id = elementNode.getId();
        String items = id.substring(0, id
                .indexOf(ExtensionConstants.SAVE_SUFFIX));
        properties.put(JsfConstants.VALUE_ATTR, getBindingExpression(pageDesc
                .getPageName(), items));
        properties.put(ExtensionConstants.PAGE_NAME_ATTR, pageDesc
                .getPageName());

    }

    protected String getTagName() {
        return TAG_NAME;
    }

    protected String getUri() {
        return ExtensionConstants.TEEDA_EXTENSION_URI;
    }

}